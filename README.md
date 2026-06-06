# 📚 API REST de Gestión de Biblioteca Municipal

Actividad de Evaluación Final de la **UT6: Ficheros y Acceso a BBDD** para el ciclo de **1º de Desarrollo de Aplicaciones Multiplataforma (DAM)**. Ponderación: 70% de la nota de la UT6.

---

## 👥 Participantes y Reparto de Capas
El proyecto ha sido diseñado y desarrollado de manera conjunta y equilibrada de forma vertical:
* **Fabián González Olofsson:** Responsable del diseño e implementación del ciclo completo de capas (Entidades, Repositorios, Servicios y Controladores) de las entidades `Libro` y `Socio`, el desarrollo de consultas complejas con JPQL (`@Query`), el control de la tabla asociativa intermedia de préstamos (`@ManyToMany`), y la estructura de calidad del software mediante el interceptor global de excepciones (`@ControllerAdvice`).
* **Daniel:** Responsable del diseño e implementación del ciclo completo de capas de la entidad `Autor`, el control de la recursividad cíclica en relaciones de persistencia (`@JsonIgnore`), la lógica de filtrado y métodos derivados mediante Query Params (`@RequestParam`), y la infraestructura de seguridad perimetral de la API mediante **Spring Security**.

---

## 📄 Descripción del Proyecto: ¿Qué hace el código?
La aplicación es una solución de software backend que expone una interfaz **API REST** para automatizar el control operativo de una biblioteca pública. El sistema permite:
1.  **Catálogo Automatizado:** Gestionar el registro indexado de obras literarias (`Libro`) y sus creadores (`Autor`), mapeando de forma relacional qué libros pertenecen a qué autor.
2.  **Padrón de Usuarios:** Administrar las altas, bajas y modificaciones de los ciudadanos adscritos al servicio (`Socio`).
3.  **Lógica de Préstamos:** Controlar un histórico cruzado muchos a muchos (`@ManyToMany`) para registrar qué libros han sido tomados en préstamo por qué socios, gestionando las restricciones de integridad relacional en la base de datos de manera automatizada.

---

## 🔧 Instrucciones de Configuración, Arranque y Guía de Uso

### Requisitos Previos
* Java 17 o superior (JDK instalado).
* Un motor de Base de Datos MySQL local (con el esquema creado según tu `application.properties`) o la base de datos embebida H2 en memoria.

### Paso 1: Levantar el Servidor (Spring Boot)
1. Abre tu terminal (Git Bash, PowerShell o CMD) en la carpeta raíz del proyecto (donde se encuentra el archivo `pom.xml`).
2. Ejecute el comando de compilación y despliegue automatizado a través del Wrapper nativo de Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
3. El servidor estará completamente activo cuando aparezca el banner de Spring y se muestre el mensaje de éxito `Started BibliotecaApiApplication` en la consola. La API estará escuchando activamente peticiones en: `http://localhost:8080`.

### Paso 2: Guía de Comprobación y Uso con Thunder Client / Postman
Nuestra API diferencia de forma clara dos tipos de accesos según la seguridad implementada (**Módulo D**): **Consultas del Catálogo (Públicas)** y **Acciones de Gestión (Privadas)**. Sigue estos sencillos pasos para verificar su correcto funcionamiento:

#### 🔓 1. Probar Rutas Públicas (Lectura - GET)
Cualquier usuario o lector externo puede consultar los libros o autores disponibles sin necesidad de proporcionar credenciales.
* **Acción:** Crea una nueva petición en Thunder Client o Postman con el método **`GET`**.
* **Dirección (URL):** `http://localhost:8080/api/v1/libros` o `http://localhost:8080/api/v1/autores`
* **Ejecución:** Haz clic en **Send**.
* **Resultado:** Recibirás un código de estado `200 OK` junto a un cuerpo vacío `[]` (si la base de datos acaba de inicializarse), demostrando el libre acceso a la lectura.

#### 🔒 2. Probar Rutas Protegidas (Escritura - POST, PUT, DELETE)
Para simular el rol de un bibliotecario y poder añadir o modificar datos, la API requiere el uso de Autenticación Básica (**Basic Auth**). Si envías una petición de escritura sin identificarte, el servidor responderá con un código `401 Unauthorized` por seguridad.

* **Fase A (Introducir Credenciales):**
  1. En tu ventana de petición de Thunder Client o Postman, dirígete a la pestaña **Auth** (ubicada debajo de la barra de la URL).
  2. Selecciona el tipo de autenticación **Basic**.
  3. Introduce las siguientes claves de administrador configuradas en memoria:
     * **Username:** `admin`
     * **Password:** `admin123`

* **Fase B (Enviar datos en formato JSON - Ejemplo POST):**
  1. Cambia el método de tu petición a **`POST`**.
  2. Configura la URL de destino: `http://localhost:8080/api/v1/libros`
  3. Ve a la pestaña **Body**, asegúrate de marcar la opción **JSON** y pega la estructura del registro que deseas dar de alta:
     ```json
     {
       "titulo": "El Alquimista",
       "isbn": "9788408045052",
       "anioPublicacion": 1988
     }
     ```
  4. Presiona **Send**. La API responderá con un código `201 Created` junto al objeto JSON definitivo que ya incluye su clave primaria (`id`) autoincremental guardada en base de datos.

#### ⚠️ 3. Comprobación del Control de Calidad y Errores
Para verificar la interceptación global de errores solicitada en la práctica, puedes forzar de forma sencilla una respuesta controlada por el software:
* **Acción:** Realiza una petición **`GET`** buscando intencionadamente un identificador inexistente: `http://localhost:8080/api/v1/socios/999`
* **Resultado:** En lugar de crashear o lanzar una pantalla en blanco genérica, el servidor responderá de forma pulida con un estado `404 Not Found` y un JSON estructurado bajo la firma de nuestra clase `ErrorDetalles` con el mensaje exacto: *"No se encontró ningún socio con el ID: 999"*.

---

## ✅ Justificación del Checklist de Evaluación

A continuación, se detalla la justificación técnica de cómo el código fuente entregado cumple rigurosamente con los requisitos del checklist exigido por la cátedra:

### 🟩 [X] El proyecto arranca sin errores con `./mvnw spring-boot:run`
* **Justificación:** El proyecto cuenta con un archivo `BibliotecaApiApplication.java` limpio y libre de código de inicialización ruidoso. El motor de inversión de control (IoC) levanta los contextos de Spring, compila las anotaciones de Lombok y mapea las rutas sin registrar excepciones de colisión ni bloqueos de puertos.

### 🟩 [X] Al menos 2 entidades JPA con todos los campos anotados
* **Justificación:** El sistema cuenta con **3 entidades JPA** completas en el paquete `models`:
    * `Autor.java`: Anotada con `@Entity` y `@Table(name="autores")`.
    * `Libro.java`: Mapea campos básicos y la relación `@ManyToOne` (Muchos Libros a un Autor) con `@JoinColumn(name = "autor_id")`.
    * `Socio.java`: Mapea la relación asociativa muchos a muchos (`@ManyToMany`) delegando en una tabla física intermedia mediante `@JoinTable(name = "prestamos")`.
    * Todos los IDs usan la estrategia de clave autoincremental `GenerationType.IDENTITY` nativa del motor relacional.

### 🟩 [X] CRUD completo en ambas entidades (GET, POST, PUT, DELETE)
* **Justificación:** Se exponen controladores REST para todas las entidades con sus respectivos verbos HTTP mapeados de forma semántica:
    * `GET /api/v1/libros` y `GET /api/v1/libros/{id}` (Lectura completa e individual).
    * `POST /api/v1/libros` (Creación de registros, devuelve `201 Created`).
    * `PUT /api/v1/libros/{id}` (Actualización de registros mediante mutación de estados y guardado asíncrono).
    * `DELETE /api/v1/libros/{id}` (Eliminación física, devuelve `204 No Content`).
    * *Nota:* Las entidades `Socios` y `Autores` replican este comportamiento exacto garantizando un CRUD completo en todo el dominio.

### 🟩 [X] Optional usado correctamente en todos los `findById`
* **Justificación:** Para blindar la aplicación contra excepciones de puntero nulo (`NullPointerException`), las capas de Servicio devuelven tipos abstractos `Optional<T>`. En los controladores, se procesa este flujo de forma funcional estricta:
    ```java
    Libro libro = libroService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún libro con el ID: " + id));
    ```
    Jamás se invoca un método `.get()` de forma directa sin comprobación previa.

### 🟩 [X] Controller nunca accede directamente al Repository
* **Justificación:** Se respeta escrupulosamente el principio de aislamiento arquitectónico por capas (**RA9**). Los controladores (`@RestController`) solo tienen dependencias inyectadas hacia las clases de lógica de negocio (`@Service`). Los servicios son los únicos autorizados para comunicarse con la capa de persistencia (`@Repository`) que extiende de `JpaRepository`.

### 🟩 [X] Repositorio GitHub con commits de ambos miembros
* **Justificación:** El repositorio refleja una trazabilidad transparente en el histórico de Git. Los commits identifican claramente las aportaciones de Fabián González y Daniel, demostrando un co-desarrollo en paralelo a lo largo de las distintas unidades de trabajo de la práctica.

### 🟩 [X] README.md con instrucciones para arrancar el proyecto
* **Justificación:** Satisfecho explícitamente en el bloque correspondiente de este documento.

### 🟩 [X] Documento de diseño con diagrama ER y lista de endpoints
* **Justificación:** Adjunto en la raíz del repositorio en formato PDF. El diagrama de Entidad-Relación define las claves primarias (PK), foráneas (FK) y la cardinalidad de las tablas resultantes (`autores`, `libros`, `socios` y la tabla intermedia de unión `prestamos`).

### 🟩 [X] Capturas o vídeo de las tablas generadas en la BD
* **Justificación:** Se incluyen en el documento de diseño las evidencias visuales del esquema relacional autogenerado por Hibernate a través de las herramientas de inspección de base de datos.

### 🟩 [X] Todos los endpoints probados con Postman/Thunder Client
* **Justificación:** Se ha exportado e incluido en el repositorio la colección de pruebas en formato JSON compatible con **Thunder Client**. Esta colección permite simular en directo el comportamiento de la API frente a peticiones públicas (GET) y protegidas (POST, PUT, DELETE).

---

## 🔒 Módulo D: Justificación Avanzada de Seguridad y Calidad

Para optar a la calificación máxima de la actividad (Nota: 10), el proyecto incorpora los siguientes componentes avanzados del Módulo D:

1.  **Seguridad Perimetral (`SecurityConfig.java`):** Implementa **Spring Security** bajo un esquema de Autenticación Básica HTTP (`httpBasic`). Bloquea con roles restrictivos (`hasRole("ADMIN")`) cualquier operación que altere el estado de los datos (POST, PUT, DELETE), protegiendo la base de datos de accesos no autorizados. Los endpoints de consulta (GET) se mantienen públicos (`permitAll()`) para permitir el acceso al catálogo de la biblioteca.
2.  **Calidad mediante Validaciones Semánticas:** Las entidades cuentan con reglas de validación en origen mediante anotaciones como `@NotBlank` y `@NotNull` (paquete `jakarta.validation.constraints`). Los controladores interceptan y validan estas estructuras en los puntos de entrada usando la anotación `@Valid`.
3.  **Estructura Global de Excepciones:** Se cuenta con una clase de asesoramiento centralizado `@ControllerAdvice` (`GlobalExceptionHandler.java`). Si ocurre un error (como buscar un ID inexistente o un fallo interno del servidor), el interceptor frena el volcado de trazas nativas de Java (que exponen fallos de seguridad) y construye una respuesta JSON limpia, homogeneizada y estructurada mediante la clase de transferencia de datos `ErrorDetalles.java`.
