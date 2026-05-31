# BIBLIOTECA-GRUPO-2
# Informe de Diseño e Implementación: Sistema de Gestión "Biblioteca 2.0"

# 1. Introducción y Propósito del Sistema

"Biblioteca 2.0" es una aplicación de escritorio desarrollada en **Java SE** con un entorno gráfico construido en **Swing**. El sistema resuelve de manera eficiente la gestión de inventario y el control de usuarios dentro de una biblioteca, permitiendo realizar operaciones clave como:
* Alta y administración de materiales.
* Registro y control de usuarios.
* Gestión transaccional de préstamos y devoluciones.

El objetivo principal es optimizar la administración interna mediante interfaces intuitivas, aplicando reglas de negocio estrictas que impiden préstamos excedidos y mantienen un control absoluto sobre qué materiales tiene asignado cada usuario.

# 2. Encapsulamiento y Validaciones

Toda la lógica de datos sensibles se encuentra protegida mediante modificadores de acceso `private`. El acceso y modificación de los atributos se realiza exclusivamente a través de métodos mutadores y accesores (**Getters y Setters**), los cuales incorporan lógica de validación previa.

### Clases con Encapsulamiento

* **`AbstractMaterial`:** Sus atributos (`id`, `title`, `isAvailable`) son `private`. Expone métodos públicos para leerlos (`getId`, `getTitle`, `isAvailable`) y restringe la modificación de la disponibilidad únicamente mediante `setAvailable`.
* **`Book`:** Su atributo específico (`author`) es `private` y se accede o modifica mediante `getAuthor()` y `setAuthor()`.
* **`Magazine`:** Su atributo específico (`issueNumber`) es `private` y cuenta con sus respectivos métodos `getIssueNumber()` y `setIssueNumber()`.
* **`User`:** Sus atributos (`carnet`, `name`, `activeLoans`) son `private`. Tiene métodos para leerlos y restringe la forma en que se modifica la lista de préstamos a través de métodos específicos (`addLoan` y `removeLoan`) en lugar de dejar la colección expuesta a modificaciones externas directas.
* **`FilePersistence`:** Esta clase no maneja encapsulamiento de datos propiamente, ya que es una clase utilitaria basada en métodos estáticos (`static`) para leer y escribir archivos. No almacena un estado interno que requiera ser protegido.

### Clases con Validaciones Activas

* **`User`:** Es la única clase del modelo que valida activamente sus datos:
  * **Validación en `setName(String name)`:** Verifica si el nombre es nulo (`null`), está vacío o lleno de espacios en blanco (`name.trim().isEmpty()`). Si no cumple, frena el flujo lanzando un `IllegalArgumentException`. Al llamarse desde el constructor, asegura que ningún usuario nazca sin un nombre válido.
  * **Validación en `addLoan(AbstractMaterial material)`:** Implementa una regla de negocio que limita los préstamos activos. Solo añade el material a la lista si el usuario tiene menos de 3 elementos prestados (`activeLoans.size() < 3`).
* **`FilePersistence`:** Posee validaciones de tipo estructural y de flujo. Aunque no valida objetos de negocio, valida el entorno y la integridad de los datos del archivo externo:
  * **Validación de existencia:** Verifica si el archivo existe antes de intentar leerlo (`if (!file.exists()) return inventory;`).
  * **Validación de longitud:** Al separar las líneas por el delimitador `;`, valida que los arreglos tengan la estructura mínima esperada antes de procesarla (`if (data.length < 5) continue;` y `if (data.length < 2) continue;`).
  * **Manejo de Excepciones:** Usa bloques `try-catch` para capturar errores de entrada/salida (`IOException`) o errores de formato numérico (`NumberFormatException`), evitando que la aplicación sufra una caída crítica si el archivo CSV se encuentra corrupto.

 **Nota de Diseño:** Las clases `AbstractMaterial`, `Book` y `Magazine` no poseen validaciones internas en su modelo. Los constructores reciben los parámetros (`id`, `title`, `author`, `issueNumber`) y los asignan directamente a los atributos sin comprobar si vienen vacíos, nulos o si el número de edición de la revista es un valor negativo (dejando dicha responsabilidad a la capa de vista).

# 3. Herencia (Estructura Jerárquica de POO)

La herencia se utilizó para reutilizar código y crear estructuras jerárquicas limpias a través de la palabra clave `extends`.

### 1. `Book` (Libro) es hija de `AbstractMaterial`
* **¿Qué hereda?:** Los atributos `id`, `title` e `isAvailable`, así como sus métodos `getId()`, `getTitle()`, `isAvailable()` y `setAvailable()`.
* **Reutilización del constructor:** En su constructor, `Book` utiliza `super(id, title);` para invocar al constructor de la clase padre y delegar la inicialización de esos datos comunes.
* **Sobrescritura (`@Override`):** Redefine el método abstracto `getMaxLoanDays()` para especificar que un libro se presta por un máximo de **15 días**.

### 2. `Magazine` (Revista) es hija de `AbstractMaterial`
* **¿Qué hereda?:** Comparte exactamente la misma base (ID, título y disponibilidad) sin necesidad de duplicar código en los atributos.
* **Reutilización del constructor:** También usa `super(id, title);` para inicializar la parte del material abstracto.
* **Sobrescritura (`@Override`):** Modifica el comportamiento de `getMaxLoanDays()` para devolver **3 días**, cumpliendo con la regla de negocio diferenciada para las revistas.

# 4. Persistencia en Archivos

El ciclo de vida de la información pasa por dos fases críticas gestionadas en el paquete de persistencia de datos:

1. **Carga Inicial:** Al arrancar la aplicación, el sistema lee los archivos de texto plano en formato **CSV** (separado por punto y coma `;`). El módulo lee línea por línea, parsea las cadenas de texto correspondientes, reconstruye los objetos específicos (`Book` o `Magazine`) y los inserta en las colecciones dinámicas (`ArrayList`) en memoria RAM.
2. **Guardado Automático:** Al realizar transacciones críticas (como registrar un préstamo exitoso o un nuevo elemento), el sistema reescribe los cambios de vuelta en el archivo CSV de forma limpia. Esto asegura que si el usuario cierra la aplicación de imprevisto, no ocurra pérdida de datos. Los errores de lectura/escritura (`IOException`) se capturan mediante bloques `try-catch` y se notifican amigablemente al usuario a través de la interfaz gráfica de Swing.
