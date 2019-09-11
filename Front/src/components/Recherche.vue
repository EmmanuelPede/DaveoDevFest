<template>
  <div class="searchform">
    <h4>Rechercher par email</h4>
    <div class="form-group">
      <input class="form-control" id="email" required v-model="email" email="email">
    </div>
 
    <div class="btn-group">
      <button v-on:click="searchCustomersByEmail" class="btn btn-success">Recherche</button>
    </div>

    <ul class="search-result">
      <li v-for="(customer, index) in customers" :key="index">
        <h6>{{customer.firstName}} {{customer.lastName}} ({{customer.score}})</h6>
      </li>
    </ul>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  firstName: "search-customer",
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