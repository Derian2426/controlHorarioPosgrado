Descripción del sistema de control de horario para la Unidad de Posgrado de la UTEQ

Este proyecto es un sistema de control de horario desarrollado para la Unidad de Posgrado de la Universidad Técnica Estatal de Quevedo (UTEQ). El sistema se ha implementado utilizando la arquitectura Modelo-Vista-Controlador (MVC) para lograr una separación clara entre la lógica de negocio, la presentación y el control.

La interfaz de usuario se ha construido utilizando PrimeFaces, un framework de componentes de interfaz de usuario para JavaServer Faces (JSF). Hemos utilizado Java EE 7 web como plataforma de desarrollo y el servidor Apache Tomcat junto con JDK 1.8 para desplegar la aplicación.

Las principales funcionalidades del sistema incluyen:

Inicio de sesión: garantiza la seguridad y la autenticación de los usuarios. Los usuarios deben proporcionar sus credenciales de acceso (nombre de usuario y contraseña) para acceder al sistema. Dependiendo de su rol y permisos, se les redirige a diferentes áreas del sistema, como la gestión de maestrías, inscripciones, horarios o administración de usuarios. El inicio de sesión garantiza que solo los usuarios autorizados puedan acceder a las funcionalidades y los datos del sistema, manteniendo así la confidencialidad y la integridad de la información.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/0d050614-6258-4277-8b9e-f0a520cf005a)

Página de inicio: es la primera interfaz que los usuarios ven al acceder al sistema. Proporciona información general sobre la Unidad de Posgrado, como su misión, visión y objetivos. También puede incluir noticias y anuncios relevantes relacionados con las maestrías y los horarios.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/86ef4f8b-caa2-4b86-9e83-4daf217d3832)

Registro y modificación de maestrías: El sistema permite registrar y modificar la información de las maestrías ofrecidas por la Unidad de Posgrado. Esto incluye detalles como el nombre de la maestría, los módulos asociados y los docentes encargados de impartir las clases en cada módulo.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/51edc9b9-366a-4bf6-b2b7-81f6fce64d0e)

Registro de estudiantes e inscripciones: Los estudiantes pueden ser registrados en el sistema y posteriormente inscritos en las maestrías disponibles. El sistema gestiona el proceso de inscripción, manteniendo un registro de los estudiantes inscritos en cada maestría.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/09805825-5334-43cb-85ac-efea6e12fe5d)

Gestión de horarios: El sistema permite registrar los horarios de las maestrías para cada período académico. Esto incluye la asignación de módulos a días y horas específicas, así como la gestión de posibles solapamientos en los horarios.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/01616178-483d-4371-ae67-a87f54aa3031)
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/ca1435d6-856f-4a0a-8f96-af737af53461)

Generación de horarios en formato Excel: El sistema es capaz de generar un archivo Excel que contiene el horario correspondiente a cada maestría y período académico. Esto facilita la distribución y visualización del horario para todos los involucrados.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/dfbf01fa-2d34-489c-8c3e-c9b2b6a67f8c)

Panel de administración de roles de usuario: Se ha implementado un panel de administración que permite gestionar los roles de usuario en el sistema. Esto incluye la creación, modificación y eliminación de usuarios con diferentes niveles de acceso y permisos.
![image](https://github.com/Derian2426/controlHorarioPosgrado/assets/87103650/b67a7a2f-35c4-407a-b699-6777358483dd)

