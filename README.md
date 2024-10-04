
## Introducción

**CommuVerse** es una plataforma web que permite a los creadores de contenido publicar sus artículos y generar ingresos mediante suscripciones y donaciones. Proporciona herramientas de análisis avanzadas para ayudar a los creadores a conocer el impacto de su contenido y su audiencia, todo en un entorno seguro y fácil de usar.

Con el objetivo de garantizar la seguridad de las transacciones y el cumplimiento de normativas locales, CommuVerse ofrece una solución completa para que los creadores puedan monetizar su trabajo, ampliar su comunidad y seguir creando contenido de calidad.



### Colaboradores del Proyecto

| **Nombre**                        | **Rol**                                     | **Perfil**                                                 |
|-----------------------------------|---------------------------------------------|------------------------------------------------------------|
|Stefano Yepez Zapata    |Scrum master| [LinkedIn](https://www.linkedin.com/in/stefano-yepez-zapata-843754243/)           |
| Angelo Sosa Macuyama | Scrum team | [LinkedIn](http://www.linkedin.com/in/angelo-sosa-28800a2a3/)           |
|Pierreluiggi Zevallos Bocanegra   |  Scrum team| [LinkedIn](http://www.linkedin.com/in/rider-pierreluiggi-zevallos-bocanegra-98162a326)           |
|Matias Felipe Alcalde Lavado|  Scrum team| [LinkedIn](https://www.linkedin.com/in/matias-felipe-alcalde-lavado-8a49aa327/)           |
|Clever Aguilar Idrogo| Scrum team| [LinkedIn](https://www.linkedin.com/in/clever-josue-aguilar-idrogo-a1b862307/)           |

### Revisa el Progreso del Proyecto CommuVerse

| **Columna**       | **Descripción**                                                                                                                                    |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backlog**       | Contiene todas las historias de usuario, tareas y características que deben desarrollarse. Es el listado de todo el trabajo pendiente.              |
| **En Progreso**   | Incluye las tareas que están actualmente en desarrollo. Visualiza el trabajo en curso para asegurar el flujo continuo de trabajo.                   |
| **Revisión**      | Después de completar una tarea, se mueve aquí para una revisión de código y revisión por pares (pre review). Esta fase incluye la creación de **pull requests** para asegurar que el código cumpla con los estándares de calidad antes de integrarse al proyecto principal. |
| **En Pruebas**    | Contiene las tareas que han pasado la revisión de código y necesitan pruebas exhaustivas (unitarias, de integración y de aceptación) para garantizar su calidad. |
| **Hecho**         | Las tareas completamente desarrolladas, revisadas y probadas se mueven aquí, indicando que están listas y finalizadas.                               |

Mira cómo va avanzando nuestro trabajo visitando el siguiente enlace: [Tablero de Trello](https://trello.com/b/7DuasOt9/transaccional).

También nos puedes visitar en nuestro canal de youtube: [Canal de Youtube](https://www.youtube.com/@CommuVerseStartup)

### Funcionalidades de la Aplicación CommuVerse

#### **Módulo de Gestión de Usuarios**

- **Creación de Usuarios e Inicio de Sesión:**

    - Permitir a los usuarios registrarse en la plataforma.
    - Facilitar el inicio de sesión para acceder a la cuenta personal.
    - Mantener la seguridad de las credenciales de los usuarios.
    - Notificaciones en tiempo real para responder a los comentarios

#### **Módulo de Gestion Contenidos**

- **Gestión de contenidos:**
    - Los creadores pueden redactar, editar y publicar articulos, con la posibilidad de poner un link de imagen o videos de ser necesario.
    - Organización de articulos mediante categorías para facilitar la búsqueda
    - Permitir a los lectores agregar articulos a una lista de favoritos
    - Comentarios en los articulos, permitiendo la interacción directa entre creadores y lectores

#### **Módulo de Gestion de Monetización**
-
  **Gestión de suscripciones y donaciones**
    - Los creadores pueden habilitar suscripciones mensuales para sus artículos.
    - Opción para que los lectores realicen donaciones únicas a los creadores.
    - Reportes detallados sobre el desempeaño de los articulos: suscripciones generadas.
    

## Diagramas de la Aplicación

Para entender mejor la estructura y diseño de la aplicación "CommuVerse", revisa los siguientes diagramas:

### Diagrama de Clases

![Diagrama de Clases](DiagramaCommuVerse.png)


### Diagrama de Base de Datos

![Diagrama de Base de Datos](DiagramaBD.png)

Este diagrama ilustra el esquema de la base de datos utilizada por la aplicación, mostrando las tablas, columnas, y relaciones entre las entidades.

### Descripción de Capas del Proyecto

| Capa        | Descripción                                                                                  |
|-------------|----------------------------------------------------------------------------------------------|
| api         | Contiene los controladores REST que manejan las solicitudes HTTP y las respuestas.            |
| entity      | Define las entidades del modelo de datos que se mapean a las tablas de la base de datos.      |
| repository  | Proporciona la interfaz para las operaciones CRUD y la interacción con la base de datos.      |
| service     | Declara la lógica de negocio y las operaciones que se realizarán sobre las entidades.         |
| service impl| Implementa la lógica de negocio definida en los servicios, utilizando los repositorios necesarios. |

# Asignación de Historias de Usuario

**Sprint 1:** Funcionalidades Básicas  
  *Enfocado en implementar las funcionalidades esenciales de CRUD para la gestión de usuarios, creadores, lectores, y módulos de contenido y suscripciones, asegurando que el producto permita la publicación y monetización de artículos, ofreciendo un flujo básico de interacción entre creadores y lectores a través de donaciones y suscripciones, para que esté listo para un uso inicial en CommuVerse.*


| Integrante  | Módulo                       | Historia de Usuario                                                   | Descripción                                                                                                                                      
|-------------|------------------------------|----------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------
| Stefano Yepez Zapata| Gestión de Usuario | HU 01: Crear categorías.                              | Como administrador, quiero poder crear, leer, actualizar y eliminar categorías para mantener organizada la colección de libros.                   
|             | Gestión de Usuario           | HU 02: Iniciar sesión.                                  | Como administrador, quiero poder crear, leer, actualizar y eliminar libros para mantener actualizada la oferta disponible en la plataforma.           
| Matias Alcalde Lavado| Gestión de monetización      |HU 16 Crear planes de suscripción                              | Como creador de contenido, quiero configurar diferentes opciones de suscripción para que mis seguidores puedan apoyarme de manera regular según su capacidad.
|             |  Gestión de monetización          | HU 17 Mostrar planes de suscripción | Como creador de contenido quiero visualizar los planes de suscripción que he creado previamente.               
| Angelo Sosa Macuyama| Gestión de de articulo     | HU 11: Buscar articulo.                            | Como usuario quiero poder buscar por palabras claves para encontrar fácilmente contenido de mi interés.                                
|             |Gestión de de articulo     | HU 12:Comentar articulo.                             |Como usuario, quiero dar mi opinión sobre el artículo para interactuar con el creador y la comunidad.                         
| Pierreluiggi Zevallos Bocanegra| Gestión de articulo        | HU 07: Crear articulo. | Como creador de contenido, quiero poder redactar y crear un artículo utilizando una herramienta de edición intuitiva y funcional dentro de la plataforma, de manera que pueda estructurar mi contenido de forma clara y atractiva. Quiero tener la opción de guardar mi trabajo como borrador para seguir editándolo en otro momento o publicarlo directamente cuando esté listo. Además, el sistema debe permitirme agregar etiquetas o categorías para mejorar la visibilidad del artículo y asegurar que los lectores interesados puedan encontrarlo fácilmente.
|             | Gestión de articulo       | HU 08:Editar articulo.      | Como creador de contenido, quiero poder editar un artículo publicado o en borrador para hacer cambios o actualizaciones.                   
| Clever Aguilar Idrogo|Gestión de articulos |HU 14 Agregar a favoritos | Como usuario quiero que la plataforma me permita agregar artículos a la sección de favoritos para poder acceder fácilmente a los artículos que me interesan sin tener que buscarlos nuevamente.                        
|             | Gestion de articulos | HU 15 Eliminar un Favorito     | Como usuario quiero que la plataforma me permita eliminar  artículos de la sección de favoritos con el fin de que ese artículo ya no me aparezca en la sección de favoritos.

