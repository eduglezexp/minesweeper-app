## 🧩 Buscaminas

<p align="center">
  <img src="img/logo.png" alt="Logo Buscaminas" width="200" />
</p>

> **El único juego en el que al hacer clic en el cuadrado equivocado te hará cuestionar todas tus decisiones de vida.**

---

## 📋 Índice

1. [🔍 Descripción general](#descripción-general)  
2. [✨ Características](#características)  
   2.1. [🎮 Modos de juego](#modos-de-juego)  
   2.2. [🏬 Tienda, Objetos e Inventario](#tienda-objetos-e-inventario)  
   2.3. [🎨 Temas y Avatares](#temas-y-avatares)  
   2.4. [🌐 Multiidioma](#multiidioma)  
3. [🛠️ Tecnologías utilizadas](#tecnologías-utilizadas)  
4. [ℹ️ Información adicional](#información-adicional)  
   4.1. [🚀 Mejoras planeadas](#mejoras-planeadas)  
   4.2. [⚙️ Desafíos técnicos](#desafíos-técnicos)  
   4.3. [📚 Lecciones aprendidas](#lecciones-aprendidas)  
5. [💻 Requisitos del sistema](#requisitos-del-sistema)  
6. [🚧 Guía de instalación](#guía-de-instalación)  
7. [📖 Manual de usuario](#manual-de-usuario)  
8. [🤝 Contribuciones](#contribuciones)  
9. [📝 Licencia](#licencia)  

---

## 🔍 Descripción general

Versión mejorada del clásico Buscaminas, con modos de juego adicionales, tienda de potenciadores, personalización visual y soporte multilingüe. Ideal tanto para nostálgicos como para nuevos jugadores.

<p align="center">
  <img src="ruta/a/captura_inicial.png" alt="Captura de pantalla inicial" width="600" />
</p>

---

## ✨ Características

### 🎮 Modos de juego
- **Fácil, Normal, Difícil**: los tres niveles clásicos.  
- **Aleatorio**: genera un tablero con tamaño y minas al azar.  
- **Personalizado**: tú eliges filas, columnas y número de minas.

### 🏬 Tienda, Objetos e Inventario

<p align="center">
  <img src="ruta/a/tienda.png" alt="Tienda de objetos" width="600" />
</p>

- **Escudo**: protege de una bomba.  
- **Fantasma**: revela el mapa de minas durante 5 segundos.  
- **Alquimia**: convierte una mina en escudo, convierte una celda en segura… ¡o explota!

#### ¿Cómo usarlos?

<p align="center">
  <img src="ruta/a/uso_potenciadores.gif" alt="Uso de potenciadores" width="600" />
</p>

---

### 🎨 Temas y Avatares

- **Temas**: claro, oscuro y retro.

<p align="center">
  <img src="ruta/a/uso_temas.gif" alt="Cambio de temas" width="600" />
</p>

- **Avatares**: selección de personajes para tu perfil.

<p align="center">
  <img src="ruta/a/uso_avatares.gif" alt="Elección de avatares" width="600" />
</p>

---

### 🌐 Multiidioma

Disponible en:  
- 🇪🇸 Español  
- 🇬🇧 Inglés  
- 🇫🇷 Francés  

<p align="center">
  <img src="ruta/a/uso_idioma.gif" alt="Cambio de idioma" width="600" />
</p>

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Descripción                             |
|------------|---------|-----------------------------------------|
| Java       | 17      | Lenguaje principal                     |
| JavaFX     | 21      | Framework de interfaz gráfica          |
| SQLite     | 3.45.2  | Persistencia de datos                  |
| CSS        | —       | Estilos y temas                        |
| Maven      | 3.9.6   | Gestión de proyecto y dependencias     |

---

## ℹ️ Información adicional

### 🚀 Mejoras planeadas
- Skins desbloqueables con puntos en la tienda.  
- Modo contrarreloj (“Against Clock Timer”).  
- Slider para ajustar la cantidad de un objeto al comprar.  
- Guardado de la partida actual en la base de datos.  
- Selección de imagen personalizada para el perfil.  
- Clasificación global (Leaderboard).  
- “Infierno Mode”: minas rotativas.  
- Compatibilidad con múltiples resoluciones de pantalla.

### ⚙️ Desafíos técnicos
- Implementar el cambio dinámico de estilos al aplicar temas y avatares.  
- Diseñar una tienda e inventario intuitivos y escalables.

### 📚 Lecciones aprendidas
- Buenas prácticas de MVC con JavaFX.  
- Manejo de concurrencia para temporizadores y animaciones.  
- Persistencia ligera con SQLite.

---

## 💻 Requisitos del sistema

- Java 17 o superior  
- Maven 3.6+  
- Windows 10+, macOS 10.14+ o Linux con entorno gráfico

---

## 🚧 Guía de instalación

```bash
# Clonar repositorio
git clone https://github.com/eduglezexp/minesweeper-app.git

# Entrar al directorio del proyecto
cd minesweeper-app

# Compilar y ejecutar
mvn clean javafx:run
```

---

## 📖 Manual de usuario

1. Selecciona modo de juego y nivel de dificultad.  
2. Compra potenciadores desde la tienda si lo deseas.  
3. Haz clic derecho para marcar minas, izquierdo para despejar casillas.  
4. ¡Diviértete y no pises ninguna mina!

---

## 🤝 Contribuciones

¡Todas las contribuciones son bienvenidas!  
1. Haz un *fork* del repositorio.  
2. Crea una rama con tu feature: `git switch -c feature/nombre` 
3. Haz *commit* de tus cambios: `git commit -m "Añade nueva característica"`.  
4. Envía un *pull request*.

---

## 📝 Licencia

Este proyecto está bajo la licencia MIT.

<p align="center">
  <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="Licencia MIT" />
</p>