# Alquiler de Vehículos

## Descripción

Este proyecto permite gestionar un sistema de alquiler de vehículos con funcionalidades para la creación, consulta, edición y eliminación de usuarios, medios de pago, tarjetas y vehículos. Además, soporta la creación y consulta de alquileres, con la capacidad de actualizar el estado de los alquileres y guardar automáticamente la fecha de finalización cuando el estado se cambia a "Cerrado".

## Funcionalidades

### Usuarios

- **Crear usuarios**: Permite registrar nuevos usuarios en el sistema.
- **Consultar usuarios**: Permite obtener información de los usuarios registrados.
- **Eliminar usuarios**: Permite eliminar usuarios del sistema.

### Medios de Pago

- **Crear medios de pago**: Permite registrar nuevos métodos de pago que se pueden asociar a los usuarios.
- **Editar medios de pago**: Permite modificar los detalles de los métodos de pago existentes.
- **Eliminar medios de pago**: Permite eliminar métodos de pago del sistema.

### Tarjetas

- **Agregar tarjetas**: Permite a los usuarios añadir tarjetas para realizar pagos.
- **Editar tarjetas**: Permite modificar los detalles de las tarjetas existentes.
- **Eliminar tarjetas**: Permite eliminar tarjetas del sistema.

### Vehículos

- **Crear vehículos**: Permite registrar nuevos vehículos en el sistema.
- **Editar vehículos**: Permite modificar los detalles de los vehículos existentes.
- **Eliminar vehículos**: Permite eliminar vehículos del sistema.

### Alquileres

- **Crear alquileres**: Permite registrar nuevos alquileres de vehículos.
- **Consultar alquileres**: Permite obtener información de los alquileres registrados.
- **Actualizar estado de alquileres**: Permite cambiar el estado de un alquiler y, al cambiar el estado a "Cerrado", se guarda automáticamente la fecha de finalización del alquiler.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework para la creación de aplicaciones web.
- **JPA (Java Persistence API)**: Para la gestión de la persistencia de datos.
- **MySQL**: Base de datos relacional utilizada para almacenar la información.
- **Git**: Sistema de control de versiones.
