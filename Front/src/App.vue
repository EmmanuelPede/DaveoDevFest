<template>
    <div id="app" class="container-fluid">
        <div class="site-info">
            <h1>Daveo</h1>
            <h3>DevFest 2019</h3>
        </div>
        <nav>
            <router-link class="btn btn-primary" to="/">Liste des joueurs</router-link>
            <router-link class="btn btn-primary" to="/ajouterScanner">Scanner un joueur</router-link>
            <router-link class="btn btn-primary" to="/ajouterManuellement">Ajouter un joueur manuellement</router-link>
            <router-link class="btn btn-primary" to="/rechercher">Rechercher un joueur</router-link>
        </nav>
        <br/>
        <router-view/>
    </div>
</template>

<script>
export default {
    firstName: "app",
    mounted: function () {
        var Instascan = require('instascan');
        var self = this;
        self.scanner = new Instascan.Scanner({ video: document.getElementById('Précédent'), scanPeriod: 5 });
        // eslint-disable-next-line
        self.scanner.addListener('scan', function (content, image) {
            self.scans.unshift({ date: +(Date.now()), content: content });
        });
        Instascan.Camera.getCameras().then(function (cameras) {
            self.cameras = cameras;
            if (cameras.length > 0) {
                self.activeCameraId = cameras[0].id;
                self.scanner.start(cameras[0]);
            } else {
                // eslint-disable-next-line no-console
                console.error('Aucune caméra disponible.');
            }
        }).catch(function (e) {
            // eslint-disable-next-line no-console
            console.error(e);
        });
    }
};
</script>

<style>
body, html {
    padding: 0;
    margin: 0;
    font-family: 'Helvetica Neue', 'Calibri', Arial, sans-serif;
    height: 100%;
}
.site-info {
  color: blue;
  margin-bottom: 20px;
}

.btn-primary {
  margin-right: 5px;
}

.container-fluid {
  text-align: center;
}
</style>
