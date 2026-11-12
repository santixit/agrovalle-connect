# ADR-001: Adopción de PaaS y GitHub Actions para CD en Staging

- **Estado:** Aprobada
- **Fecha:** 2026-11-12
- **Autores:** Célula de Desarrollo AgroValle Connect
- **Contexto académico:** Ingeniería de Software II, UNIAJC

## Contexto y problemática

AgroValle Connect necesita publicar los incrementos integrados en `main` en un entorno de
staging reproducible. El equipo requiere automatización conectada con sus controles de calidad,
sin administrar servidores ni duplicar las reglas de esquema que ya gestiona Flyway.

## Opciones consideradas

1. **Despliegue manual en un VPS:** brinda control del sistema operativo, pero exige administrar
   el servidor, sus parches y scripts de publicación.
2. **Despliegue automatizado en una plataforma PaaS:** integra un servicio Spring Boot y una base
   PostgreSQL gestionada, y permite solicitar despliegues mediante webhook.

## Decisión

Se adopta la opción PaaS junto con GitHub Actions. El workflow de CD escucha la finalización del
workflow de CI y solo invoca el webhook cuando CI concluye exitosamente para `main`. El servicio
usa el perfil Spring `staging`; URL, usuario y contraseña PostgreSQL y clave JWT se inyectan como
variables de entorno en la plataforma. La URL privada del webhook se almacena en GitHub como
`DEPLOY_HOOK_URL`.

## Consecuencias

### Positivas

- El despliegue se condiciona a compilación, pruebas, Checkstyle, cobertura y pruebas E2E de CI.
- Las credenciales y la clave JWT no se escriben en el repositorio.
- La configuración del entorno se mantiene fuera del código y Flyway conserva el control del
  esquema.

### Limitaciones

- El webhook confirma que la plataforma recibió la solicitud, no que la aplicación ya esté lista.
- Los servicios gratuitos pueden tener latencia de arranque después de periodos de inactividad.
- El equipo debe comprobar el endpoint de salud después del despliegue y mantener configurados
  los secretos en GitHub y las variables del servicio PaaS.

## Verificación

La aplicación expone `GET /api/v1/health`, que devuelve HTTP 200 cuando el servicio atiende
solicitudes. La conexión a PostgreSQL se valida al iniciar mediante Flyway y Hibernate.
