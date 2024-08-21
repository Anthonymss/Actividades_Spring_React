class Pescado:
    def __init__(self,peso, especie,longitud):
        self.peso =peso
        self.especie = especie
        self.longitud = longitud
    def __str__(self):
        return f"Especie: {self.especie}, Peso: {self.peso} kg, Longitud: {self.longitud} m"
    def nadar(self):
        return f"El {self.especie}, esta nadando ahora"

class Jurel(Pescado):
    def __init__(self,peso, longitud, Tipoescamas):
        super().__init__(peso, "Jurel", longitud)
        self.Tipoescamas=Tipoescamas
    def nadar(self):
        return f" El Jurel con tipo {self.Tipoescamas} nada en cardumen"

jurel1=Jurel(2.3,30,"Peligrosas")
print(jurel1.nadar())
