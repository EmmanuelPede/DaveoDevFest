<template>
  <div class="container" v-if="this.customer">
    <div class="row">
        <h4>Joueur</h4>
    </div>
    <div class="row">
      <label>Nom: </label> {{this.customer.firstName}}
    </div>
    <div class="row">
      <label>Prénom: </label> {{this.customer.lastName}}
    </div>
    <div class="row">
      <label>Email: </label> {{this.customer.email}}
    </div>
    <div class="row">
      <label>Score: </label> {{this.customer.score}}
    </div>
    <div class="row">
      <label>Active: </label> {{this.customer.active}}
    </div>
    <div class="row">
        <div v-if="this.customer.active" v-on:click="updateActive(false)" class="btn">Inactif</div>
        <div v-else v-on:click="updateActive(true)" class="btn">Actif</div>
        <div class="btn" v-on:click="deleteCustomer()">Supprimer</div>
    </div>
  </div>

  <div v-else>
    <br/>
    <p>Cliquer sur un joueur</p>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  firstName: "customer",
  props: ["customer"],
  methods: {
    /* eslint-disable no-console */
    updateActive(status) {
      var data = {
        id: this.customer.id,
        firstName: this.customer.firstName,
        lastName: this.customer.lastName,
        email: this.customer.email,
        score: this.customer.score,
        active: status
      };

      http
        .put("/customer/" + this.customer.id, data)
        .then(response => {
          this.customer.active = response.data.active;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    deleteCustomer() {
      http
        .delete("/customer/" + this.customer.id)
        .then(response => {
          console.log(response.data);
          this.$emit("refreshData");
          this.$router.push('/');
        })
        .catch(e => {
          console.log(e);
        });
    }
    /* eslint-enable no-console */
  }
};
</script>