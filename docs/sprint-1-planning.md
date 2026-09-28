# Sprint 1 Planning - AgroValle Connect

## 1. Información del Sprint

**Proyecto:** AgroValle Connect
**Sprint:** Sprint 1
**Capacidad:** 10 Story Points
**Framework:** Scrum
**Tecnología:** Java 17 + Spring Boot
**Base de datos:** PostgreSQL
**Pruebas:** JUnit 5

---

## 2. Sprint Goal

Implementar el registro de agricultores y la publicación de cosechas mediante servicios REST en Spring Boot, asegurando validaciones, persistencia, seguridad básica y pruebas automatizadas derivadas de los criterios BDD definidos en el Product Backlog.

---

## 3. Historias de Usuario seleccionadas

| ID        | Historia de Usuario                                                | MoSCoW | Story Points |
| --------- | ------------------------------------------------------------------ | ------ | -----------: |
| HU-01     | Como agricultor, quiero registrarme para ofrecer mis productos.    | MUST   |            5 |
| HU-02     | Como agricultor, quiero publicar una cosecha para que sea visible. | MUST   |            5 |
| **Total** |                                                                    |        |    **10 SP** |

La selección corresponde a la capacidad máxima establecida para el Sprint 1.

---

# 4. HU-01 - Registro de agricultores

## Historia

Como agricultor, quiero registrarme para ofrecer mis productos.

**Story Points:** 5
**Prioridad:** MUST

## Criterio BDD

**Given** que un visitante envía `POST /api/v1/auth/register` con nombre, ubicacion_valle y cedula válidos,

**When** el servicio valida que la cédula no existe,

**Then** responde `201 Created` y persiste el agricultor en PostgreSQL.

---

## 4.1 Descomposición técnica

### Tarea HU-01.1 - Crear entidad Agricultor

* Crear la entidad `Agricultor`.
* Definir sus atributos principales.
* Configurar la entidad para persistencia mediante JPA.
* Establecer las validaciones correspondientes.

**ISO/IEC 25010:** Mantenibilidad y fiabilidad.

---

### Tarea HU-01.2 - Crear repositorio

* Crear `AgricultorRepository`.
* Configurar Spring Data JPA.
* Implementar la consulta necesaria para verificar si una cédula ya existe.

**ISO/IEC 25010:** Fiabilidad y mantenibilidad.

---

### Tarea HU-01.3 - Implementar servicio de registro

* Crear el servicio encargado del registro.
* Validar los datos recibidos.
* Verificar que la cédula no esté registrada.
* Persistir el agricultor en PostgreSQL.
* Manejar errores de validación y duplicidad.

**ISO/IEC 25010:** Fiabilidad y seguridad.

---

### Tarea HU-01.4 - Implementar endpoint REST

Crear:

`POST /api/v1/auth/register`

El endpoint debe:

* Recibir los datos del agricultor.
* Validar la información.
* Ejecutar el servicio de registro.
* Retornar `201 Created` cuando el registro sea exitoso.
* Retornar una respuesta apropiada cuando los datos sean inválidos o la cédula ya exista.

**ISO/IEC 25010:** Compatibilidad y fiabilidad.

---

### Tarea HU-01.5 - Traducir BDD a pruebas automatizadas

Crear pruebas con **JUnit 5** para verificar el escenario BDD de HU-01.

Las pruebas deben comprobar como mínimo:

* Registro exitoso de un agricultor.
* Rechazo de una cédula ya registrada.
* Validación de datos obligatorios.
* Persistencia correcta del agricultor.

**ISO/IEC 25010:** Fiabilidad.

---

# 5. HU-02 - Publicación de cosechas

## Historia

Como agricultor, quiero publicar una cosecha para que sea visible.

**Story Points:** 5
**Prioridad:** MUST

## Criterio BDD

**Given** un agricultor autenticado mediante JWT,

**When** envía `POST /api/v1/productos` con tipo, cantidad y fecha_cosecha no anterior a hoy,

**Then** responde `201 Created` con un identificador único y persiste la oferta.

---

## 5.1 Descomposición técnica

### Tarea HU-02.1 - Implementar entidad Producto

* Crear o completar la entidad `Producto`.
* Definir los atributos de la cosecha.
* Configurar la relación con el agricultor.
* Aplicar las validaciones necesarias.

**ISO/IEC 25010:** Mantenibilidad y fiabilidad.

---

### Tarea HU-02.2 - Crear ProductoRepository

* Crear `ProductoRepository`.
* Configurar Spring Data JPA.
* Permitir la persistencia de las ofertas agrícolas.

**ISO/IEC 25010:** Mantenibilidad y fiabilidad.

---

### Tarea HU-02.3 - Implementar servicio de publicación

* Crear el servicio para registrar una cosecha.
* Validar tipo y cantidad.
* Validar que la fecha de cosecha no sea anterior a la fecha actual.
* Asociar la cosecha con el agricultor autenticado.
* Persistir la oferta.

**ISO/IEC 25010:** Fiabilidad y seguridad.

---

### Tarea HU-02.4 - Implementar endpoint REST

Crear:

`POST /api/v1/productos`

El endpoint debe:

* Validar la autenticación del agricultor.
* Recibir los datos de la cosecha.
* Ejecutar el servicio de publicación.
* Generar un identificador único.
* Retornar `201 Created` cuando la operación sea exitosa.

**ISO/IEC 25010:** Seguridad, fiabilidad y compatibilidad.

---

### Tarea HU-02.5 - Validar disponibilidad y reglas de negocio

* Verificar que la cantidad publicada sea válida.
* Validar las reglas relacionadas con disponibilidad.
* Evitar la publicación de datos inconsistentes.
* Validar la fecha de cosecha.

**ISO/IEC 25010:** Fiabilidad.

---

### Tarea HU-02.6 - Traducir BDD a pruebas automatizadas

Crear pruebas con **JUnit 5** para verificar el escenario BDD de HU-02.

Las pruebas deben comprobar como mínimo:

* Publicación exitosa de una cosecha.
* Rechazo de una fecha de cosecha anterior a la fecha actual.
* Validación de cantidad.
* Asociación correcta entre agricultor y producto.
* Persistencia correcta de la oferta.
* Validación de autenticación mediante JWT.

**ISO/IEC 25010:** Fiabilidad y seguridad.

---

# 6. Relación con ISO/IEC 25010

Las actividades del Sprint 1 consideran las siguientes características de calidad:

| Característica     | Aplicación en el Sprint                                                |
| ------------------ | ---------------------------------------------------------------------- |
| **Fiabilidad**     | Validaciones, manejo de errores, persistencia y pruebas automatizadas. |
| **Seguridad**      | Autenticación mediante JWT y protección de los endpoints.              |
| **Mantenibilidad** | Separación entre entidades, repositorios, servicios y controladores.   |
| **Compatibilidad** | Implementación de servicios REST mediante Spring Boot.                 |

---

# 7. Pruebas automatizadas

La traducción de los criterios BDD a pruebas automatizadas se realizará utilizando **JUnit 5**.

Cada Historia de Usuario deberá contar con pruebas que permitan verificar el comportamiento esperado definido en sus escenarios Given-When-Then.

### HU-01

Se probará:

* Registro válido.
* Cédula duplicada.
* Datos obligatorios.
* Persistencia.

### HU-02

Se probará:

* Publicación válida.
* Fecha inválida.
* Cantidad inválida.
* Autenticación.
* Persistencia.
* Asociación agricultor-producto.

---

# 8. Definition of Done para el Sprint

Una tarea se considerará terminada cuando:

* El código esté implementado.
* Cumpla con las reglas de Checkstyle.
* Las pruebas automatizadas estén creadas.
* Las pruebas JUnit 5 sean exitosas.
* Las validaciones BDD hayan sido cubiertas.
* El código haya sido revisado mediante Pull Request.
* La funcionalidad esté integrada en la rama correspondiente.
* No existan errores conocidos que impidan el funcionamiento de la Historia de Usuario.
* La documentación correspondiente esté actualizada.

---

# 9. Resultado esperado del Sprint

Al finalizar el Sprint 1, AgroValle Connect deberá permitir:

1. Registrar agricultores mediante `POST /api/v1/auth/register`.
2. Validar los datos del agricultor.
3. Evitar registros duplicados por cédula.
4. Persistir los agricultores en PostgreSQL.
5. Permitir a un agricultor autenticado publicar una cosecha mediante `POST /api/v1/productos`.
6. Validar los datos de la cosecha.
7. Generar un identificador único para la oferta.
8. Persistir la oferta asociada al agricultor.
9. Contar con pruebas automatizadas en JUnit 5 derivadas de los criterios BDD.
10. Mantener las condiciones de calidad definidas para el Sprint mediante prácticas relacionadas con ISO/IEC 25010.
