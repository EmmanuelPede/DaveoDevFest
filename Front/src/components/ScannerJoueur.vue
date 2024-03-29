<template>
  <div id="scan">
    <div class="sidebar">
      <section class="cameras">
        <h2>Cameras</h2>
        <ul>
          <li v-if="cameras.length === 0" class="empty">
            Aucune caméra détectée
          </li>
          <li v-for="(camera, index) in cameras" :key="index">
            <span
                v-if="camera.id === activeCameraId"
                :title="formatName(camera.name)"
                class="active"
            >{{ formatName(camera.name) }}</span
            >
            <span v-else :title="formatName(camera.name)">
              <a @click.stop="selectCamera(camera)">{{
                  formatName(camera.name)
                }}</a>
            </span>
          </li>
        </ul>
      </section>
      <section class="scans d-flex flex-column">
        <h2>Scans</h2>
        <span v-if="!customer" class="empty d-flex justify-content-center"
        >Aucun badge scanné</span
        >
        <span v-else>{{ customer.name }}</span>
      </section>
    </div>
    <div class="preview-container">
      <video id="preview"></video>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import {CustomerEventService} from "@/services/CustomerEventService";

export default {
  name: "scan-customer",
  data() {
    return {
      scanner: null,
      activeCameraId: null,
      cameras: [],
      scans: [],
      customer: null,
      submitted: false,
    };
  },
  beforeDestroy() {
    const self = this;
    self.scanner.removeListener("scan", self.listenScan);
    Instascan.Camera.getCameras().then(function (cameras) {
      self.scanner.stop(cameras[0]);
    });
  },
  mounted: function () {
    const self = this;
    self.scanner = new Instascan.Scanner({
      video: document.getElementById("preview"),
      scanPeriod: 2,
    });
    // eslint-disable-next-line
    self.scanner.addListener("scan", self.listenScan);
    Instascan.Camera.getCameras()
        .then(function (cameras) {
          self.cameras = cameras;
          if (cameras.length > 0) {
            self.activeCameraId = cameras[0].id;
            self.scanner.start(cameras[0]);
            // eslint-disable-next-line no-console
            console.info("Cam " + self.activeCameraId);
          } else {
            // eslint-disable-next-line no-console
            console.error("Aucune caméra disponible.");
          }
        })
        .catch(function (e) {
          // eslint-disable-next-line no-console
          console.error(e);
        });
  },
  methods: {
    listenScan: function (content, image) {
      this.scans.unshift({date: +Date.now(), content: content});
      this.saveCustomer(content);
    },
    /* eslint-disable no-console */
    formatName: function (name) {
      return name || "(Inconnu)";
    },
    selectCamera: function (camera) {
      this.activeCameraId = camera.id;
      this.scanner.start(camera);
    },
    parseVCard: function (contentScan) {
      const Re1 = /^(version|n|fn|title|org|email|voice):(.+)$/i;
      const Re2 = /^([^:;]+);([^:]+):(.+)$/;
      const ReKey = /item\d{1,2}\./;
      const fields = {};

      contentScan.split(/\r\n|\r|\n/).forEach(function (line) {
        let results, key;

        if (Re1.test(line)) {
          results = line.match(Re1);
          key = results[1].toLowerCase();
          fields[key] = results[2];
        } else if (Re2.test(line)) {
          results = line.match(Re2);
          key = results[1].replace(ReKey, "").toLowerCase();

          const meta = {};
          results[2]
              .split(";")
              .map(function (p, i) {
                const match = p.match(/([a-z]+)=(.*)/i);
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
            value: results[3].split(";"),
          });
        }
      });

      return fields;
    },
    getName: function (fn) {
      if (fn) {
        return fn.replace(";", " ");
      }
      return null;
    },
    saveCustomer: function (contentScan) {
      console.info("contentScan : " + contentScan);
      const card = this.parseVCard(contentScan);
      console.info("vCard : " + JSON.stringify(card));
      const data = {
        name: this.getName(card.n) || this.getName(card.fn) || "",
        email: card.email || "",
        telephone:
            card.tel &&
            card.tel.length > 0 &&
            card.tel[0].value.length > 0 &&
            card.tel[0].value[0],
        vCard: contentScan || "",
      };
      console.info(data);
      http
          .post("/customer", data)
          .then((response) => {
            this.customer = response.data;
            CustomerEventService.$emit("currentCustomer", response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      this.submitted = true;
    },
    /* eslint-enable no-console */
  },
};
</script>
