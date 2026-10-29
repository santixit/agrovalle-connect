from pathlib import Path
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import letter, landscape
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, PageBreak, Image, Table, TableStyle, KeepTogether
from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader
from pypdf import PdfReader, PdfWriter
from xml.sax.saxutils import escape

root = Path(r'C:\Users\Administrator\Desktop\agrovalle-connect')
out = root / 'docs' / 'entrega-segundo-corte' / 'Grupo01_Entrega_Segundo_Corte_AgroValle.pdf'
temp_dir = root / 'tmp' / 'pdfs'
temp_dir.mkdir(parents=True, exist_ok=True)
base_pdf = temp_dir / 'segundo-corte-base.pdf'
appendix_pdf = temp_dir / 'segundo-corte-diseno.pdf'
navy = colors.HexColor('#16324F')
teal = colors.HexColor('#2C7A7B')
light = colors.HexColor('#EAF1F5')
muted = colors.HexColor('#536273')
styles = getSampleStyleSheet()
styles.add(ParagraphStyle(name='CoverInstitution', parent=styles['Normal'], fontName='Helvetica-Bold', fontSize=13, leading=18, textColor=colors.black, alignment=TA_CENTER, spaceAfter=7))
styles.add(ParagraphStyle(name='CoverCourse', parent=styles['Normal'], fontName='Helvetica', fontSize=10.5, leading=16, textColor=muted, alignment=TA_CENTER))
styles.add(ParagraphStyle(name='CoverTitle', parent=styles['Title'], fontName='Helvetica-Bold', fontSize=25, leading=31, textColor=colors.black, alignment=TA_CENTER, spaceAfter=10))
styles.add(ParagraphStyle(name='CoverProject', parent=styles['Normal'], fontName='Helvetica-Bold', fontSize=17, leading=23, textColor=colors.black, alignment=TA_CENTER))
styles.add(ParagraphStyle(name='H1x', parent=styles['Heading1'], fontName='Helvetica-Bold', fontSize=17, leading=22, textColor=colors.black, spaceBefore=1, spaceAfter=11))
styles.add(ParagraphStyle(name='H2x', parent=styles['Heading2'], fontName='Helvetica-Bold', fontSize=11.5, leading=15, textColor=colors.black, spaceBefore=7, spaceAfter=4))
styles.add(ParagraphStyle(name='Bodyx', parent=styles['BodyText'], fontName='Helvetica', fontSize=9.2, leading=13.2, textColor=colors.HexColor('#1F2933'), spaceAfter=5))
styles.add(ParagraphStyle(name='Smallx', parent=styles['BodyText'], fontName='Helvetica', fontSize=8, leading=10.5, textColor=muted, spaceAfter=3))
styles.add(ParagraphStyle(name='Tablex', parent=styles['BodyText'], fontName='Helvetica', fontSize=8.4, leading=11, textColor=colors.HexColor('#1F2933')))
styles.add(ParagraphStyle(name='Centerx', parent=styles['BodyText'], fontName='Helvetica', fontSize=10, leading=15, alignment=TA_CENTER, textColor=colors.HexColor('#1F2933')))

def P(text, style='Bodyx'):
    return Paragraph(text, styles[style])

def bullet(text):
    return Paragraph('&bull;&nbsp;&nbsp;' + text, styles['Bodyx'])

def link(label, url):
    return f'<link href="{url}" color="#176B87"><u>{escape(label)}</u></link>'

def footer(canvas, doc):
    return

doc = SimpleDocTemplate(str(base_pdf), pagesize=letter, rightMargin=0.72*inch, leftMargin=0.72*inch, topMargin=0.66*inch, bottomMargin=0.76*inch, title='Grupo 1 - Entrega Segundo Corte - AgroValle Connect', author='Grupo 1 - 6303B', subject='Resumen ejecutivo y evidencia CI/JaCoCo del Sprint 2')
story = []
# Cover
story += [Spacer(1, 0.48*inch), P('INSTITUCIÓN UNIVERSITARIA ANTONIO JOSÉ CAMACHO', 'CoverInstitution'), P('Facultad de Ingeniería<br/>Programa de Ingeniería de Sistemas<br/>Ingeniería de Software II | Período 2-2026', 'CoverCourse'), Spacer(1, 0.65*inch)]
story += [P('SEGUNDO CORTE', 'CoverTitle'), P('Entrega del proyecto integrador', 'CoverCourse'), Spacer(1, 0.17*inch), P('AgroValle Connect', 'CoverProject'), P('Plataforma agrícola del Valle del Cauca', 'CoverCourse'), Spacer(1, 0.62*inch)]
coverdata = [[P('<b>Grupo / célula</b>', 'Tablex'), P('Grupo 1 — 6303B', 'Tablex')], [P('<b>Integrantes</b>', 'Tablex'), P('Danny Alexander Gómez<br/>Michelle Guerrero Arboleda<br/>Starlin Gómez Asprilla<br/>Raúl Santiago Carrillo Hernández', 'Tablex')], [P('<b>Sprint</b>', 'Tablex'), P('Sprint 2 | periodo de referencia: 19–29 de octubre de 2026', 'Tablex')]]
t = Table(coverdata, colWidths=[1.35*inch, 4.8*inch], hAlign='CENTER')
t.setStyle(TableStyle([('BACKGROUND',(0,0),(0,-1),light),('BOX',(0,0),(-1,-1),0.7,colors.HexColor('#C7D2DC')),('INNERGRID',(0,0),(-1,-1),0.4,colors.HexColor('#D6DEE5')),('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),10),('RIGHTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),9),('BOTTOMPADDING',(0,0),(-1,-1),9)]))
story += [t, Spacer(1, 0.5*inch), P('Resumen ejecutivo y evidencia técnica', 'Centerx'), Spacer(1, 0.15*inch), P('Estado de evidencia: CI #86 (77b5141) y JaCoCo #83 (6c19aae) son ejecuciones verdes de main. El código y las pruebas no cambiaron entre ambos commits. Este PDF incorpora las pantallas y secuencias entregadas por el equipo; sus fuentes están en el repositorio.', 'Smallx'), PageBreak()]
# Executive summary page 2
story += [P('Resumen ejecutivo del Sprint 2', 'H1x'), P('<b>Periodo de referencia:</b> 19–29 de octubre de 2026. El inicio se reconstruyó desde el primer registro local fechado y el cierre fue indicado por el Product Owner; no son fechas de una ceremonia formal.', 'Bodyx'), P('<b>Objetivo reconstruido:</b> alinear el incremento con los wireframes y mockups corregidos, y dejar documentados los flujos principales enviados por el equipo, la estrategia demostrativa de fletes, el modelo de datos, CI y Kanban, distinguiendo lo integrado de lo que falta validar.', 'Bodyx'), P('Resultados y trabajo seleccionado', 'H2x')]
story += [bullet(f'<b>Interfaz y pruebas de navegador.</b> Se integraron los mockups corregidos del marketplace y se alineó el flujo E2E con los campos actuales. {link("PR #52", "https://github.com/santixit/agrovalle-connect/pull/52")}.'), bullet('<b>Diseño recibido.</b> El apéndice incluye los 7 wireframes, 7 mockups y 6 diagramas de secuencia que entregó el equipo.'), bullet(f'<b>Estrategia de fletes.</b> Se incorporó Strategy para tarifas demostrativas por municipio, seleccionadas por peso/destino; los valores no son tarifas comerciales validadas. {link("PR #51", "https://github.com/santixit/agrovalle-connect/pull/51")}.'), bullet('<b>UML técnico complementario.</b> Los exports de componentes, despliegue y secuencias PlantUML son diagramas técnicos derivados de sus fuentes; son distintos de las secuencias visuales que entregó el equipo.'), bullet(f'<b>Gestión del trabajo.</b> El tablero muestra issues reales, sin borradores: 3/3 en In Progress, 2/2 en Code Review / Testing y 14 en Done. {link("GitHub Project", "https://github.com/users/santixit/projects/3/views/1")}.'), P('Calidad técnica observada', 'H2x'), bullet(f'La ejecución {link("GitHub Actions #86", "https://github.com/santixit/agrovalle-connect/actions/runs/40824085047")} terminó en verde para `main`, commit `77b5141` (Build, tests, Checkstyle, JaCoCo y despliegue a staging). El reporte JaCoCo #83 para `6c19aae` informa 89% de instrucciones, 63% de ramas y 91% de líneas; el código y las pruebas son iguales en ambos commits.'), bullet('La documentación de pruebas conserva evidencia del ciclo TDD Red–Green–Refactor y de pruebas unitarias/integración; la evidencia detallada de TDD corresponde a su propio cambio y no se atribuye automáticamente a cada archivo del incremento.'), PageBreak()]
# Executive summary page 3
story += [P('Aprendizajes, límites y cierre', 'H1x'), P('Patrones y refactorización', 'H2x'), P('La evidencia de código mapea SRP a la separación entre catálogo y gestión de productos; OCP y LSP a estrategias de flete intercambiables; ISP al contrato reducido de cálculo de flete; y DIP a la abstracción de cálculo de descuentos inyectada en reservas. Es un mapeo acotado a ejemplos concretos, no una afirmación de que todo el sistema cumpla cada principio de forma uniforme. Strategy se usa en fletes/descuentos y Observer en eventos de notificación.', 'Bodyx'), P('Aprendizajes observables', 'H2x'), bullet('La interfaz debe distinguir lo que solo es visual de lo que la API realmente persiste o expone; por ejemplo, la vista previa de fotografía sigue siendo local y algunas consultas de historial no tienen soporte completo.'), bullet('Los snapshots Kanban permiten reportar conteos y WIP en fechas observadas, pero no calcular de forma fiable throughput, lead time, cycle time, CFD o Ley de Little sin eventos fechados completos.'), bullet('La revisión y la retrospectiva se registran como documentos asíncronos preparados por el Product Owner. El equipo confirmó que el resumen representa el trabajo y que no tiene comentarios adicionales; no se inventa una ceremonia ni consenso sobre cada propuesta.'), P('Estado del incremento', 'H2x'), bullet('<b>Modelo de datos.</b> El issue #46 registra conservar el esquema actual y dejar los catálogos normalizados como mejora futura. El snapshot SQL resume Flyway V1–V6, que sigue siendo la fuente ejecutable. La revisión describe las dependencias que el DDL sí expresa y deja las reglas de dominio no codificadas como alcance por validar; no se propone una migración sin una decisión de dominio.'), bullet('<b>Calidad técnica.</b> Actions #86 terminó en verde en `main` para `77b5141`. El reporte JaCoCo #83 de `6c19aae` muestra 89% de instrucciones, 63% de ramas y 91% de líneas; entre esos commits cambiaron documentos y workflow, no el código ni las pruebas. Ambas evidencias están incluidas en estas páginas.'), bullet('<b>Paquete documental.</b> Esta copia reúne resumen, evidencias CI/JaCoCo y las 20 láminas de diseño recibidas. Las fuentes PNG/JPG también permanecen en el repositorio.'), P('Enlaces: ' + link('repositorio main', 'https://github.com/santixit/agrovalle-connect/tree/main') + ' | ' + link('GitHub Project', 'https://github.com/users/santixit/projects/3/views/1') + ' | ' + link('PR #53 (roles del equipo)', 'https://github.com/santixit/agrovalle-connect/pull/53'), 'Smallx'), PageBreak()]
# CI evidence page
story += [P('Evidencia de Integración Continua', 'H1x'), P(f'GitHub Actions #86 — estado <b>Success</b> — commit <b>77b5141</b> en `main`. La ejecución incluye Build, tests, Checkstyle, JaCoCo y despliegue a staging. {link("Abrir ejecución", "https://github.com/santixit/agrovalle-connect/actions/runs/40824085047")}.', 'Bodyx'), Spacer(1, 0.15*inch)]
ci = Image(str(root/'docs/evidencias/ci-run-main-77b5141.png'))
ci._restrictSize(7.05*inch, 4.35*inch)
story += [ci, Spacer(1, 0.15*inch), P('La captura muestra el título neutral de la ejecución #86, su estado Success y el commit de `main`. La cobertura se presenta en la página siguiente con el reporte JaCoCo #83; el código y las pruebas no cambiaron entre ambas ejecuciones.', 'Smallx'), PageBreak()]
# JaCoCo page
story += [P('Evidencia de cobertura JaCoCo', 'H1x'), P(f'Reporte HTML descargado del artefacto de GitHub Actions #83 para el commit <b>6c19aae</b>. El artefacto original está en {link("GitHub Actions", "https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141")}.', 'Bodyx'), Spacer(1, 0.12*inch)]
j = Image(str(root/'docs/evidencias/jacoco-main-6c19aae.png'))
j._restrictSize(7.05*inch, 2.3*inch)
story += [j, Spacer(1, 0.12*inch)]
coverage = [[P('<b>Métrica</b>', 'Tablex'),P('<b>Resultado del HTML</b>', 'Tablex'),P('<b>Umbral del proyecto</b>', 'Tablex'),P('<b>Estado</b>', 'Tablex')], [P('Instrucciones', 'Tablex'),P('89%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')], [P('Ramas', 'Tablex'),P('63%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')], [P('Líneas', 'Tablex'),P('91%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')]]
t=Table(coverage,colWidths=[1.8*inch,1.5*inch,1.55*inch,1.3*inch],hAlign='LEFT')
t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),light),('BOX',(0,0),(-1,-1),0.6,colors.HexColor('#C7D2DC')),('INNERGRID',(0,0),(-1,-1),0.35,colors.HexColor('#D6DEE5')),('VALIGN',(0,0),(-1,-1),'MIDDLE'),('LEFTPADDING',(0,0),(-1,-1),7),('RIGHTPADDING',(0,0),(-1,-1),7),('TOPPADDING',(0,0),(-1,-1),7),('BOTTOMPADDING',(0,0),(-1,-1),7),('TEXTCOLOR',(3,1),(3,-1),teal)]))
story += [t, Spacer(1, 0.15*inch), P('Las cifras cumplen los umbrales configurados para el commit de `main` indicado. Desde `6c19aae` hasta `77b5141` no cambiaron el código ni las pruebas; los documentos y exports del paquete ya están publicados en `main`.', 'Smallx')]

doc.build(story, onFirstPage=footer, onLaterPages=footer)

design_pages = [
    ('Wireframe - Inicio', 'mockups/ui-wireframe-inicio.png'),
    ('Wireframe - Catálogo', 'mockups/ui-wireframe-catalogo.png'),
    ('Wireframe - Detalles', 'mockups/ui-wireframe-detalle.png'),
    ('Wireframe - Registro', 'mockups/ui-wireframe-registro.png'),
    ('Wireframe - Publicar', 'mockups/ui-wireframe-publicar.png'),
    ('Wireframe - Panel agricultor', 'mockups/ui-wireframe-panel-agricultor.png'),
    ('Wireframe - Panel administrador', 'mockups/ui-wireframe-panel-administrador.png'),
    ('Mockup - Inicio', 'mockups/ui-mockup-inicio.png'),
    ('Mockup - Catálogo', 'mockups/ui-mockup-catalogo.png'),
    ('Mockup - Detalles', 'mockups/ui-mockup-detalle.png'),
    ('Mockup - Registro', 'mockups/ui-mockup-registro.png'),
    ('Mockup - Publicar', 'mockups/ui-mockup-publicar.png'),
    ('Mockup - Panel agricultor', 'mockups/ui-mockup-panel-agricultor.png'),
    ('Mockup - Panel administrador', 'mockups/ui-mockup-panel-administrador.png'),
    ('Secuencia - Registro de agricultor', 'secuencias/secuencia-registro-agricultor.jpg'),
    ('Secuencia - Inicio de sesión y publicación', 'secuencias/secuencia-iniciar-sesion-y-publicar.jpg'),
    ('Secuencia - Reserva de comprador', 'secuencias/secuencia-reserva-comprador.jpg'),
    ('Secuencia - Contacto al agricultor', 'secuencias/secuencia-contacto-agricultor.jpg'),
    ('Secuencia - Programación de despacho', 'secuencias/secuencia-programar-despacho.jpg'),
    ('Secuencia - Registro de producto (referencia anterior)', 'secuencias/secuencia-registro-producto-referencia.jpg'),
]
page_width, page_height = landscape(letter)
pdf_canvas = canvas.Canvas(str(appendix_pdf), pagesize=(page_width, page_height), pageCompression=1)
pdf_canvas.setTitle('Wireframes, mockups y secuencias recibidos - Grupo 1')
pdf_canvas.setFillColor(colors.black)
pdf_canvas.setFont('Helvetica-Bold', 22)
pdf_canvas.drawString(0.7*inch, page_height - 1.05*inch, 'Diseño y arquitectura')
pdf_canvas.setFont('Helvetica', 11)
pdf_canvas.setFillColor(colors.HexColor('#1F2933'))
overview = [
    'Esta sección organiza los artefactos gráficos de modelado y prototipado que entregó el equipo,',
    'según la guía del Sprint de Diseño y Arquitectura de AgroValle Connect.',
    '',
    'Prototipos de interfaz',
    '• 7 wireframes de baja fidelidad y 7 mockups de alta fidelidad: inicio, catálogo, detalle,',
    '  registro, publicación, panel del agricultor y panel del administrador.',
    '',
    'Diagramas de secuencia recibidos',
    '• Registro de agricultor; inicio de sesión y publicación; reserva; contacto; despacho;',
    '  y una referencia anterior del registro de producto.',
    '',
    'Las imagenes se reproducen en las paginas siguientes y sus fuentes se conservan en',
    'docs/diseno/mockups/ y docs/diseno/secuencias/. Los UML generados desde PlantUML',
    'son artefactos técnicos complementarios, separados de estas láminas recibidas.',
]
y = page_height - 1.65*inch
for line in overview:
    if line:
        pdf_canvas.drawString(0.7*inch, y, line)
    y -= 0.29*inch
pdf_canvas.showPage()
for title, relative_path in design_pages:
    image_path = root / 'docs' / 'diseno' / relative_path
    pdf_canvas.setFont('Helvetica-Bold', 15)
    pdf_canvas.setFillColor(colors.black)
    pdf_canvas.drawString(0.5*inch, page_height - 0.45*inch, title)
    pdf_canvas.setFont('Helvetica', 8)
    pdf_canvas.setFillColor(muted)
    pdf_canvas.drawString(0.5*inch, page_height - 0.62*inch, 'Lámina fuente recibida del equipo')
    image = ImageReader(str(image_path))
    image_width, image_height = image.getSize()
    max_width = page_width - inch
    max_height = page_height - 1.05*inch
    scale = min(max_width / image_width, max_height / image_height)
    drawn_width, drawn_height = image_width * scale, image_height * scale
    x = (page_width - drawn_width) / 2
    y = (page_height - 0.78*inch - drawn_height) / 2
    pdf_canvas.drawImage(image, x, y, width=drawn_width, height=drawn_height, preserveAspectRatio=True, mask='auto')
    pdf_canvas.showPage()
pdf_canvas.save()

writer = PdfWriter()
base_reader = PdfReader(str(base_pdf))
for page in base_reader.pages[:3]:
    writer.add_page(page)
writer.append_pages_from_reader(PdfReader(str(appendix_pdf)))
for page in base_reader.pages[3:]:
    writer.add_page(page)
writer.add_metadata({'/Title': 'Grupo 1 - Entrega Segundo Corte - AgroValle Connect', '/Author': 'Grupo 1 - 6303B'})
with out.open('wb') as stream:
    writer.write(stream)
base_pdf.unlink()
appendix_pdf.unlink()
print(out)
print(f'bytes={out.stat().st_size}')
