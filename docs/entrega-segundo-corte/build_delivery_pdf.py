from pathlib import Path
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, PageBreak, Image, Table, TableStyle, KeepTogether, Preformatted
from reportlab.pdfgen import canvas
from pypdf import PdfReader, PdfWriter
from xml.sax.saxutils import escape

root = Path(__file__).resolve().parents[2]
out = root / 'docs' / 'entrega-segundo-corte' / 'Grupo01_Entrega_Segundo_Corte_AgroValle.pdf'
temp_dir = root / 'tmp' / 'pdfs'
temp_dir.mkdir(parents=True, exist_ok=True)
base_pdf = temp_dir / 'segundo-corte-base.pdf'
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
styles.add(ParagraphStyle(name='LogBlock', parent=styles['Code'], fontName='Courier', fontSize=8, leading=11, textColor=colors.HexColor('#1F2933'), backColor=light, borderColor=colors.HexColor('#C7D2DC'), borderWidth=0.5, borderPadding=8, spaceBefore=4, spaceAfter=8))

ci_run_url = 'https://github.com/santixit/agrovalle-connect/actions/runs/40894179070'
jacoco_artifact_url = ci_run_url + '/artifacts/12906676338'
jacoco_log_url = ci_run_url + '/job/123519248186#step:5:169'
repository_url = 'https://github.com/santixit/agrovalle-connect/tree/main'
project_url = 'https://github.com/users/santixit/projects/3/views/1'

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
coverdata = [[P('<b>Grupo / célula</b>', 'Tablex'), P('Grupo 1 - 6303B', 'Tablex')], [P('<b>Integrantes</b>', 'Tablex'), P('Danny Alexander Gómez<br/>Michelle Guerrero Arboleda<br/>Starlin Gómez Asprilla<br/>Raúl Santiago Carrillo Hernández', 'Tablex')], [P('<b>Sprint</b>', 'Tablex'), P('Sprint 2 | periodo de referencia: 19-29 de octubre de 2026', 'Tablex')]]
t = Table(coverdata, colWidths=[1.35*inch, 4.8*inch], hAlign='CENTER')
t.setStyle(TableStyle([('BACKGROUND',(0,0),(0,-1),light),('BOX',(0,0),(-1,-1),0.7,colors.HexColor('#C7D2DC')),('INNERGRID',(0,0),(-1,-1),0.4,colors.HexColor('#D6DEE5')),('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),10),('RIGHTPADDING',(0,0),(-1,-1),10),('TOPPADDING',(0,0),(-1,-1),9),('BOTTOMPADDING',(0,0),(-1,-1),9)]))
story += [t, Spacer(1, 0.5*inch), P('Resumen ejecutivo y evidencia técnica', 'Centerx'), Spacer(1, 0.15*inch), P(f'Estado de evidencia: GitHub Actions #103 terminó Success en main, commit a7e2097. El mismo run publicó el reporte JaCoCo; esta entrega usa sus resultados y enlaza los logs y el artefacto correspondientes.', 'Smallx'), PageBreak()]
# Executive summary page 2
story += [P('Resumen ejecutivo del Sprint 2', 'H1x'), P('<b>Periodo de referencia:</b> 19-29 de octubre de 2026. El inicio se reconstruyó desde el primer registro local fechado y el cierre fue indicado por el Product Owner; no son fechas de una ceremonia formal.', 'Bodyx'), P('<b>Objetivo reconstruido:</b> consolidar el incremento de AgroValle Connect, documentar el trabajo y sus límites de integración, y dejar evidencia pública de calidad y gestión del Sprint 2.', 'Bodyx'), P('Resultados integrados', 'H2x')]
story += [bullet(f'<b>Interfaz y pruebas de navegador.</b> Se alineó el flujo E2E del marketplace con los campos actuales. {link("PR #52", "https://github.com/santixit/agrovalle-connect/pull/52")}.'), bullet(f'<b>Estrategia de fletes.</b> Se incorporó Strategy para tarifas demostrativas por municipio, seleccionadas por peso/destino; los valores no son tarifas comerciales validadas. {link("PR #51", "https://github.com/santixit/agrovalle-connect/pull/51")}.'), bullet(f'<b>Gestión del trabajo.</b> El tablero muestra issues reales, sin borradores: 3/3 en In Progress, 2/2 en Code Review / Testing y 14 en Done. Límites WIP: 3 y 2 respectivamente. {link("GitHub Project", project_url)}.'), P('Calidad técnica observada', 'H2x'), bullet(f'La última ejecución de main incluida en esta entrega, {link("GitHub Actions #103", ci_run_url)}, terminó en verde para el commit a7e2097: pasaron Build, tests, Checkstyle, JaCoCo, E2E y despliegue a staging. El run publicó el {link("artefacto JaCoCo #103", jacoco_artifact_url)}.'), bullet('El reporte HTML del mismo run #103 muestra 88% de instrucciones, 63% de ramas y 90% de líneas. La consola de Maven confirmó que todos los controles de cobertura se cumplieron.'), bullet('La documentación de pruebas conserva evidencia del ciclo TDD Red-Green-Refactor y de pruebas unitarias/integración; la evidencia detallada de TDD corresponde a su propio cambio y no se atribuye automáticamente a cada archivo del incremento.'), PageBreak()]
# Executive summary page 3
story += [P('Aprendizajes, límites y cierre', 'H1x'), P('Patrones y refactorización', 'H2x'), P('La evidencia de código mapea SRP a la separación entre catálogo y gestión de productos; OCP y LSP a estrategias de flete intercambiables; ISP al contrato reducido de cálculo de flete; y DIP a la abstracción de cálculo de descuentos inyectada en reservas. Es un mapeo acotado a ejemplos concretos, no una afirmación de que todo el sistema cumpla cada principio de forma uniforme. Strategy se usa en fletes/descuentos y Observer en eventos de notificación.', 'Bodyx'), P('Aprendizajes observables', 'H2x'), bullet('La interfaz debe distinguir lo que solo es visual de lo que la API realmente persiste o expone; por ejemplo, la vista previa de fotografía sigue siendo local y algunas consultas de historial no tienen soporte completo.'), bullet('Los snapshots Kanban permiten reportar conteos y WIP en fechas observadas, pero no calcular de forma fiable throughput, lead time, cycle time, CFD o Ley de Little sin eventos fechados completos.'), bullet('La revisión y la retrospectiva se registran como documentos asíncronos preparados por el Product Owner. El equipo confirmó que el resumen representa el trabajo y que no tiene comentarios adicionales; no se inventa una ceremonia ni consenso sobre cada propuesta.'), P('Estado del incremento', 'H2x'), bullet('<b>Demostración complementaria.</b> El 29 de octubre se consultaron en staging la página, el health check y el catálogo; las tres lecturas respondieron HTTP 200. Es una comprobación puntual, de solo lectura y posterior al Sprint. El registro incluye un guion; repetirlo en vivo.'), bullet('<b>Modelo de datos.</b> Flyway V7 y su código de parcelas, ubicaciones e historial de transferencias están integrados en main. El run #103 ejecutó Maven con Java 17, pruebas, Checkstyle y JaCoCo; el job terminó correctamente. La integración conserva el municipio derivado de la finca y el agricultor destinatario del contacto al momento de crearlo.'), bullet(f'<b>Calidad técnica.</b> Actions #103 terminó en verde en el commit a7e2097 de main; {link("consulta el run y sus jobs", ci_run_url)}. El reporte del mismo run muestra 88% de instrucciones, 63% de ramas y 90% de líneas.'), bullet('<b>Historias con funcionalidad implementada.</b> HU-04, filtrar ofertas por municipio y categoría; y HU-06, registrar una finca y asociarla al agricultor autenticado. El repositorio acredita la funcionalidad; no se afirma una selección formal para Sprint 2 que no esté documentada.'), bullet('<b>Entrega.</b> Este PDF reúne el resumen ejecutivo, enlaces públicos y evidencia de CI/JaCoCo del commit a7e2097.'), P('Enlaces públicos: ' + link('Repositorio main', repository_url) + ' | ' + link('Tablero GitHub Projects', project_url), 'Smallx'), PageBreak()]
# CI evidence page
story += [P('Evidencia de Integración Continua', 'H1x'), P(f'GitHub Actions #103 - estado <b>Success</b> - commit <b>a7e2097</b> en main, ejecutado el 29 de octubre de 2026. Build, tests, Checkstyle, JaCoCo, E2E y despliegue a staging pasaron. {link("Abrir ejecución #103", ci_run_url)} | {link("Artefacto JaCoCo #103", jacoco_artifact_url)}.', 'Bodyx'), Spacer(1, 0.15*inch)]
ci = Image(str(root/'docs/evidencias/ci-run-main-a7e2097.png'))
ci._restrictSize(7.05*inch, 4.35*inch)
story += [ci, Spacer(1, 0.15*inch), P('Captura del run #103: estado Success, commit a7e2097 en main y jobs de calidad y despliegue completados. El enlace abre la ejecución que respalda esta evidencia.', 'Smallx'), PageBreak()]
# JaCoCo page
story += [P('Evidencia de cobertura JaCoCo', 'H1x'), P(f'Reporte HTML publicado por Actions #103 para el commit <b>a7e2097</b>: 88% de instrucciones, 63% de ramas y 90% de líneas. Los dos criterios solicitados (instrucciones y ramas) superan el 60%. {link("Abrir artefacto JaCoCo #103", jacoco_artifact_url)}.', 'Bodyx'), Spacer(1, 0.12*inch)]
coverage = [[P('<b>Métrica</b>', 'Tablex'),P('<b>Resultado HTML #103</b>', 'Tablex'),P('<b>Umbral del proyecto</b>', 'Tablex'),P('<b>Estado</b>', 'Tablex')], [P('Instrucciones', 'Tablex'),P('88%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')], [P('Ramas', 'Tablex'),P('63%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')], [P('Líneas', 'Tablex'),P('90%', 'Tablex'),P('≥ 60%', 'Tablex'),P('Cumple', 'Tablex')]]
t=Table(coverage,colWidths=[1.8*inch,1.5*inch,1.55*inch,1.3*inch],hAlign='LEFT')
t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),light),('BOX',(0,0),(-1,-1),0.6,colors.HexColor('#C7D2DC')),('INNERGRID',(0,0),(-1,-1),0.35,colors.HexColor('#D6DEE5')),('VALIGN',(0,0),(-1,-1),'MIDDLE'),('LEFTPADDING',(0,0),(-1,-1),7),('RIGHTPADDING',(0,0),(-1,-1),7),('TOPPADDING',(0,0),(-1,-1),7),('BOTTOMPADDING',(0,0),(-1,-1),7),('TEXTCOLOR',(3,1),(3,-1),teal)]))
story += [t, Spacer(1, 0.15*inch), P('Extracto del log del paso de verificación JaCoCo en GitHub Actions #103', 'H2x'), Preformatted('[INFO] --- jacoco:0.8.15:check (enforce-coverage) @ agrovalle-connect ---\n[INFO] Analyzed bundle \'agrovalle-connect\' with 116 classes\n[INFO] All coverage checks have been met.\n[INFO] BUILD SUCCESS', styles['LogBlock']), P(f'Fuente verificable: {link("log de Maven del run #103", jacoco_log_url)}. El reporte HTML completo se conserva en el artefacto enlazado arriba.', 'Smallx')]

doc.build(story, onFirstPage=footer, onLaterPages=footer)

writer = PdfWriter()
base_reader = PdfReader(str(base_pdf))
for page in base_reader.pages:
    writer.add_page(page)
writer.add_metadata({'/Title': 'Grupo 1 - Entrega Segundo Corte - AgroValle Connect', '/Author': 'Grupo 1 - 6303B'})
with out.open('wb') as stream:
    writer.write(stream)
base_pdf.unlink()
print(out)
print(f'bytes={out.stat().st_size}')
