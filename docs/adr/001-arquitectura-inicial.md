# ADR 001 Arquitectura inicial en capas

## Estado
Aceptada - Sprint 0.

## Contexto
AgroValle Connect requiere una base mantenible para una API web con Java 17, Spring Boot y PostgreSQL. El curso exige separacion MVC y patrones Repository, Factory, Observer y Singleton cuando sean pertinentes.

## Decision
Se organiza el backend por capas: `controller` expone contratos REST, `service` concentra casos de uso, `repository` abstrae persistencia y `domain` contiene entidades y reglas. Spring Data implementa Repository. Spring administra instancias singleton de componentes; `ReglasDisponibilidad` centraliza reglas compartidas de inventario. `UsuarioFactory` crea cuentas con el rol y el hash de contraseña correctos. Los eventos transaccionales de contacto y pedido son observados por `NotificacionObserver` para persistir avisos solo después de confirmar la operación.

## Consecuencias
La separación facilita pruebas y cambios de infraestructura. Factory y Observer tienen responsabilidades del dominio verificables y no son clases decorativas.

