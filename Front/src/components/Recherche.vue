<template>
  <div class="container mt-4">
    <h2 class="d-flex justify-content-center">Rechercher par email</h2>

    <div class="form-group mt-4">
      <label for="email">Email</label>
      <input
          class="form-control mt-2"
          id="email"
          required
          v-model="email"
          email="email"
      />
    </div>

    <div class="d-flex justify-content-center mt-4">
      <button v-on:click="searchCustomersByEmail" class="btn">Recherche</button>
    </div>

    <div class="container search-result mt-5">
      <div
          class="d-flex flex-column justify-content-center"
          v-for="(customer, index) in customers"
          :key="index"
      >
        <hr/>
        <h4 class="d-flex justify-content-center">{{ customer.name }}</h4>
        <br/>
        <div class="d-flex flex-row justify-content-around">
          <div class="d-flex flex-column align-items-center">
            Email: {{ customer.email }} <br/>
            Dernier score : {{ customer.lastScore }} pts <br/>
            Meilleur score : {{ customer.bestScore }} pts
          </div>

          <div class="d-flex flex-row align-items-center group-btn">
            <div class="mr-4">
              <button v-on:click="selectCustomer(customer.id)" class="btn">
                <font-awesome-icon icon="user-check"/>
                Sélectionner
              </button>
            </div>

            <div>
              <button v-on:click="deleteCustomer(customer)" class="btn">
                <font-awesome-icon icon="skull-crossbones"/>
                Supprimer
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import * as fontawesome from "@fortawesome/fontawesome-svg-core";
import {faSkullCrossbones,} from "@fortawesome/free-solid-svg-icons";
import {CustomerEventService} from "@/services/CustomerEventService";

export default {
  name: "search-customer",
  data() {
    return {
      email: "",
      customers: [],
    };
  },
  methods: {
    /* eslint-disable no-console */
    searchCustomersByEmail() {
      http
          .get("/customers/email/" + this.email)
          .then((response) => {
            this.customers = response.data; // JSON are parsed automatically.
          })
          .catch((e) => {
            console.log(e);
          });
    },

    selectCustomer(customerId) {
      http
          .put("/customer/" + customerId, {
            lastSelectDate: new Date(),
            active: true,
          })
          .then((response) => {
            this.customers = [response.data]; // JSON are parsed automatically.

            CustomerEventService.$emit("currentCustomer", response.data);
          })
          .catch((e) => {
            console.log(e);
          });
    },

    deleteCustomer(customer) {
      http
          .delete("/customer/" + customer.id)
          .then((response) => {
            if (response.status !== 200) {
              throw new Error(response);
            }
            const toastMessage = `${
                fontawesome.icon(faSkullCrossbones).html
            }<span> <span class="score">${
                customer.name
            }</span> a bien été supprimé</span>`;

            this.$toasted.show(toastMessage, {
              duration: 20000,
              // fullWidth: true,
              // fitToScreen: true
              position: "bottom-center",
              theme: "bubble",
            });

            this.searchCustomersByEmail();
          })
          .catch((e) => {
            console.log(e);
          });
    },
  },
};
</script>
