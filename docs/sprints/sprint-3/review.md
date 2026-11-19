# Sprint 3 — registro de validación

**Estado:** revisión técnica parcial; la demo y aceptación funcional del Product Owner siguen pendientes.

## Pruebas automatizadas reportadas

El 19 de noviembre de 2026, el usuario ejecutó las pruebas seleccionadas con Maven y compartió este resultado: **37 pruebas, 0 fallos, 0 errores y 0 omitidas; `BUILD SUCCESS`**. Esta evidencia acredita el resultado JUnit de esa ejecución, no una ejecución de Checkstyle/JaCoCo, demo en staging ni aceptación del PO.

## Pendiente para cerrar la revisión

- [ ] Demostrar HU-02, HU-06, HU-09 y HU-10 en staging con datos sintéticos.
- [ ] Guardar capturas o artefactos bajo `docs/evidencias/sprint-3/`, sin tokens ni datos reales.
- [ ] Registrar para cada HU la decisión y comentario del PO.

## Matriz de aceptación

Completar esta tabla durante la demostración. No cambiar el estado a aceptado sin decisión explícita del PO y evidencia del escenario revisado.

| Historia | Pruebas automatizadas | Demo en staging | Decisión del PO | Fecha y evidencia |
|---|---|---|---|---|
| HU-02 | 37 pruebas aprobadas en ejecución local reportada; vincular los casos concretos al escenario. | Pendiente | Pendiente | Pendiente |
| HU-06 | 37 pruebas aprobadas en ejecución local reportada; vincular los casos concretos al escenario. | Pendiente | Pendiente | Pendiente |
| HU-09 | 37 pruebas aprobadas en ejecución local reportada; vincular los casos concretos al escenario. | Pendiente | Pendiente | Pendiente |
| HU-10 | 37 pruebas aprobadas en ejecución local reportada; vincular los casos concretos al escenario. | Pendiente | Pendiente | Pendiente |

El conteo de 37 corresponde a la suite seleccionada y no acredita por sí solo que cada criterio BDD esté cubierto ni que el PO haya aceptado la historia.

La prueba automatizada ampliada está en `src/test/java/co/edu/uniajc/agrovalle/AgrovalleConnectApplicationTests.java`.
