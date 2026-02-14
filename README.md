# DAW Proyecto Pokedex 📦

Este repositorio contiene el proyecto de fin de ciclo
"Pokedex" desarrollado en el módulo de Desarrollo de Aplicaciones Web (DAW).
La aplicación es una Pokedex interactiva basada en Spring Boot, Thymeleaf y
Java que gestiona un catálogo de pokémon.

## 📝 Contenido del proyecto

- `src/main/java/com/example/app/` – Código fuente Java, controladores,
  servicios, dominio y excepciones.
- `src/main/resources/` – Recursos estáticos (CSS, JavaScript, imágenes) y
  webs con Thymeleaf.
- `pom.xml` – Configuración de Maven.
- `mvnw`, `mvnw.cmd` – Wrappers de Maven para ejecutar sin instalación previa.

## 🚀 Requisitos

1. Java 17 (o superior).
2. Maven (se puede usar el wrapper incluido).
3. Un navegador web moderno para la interfaz.
4. Mvn installed

## 🛠️ Configuración y ejecución

```bash
# Clonar el repositorio
git clone https://github.com/MarquesitoClase/DAW-proyect-Pokedex.git
cd DAW-proyect-Pokedex-

# Construir el proyecto
./mvnw clean package   # (Windows: mvnw.cmd clean package)

# Ejecutar la aplicación
./mvnw spring-boot:run
# o ejecutar el jar generado:
# java -jar target/*.jar
```

La aplicación estará disponible en `http://localhost:8080`.

## 📂 Estructura de datos

Los pokémon se cargan desde `src/main/resources/static/json/pokemon.json`.
Se pueden visualizar, editar y eliminar desde la interfaz web.

## 🧪 Pruebas

Actualmente no hay pruebas automatizadas. Para futuros desarrollos se
recomienda añadir tests unitarios con JUnit y Mockito.

## 📄 Licencia

Este proyecto está bajo licencia MIT. Consulta el archivo `LICENSE` para más
detalles (si se añade más adelante).

## 👨‍💻 Autor

- Juan Luis Marquez Canedo (jlmsc) – desarrollador del proyecto.

---

¡Gracias por revisar este trabajo de fin de ciclo! Espero que te sea útil y
que puedas continuar desarrollando nuevas funcionalidades.
