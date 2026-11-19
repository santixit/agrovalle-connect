# ADR-006 — Mantener el alcance en staging; no habilitar producción

## Estado

**Aceptada como límite de alcance del prototipo académico** el 2026-11-19. No define una estrategia futura de producción.

## Contexto

El proyecto cuenta con un ambiente de staging en Render y una base PostgreSQL de pruebas separada. No se ha documentado proveedor de producción, responsable operativo, políticas de datos reales, presupuesto, respaldo/restauración, monitoreo ni objetivo de disponibilidad.

## Decisión

Se mantiene el proyecto en staging para demostración y evaluación. No se anuncia ni habilita producción ni se procesa información real de agricultores/compradores hasta que exista una decisión posterior que cubra proveedor, secretos, protección de datos, respaldo y recuperación, monitoreo, costos y responsables.

## Consecuencias

- CI/CD y la guía actual describen staging, no una promesa de disponibilidad productiva.
- Se usan datos sintéticos en pruebas, evidencias y demostraciones.
- No se fija un SLA, RPO o RTO para esta versión.
- Una futura habilitación de producción requiere un nuevo ADR o una revisión de este y evidencias operativas correspondientes.

## Trazabilidad

- [Decisiones de línea base del prototipo](../requirements/baseline-decisions-2026-11-19.md)
- [Guía de despliegue a staging](../deploy-staging.md)
