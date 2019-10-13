<template>
  <div class="searchform">
  <div class="container">
    <h4>Rechercher par email</h4>
    <div class="form-group">
      <input class="form-control" id="email" required v-model="email" email="email">
    </div>
 
    <div class="btn-group">
      <button v-on:click="searchCustomersByEmail" class="btn">Recherche</button>
    </div>

    <div class="container search-result">
      <div class="row" v-for="(customer, index) in customers" :key="index">
        {{customer.name}} ({{customer.score}} pts)
      </div>
    </div>
  </div>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "search-customer",
  data() {
    return {
      email: "",
      customers: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    searchCustomersByEmail() {
      http
        .get("/customers/email/" + this.email)
        .then(response => {
          this.customers = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
    /* eslint-enable no-console */
  }
};
</script>
