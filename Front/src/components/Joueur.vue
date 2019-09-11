<template>
  <div v-if="this.customer">
    <h4>Joueur</h4>
    <div>
      <label>Nom: </label> {{this.customer.firstName}}
    </div>
    <div>
      <label>Prénom: </label> {{this.customer.lastName}}
    </div>
    <div>
      <label>Email: </label> {{this.customer.email}}
    </div>
    <div>
      <label>Score: </label> {{this.customer.score}}
    </div>
    <div>
      <label>Active: </label> {{this.customer.active}}
    </div>
  
    <span v-if="this.customer.active" v-on:click="updateActive(false)"
      class="button is-small btn-primary">Inactif</span>
    <span v-else v-on:click="updateActive(true)"
      class="button is-small btn-primary">Actif</span>
  
    <span class="button is-small btn-danger" v-on:click="deleteCustomer()">Supprimer</span>
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