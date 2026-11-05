# Sprint 3 — checklist para empezar

Usa cuentas y datos de prueba. No pegues contraseñas, tokens ni datos personales reales en capturas o documentos.

## Preparación

- [ ] Abre GitHub Projects y busca HU-05 y HU-10.
- [ ] Confirma si cada historia está abierta, cerrada o en revisión.
- [ ] Si ambas ya fueron aceptadas, no las vuelvas a mover a Sprint 3: acuerda con el Product Owner qué historias pendientes reemplazan este borrador.
- [ ] Si siguen pendientes, confirma que el Product Owner acepta revisarlas en este Sprint.
- [ ] Crea o selecciona una rama de trabajo siguiendo la estrategia vigente del repositorio.

## Comprobación manual del detalle

- [ ] Levanta la aplicación siguiendo las instrucciones del README.
- [ ] Inicia sesión como usuario de prueba o abre el catálogo sin sesión.
- [ ] Abre una oferta de prueba con **Ver detalles**.
- [ ] Comprueba nombre, finca (si tiene), municipio, precio y cantidad disponible.
- [ ] Confirma que no se ve cédula, contraseña, token ni información privada.
- [ ] Repite con una oferta que no exista o que no esté disponible y anota el resultado.

## Comprobación manual del contacto

- [ ] Inicia sesión como comprador de prueba.
- [ ] Abre el detalle de una oferta activa y pulsa **Contactar**.
- [ ] Envía un mensaje de prueba, por ejemplo: `Hola, ¿sigue disponible esta cosecha?`.
- [ ] Confirma el mensaje de éxito en la interfaz.
- [ ] Inicia sesión como agricultor dueño de esa oferta.
- [ ] Revisa **Mensajes de contacto** o las notificaciones y confirma que apareció el aviso.
- [ ] Anota el resultado observado y cualquier error en `planificacion.md`.

## Después de la comprobación manual

- [ ] Si todo funciona, implementa la automatización E2E descrita como S3-T4.
- [ ] Si algo falla, registra el paso exacto, resultado esperado y resultado obtenido antes de corregir el código.
- [ ] Guarda evidencia sin secretos ni información real.
- [ ] Actualiza el tablero y la revisión del Sprint según el resultado real.
