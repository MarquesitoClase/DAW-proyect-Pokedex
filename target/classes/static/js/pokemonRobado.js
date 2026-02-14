let player;

window.addEventListener('DOMContentLoaded', function() {
    crearReproductor();
});

function crearReproductor() {
    player = new YT.Player('reproductor', {
        height: '315',
        width: '560',
        videoId: 'SXD00dJFQQk',
        playerVars: {
            start: 58,
            end: 80,
            autoplay: 1,
            loop: 1,
            controls: 1,
            modestbranding: 1,
            rel: 0
        },
        events: {
            onReady: onPlayerReady,
            onStateChange: function(event) {
                if (event.data === YT.PlayerState.ENDED) {
                    player.seekTo(58);
                }
            }
        }
    });
}

function onPlayerReady() {
    player.playVideo();
}
