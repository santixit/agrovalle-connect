# Exportaciones de UML técnico

Estos exports se generaron desde las fuentes PlantUML editables de la carpeta padre. Representan componentes, despliegue y flujos técnicos seleccionados del backend; no sustituyen las imágenes de wireframes/mockups ni los seis diagramas de secuencia recibidos del equipo, que se conservan por separado en [`../mockups/`](../mockups/) y [`../secuencias/`](../secuencias/).

Archivos generados:

- [Componentes](../diagrama-componentes.puml): [PNG](uml-componentes.png).
- [Despliegue](../diagrama-despliegue.puml): [PNG](uml-despliegue.png).
- [Secuencia HU-01, registro](../diagrama-secuencia.puml): [PNG](uml-secuencia-registro.png).
- [Secuencia HU-02, publicación](../diagrama-secuencia-publicacion.puml): [PNG](uml-secuencia-publicacion.png).
- [PDF de cuatro páginas](diagramas-uml.pdf), una página por diagrama exportado.

Para regenerarlos con PlantUML desde la raíz del repositorio:

```powershell
java -jar plantuml.jar -tpng -o docs/diseno/exports docs/diseno/diagrama-componentes.puml docs/diseno/diagrama-despliegue.puml docs/diseno/diagrama-secuencia.puml docs/diseno/diagrama-secuencia-publicacion.puml
```

Las secuencias tienen fuentes separadas para que cada flujo se renderice en su propia lámina. El PDF reúne ambas secuencias más componentes y despliegue. Si se modifica una fuente, hay que regenerar los PNG y el PDF para mantenerlos sincronizados.
