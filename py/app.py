from flask_cors import CORS
from googletrans import Translator
from flask import Flask, request, jsonify
import fitz  # PyMuPDF
import spacy

app = Flask(__name__)
CORS(app)
nlp_es = spacy.load("es_core_news_sm")

def extraer_acciones(texto):
    doc = nlp_es(texto)
    acciones = []

    for sent in doc.sents:
        for token in sent:
            if token.pos_ in ['VERB', 'AUX']:
                accion = " ".join([child.text for child in token.subtree])
                if accion not in acciones:
                    acciones.append(accion)

    return acciones

def leer_pdf(file):
    pdf_texto = ""
    with fitz.open(stream=file.read(), filetype="pdf") as doc:
        for pagina in doc:
            pdf_texto += pagina.get_text()
    return pdf_texto

@app.route('/extraer-acciones', methods=['POST'])
def procesar_pdf():
    if 'file' not in request.files:
        return jsonify({"error": "No se ha enviado ningún archivo."}), 400

    archivo = request.files['file']

    if archivo.filename == '':
        return jsonify({"error": "No se seleccionó ningún archivo."}), 400

    texto = leer_pdf(archivo)
    acciones = extraer_acciones(texto)
    return jsonify(acciones)

@app.route('/extraer-acciones-texto', methods=['POST'])
def extraer_acciones_texto():
    data = request.json
    if 'texto' not in data:
        return jsonify({"error": "No se ha proporcionado texto."}), 400

    texto = data['texto']
    idioma_destino = data.get('idioma', 'es')

    # Extraer acciones del texto en el idioma original
    acciones = extraer_acciones(texto)

    # Traducir acciones si es necesario
    if idioma_destino == 'en':
        translator = Translator()
        acciones = [translator.translate(accion, dest='en').text for accion in acciones]

    return jsonify({"acciones": acciones}), 200

@app.route('/hola', methods=['GET'])
def saludo():
    return jsonify({"mensaje": "¡Hola, mundo!"})

if __name__ == '__main__':
    app.run(port=5001)
