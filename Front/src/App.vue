<template>
  <div id="app" class="container-fluid">
    <div class="sidebar">
      <section class="daveo">
        <img class="logo" src="./assets/logoDaveoMagellan.jpg"/>
      </section>
      <section>
        <nav>
          <router-link class="item-menu" to="/">TOP 10</router-link>
          <router-link class="item-menu" to="/ajouterScanner"
          >Scanner un joueur
          </router-link
          >
          <router-link class="item-menu" to="/ajouterManuellement"
          >Ajouter un joueur manuellement
          </router-link
          >
          <router-link class="item-menu" to="/rechercher"
          >Rechercher un joueur
          </router-link
          >
        </nav>
      </section>
      <section>
        <div v-if="customer" class="customer">
          Joueur en cours :<br/>
          {{ customer.name }}
        </div>
        <div v-if="lastRide && lastRide.customer" class="customer">
          Dernier joueur :<br/>
          {{ lastRide.customer.name }} ({{ lastRide.score }}pts)
        </div>
      </section>
    </div>
    <router-view/>
  </div>
</template>

<script>
import {RideEventSourceService} from "@/services/RideEventSourceService";
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faThumbsUp, faUserCheck} from "@fortawesome/free-solid-svg-icons";
import {CustomerEventService} from "@/services/CustomerEventService";
import http from "@/http-common";

export default {
  name: "app",
  data() {
    return {
      customer: null,
      lastRide: null,
    };
  },
  methods: {
    listenEvent() {
      RideEventSourceService.init();

      RideEventSourceService.$on("lastRide", (lastRide) => {
        console.log("Last Ride Event Received", lastRide);
        this.lastRide = JSON.parse(lastRide);

        const toastMessage = `${
            fontawesome.icon(faThumbsUp).html
        }<span> <span class="score">${
            this.lastRide.customer.name
        }</span> a enregistré un nouveau score <span class="score">${this.lastRide.score.toLocaleString(
            "fr-FR"
        )} </span> pts !</span>`;
        this.$toasted.show(toastMessage, {
          duration: 20000,
          // fullWidth: true,
          // fitToScreen: true
          position: "bottom-center",
          theme: "bubble",
        });

        CustomerEventService.$emit("refreshData");
      });

      CustomerEventService.$on("currentCustomer", (customer) => {
        this.customer = customer;

        const toastMessage = `${
            fontawesome.icon(faUserCheck).html
        }<span> <span class="score">${
            customer.name
        }</span> a bien été sélectionné</span>`;

        this.$toasted.show(toastMessage, {
          duration: 20000,
          // fullWidth: true,
          // fitToScreen: true
          position: "bottom-center",
          theme: "bubble",
        });
      });
    },
    getCurrentCustomer() {
      http
          .get("/customers/current")
          .then((response) => {
            CustomerEventService.$emit("currentCustomer", response.data);
          })
          .catch((e) => {
            console.log(e);
          });
    },
  },
  mounted() {
    this.listenEvent();
    this.getCurrentCustomer();
  },
  destroyed() {
    RideEventSourceService.$off("lastRide");
  },
};
</script>
