let pokemonDisponibles = [];

document.addEventListener("DOMContentLoaded", function () {
    inicializarTablaPokemon();
    inicializarToolbar();
    actualixarImgEfectividades();
    cargarPokemonDisponibles();
});

function inicializarTablaPokemon() {
    setTimeout(function () {
        const TablaPokemon = document.getElementById("tablaPokemon");
        for (let index = 0; index < TablaPokemon.rows.length; index++) {
            const element = TablaPokemon.rows[index];
            element.addEventListener("click", function (event) {
                const h2Element = element.querySelector("h2");
                const texto = h2Element.textContent;

                const partes = texto.split(":");
                const numeroDex = partes[0].replace("#", "");

                const url = `http://localhost:9000/pokemonView/${numeroDex}`;

                if (event.ctrlKey) {
                    const enlace = document.createElement("a");
                    enlace.href = url;
                    enlace.target = "_blank";
                    enlace.rel = "noopener noreferrer";
                    enlace.click();
                } else {
                    window.location.href = url;
                }
            });
        }
    }, 1000);
}

function actualixarImgEfectividades() {
    document.querySelectorAll("#tablaEfectividadesContra tbody tr").forEach(fila => {
        fila.classList.remove(
            "fondo-normal", "fondo-fuego", "fondo-agua", "fondo-planta", "fondo-electrico",
            "fondo-hielo", "fondo-lucha", "fondo-veneno", "fondo-tierra", "fondo-roca",
            "fondo-bicho", "fondo-fantasma", "fondo-acero", "fondo-dragon", "fondo-siniestro",
            "fondo-hada", "fondo-psiquico", "fondo-volador"
        );
    
        if (fila.querySelector(".NORMAL")) fila.classList.add("fondo-normal");
        if (fila.querySelector(".FUEGO")) fila.classList.add("fondo-fuego");
        if (fila.querySelector(".AGUA")) fila.classList.add("fondo-agua");
        if (fila.querySelector(".PLANTA")) fila.classList.add("fondo-planta");
        if (fila.querySelector(".ELECTRICO")) fila.classList.add("fondo-electrico");
        if (fila.querySelector(".HIELO")) fila.classList.add("fondo-hielo");
        if (fila.querySelector(".LUCHA")) fila.classList.add("fondo-lucha");
        if (fila.querySelector(".VENENO")) fila.classList.add("fondo-veneno");
        if (fila.querySelector(".TIERRA")) fila.classList.add("fondo-tierra");
        if (fila.querySelector(".ROCA")) fila.classList.add("fondo-roca");
        if (fila.querySelector(".BICHO")) fila.classList.add("fondo-bicho");
        if (fila.querySelector(".FANTASMA")) fila.classList.add("fondo-fantasma");
        if (fila.querySelector(".ACERO")) fila.classList.add("fondo-acero");
        if (fila.querySelector(".DRAGON")) fila.classList.add("fondo-dragon");
        if (fila.querySelector(".SINIESTRO")) fila.classList.add("fondo-siniestro");
        if (fila.querySelector(".HADA")) fila.classList.add("fondo-hada");
        if (fila.querySelector(".PSIQUICO")) fila.classList.add("fondo-psiquico");
        if (fila.querySelector(".VOLADOR")) fila.classList.add("fondo-volador");
    });
}

function inicializarToolbar() {
    const contenedorToolbar = document.getElementById('contenedorToolbar');
    const toggleBtn = document.getElementById('toggleToolbar');
    const verBtn = document.getElementById('verPokemon');
    const editarBtn = document.getElementById('editarPokemon');
    const borrarBtn = document.getElementById('borrarPokemon');
    const numDexInput = document.getElementById('numDexBuscar');

    toggleBtn.addEventListener('click', () => {
        contenedorToolbar.classList.toggle('toolbar-hidden');
        toggleBtn.textContent = contenedorToolbar.classList.contains('toolbar-hidden') ? 'Mostrar' : 'Ocultar';
    });

    verBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex) window.location.href = `http://localhost:9000/pokemonView/${numDex}`;
    });

    editarBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex) window.location.href = `/editar/${numDex}`;
    });

    borrarBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex && confirm('¿Estás seguro de que quieres borrar este Pokémon?')) {
            window.location.href = `/borrar/${numDex}`;
        }
    });

    numDexInput.addEventListener('keydown', (e) => {
        if (e.key === 'ArrowUp' || e.key === 'ArrowDown') {
            e.preventDefault();
            const currentValue = parseInt(numDexInput.value) || 0;
            const nextValue = e.key === 'ArrowUp' 
                ? encontrarSiguientePokemon(currentValue)
                : encontrarAnteriorPokemon(currentValue);
            numDexInput.value = nextValue;
        }
    });
}

function cargarPokemonDisponibles() {
    fetch('/api/pokemons')
        .then(response => response.json())
        .then(data => {
            pokemonDisponibles = data;
        })
        .catch(error => console.error('Error al cargar Pokémon disponibles:', error));
}

function encontrarSiguientePokemon(currentNum) {
    return pokemonDisponibles.find(num => num > currentNum) || pokemonDisponibles[0];
}

function encontrarAnteriorPokemon(currentNum) {
    return pokemonDisponibles.slice().reverse().find(num => num < currentNum) || pokemonDisponibles[pokemonDisponibles.length - 1];
}

function inicializarToolbar() {
    const contenedorToolbar = document.getElementById('contenedorToolbar');
    const toggleBtn = document.getElementById('toggleToolbar');
    const verBtn = document.getElementById('verPokemon');
    const editarBtn = document.getElementById('editarPokemon');
    const borrarBtn = document.getElementById('borrarPokemon');
    const numDexInput = document.getElementById('numDexBuscar');

    toggleBtn.addEventListener('click', () => {
        contenedorToolbar.classList.toggle('toolbar-hidden');
        toggleBtn.textContent = contenedorToolbar.classList.contains('toolbar-hidden') ? 'Mostrar' : 'Ocultar';
    });

    verBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex) window.location.href = `http://localhost:9000/pokemonView/${numDex}`;
    });

    editarBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex) window.location.href = `/editar/${numDex}`;
    });

    borrarBtn.addEventListener('click', () => {
        const numDex = numDexInput.value;
        if (numDex && confirm('¿Estás seguro de que quieres borrar este Pokémon?')) {
            window.location.href = `/borrar/${numDex}`;
        }
    });

	// Esta función evitará el comportamiento predeterminado de las flechas arriba y abajo en el campo de entrada numérico.
    numDexInput.addEventListener('keydown', (e) => {
		// Comprobamos si la tecla presionada es la flecha arriba o la flecha abajo.
        if (e.key === 'ArrowUp' || e.key === 'ArrowDown') {
			// Evita que la página se desplace cuando se presionan las teclas de flecha.
            e.preventDefault();
			// Convierte el valor actual del campo de entrada a un número entero. Si está vacío o no es un número, se establece en 0.
            const currentValue = parseInt(numDexInput.value) || 0;
			// Si se presionó la flecha arriba, llama a la función encontrarSiguientePokemon con el número actual. De lo contrario, llama a la función encontrarAnteriorPokemon.
            const nextValue = e.key === 'ArrowUp' 
                ? encontrarSiguientePokemon(currentValue)
                : encontrarAnteriorPokemon(currentValue);
			// Actualiza el valor del campo de entrada con el siguiente valor encontrado.
            numDexInput.value = nextValue;
        }
    });
}
function cargarPokemonDisponibles() {
    fetch('/api/pokemons')
        .then(response => response.json())
        .then(data => {
            pokemonDisponibles = data;
        })
        .catch(error => console.error('Error al cargar Pokémon disponibles:', error));
}
function encontrarSiguientePokemon(currentNum) {
    return pokemonDisponibles.find(num => num > currentNum)
		|| pokemonDisponibles[0];
}
function encontrarAnteriorPokemon(currentNum) {
    return pokemonDisponibles
		.slice()
		.reverse()
		.find(num => num < currentNum)
		|| pokemonDisponibles[pokemonDisponibles.length - 1];
}
