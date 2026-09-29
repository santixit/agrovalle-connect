# ADR 003 — Autenticación y autorización

## Estado

Aceptada.

## Contexto

La publicación de cosechas, reservas, contactos y operaciones logísticas modifican datos del proyecto. El dueño de cada registro debe derivarse de una identidad autenticada y las contraseñas no pueden persistirse en texto plano.

## Decisión

- Guardar un hash BCrypt de la contraseña y un rol (`AGRICULTOR`, `COMPRADOR`, `ADMIN`) por cuenta.
- Emitir JWT HS256 de dos horas desde `POST /api/v1/auth/login` y validar Bearer tokens con Spring Security.
- Configurar una llave aleatoria de al menos 32 bytes codificada en Base64 mediante `JWT_SECRET`; no incluirla en Git.
- Obtener el identificador de usuario y el rol de los claims verificados. El servicio valida propiedad de fincas, ofertas, reservas y pedidos.
- Mantener los endpoints de lectura del catálogo y perfil como públicos. La aplicación no crea cuentas admin en el registro público.

## Consecuencias

- Los clientes necesitan iniciar sesión para publicar, reservar, contactar, guardar favoritos o cambiar estados.
- Perfiles antiguos de agricultor siguen consultables, pero deben vincular credenciales para operar como cuenta autenticada.
- La configuración de ejecución requiere `JWT_SECRET`, `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` en los entornos que no usen valores de desarrollo.
- El JWT es stateless y no tiene renovación/revocación; cambiar la contraseña o desactivar una cuenta debe acompañarse de una estrategia de invalidación si el proyecto crece.
