# Flujo de ramas y protección de `main`

## Estrategia del repositorio

El README adopta **Trunk-Based Development**: se conserva `main` como rama de integración y se usan ramas cortas `feature/HU-XX-descripcion`, `fix/...` o `docs/...`. Los cambios se integran por Pull Request después de revisión y CI. El proyecto no usa una rama permanente `develop`; crearla cambiaría el flujo y no se hace automáticamente.

GitHub tiene un ruleset activo llamado main-protection dirigido a ~DEFAULT_BRANCH. La configuración se leyó desde la API de Rulesets el 2026-11-19 y se registra en la tabla de verificación. El endpoint antiguo de branch protection no es accesible para la integración, pero eso no impide leer el ruleset activo.

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

## Configuración verificada en GitHub

| Control | Configuración de main-protection | Estado |
|---|---|---|
| Rama objetivo | ~DEFAULT_BRANCH, que corresponde a main en este repositorio. | Confirmado |
| Exigir Pull Request | Regla pull_request activa. | Confirmado |
| Aprobación requerida | Una aprobación antes de integrar; no se exige revisión de Code Owners y no se descartan aprobaciones obsoletas tras nuevos commits. | Confirmado; una persona debe aprobar el PR. |
| Check requerido | Build, tests, Checkstyle and JaCoCo. | Confirmado |
| Estado de rama actualizado antes de merge | strict_required_status_checks_policy=false; el ruleset no exige actualizar la rama con la base antes de integrar. | No exigido |
| Resolver conversaciones de revisión | required_review_thread_resolution=false. | No exigido por este ruleset |
| Borrado y force push | Reglas deletion y non_fast_forward. | Bloqueados |
| Métodos de merge permitidos | Merge commit, squash y rebase. | Confirmado |

**Fuente:** [ruleset main-protection en GitHub](https://github.com/santixit/agrovalle-connect/rules/24141794), consultado por la API de Rulesets el 2026-11-19. La configuración es operativa; este archivo solo la documenta.
