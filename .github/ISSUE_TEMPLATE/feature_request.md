---
name: Proponer una mejora
about: Registra una necesidad de usuario, su valor y criterios verificables
title: "feat: [HU-XX] capacidad resumida"
labels: enhancement
assignees: ""
---

## Necesidad y valor

¿Qué problema concreto tiene el usuario? ¿Qué resultado le permitiría completar mejor su tarea? Describe el contexto actual y el beneficio esperado.

## Historia de usuario

Como **[tipo de usuario]**, quiero **[capacidad]**, para **[beneficio verificable]**.

## Criterios de aceptación (BDD)

Escribe un escenario por resultado observable. Usa datos sintéticos y condiciones que otra persona pueda comprobar.

### Escenario principal

- **Given (Dado):** contexto inicial y precondiciones.
- **When (Cuando):** acción del usuario o evento del sistema.
- **Then (Entonces):** resultado visible y verificable.

### Escenarios alternos o de error

Agrega escenarios para datos inválidos, falta de permisos, registros inexistentes o límites relevantes. Indica “No aplica” si no hay otro escenario necesario.

## Prioridad y estimación propuestas

- MoSCoW propuesto: Must / Should / Could / Won’t (esta vez)
- Story Points propuestos: 1 / 2 / 3 / 5 / 8 / 13
- Justificación o incertidumbres:

La prioridad definitiva y los Story Points los confirma el equipo en la planificación; esta propuesta no los compromete por sí sola.

## Alcance

- Incluye:
- No incluye:
- Historias o requisitos relacionados:
- Dependencias o bloqueos conocidos:

## Impacto técnico conocido

- [ ] API/contrato REST
- [ ] Modelo de datos o migración Flyway
- [ ] Interfaz o mockup
- [ ] Autenticación, roles o permisos
- [ ] Notificaciones o procesos externos
- [ ] No se conoce todavía

Describe restricciones, cambios de compatibilidad, datos afectados y consideraciones de seguridad o privacidad. No incluyas secretos ni datos reales.

## Cómo se validará

Indica qué pruebas automatizadas se esperan (JUnit, integración, E2E), qué comprobación manual requiere el Product Owner y qué evidencia se enlazará al PR.

## Definition of Done relacionada

- [ ] Los criterios BDD tienen pruebas o una justificación verificable.
- [ ] La respuesta y los errores respetan los contratos y permisos.
- [ ] La documentación y los diagramas afectados quedan sincronizados.
- [ ] CI y los controles de calidad aplicables pasan.
- [ ] La historia solo se marcará Done tras cumplir el DoD del repositorio y registrar su aceptación cuando corresponda.
