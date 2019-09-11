<template>
    <div id="scan">
        <div class="sidebar">
            <section class="cameras">
                <h2>Cameras</h2>
                <ul>
                    <li v-if="data.cameras.length === 0" class="empty">Aucune caméra détectée</li>
                    <li v-for="camera in data.cameras" :key="camera">
                        <span v-if="camera.id == activeCameraId" :title="formatName(camera.firstName)" class="active">{{ formatName(camera.firstName) }}</span>
                        <span v-if="camera.id != activeCameraId" :title="formatName(camera.firstName)">
                    <a @click.stop="selectCamera(camera)">{{ formatName(camera.firstName) }}</a>
                  </span>
                    </li>
                </ul>
            </section>
            <section class="scans">
                <h2>Scans</h2>
                <ul v-if="data.scans.length === 0">
                    <li class="empty">Aucun badge scanné</li>
                </ul>
                <transition-group firstName="scans" tag="ul">
                    <li v-for="scan in data.scans" :key="scan.date" :title="scan.content">{{ scan.content }}</li>
                </transition-group>
            </section>
        </div>
        <div class="preview-container">
            <video id="preview"></video>
        </div>
    </div>
</template>

<script>

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
            data: {
                scanner: null,
                activeCameraId: null,
                cameras: [],
                scans: []
            },
            submitted: false
        };
    },
    methods: {
        /* eslint-disable no-console */
        formatName: function (name) {
            return name || '(Inconnu)';
        },
        selectCamera: function (camera) {
            this.activeCameraId = camera.id;
            this.scanner.start(camera);
        }
        /* eslint-enable no-console */
    }
};
</script>
