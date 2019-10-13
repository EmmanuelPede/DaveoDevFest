<template>
  <div class="container">
  <h2>Ajouter un joueur manuellement</h2>
    <div v-if="!submitted">
        <div class="form-group">
          <label for="firstName">Nom & Prénom</label>
          <input type="text" class="form-control" id="name" required v-model="customer.name" name="name">
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
        name: "",
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
            name: this.customer.name,
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
