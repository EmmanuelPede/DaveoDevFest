<template>
    <div id="scan">
        <div class="sidebar">
            <section class="cameras">
                <h2>Cameras</h2>
                <ul>
                    <li v-if="cameras.length === 0" class="empty">Aucune caméra détectée</li>
                    <li v-for="(camera, index) in cameras" :key="index">
                        <span v-if="camera.id == activeCameraId" :title="formatName(camera.name)" class="active">{{ formatName(camera.name) }}</span>
                        <span v-if="camera.id != activeCameraId" :title="formatName(camera.name)">
                    <a @click.stop="selectCamera(camera)">{{ formatName(camera.name) }}</a>
                  </span>
                    </li>
                </ul>
            </section>
            <section class="scans">
                <h2>Scans</h2>
                <ul v-if="scans.length === 0">
                    <li class="empty">Aucun badge scanné</li>
                </ul>
                <transition-group name="scans" tag="ul">
                    <li v-for="scan in scans" :key="scan.date" :title="saveCustomer(scan.content)">{{ scan.content }}</li>
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
    name: "scan-customer",
    data() {
        return {
            scanner: null,
            activeCameraId: null,
            cameras: [],
            scans: [],
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
        var Instascan = require('instascan');
        var self = this;
        self.scanner = new Instascan.Scanner({ video: document.getElementById('preview'), scanPeriod: 2 });
        // eslint-disable-next-line
        self.scanner.addListener('scan', function (content, image) {
            self.scans.unshift({ date: +(Date.now()), content: content });
        });
        Instascan.Camera.getCameras().then(function (cameras) {
            self.cameras = cameras;
            if (cameras.length > 0) {
                self.activeCameraId = cameras[0].id;
                self.scanner.start(cameras[0]);
                // eslint-disable-next-line no-console
                console.info('Cam '+ self.activeCameraId);
            } else {
                // eslint-disable-next-line no-console
                console.error('Aucune caméra disponible.');
            }
        }).catch(function (e) {
            // eslint-disable-next-line no-console
            console.error(e);
        });
    },
    methods: {
        /* eslint-disable no-console */
        formatName: function (name) {
            return name || '(Inconnu)';
        },
        selectCamera: function (camera) {
            this.activeCameraId = camera.id;
            this.scanner.start(camera);
        },
        parseVCard: function(contentScan) {
            var Re1 = /^(version|n|fn|title|org):(.+)$/i;
            var Re2 = /^([^:;]+);([^:]+):(.+)$/;
            var ReKey = /item\d{1,2}\./;
            var fields = {};

            contentScan.split(/\r\n|\r|\n/).forEach(function (line) {
                var results, key;

                if (Re1.test(line)) {
                    results = line.match(Re1);
                    key = results[1].toLowerCase();
                    fields[key] = results[2];
                } else if (Re2.test(line)) {
                    results = line.match(Re2);
                    key = results[1].replace(ReKey, '').toLowerCase();

                    var meta = {};
                    results[2].split(';')
                        .map(function (p, i) {
                            var match = p.match(/([a-z]+)=(.*)/i);
                            if (match) {
                                return [match[1], match[2]];
                            } else {
                                return ["TYPE" + (i === 0 ? "" : i), p];
                            }
                        })
                        .forEach(function (p) {
                            meta[p[0]] = p[1];
                        });

                    if (!fields[key]) fields[key] = [];

                    fields[key].push({
                        meta: meta,
                        value: results[3].split(';')
                    })
                }
            });

            return fields;
        },
        getName: function (fn) {
            return fn.replace(';',' ');
        },
        saveCustomer: function (contentScan) {
            console.info(contentScan);
            console.info("contentScan : " + contentScan);
            var card = this.parseVCard(contentScan);
            console.info("vCard : " + JSON.stringify(card));
            var data = {
                name: this.getName(card.n) || this.getName(card.fn) || "",
                email: card.email || "",
                vCard: contentScan || ""
            };
            console.info(data);
          http.post("/customer", data)
            .then(response => {
              this.customer.id = response.data.id;
              console.log(response.data);
            })
            .catch(e => {
              console.log(e);
            });
          this.submitted = true;
        }
        /* eslint-enable no-console */
    }
};
</script>