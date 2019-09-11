<template>
    <div id="scan">
        <div class="sidebar">
            <section class="daveo">
                <img class="logo" src="../assets/logoDaveo.png"/>
            </section>
            <section class="cameras">
                <h2>Cameras</h2>
                <ul>
                    <li v-if="data.cameras.length === 0" class="empty">Aucune caméra détectée</li>
                    <li v-for="camera in data.cameras">
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
    body, html {
        padding: 0;
        margin: 0;
        font-family: 'Helvetica Neue', 'Calibri', Arial, sans-serif;
        height: 100%;
    }
    #scan {
        background: #263238;
        display: flex;
        align-items: stretch;
        justify-content: stretch;
        height: 100%;
        width: 100%;
    }
    .sidebar {
        background: #eaeaea;
        min-width: 250px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        overflow: auto;
    }
    .sidebar h2 {
        font-weight: normal;
        font-size: 1.0rem;
        background: #D54972;
        color: #fff;
        padding: 10px;
        margin: 0;
    }
    .sidebar ul {
        margin: 0;
        padding: 0;
        list-style-type: none;
    }
    .sidebar li {
        line-height: 175%;
        white-space: nowrap;
        overflow: hidden;
        text-wrap: none;
        text-overflow: ellipsis;
    }
    .cameras ul {
        padding: 15px 20px;
    }
    .cameras .active {
        font-weight: bold;
        color: #009900;
    }
    .cameras a {
        color: #555;
        text-decoration: none;
        cursor: pointer;
    }
    .cameras a:hover {
        text-decoration: underline;
    }
    .scans li {
        padding: 10px 20px;
        border-bottom: 1px solid #ccc;
    }
    .scans-enter-active {
        transition: background 3s;
    }
    .scans-enter {
        background: yellow;
    }
    .empty {
        font-style: italic;
    }
    .preview-container {
        flex-direction: column;
        align-items: center;
        justify-content: center;
        display: flex;
        width: 100%;
        overflow: hidden;
        background: url("../assets/logo_devfest.png") no-repeat center #FFF;
    }
    /**CUSTOM**/
    .daveo {
        padding: 10px;
    }
    .logo {
        max-width: 230px;
    }

</style>
