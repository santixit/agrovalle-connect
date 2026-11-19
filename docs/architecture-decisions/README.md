# Decisiones de arquitectura

Los identificadores ya asignados se conservan para no romper su trazabilidad. Los cuatro ADR existentes se trasladaron aquí con sus IDs originales.

| ADR | Decisión | Estado registrado |
|---|---|---|
| [ADR-001 Arquitectura inicial](ADR-001-arquitectura-inicial.md) | Organización por capas y patrones de base | Aceptada en Sprint 0 |
| [ADR-002 Modelo relacional](ADR-002-modelo-relacional-integrador.md) | PostgreSQL, Flyway y relaciones del dominio | Aceptada |
| [ADR-003 Autenticación](ADR-003-autenticacion-y-autorizacion.md) | BCrypt, JWT y Spring Security | Aceptada |
| [ADR-004 Patrones](ADR-004-patrones-de-diseno.md) | Repository, Factory, Observer, Strategy y Singleton | Aceptada |
| [ADR-006 Alcance de producción](ADR-006-estrategia-produccion-pendiente.md) | Mantener el alcance en staging; no habilitar producción | Aceptada como límite del prototipo (2026-11-19) |

La guía de tercer corte propone un ADR específico de despliegue staging y otro para producción. No se renumeran los ADR existentes para imitar ese ejemplo. El procedimiento de staging está documentado en [`../deploy-staging.md`](../deploy-staging.md). El ADR-006 limita el alcance a staging; no aprueba un servicio de producción.
