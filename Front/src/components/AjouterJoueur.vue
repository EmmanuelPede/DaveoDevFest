<template>
  <div class="submitform">
    <div v-if="!submitted">
        <div class="form-group">
          <label for="firstName">Nom</label>
          <input type="text" class="form-control" id="firstName" required v-model="customer.firstName" firstName="firstName">
        </div>

        <div class="form-group">
            <label for="firstName">Prénom</label>
            <input type="text" class="form-control" id="lastName" required v-model="customer.lastName" lastName="lastName">
        </div>

        <div class="form-group">
            <label for="firstName">Email</label>
            <input type="text" class="form-control" id="email" required v-model="customer.email" lastName="email">
        </div>
    
        <div class="form-group">
          <label for="score">Score</label>
          <input type="number" class="form-control" id="score" required v-model="customer.score" firstName="score">
        </div>
    
        <button v-on:click="saveCustomer" class="btn btn-success">Enregister</button>
    </div>
    
    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" v-on:click="newCustomer">Ajouter un nouveau joueur</button>
    </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  firstName: "add-customer",
  data() {
    return {
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
  methods: {
    /* eslint-disable no-console */
    saveCustomer() {
      var data = {
        firstName: this.customer.firstName,
        lastName: this.customer.lastName,
        email: this.customer.email,
        score: this.customer.score
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
