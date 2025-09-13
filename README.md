# 🔴 Pokédex App

> Una aplicación Android estilo **Pokédex** que consume la [PokeAPI](https://pokeapi.co/) para mostrar información de los Pokémon en tiempo real.  
Construida en **Java** con **Retrofit** y **Glide**, y diseñada con una interfaz estilo Pokédex clásica.

---

## 📸 Vista previa

<img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png" width="120" alt="pikachu" />

Pantalla principal con búsqueda, selección de sprites/artworks y detalles de Pokémon.

---

## 🧾 Características

- 🔍 **Búsqueda por nombre o ID** (ejemplo: `pikachu` o `25`)  
- 🎲 **Pokémon aleatorio** con un botón dedicado  
- ✨ **Modo Shiny** (CheckBox)  
- 🔄 **Sprites** vs **Artwork oficial** (ToggleButton)  
- ↔️ **Frente/Espalda** (RadioButtons)  
- ℹ️ **Mostrar/ocultar detalles** (SwitchCompat)  
- 🖼️ **Carga de imágenes** con Glide  
- ❌ **Botón limpiar** para reiniciar la UI  
- ⚠️ Manejo de errores (Pokémon no encontrado o problemas de conexión)  

---

## 🏗️ Arquitectura

- **Lenguaje:** Java  
- **UI:** XML con estilo inspirado en la Pokédex (rojo, amarillo, negro, blanco)  
- **Cliente HTTP:** Retrofit2  
- **Imágenes:** Glide  
- **API:** [PokeAPI v2](https://pokeapi.co/api/v2/)  




