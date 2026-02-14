document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".pokemon-row");

    rows.forEach(row => {
        const tipo1 = row.querySelector(".tipo1");
        const tipo2 = row.querySelector(".tipo2");

        if (tipo1 && tipo2) {
            const tipo1Class = tipo1.classList[1];
            const tipo2Class = tipo2.classList[1];
            row.style.background = `linear-gradient(to right, ${getTypeColor(tipo1Class)}, ${getTypeColor(tipo2Class)})`;
        }
        else if (tipo1) {
            const tipo1Class = tipo1.classList[1];
            row.style.backgroundColor = getTypeColor(tipo1Class);
        }
    });
});

function getTypeColor(tipo) {
    switch (tipo) {
        case "FUEGO":
            return "#E72324"; // Rojo intenso
        case "AGUA":
            return "#2481EF"; // Azul brillante
        case "PLANTA":
            return "rgb(42, 163, 42)"; // Verde vibrante
        case "FUEGO":
            return "#E72324"; // Rojo intenso
        case "AGUA":
            return "#2481EF"; // Azul brillante
        case "PSIQUICO":
            return "#EF3F7A";// Rosa fuerte
        case "SINIESTRO":
            return "#4F3F3D";// Marrón grisáceo oscuro
        case "VENENO":
            return "#923FCC"; // Purpura oscuro
        case "ROCA":
            return "#C8A56D"; // Marrón claro
        case "TIERRA":
            return "#3E2B2B"; // Marrón oscuro
        case "LUCHA":
            return "#F48C06"; // Naranja
        case "ACERO":
            return "#A1A1A1"; // Gris metálico
        case "DRAGON":
            return "#0062A1"; // Azul oscuro
        case "HADA":
            return "#F9C8E4"; // Rosa
        case "NORMAL":
            return "#D3D3D3"; // Gris clarito
        case "VOLADOR":
            return "#A7D8F2"; // Celeste
        case "ELECTRICO":
            return "#FFDD00"; // Amarillo potente
        case "FANTASMA":
            return "#8A6D8B"; // Malva oscuro
        case "BICHO":
            return "#7A9E56"; // Verde apagado
        default:
            return "#FFFFFF"; // Por si acaso
    }
}
