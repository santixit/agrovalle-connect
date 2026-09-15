# ADR 001 Arquitectura inicial en capas

## Estado
Aceptada - Sprint 0.

## Contexto
AgroValle Connect requiere una base mantenible para una API web con Java 17, Spring Boot y PostgreSQL. El curso exige separacion MVC y patrones Repository, Factory, Observer y Singleton cuando sean pertinentes.

## Decision
Se organiza el backend por capas: `controller` expone contratos REST, `service` concentra casos de uso, `repository` abstrae persistencia y `domain` contiene entidades y reglas. Spring Data implementara Repository. La configuracion sera externalizada con perfiles; Spring administrara instancias singleton de componentes. Factory y Observer se introduciran solamente cuando existan multiples tipos de pedido/usuario y eventos de notificacion reales.

## Consecuencias
La separacion facilita pruebas unitarias y cambios de infraestructura. La aplicacion conserva una estructura inicial pequena para no introducir abstracciones sin necesidad.

