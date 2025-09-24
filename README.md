Proyecto Final: Gestión de Ventas para Supermercado

Descripción

És una API RESTful desarrollada con Spring Boot para gestionar ventas en una cadena de supermercados 
con autenticación JWT y documentación Swagger.
Permite administrar:
- Productos
- Sucursales
- Ventas
- Usuarios
- Estadísticas

Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- JWT (autenticación)
- Swagger (documentación)
- Postman (testing)


Funcionalidades principales

Productos
- `GET /api/productos` → Listar productos
- `POST /api/productos` → Crear producto
- `PUT /api/productos/{id}` → Actualizar producto
- `DELETE /api/productos/{id}` → Eliminar producto

Sucursales
- `GET /api/sucursales` → Listar sucursales
- `POST /api/sucursales` → Crear sucursal
- `PUT /api/sucursales/{id}` → Actualizar sucursal
- `DELETE /api/sucursales/{id}` → Eliminar sucursal

Ventas
- `POST /api/ventas` → Registrar venta
- `GET /api/ventas?sucursalId=1&fecha=2025-09-24` → Consultar ventas por sucursal y fecha
- `DELETE /api/ventas/{id}` → Anular venta (borrado lógico)

Estadísticas
- `GET /api/estadisticas/producto-mas-vendido` → Consultar producto más vendido

Autenticación
- `POST /api/auth/register` → Registro de usuario
- `POST /api/auth/login` → Login y generación de token JWT

Documentación Swagger

La API cuenta con documentación interactiva generada automáticamente con Swagger UI.

Accede desde: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html#/)

Incluye modelos como:
- `ProductoDto`
- `SucursalDto`
- `VentaDto`
- `DetalleVentaDto`
- `Usuario`

Testing

- Validación manual con Postman
- Tests unitarios e integración con rollback automático
- Validación de casos negativos y flujos de error
- Configuración de entornos JWT en Postman

