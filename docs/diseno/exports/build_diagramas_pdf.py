"""Build the four-page UML PDF from its checked-in PNG exports."""

from pathlib import Path

from PIL import Image
from reportlab.pdfgen import canvas


directory = Path(__file__).resolve().parent
images = [
    directory / "uml-componentes.png",
    directory / "uml-despliegue.png",
    directory / "uml-secuencia-registro.png",
    directory / "uml-secuencia-publicacion.png",
]
output = directory / "diagramas-uml.pdf"

for image_path in images:
    if not image_path.is_file():
        raise FileNotFoundError(f"Missing UML export: {image_path}")

pdf = canvas.Canvas(str(output))
for image_path in images:
    with Image.open(image_path) as image:
        width, height = image.size
    pdf.setPageSize((width, height))
    pdf.drawImage(str(image_path), 0, 0, width=width, height=height)
    pdf.showPage()

pdf.setTitle("AgroValle Connect - Diagramas UML")
pdf.setAuthor("Grupo 1 - 6303B")
pdf.save()
print(output)
