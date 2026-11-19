# ADR-006 — Estrategia de producción

## Estado

Pendiente de decisión del equipo. Este documento registra el vacío identificado, no una aprobación de despliegue de producción.

## Contexto

El proyecto tiene un entorno de staging en Render con PostgreSQL separado. La guía de tercer corte solicita discutir una estrategia de producción, pero en el repositorio no se encontró un proveedor de producción ni una decisión de disponibilidad, operación, costos o responsables.

## Decisión

No activar un despliegue de producción hasta que el equipo seleccione y documente proveedor, datos, secretos, monitoreo, copias de respaldo, restauración y responsable operativo.

## Consecuencias

- El único entorno desplegado documentado continúa siendo staging.
- La futura elección debe registrarse en este ADR y reflejarse en el pipeline y la guía de despliegue.
