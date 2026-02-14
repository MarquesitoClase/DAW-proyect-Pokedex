document.querySelectorAll('span[id^="tipo"]').forEach(function(elemento){
    let posicionX = Math.random()*window.innerWidth;
    elemento.style.left=posicionX+"px";

    let posicionY = Math.random()*window.innerHeight;
    elemento.style.top=posicionY+"px";
})