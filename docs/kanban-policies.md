# Políticas Kanban y métricas de flujo

Estas políticas aplican al tablero [Agrovalle-Kamba](https://github.com/users/santixit/projects/3/views/1) y complementan las ceremonias del Sprint con flujo continuo (Scrumban). El equipo debe hacerlas visibles en la descripción del tablero y revisarlas en cada Daily Scrum.

## Columnas y límites WIP

| Columna | Límite WIP | Política |
|---|---:|---|
| Backlog / Ready | Sin límite | El Product Owner ordena y refina continuamente. Solo se inicia trabajo que cumpla la política de entrada a In Progress. |
| In Progress | 3 elementos | Al llegar a 3, nadie toma una tarea nueva. El equipo colabora para terminar o desbloquear trabajo activo. |
| Code Review / Testing | 2 elementos de trabajo | Al llegar a 2, se detiene el inicio de trabajo nuevo y se prioriza revisar y atender comentarios. |
| Done | Sin límite | Solo se mueve aquí trabajo que cumpla la Definition of Done y tenga evidencia enlazada. |

Los límites cuentan historias y tareas activas, no subtareas usadas solo para descomponer una tarjeta. Una tarjeta bloqueada sigue contando en su columna; se etiqueta `blocked`, se documenta el impedimento y se solicita ayuda durante el Daily. Las excepciones al límite se acuerdan explícitamente en el Daily, con motivo y responsable de resolverlas.

En la vista **Backlog** del proyecto se verificaron los límites de columna `In Progress = 3` y `Code Review / Testing = 2`. GitHub muestra el conteo y resalta cuando se supera el límite, pero no bloquea movimientos ni automatizaciones; el equipo sigue aplicando las políticas de *pull* descritas aquí. Los límites son específicos de cada vista, por lo que cualquier otra vista Board activa debe mantener los mismos valores (menú de la columna → **Set column limit**).

## Políticas de entrada y salida

### Ready → In Progress

- La historia tiene objetivo y criterios de aceptación entendibles, estimación acordada y prioridad definida por el Product Owner.
- Dependencias conocidas y acceso al entorno de desarrollo están disponibles.
- Hay capacidad bajo el límite WIP de In Progress y una persona asume la tarjeta.

### In Progress → Code Review

- La implementación cumple los criterios de aceptación y tiene pruebas automatizadas pertinentes.
- El cambio está en una rama corta actualizada desde `main`, usa Conventional Commits y no contiene secretos.
- La compilación y `mvn checkstyle:check` pasan localmente; el Pull Request explica el cambio y enlaza la historia.

### Code Review → Done

- Una persona distinta al autor revisó y aprobó el Pull Request.
- CI está verde, la cobertura conserva el mínimo del proyecto y no quedan comentarios bloqueantes.
- Se cumplen todos los puntos aplicables de [Definition of Done](dod.md), y la documentación/evidencia quedó actualizada.
- El cambio se integró a `main`; solo entonces se cierra la tarjeta.

## Métricas de flujo

- **Lead time:** desde que el Product Owner registra una necesidad en Backlog hasta que el cambio está desplegado y operativo. Reportar mediana y rango por historia, no solo promedio.
- **Cycle time:** desde el primer movimiento a In Progress hasta que el trabajo cumple DoD y pasa a Done. Comparar mediana y rango por historia.
- **Throughput:** cantidad de historias o tareas terminadas por semana y por Sprint. Contar elementos cerrados, no puntos estimados.
- **WIP:** cantidad de tarjetas en columnas activas en un momento dado; contrastarla con los límites anteriores.

Al revisar métricas, indicar fechas y el conjunto de elementos medidos. No usar throughput como compromiso individual ni comparar personas. La Ley de Little (`WIP ≈ throughput × cycle time`) sirve como relación de largo plazo para un flujo estable; reducir WIP y terminar bloqueos ayuda a bajar el tiempo de ciclo, pero la fórmula no predice por sí sola la fecha de una historia individual.

## Diagrama de flujo acumulado (CFD)

Revisar el CFD del tablero al cierre de cada Sprint y en retrospectiva. Las bandas deben avanzar de forma continua; una banda que se ensancha indica acumulación en esa etapa. Si aumenta Code Review, bajar el WIP de desarrollo y ayudar a revisar antes de iniciar historias nuevas. Una línea Done que se aplana indica menor throughput; revisar bloqueos y causas en equipo. Registrar el periodo, el cuello de botella observado y una acción de mejora verificable.

## Cadencia Scrumban

Se conservan Sprint Planning, Daily Scrum, Review y Retrospective para alineación y mejora. El tablero usa sistema *pull*: cada persona toma el siguiente elemento priorizado solo al liberar capacidad, respetando límites WIP. El Product Owner puede reordenar Backlog durante el Sprint; el trabajo ya iniciado mantiene su alcance salvo acuerdo explícito del equipo. Las tareas de refactorización SOLID se priorizan en el mismo flujo y pasan por el mismo DoD y revisión cruzada.
