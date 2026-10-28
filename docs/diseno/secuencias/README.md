# Diagramas de secuencia recibidos

Estas imágenes conservan los diagramas de secuencia corregidos que acompañan los mockups:

- [Registro de agricultor](secuencia-registro-agricultor.jpg): valida campos, detecta correo/documento duplicado y guarda el perfil.
- [Inicio de sesión y publicación](secuencia-iniciar-sesion-y-publicar.jpg): autenticación seguida de la validación de finca y publicación de cosecha.
- [Reserva de comprador](secuencia-reserva-comprador.jpg): reserva con control de disponibilidad y aviso al agricultor.
- [Contacto al agricultor](secuencia-contacto-agricultor.jpg): registra el mensaje y genera aviso.
- [Programación de despacho](secuencia-programar-despacho.jpg): calcula flete con origen, destino y peso antes de guardar el despacho.
- [Registro de producto, referencia anterior](secuencia-registro-producto-referencia.jpg): versión general recibida el 22 de octubre, conservada como antecedente del flujo más detallado de publicación.

Las láminas que incluyen cola de eventos muestran el procesamiento asíncrono propuesto. El Observer de notificaciones implementado en esta versión del backend se ejecuta de forma síncrona dentro de la operación confirmada; las imágenes se conservan como diseño de referencia, no como descripción exacta de esa decisión técnica.
