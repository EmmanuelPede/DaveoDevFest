<template>
  <div class="container mt-4">
    <h2 class="d-flex justify-content-center">
      Ajouter un joueur manuellement
    </h2>
    <div v-if="!submitted">
      <div class="form-group mt-4">
        <label for="firstName">Nom & Prénom</label>
        <input
            type="text"
            class="form-control mt-2"
            id="name"
            required
            v-model="customer.name"
            name="name"
        />
      </div>

      <div class="form-group mt-4">
        <label for="email">Email</label>
        <input
            type="text"
            class="form-control mt-2"
            id="email"
            required
            v-model="customer.email"
            name="email"
        />
      </div>

      <div class="form-group mt-4">
        <label for="score">Score</label>
        <input
            type="number"
            class="form-control mt-2"
            id="score"
            required
            v-model="customer.lastScore"
            name="score"
        />
      </div>

      <div class="d-flex justify-content-center mt-4">
        <button v-on:click="saveCustomer" class="btn">Enregister</button>
      </div>
    </div>

    <div v-else class="mt-4">
      <h4 class="d-flex justify-content-center">
        Le joueur {{ customer.name }} a bien été ajouté à la liste
      </h4>
      <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-success" v-on:click="newCustomer">
          Ajouter un nouveau joueur
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import {CustomerEventService} from "@/services/CustomerEventService";

export default {
  name: "add-customer",
  data() {
    return {
      customer: {
        id: 0,
        name: "",
        email: "",
        lastScore: 0,
      },
      submitted: false,
    };
  },
  methods: {
    /* eslint-disable no-console */
    saveCustomer() {
      const data = {
        name: this.customer.name,
        email: this.customer.email,
        lastScore: this.customer.lastScore,
      };

      http
          .post("/customer", data)
          .then((response) => {
            if (response.status !== 200) {
              throw new Error(response);
            }
            this.customer = response.data;
            this.submitted = true;
            CustomerEventService.$emit("currentCustomer", response.data);
          })
          .catch((e) => {
            console.log(e);
          });
    },
    newCustomer() {
      this.submitted = false;
      this.customer = {};
    },
    /* eslint-enable no-console */
  },
};
</script>
