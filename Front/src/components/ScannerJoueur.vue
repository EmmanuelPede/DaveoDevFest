<template>
    <div id="app">
        <div class="sidebar">
            <section class="daveo">
                <img class="logo" src="../assets/logo.png"/>
            </section>
            <section class="cameras">
                <h2>Cameras</h2>
                <ul>
                    <li v-if="cameras.length === 0" class="empty">Aucune caméra détectée</li>
                    <li v-for="camera in cameras">
                        <span v-if="camera.id == activeCameraId" :title="formatName(camera.firstName)" class="active">{{ formatName(camera.firstName) }}</span>
                        <span v-if="camera.id != activeCameraId" :title="formatName(camera.firstName)">
                <a @click.stop="selectCamera(camera)">{{ formatName(camera.firstName) }}</a>
              </span>
                    </li>
                </ul>
            </section>
            <section class="scans">
                <h2>Scans</h2>
                <ul v-if="scans.length === 0">
                    <li class="empty">Aucun badge scanné</li>
                </ul>
                <transition-group firstName="scans" tag="ul">
                    <li v-for="scan in scans" :key="scan.date" :title="scan.content">{{ scan.content }}</li>
                </transition-group>
            </section>
        </div>
        <div class="preview-container">
            <video id="preview"></video>
        </div>
    </div>
</template>

<script>
import http from "../http-common";

export default {
    firstName: "scan-customer",
    data() {
        return {
            customer: {
                id: 0,
                firstName: "",
                lastName: "",
                email: "",
                score: 0,
                active: false
            },
            submitted: false
        };
    },
    mounted: function () {
        var self = this;
        self.scanner = new Instascan.Scanner({ video: document.getElementById('Précédent'), scanPeriod: 5 });
        self.scanner.addListener('scan', function (content, image) {
            self.scans.unshift({ date: +(Date.now()), content: content });
        });
        Instascan.Camera.getCameras().then(function (cameras) {
            self.cameras = cameras;
            if (cameras.length > 0) {
                self.activeCameraId = cameras[0].id;
                self.scanner.start(cameras[0]);
            } else {
                console.error('Aucune caméra disponible.');
            }
        }).catch(function (e) {
            console.error(e);
        });
    },
    methods: {
        formatName: function (name) {
            return name || '(Inconnu)';
        },
        selectCamera: function (camera) {
            this.activeCameraId = camera.id;
            this.scanner.start(camera);
        }
    }
};
</script>

<style>
.submitform {
  max-width: 300px;
  margin: auto;
}
</style>
