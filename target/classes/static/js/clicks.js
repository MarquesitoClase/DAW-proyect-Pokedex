document.addEventListener('DOMContentLoaded', function() {
    const enlace = document.getElementById('enlace');

    enlace.addEventListener('click', function(event) {
        event.preventDefault();

        const urlOriginal = this.href;
        //Ejemplo url sin esto: https://tenor.com/es/view/ivysaur-pokemon-pokemon-ivysaur-vine-vine-whip-/gif-14693075095962184049
        //Con esto la misma   : https://tenor.com/es/view/ivysaur-pokemon-pokemon-ivysaur-vine-vine-whip-gif-14693075095962184049
        const urlCorregida = urlOriginal.replace(/\/gif/g, 'gif');
        if (event.ctrlKey) {
            window.open(urlCorregida, '_blank');
        } else {
            window.location.href = urlCorregida;
        }
    });
});