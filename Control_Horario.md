Descripción del sistema de control de horario para la Unidad de Posgrado de la UTEQ
Este proyecto es un sistema de control de horario desarrollado para la Unidad de Posgrado de la Universidad Técnica Estatal de Quevedo (UTEQ). Implementado con la arquitectura Modelo-Vista-Controlador (MVC), el sistema ofrece una separación clara entre la lógica de negocio, la presentación y el control.
La interfaz de usuario se ha construido utilizando PrimeFaces, un framework de componentes de interfaz de usuario para JavaServer Faces (JSF). La plataforma de desarrollo utilizada es Java EE 7 web y se despliega en el servidor Apache Tomcat junto con JDK 1.8.
El sistema ofrece una amplia gama de funcionalidades, entre las que se incluyen:
Inicio de sesión seguro: Los usuarios deben autenticarse mediante un sistema de inicio de sesión para acceder al sistema. Las credenciales de acceso, como el nombre de usuario y la contraseña, son requeridas para garantizar la seguridad y la protección de la información. Dependiendo de su rol y permisos, los usuarios son redirigidos a diferentes áreas del sistema, como la gestión de maestrías, inscripciones, horarios o administración de usuarios.
Ilustración Inicio de sesión
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/0d050614-6258-4277-8b9e-f0a520cf005a)
Página de inicio informativa: Al acceder al sistema, los usuarios son recibidos con una página de inicio que proporciona información general sobre la Unidad de Posgrado. Esta página incluye detalles como la misión, visión y objetivos de la institución, así como noticias y anuncios relacionados con las maestrías y los horarios.
Ilustración Página de inicio
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/86ef4f8b-caa2-4b86-9e83-4daf217d3832)
Registro y modificación de maestrías: El sistema permite el registro y la modificación de información relacionada con las maestrías ofrecidas por la Unidad de Posgrado. Se pueden ingresar detalles como el nombre de la maestría, los módulos asociados y los docentes encargados de impartir las clases en cada módulo.
Ilustración Página de maestrías
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/51edc9b9-366a-4bf6-b2b7-81f6fce64d0e)
Registro de estudiantes e inscripciones: Los estudiantes pueden registrarse en el sistema y posteriormente inscribirse en las maestrías disponibles. El sistema gestiona el proceso de inscripción, manteniendo un registro de los estudiantes inscritos en cada maestría.
Ilustración Página de estudiantes
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/09805825-5334-43cb-85ac-efea6e12fe5d)
Gestión de horarios: El sistema permite registrar y administrar los horarios de las maestrías para cada período académico. Esto incluye la asignación de módulos a días y horas específicas, asegurando una organización eficiente y evitando solapamientos en los horarios.
Ilustración Página de Horarios
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/01616178-483d-4371-ae67-a87f54aa3031)
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/ca1435d6-856f-4a0a-8f96-af737af53461)
Generación de horarios en formato Excel: El sistema tiene la capacidad de generar un archivo Excel que contiene el horario correspondiente a cada maestría y período académico. Esta función simplifica la distribución y visualización del horario para todos los involucrados.
Ilustración Página de Generación de Horarios
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/dfbf01fa-2d34-489c-8c3e-c9b2b6a67f8c)
Panel de administración de roles de usuario: Se ha implementado un panel de administración que permite gestionar los roles de usuario en el sistema. A través de este panel, los administradores pueden crear, modificar y eliminar usuarios, asignando diferentes niveles de acceso y permisos según sea necesario.
Ilustración Página de Administración
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/b67a7a2f-35c4-407a-b699-6777358483dd)
