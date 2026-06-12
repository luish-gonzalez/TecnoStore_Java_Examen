# Examen Final – Generación de Reporte Global de Gestión

## Descripción

Este proyecto corresponde a la implementación de la funcionalidad adicional **“Generación de Reporte Global de Gestión”** para el sistema TecnoStore.

La solución fue desarrollada en un paquete independiente denominado:

```text
test
```

sin modificar la arquitectura principal del proyecto.

La funcionalidad permite generar un reporte consolidado de información comercial y operativa del sistema, obteniendo los datos directamente desde MySQL mediante JDBC y generando un archivo de salida denominado:

```text
reporte_global.txt
```

La conversación con chatGPT para el desarrollo de este examen es la siguiente:

[https://chatgpt.com/g/g-6a1f873b51d081919341add6e2bc23f5-guia-java/c/6a1fa371-72fc-83e9-90a9-f49b2f4d9ef0
](https://chatgpt.com/c/6a2b5ad8-72e8-83e9-b5c2-9d1d815cccbe)
---

# Objetivos del Examen

Implementar un reporte global que incluya:

- Total de ventas realizadas.
- Cantidad de celulares vendidos agrupados por modelo.
- Clientes con créditos pendientes y saldo adeudado.
- Stock actual de productos.
- Alertas de bajo stock.
- Generación de archivo de reporte.
- Uso de JDBC.
- Uso de Stream API.
- Aplicación de POO.
- Aplicación de principios SOLID.
- Aplicación de patrón Singleton.

---

# Estructura del Paquete Test

```text
test/
├── Credito.java
├── IReporteService.java
├── ReporteService.java
├── VentaModeloDTO.java
└── EjecutarReporteGlobal.java
```

---

# Descripción de Clases

## Credito

Representa un crédito asociado a un cliente.

### Responsabilidad

- Almacenar información de saldo pendiente.
- Relacionar créditos con clientes.

### Concepto aplicado

Composición:

```text
Credito
 └── Cliente
```

---

## IReporteService

Interfaz que define las operaciones principales del servicio de reportes.

### Métodos

```java
String generarTextoReporteGlobal();

void generarArchivoReporteGlobal();
```

### Principio SOLID aplicado

DIP (Dependency Inversion Principle).

---

## ReporteService

Clase encargada de generar toda la información del reporte.

### Funcionalidades

- Obtener total de ventas.
- Obtener celulares vendidos por modelo.
- Obtener clientes con créditos pendientes.
- Obtener stock actual.
- Generar reporte completo.
- Exportar reporte a archivo.
- Demostrar uso de Stream API.

### Patrón aplicado

Singleton.

---

## VentaModeloDTO

Objeto de transferencia de datos utilizado para demostrar agrupaciones mediante Stream API.

### Responsabilidad

Transportar:

```text
modelo
cantidad
```

desde JDBC hacia colecciones Java.

---

## EjecutarReporteGlobal

Clase de prueba para ejecutar el examen de manera independiente.

### Método principal

```java
public static void main(String[] args)
```

Permite:

- Generar reporte global.
- Crear archivo TXT.
- Mostrar evidencia de Stream API.

---

# Base de Datos

## Tabla Créditos

```sql
CREATE TABLE IF NOT EXISTS creditos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    saldo DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_cliente)
    REFERENCES clientes(id)
);
```

### Datos de prueba

```sql
INSERT INTO creditos
(id_cliente, saldo, estado)
VALUES
(1, 250000, 'PENDIENTE'),
(2, 120000, 'PENDIENTE');
```

---

# JDBC Utilizado

El reporte obtiene información desde MySQL utilizando:

```java
Connection
PreparedStatement
ResultSet
```

Consultas implementadas:

### Total de ventas

```sql
SELECT SUM(total)
FROM ventas;
```

### Celulares vendidos por modelo

```sql
SELECT modelo,
       SUM(cantidad)
FROM detalle_ventas
GROUP BY modelo;
```

### Créditos pendientes

```sql
SELECT cliente,
       saldo
FROM creditos;
```

### Stock actual

```sql
SELECT modelo,
       stock
FROM celulares;
```

---

# Programación Orientada a Objetos

## Clases

- Cliente
- Celular
- Venta
- ItemVenta
- Credito

---

## Encapsulamiento

Todos los atributos son privados y se accede a ellos mediante:

```java
getters
setters
```

---

## Composición

```text
Venta
 └── ItemVenta

Credito
 └── Cliente
```

---

# Principios SOLID

## SRP (Single Responsibility Principle)

Cada clase tiene una responsabilidad única.

Ejemplos:

```text
Credito → representar créditos

ReporteService → generar reportes
```

---

## OCP (Open Closed Principle)

La interfaz:

```java
IReporteService
```

permite extender nuevos tipos de reportes sin modificar código existente.

---

## DIP (Dependency Inversion Principle)

La aplicación depende de:

```java
IReporteService
```

y no exclusivamente de:

```java
ReporteService
```

---

# Patrón de Diseño

## Singleton

Implementado en:

```java
ReporteService
```

### Objetivo

Garantizar una única instancia del generador de reportes.

### Implementación

```java
private static ReporteService instancia;

public static ReporteService getInstancia()
```

---

# Stream API

Se implementó una demostración utilizando:

```java
stream()
Collectors.groupingBy()
Collectors.summingInt()
Lambda Expressions
```

Ejemplo:

```java
ventas.stream()
      .collect(Collectors.groupingBy(
              VentaModeloDTO::getModelo,
              Collectors.summingInt(
                      VentaModeloDTO::getCantidad
              )
      ));
```

---

# Manejo de Excepciones

Se implementó manejo de:

## SQLException

Para errores de conexión y consultas MySQL.

```java
catch (SQLException e)
```

---

## IOException

Para errores durante la generación del archivo.

```java
catch (IOException e)
```

---

# Generación de Archivo

El reporte es exportado automáticamente a:

```text
reporte_global.txt
```

Utilizando:

```java
FileWriter
PrintWriter
```

---

# Ejecución

Ejecutar:

```text
test/EjecutarReporteGlobal.java
```

---

# Salida Esperada

```text
--- REPORTE GLOBAL TECNOSTORE ---

Total de ventas: $12.450.000

Celulares vendidos por modelo:
- Samsung A55: 10 unidades
- iPhone 13: 6 unidades
- Xiaomi Note 13: 8 unidades

Clientes con créditos pendientes:
- Laura Gómez → $250.000
- Carlos Ríos → $120.000

Stock actual:
- Samsung A55: 3 unidades (ALERTA: bajo stock)
- iPhone 13: 12 unidades
- Xiaomi Note 13: 2 unidades (ALERTA: bajo stock)

Reporte guardado en:
reporte_global.txt
```

---

# Requisitos Tecnológicos

- Java 17
- MySQL
- JDBC
- Stream API
- Lambda Expressions
- Programación Orientada a Objetos
- Principios SOLID
- Patrón Singleton

---

# Autor

Examen de implementación de funcionalidad adicional para el proyecto TecnoStore.

Desarrollado siguiendo arquitectura MVC, JDBC, Stream API, POO, SOLID y patrones de diseño.
