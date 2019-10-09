<template>
  <div class="container">
  <h2>Ajouter un joueur manuellement</h2>
    <div v-if="!submitted">
        <div class="form-group">
          <label for="firstName">Nom</label>
          <input type="text" class="form-control" id="firstName" required v-model="customer.firstName" name="firstName">
        </div>

        <div class="form-group">
            <label for="lastName">Prénom</label>
            <input type="text" class="form-control" id="lastName" required v-model="customer.lastName" name="lastName">
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" class="form-control" id="email" required v-model="customer.email" name="email">
        </div>
    
        <div class="form-group">
          <label for="score">Score</label>
          <input type="number" class="form-control" id="score" required v-model="customer.lastScore" name="score">
        </div>
    
        <button v-on:click="saveCustomer" class="btn">Enregister</button>
    </div>
    
    <div v-else>
      <h4>Le joueur est ajouté à la liste!</h4>
      <button class="btn btn-success" v-on:click="newCustomer">Ajouter un nouveau joueur</button>
    </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "add-customer",
  data() {
    return {
      customer: {
        id: 0,
        firstName: "",
        lastName: "",
        email: "",
        lastScore: 0,
        active: false
      },
      submitted: false
    };
  },
  methods: {
    /* eslint-disable no-console */
    saveCustomer() {
        const data = {
            firstName: this.customer.firstName,
            lastName: this.customer.lastName,
            email: this.customer.email,
            lastScore: this.customer.lastScore
        };

        http
        .post("/customer", data)
        .then(response => {
          this.customer.id = response.data.id;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });

      this.submitted = true;
    },
    newCustomer() {
      this.submitted = false;
      this.customer = {};
    }
    /* eslint-enable no-console */
  }
};
</script>
