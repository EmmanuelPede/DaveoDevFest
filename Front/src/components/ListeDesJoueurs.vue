<template>
    <div id="resultats">
        <h2>Résultats</h2>
        <div class="list">
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>
            <div class="item-list">toto</div>

            <div v-for="(customer, index) in customers" :key="index">

                <router-link class="item-list" :to="{
                        firstName: 'customer-details',
                        params: { customer: customer, id: customer.id }
                    }">
                        {{customer.firstName}}
                </router-link>
            </div>
        </div>
        <div>
            <router-view @refreshData="refreshList"></router-view>
        </div>
    </div>
</template>

<script>
import http from "../http-common";

export default {
  firstName: "customers-list",
  data() {
    return {
      customers: []
    };
  },
  methods: {
    /* eslint-disable no-console */
    retrieveCustomers() {
      http
        .get("/customers")
        .then(response => {
          this.customers = response.data; // JSON are parsed automatically.
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    refreshList() {
      this.retrieveCustomers();
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.retrieveCustomers();
  }
};
</script>
