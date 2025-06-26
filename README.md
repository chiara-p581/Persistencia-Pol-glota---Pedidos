# 📦 Proyecto Final - Ingeniería de Bases de Datos II

Este es el Trabajo Práctico Obligatorio de la materia **Ingeniería de Bases de Datos II**, desarrollado en Java con **Spring Boot** e integrando múltiples tecnologías de persistencia.  
🔍 **Sin frontend:** Todas las interacciones se realizan por consola (CLI).

---

## 🧩 Tecnologías utilizadas

- ☕ Java 17 / Spring Boot 3.2+
- 🗄️ SQL Server (Base relacional principal)
- 🍃 MongoDB (Historial de cambios del catálogo)
- 🌐 Cassandra (Registro de eventos de usuarios)
- ⚡ Redis (Caché en memoria - opcional)
- 🐳 Docker (para contenerización de servicios)

---

## ⚙️ Arquitectura del sistema

Este sistema simula una **aplicación de gestión de pedidos, facturación y pagos**, con persistencia poliglota.  
Cada tipo de base de datos se utiliza según la naturaleza de los datos a almacenar:

| Componente                        | Base de datos      |
|----------------------------------|--------------------|
| Usuarios, pedidos, productos     | 🗄️ SQL Server      |
| Historial del catálogo           | 🍃 MongoDB         |
| Eventos de usuario (login, etc.) | 🌐 Cassandra        |
| Caché (por ejemplo, listas)      | ⚡ Redis (opcional) |

---

## 🔑 Funcionalidades

- ✅ Registro e inicio de sesión de usuarios
- 🛒 Gestión de carritos (agregar, modificar, eliminar productos)
- 📦 Conversión del carrito en pedido
- 🧾 Facturación de pedidos
- 💳 Registro de pagos
- 📚 Visualización y edición del catálogo de productos
- 🧠 Registro histórico de cambios del catálogo (MongoDB)
- ⏱️ Registro de logins y cálculo de duración de sesiones (Cassandra)
- 📈 Clasificación de usuarios según nivel de actividad
- 🔄 Integración y persistencia entre múltiples bases

---

## 🧪 Consideraciones técnicas

- Sistema completamente interactivo por consola
- Uso de entidades embebidas (e.g., Cassandra con `@PrimaryKeyClass`)
- Relación entre entidades vía JPA (SQL)
- Auditoría de modificaciones del catálogo usando MongoDB
- Registro de eventos con timestamp y cálculo de duración vía Cassandra
- Redis puede ser utilizado para almacenar datos frecuentemente consultados

---

## 📂 Estructura del proyecto

```text
src/
 └── main/
     ├── java/
     │   └── com/example/demo/
     │       ├── modelo/          # Entidades de SQL, MongoDB, Cassandra
     │       ├── servicio/        # Lógica de negocio (servicios)
     │       ├── datos/           # Repositorios (JPA, Mongo, Cassandra)
     │       └── DemoApplication  # Clase principal con menú por consola
     └── resources/
         └── application.properties
```

## 🚀 Cómo ejecutar


1. Asegurate de tener Docker Desktop.

2. Iniciá los servicios requeridos en la terminal del proyecto, dentro de la carpeta Demo:
   ```bash
   docker-compose up -d
  
3. Conectate a Cassandra (por ejemplo, con cqlsh dentro del contenedor) y ejecutá la siguiente tabla manualmente:
```bash
CREATE TABLE IF NOT EXISTS pedidos.eventosUsuario (
    idUsuario UUID,
    timestamp timestamp,
    tipoEvento text,
    descripcion text,
    productoId int,
    monto int,
    horaInicio timestamp,
    horaFin timestamp,
    PRIMARY KEY (idUsuario, timestamp)
) WITH CLUSTERING ORDER BY (timestamp DESC);
```
4. Verificá que los contenedores estén corriendo correctamente:

- SQL Server en localhost:1433
- MongoDB en localhost:27017
- Cassandra en localhost:9042
- Redis en localhost:6379

5. Finalmente, ejecutá el proyecto desde tu IDE (por ejemplo IntelliJ o Eclipse) corriendo la clase DemoApplication.java.
