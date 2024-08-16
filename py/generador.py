from flask_cors import CORS
from googletrans import Translator
from flask import Flask, request, jsonify
import fitz  # PyMuPDF
import spacy

app = Flask(__name__)
CORS(app)
nlp_es = spacy.load("es_core_news_sm")

def extraer_actividades(texto):
    doc = nlp_es(texto)
    actividades = []

    for sent in doc.sents:
        actividad = None
        for token in sent:
            if token.pos_ in ("VERB", "NOUN", "ADV", "ADJ"):  
                actividad = token.lemma_
                # Reglas adicionales para identificar actividades
                if token.dep_ in ("ROOT", "acl", "advcl", "relcl") and actividad:
                    actividad_completa = " ".join([tok.text for tok in sent])
                    actividades.append(actividad_completa)
                    break

    return actividades

def leer_pdf(file):
    pdf_texto = ""
    with fitz.open(stream=file.read(), filetype="pdf") as doc:
        for pagina in doc:
            pdf_texto += pagina.get_text()
    return pdf_texto

@app.route('/extraer-actividades', methods=['POST'])
def procesar_pdf():
    if 'file' not in request.files:
        return jsonify({"error": "No se ha enviado ningún archivo."}), 400
    
    archivo = request.files['file']
    
    if archivo.filename == '':
        return jsonify({"error": "No se seleccionó ningún archivo."}), 400
    
    texto = leer_pdf(archivo)
    actividades = extraer_actividades(texto)
    return jsonify(actividades)

@app.route('/extraer-actividades-texto', methods=['POST'])
def extraer_actividades_texto():
    data = request.json
    if 'texto' not in data:
        return jsonify({"error": "No se ha proporcionado texto."}), 400

    texto = data['texto']
    idioma_destino = data.get('idioma', 'es')

    # Extraer actividades del texto en el idioma original
    actividades = extraer_actividades(texto)
    
    # Traducir actividades si es necesario
    if idioma_destino != 'es':
        translator = Translator()
        actividades = [translator.translate(actividad, dest=idioma_destino).text for actividad in actividades]

    return jsonify({"actividades": actividades}), 200

@app.route('/hola', methods=['GET'])
def saludo():
    return jsonify({"mensaje": "¡Hola, mundo!"})

if __name__ == '__main__':
    app.run(port=5001)
