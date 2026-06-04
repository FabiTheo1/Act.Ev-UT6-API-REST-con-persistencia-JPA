este es un draft del primer readme

# 📚 API REST de Gestión de Biblioteca Municipal

Actividad de Evaluación Final de la **UT6: Ficheros y Acceso a BBDD** para el ciclo de **1º de Desarrollo de Multiplatform Applications (DAM)**. Desarrollado en co-autoría por Fabián González Olofsson y Daniel.

Este proyecto consiste en el diseño y despliegue de una API REST funcional construida con **Spring Boot** y persistencia relacional a través de **JPA/Hibernate**. El sistema cubre desde el núcleo básico de operaciones CRUD hasta módulos avanzados de filtrado dinámico, relaciones complejas de bases de datos y calidad de software.

---

## 🚀 Requisitos Mínimos y Arquitectura

El corazón de la aplicación cumple estrictamente con las directrices del **Núcleo Obligatorio** y los módulos de extensión acumulativos de la rúbrica:

* **Arquitectura por Capas (RA9):** Aislamiento jerárquico estricto. El flujo de datos sigue el patrón `Controller ➡️ Service ➡️ Repository ➡️ Base de Datos`. El controlador jamás invoca de forma directa los repositorios.
* **Persistencia Segura con Optional (RA9 d):** Todos los métodos de consulta por identificador (`findById`) devuelven contenedores `Optional<T>`. Se evita el uso directo de `.get()` para prevenir excepciones de tipo `NullPointerException`, procesando los flujos mediante programación funcional con métodos como `.orElseThrow()`.
* **Manejo Global de Excepciones (Módulo D):** Se ha implementado un interceptor centralizado `@ControllerAdvice` que captura las excepciones en tiempo de ejecución (como `ResourceNotFoundException`) y estandariza las respuestas de error en un formato JSON limpio y legible para el cliente.
* **Inyección de Dependencias Optimizada:** Uso de la biblioteca **Lombok** mediante la anotación `@RequiredArgsConstructor` sobre atributos declarados como `private final`. Esto genera los constructores en tiempo de compilación y asegura una inyección limpia gestionada por el contenedor de Spring sin necesidad de acoplamiento manual.

---

## 🛠️ Tecnologías y Dependencias Utilizadas

El entorno tecnológico del proyecto utiliza las siguientes herramientas integradas mediante **Maven**:

* **Java 17 / 21** (Versión LTS del lenguaje)
* **Spring Boot 3.x**
* **Spring Web** (Despliegue de endpoints REST y serialización automática a JSON)
* **Spring Data JPA** (Abstracción de persistencia mediante Hibernate)
* **H2 Database** / **MySQL** (Motor de persistencia relacional en memoria/local)
* **Lombok** (Eliminación de código repetitivo de Boilerplate mediante anotaciones)
* **Spring Boot Starter Validation** (Validación semántica de datos de entrada)

---

## 📂 Estructura del Proyecto

El código fuente se encuentra organizado de manera modular bajo el paquete raíz `com.biblioteca.biblioteca_api`:

```text
src/main/java/com/biblioteca/biblioteca_api/
│
├── controllers/       # Capa de presentación (Endpoints REST, Request/Response mapping)
│   ├── LibroController.java
│   └── SocioController.java
│
├── services/          # Capa de negocio (Lógica de control, validación y flujos condicionales)
│   ├── LibroService.java
│   └── SocioService.java
│
├── repositories/      # Capa de acceso a datos (Interfaces que extienden JpaRepository y JPQL)
│   ├── LibroRepository.java
│   └── SocioRepository.java
│
├── models/            # Capa del modelo de dominio (Entidades JPA con mapeos relacionales)
│   ├── Libro.java
│   └── Socio.java
│   └── Autor.java     # (Entidad desarrollada por Daniel)
│
└── exceptions/        # Sistema de calidad y gestión de errores centralizado
    ├── ErrorDetalles.java
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
