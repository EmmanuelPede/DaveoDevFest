<template>
  <div class="searchform">
    <h4>Rechercher par nom</h4>
    <div class="form-group">
      <input class="form-control" id="nom" required v-model="firstName" firstName="Nom">
    </div>
 
    <div class="btn-group">
      <button v-on:click="searchCustomers" class="btn btn-success">Recherche</button>
    </div>

    <ul class="search-result">
      <li v-for="(customer, index) in customers" :key="index">
        <h6>{{customer.firstName}} ({{customer.score}})</h6>
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
      score: 0,
      customers: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    searchCustomers() {
      http
        .get("/customers/score/" + this.score)
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

<style>
.searchform {
  max-width: 300px;
  margin: auto;
}
.search-result {
  margin-top: 20px;
  text-align: left;
}
</style>