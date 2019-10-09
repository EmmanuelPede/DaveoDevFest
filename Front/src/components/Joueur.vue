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
      <label>Dernier Score: </label> {{this.customer.lastScore}}
    </div>
    <div>
      <label>Meilleur Score: </label> {{this.customer.bestScore}}
    </div>
    <div>
      <label>Active: </label> {{this.customer.active}}
    </div>
  
    <button v-if="this.customer.active" v-on:click="updateActive(false)"
      class="button is-small btn-primary">Inactif</button>
    <button v-else v-on:click="updateActive(true)"
      class="button is-small btn-primary">Actif</button>
  
    <button class="button is-small btn-danger" v-on:click="deleteCustomer()">Supprimer</button>
  </div>
  <div v-else>
    <br/>
    <p>Sélectionner sur un joueur</p>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "customer-details",
  props: ["customer"],
  methods: {
    /* eslint-disable no-console */
    updateActive(status) {
      const data = {
        id: this.customer.id,
        firstName: this.customer.firstName,
        lastName: this.customer.lastName,
        email: this.customer.email,
        lastScore: this.customer.lastScore,
        bestScore: this.customer.bestScore,
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
  },
  mounted() {

  }
};
</script>
