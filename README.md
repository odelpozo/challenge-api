# API de Usuarios

Esta API permite crear y gestionar usuarios utilizando Spring Boot, JPA y Hibernate. Además, está configurada para aceptar y retornar únicamente datos en formato JSON.

## Requisitos

- Java 17+
- Maven 3.6+
- Spring Boot
- Postman o cURL para pruebas

## Configuración

Para ejecutar esta aplicación localmente:

1. Clona este repositorio.
2. Asegúrate de tener configurado el JDK 17 en tu entorno.
3. Ejecuta el siguiente comando para compilar y ejecutar la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
4. La aplicación estará disponible en http://localhost:8080.

## Configuración de la Base de Datos
Esta API utiliza la base de datos en memoria H2 para el almacenamiento temporal de los datos durante el desarrollo.

Detalles de la configuración de la base de datos:
- El gestor de Hibernate esta disponible en  http://localhost:8080/h2-console/
```bash
URL JDBC: jdbc:h2:mem:testdb
Driver: org.h2.Driver
Usuario: 'sa'
Contraseña: 'password'
DDL-Auto: update (Spring Boot automáticamente creará o actualizará las tablas según las entidades JPA definidas).
```
## Ejemplo passwords validas para probar segun Pattern Regex
```bash
- password1
- abc12345
- Secure2024
```

## Diagrama de la solucion en formato texto
```bash
[Cliente] --> [Controlador de Usuarios (Spring Boot)]
[Controlador de Usuarios] --> [Servicio de Usuarios]
[Servicio de Usuarios] --> [Repositorio de Usuarios]
[Repositorio de Usuarios] --> [Base de Datos] (persistencia)
```


## Ejemplos de pruebas curl  

Prueba-1: Status: 201 Created.
   ```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "name": "Juan Rodriguez",
    "email": "rodriguez12@dominio.cl",
    "password": "Password1!",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'
  ```
Prueba-2: Status: 415 Unsupported Media Type.
   ```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: text/plain" \
-H "Accept: application/json" \
-d '{
    "name": "Juan Rodriguez",
    "email": "rodriguez12@dominio.cl",
    "password": "Password1!",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'
  ```
Prueba-3: Status:  406 Not Acceptable.
   ```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -H "Accept: text/html" \
  -d '{
    "name": "Juan Rodriguez",
    "email": "rodriguez12@dominio.cl",
    "password": "Password1!",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'
  ```
Prueba-4: Status: 415 Unsupported Media Type.
   ```bash
curl -X POST http://localhost:8080/api/users \
  -H "Accept: application/json" \
  -d '{
    "name": "Juan Rodriguez",
    "email": "rodriguez12@dominio.cl",
    "password": "Password1!",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'
  ```

### Resumen de las pruebas:
- **Prueba 1**: Solicitud exitosa que envía y espera JSON (`Content-Type: application/json` y `Accept: application/json`).
- **Prueba 2**: Solicitud con `Content-Type` incorrecto (`text/plain`), que debería devolver un error `415 Unsupported Media Type`.
- **Prueba 3**: Solicitud con `Accept` incorrecto (`text/html`), que debería devolver un error `406 Not Acceptable`.
- **Prueba 4**: Solicitud sin `Content-Type`, que debería devolver un error `415 Unsupported Media Type`.
