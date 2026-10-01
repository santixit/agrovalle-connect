# Definition of Done

Una historia solo pasa a Done cuando cumple todos los criterios siguientes. El equipo confirma la lista en cada Pull Request.

- [ ] La rama se actualizo desde `main` y no tiene conflictos.
- [ ] Java 17 compila el proyecto y `mvn clean verify` termina correctamente.
- [ ] `mvn checkstyle:check` termina con cero violaciones o advertencias.
- [ ] Pasa el 100% de las pruebas automatizadas; el cambio incluye pruebas para sus escenarios BDD.
- [ ] La cobertura de lineas cumple el umbral minimo del 60% en JaCoCo.
- [ ] Los criterios Given-When-Then de la historia se validaron.
- [ ] Se aplican MVC, SOLID y nombres claros.
- [ ] README, BACKLOG y documentacion tecnica se actualizaron cuando corresponde.
- [ ] Los commits siguen Conventional Commits y el hook de Husky paso.
- [ ] Existe Pull Request con revision y aprobacion de un integrante distinto al autor.
- [ ] GitHub Actions esta en verde antes de fusionar.

**Equipo:** Danny Alexander Gomez, Michelle Guerrero Arboleda, Starlin Gomez Asprilla y Raul Santiago Carrillo.
