# Flujo de ramas y protección de `main`

## Estrategia del repositorio

El README adopta **Trunk-Based Development**: se conserva `main` como rama de integración y se usan ramas cortas `feature/HU-XX-descripcion`, `fix/...` o `docs/...`. Los cambios se integran por Pull Request después de revisión y CI. El proyecto no usa una rama permanente `develop`; crearla cambiaría el flujo y no se hace automáticamente.

La consulta pública de GitHub reporta `main` como rama protegida (`protected: true`). La integración disponible no puede leer las reglas detalladas de protección; por eso el estado exacto de aprobaciones y verificaciones requeridas debe confirmarse en la configuración del repositorio.

## Reglas recomendadas para `main`

En GitHub abre **Settings → Rules → Rulesets** (o **Settings → Branches** si el repositorio usa branch protection rules) y revisa una regla dirigida a `main`:

1. Exigir Pull Request antes de integrar cambios.
2. Exigir al menos una aprobación de una persona distinta del autor.
3. Exigir que se resuelvan las conversaciones de revisión.
4. Exigir el estado de CI **Build, tests, Checkstyle and JaCoCo**. Ese job ejecuta Maven, JUnit, Checkstyle, JaCoCo y E2E.
5. Bloquear force push y eliminación accidental de `main`.
6. No exigir el job de despliegue de staging como check del PR: CD solo se llama tras un push exitoso a `main`.

Las reglas deben corresponder al nombre que GitHub muestre en el selector de status checks. Después de guardarlas, probarlas con un PR sin aprobación y verificar que GitHub impide el merge hasta cumplir los requisitos.

## Flujo para cada cambio

```text
main → rama corta → commit → Pull Request → CI verde → revisión independiente → merge a main → CD a staging
```

1. Actualiza la rama local desde `main` y crea una rama corta descriptiva.
2. Realiza un cambio acotado con commits Conventional Commits.
3. Abre el PR usando [la plantilla](../.github/pull_request_template.md) y enlaza la HU/issue.
4. Revisa el diff y solicita aprobación de otra persona; quien implementa no se aprueba a sí mismo.
5. Atiende comentarios y espera el job `quality`.
6. Fusiona por PR solo cuando estén satisfechas las reglas configuradas.
7. Tras el push a `main`, observa el job reutilizable de CD y comprueba staging.

## Registro de verificación

| Control | Evidencia observada | Estado |
|---|---|---|
| `main` existe y GitHub reporta `protected: true` | API pública de ramas consultada el 19-11-2026. | Confirmado |
| Requerir PR | No se pudo leer la configuración detallada con el acceso de integración disponible. | Confirmar en Settings |
| Aprobación independiente obligatoria | No se pudo leer la configuración detallada con el acceso de integración disponible. | Confirmar en Settings |
| Job `quality` requerido | No se pudo leer la configuración detallada con el acceso de integración disponible. | Confirmar en Settings |
| Force push y borrado bloqueados | No se pudo leer la configuración detallada con el acceso de integración disponible. | Confirmar en Settings |

Esta tabla se actualiza solo después de comprobar la configuración en GitHub Settings. Un archivo de documentación y una plantilla ayudan al equipo, pero no aplican por sí solos reglas de protección.
