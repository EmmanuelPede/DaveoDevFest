<template>
    <div id="app" class="container-fluid">
        <div class="sidebar">
            <section class="daveo">
                <img class="logo" src="./assets/logo_devfest.png"/>
            </section>
            <section>
                <nav>
                    <router-link class="item-menu" to="/">Liste des joueurs</router-link>
                    <router-link class="item-menu" to="/ajouterScanner">Scanner un joueur</router-link>
                    <router-link class="item-menu" to="/ajouterManuellement">Ajouter un joueur manuellement</router-link>
                    <router-link class="item-menu" to="/rechercher">Rechercher un joueur</router-link>
                </nav>
            </section>
        </div>
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
