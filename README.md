# DAW Proyecto Pokedex 📦

Este repositorio contiene el proyecto 
"Pokedex" desarrollado para el ciclo de Desarrollo de Aplicaciones Web (DAW) como proyecto final del mismo
La aplicación es una Pokedex interactiva basada en Spring Boot, Thymeleaf y
Java, que gestiona un catálogo de pokémon, permitiendo un CRUD de la pokedex.

## 📝 Contenido del proyecto

- `src/main/java/com/example/app/` – Código fuente Java, controladores,
  servicios, dominio y excepciones.
- `src/main/resources/` – Recursos estáticos (CSS, JavaScript, imágenes) y
  webs con Thymeleaf en Templates.
- `pom.xml` – Configuración de Maven.
- `mvnw`, `mvnw.cmd` – Wrappers de Maven para ejecutar sin instalación previa.

## 🚀 Requisitos

1. Java 17 (o superior).
2. Maven.

## 🛠️ Configuración y ejecución

```bash
# Clonar el repositorio
git clone https://github.com/MarquesitoClase/DAW-proyect-Pokedex.git
cd DAW-proyect-Pokedex

# Construir el proyecto
./mvnw clean package   # (Windows: mvnw.cmd clean package)

# Ejecutar la aplicación
./mvnw spring-boot:
## ejecutar el jar generado:
## java -jar target/*.jar

##O lanzar la app desde el IDE que uses.
```

La aplicación estará disponible en `http://localhost:9000`.

## 📂 Estructura de datos

Los pokémon se cargan desde `src/main/resources/static/json/pokemon.json`.
Se pueden visualizar, editar y eliminar desde la interfaz web.

## 🧪 Pruebas

Actualmente no hay pruebas automatizadas. Para futuros desarrollos se
recomienda añadir tests unitarios con JUnit y Mockito.

## 📄 Licencia

Este proyecto está bajo licencia CC By.
## 👨‍💻 Autor

- Juan Luis Marquez Canedo – desarrollador del proyecto.

---
