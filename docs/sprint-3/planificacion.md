# Sprint 3 — publicación y consulta de ofertas

> **Estado:** propuesta para revisión del Product Owner y el equipo; no afirma que el Sprint 3 se haya aprobado, iniciado o terminado. La selección corresponde a la proyección del siguiente corte y suma 14 SP.

## Sprint Goal propuesto

Permitir que un agricultor registre una finca y publique una oferta que el comprador pueda consultar, filtrar y encontrar en su estado correcto, verificando con pruebas los criterios de HU-02, HU-06, HU-09 y HU-10.

## Historias candidatas

| Historia | Prioridad | SP | Dependencia / estado del código |
|---|---|---:|---|
| HU-06: registrar una finca | Must have | 3 | Debe estar disponible para asociar una ubicación a la oferta; el README declara la historia implementada. |
| HU-02: publicar una cosecha | Must have | 5 | La publicación puede vincularse a una finca registrada; el README declara la historia implementada. |
| HU-09: cambiar el estado de una oferta propia | Should have | 3 | Requiere una oferta publicada y permisos del agricultor propietario; el README declara la historia implementada. |
| HU-10: consultar el detalle de una oferta | Must have | 3 | Requiere una oferta consultable; el README declara la historia implementada. |
| **Total propuesto** |  | **14** | Estimaciones existentes en `BACKLOG.md`; no se vuelven a contar como trabajo nuevo. |

La propuesta prioriza comprobar el incremento y cerrar criterios de aceptación. Si el tablero muestra que alguna historia ya fue aceptada, no se vuelve a comprometer: el Product Owner debe elegir una historia pendiente y actualizar la proyección.

## Descomposición técnica para refinar

| Tarea candidata | Resultado comprobable |
|---|---|
| Revisar estado de las cuatro HUs en GitHub Projects y confirmar criterios BDD con el Product Owner. | Cada historia queda como aceptada, fallida o pendiente con evidencia o nota concreta. |
| Verificar la secuencia de registro de finca y publicación de oferta con datos de prueba. | La oferta conserva finca, agricultor, categoría, municipio, cantidad y precio acordados. |
| Revisar el cambio de estado de una oferta propia y el rechazo de usuarios sin permiso. | Solo el propietario autorizado puede actualizar su oferta; el estado se refleja en el catálogo. |
| Verificar detalle y filtros del catálogo, incluidos identificadores inexistentes y ofertas no disponibles. | Los datos públicos acordados se muestran y no aparecen cédulas, credenciales, tokens u otros datos sensibles. |
| Traducir o completar los escenarios BDD aceptados como pruebas automatizadas JUnit 5 e integración. | Las pruebas de criterios relevantes pasan en Maven/CI y se conserva el resultado del commit revisado. |
| Corregir únicamente fallos observados y actualizar evidencia de revisión. | El PR enlaza HUs, describe los resultados reales y pasa las verificaciones requeridas. |

## Escenarios de aceptación a comprobar

### HU-06 y HU-02 — finca y oferta

- **Given** un agricultor autenticado y una finca válida registrada
- **When** publica una cosecha con los campos obligatorios
- **Then** la oferta queda asociada a su agricultor y finca y aparece en el catálogo

### HU-09 — actualización del estado

- **Given** una oferta activa propiedad del agricultor autenticado
- **When** el agricultor la marca agotada
- **Then** la oferta deja de presentarse como disponible
- **And** otro usuario no puede modificar esa oferta

### HU-10 — detalle público

- **Given** una oferta consultable
- **When** una persona abre su detalle
- **Then** ve producto, ubicación, precio y disponibilidad acordados
- **And** no recibe datos privados del agricultor

## Condición de cierre propuesta

- El Product Owner confirma qué HUs siguen en alcance y acepta o devuelve cada criterio con una observación.
- Las pruebas automatizadas asociadas a los criterios confirmados pasan en el commit candidato.
- Se conserva evidencia reproducible del CI y de la comprobación funcional con datos de prueba.
- El tablero refleja el estado observado y respeta los límites WIP del equipo.
- La revisión y la retrospectiva registran únicamente resultados que realmente ocurrieron.
