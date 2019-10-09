<template>
    <div id="resultats">
        <h2>Scoreboard</h2>
        <div class="container">
            <h3 v-if="lastRide">Dernier score : {{lastRide.score}}</h3>
            <h3 v-else>Aucun score pour le moment</h3>

            <div class="list" v-if="customers && customers.length>0">
                <div class="row" v-for="(customer) in customers" :key="customer.id">
                    <router-link class="item-list" :to="{
                        name: 'customer-details',
                        params: { customer: customer, id: customer.id }
                    }">
                        {{customer.firstName}} {{customer.lastName}} - {{customer.lastRide}} pts
                    </router-link>
                </div>
            </div>
            <div>
                <router-view @refreshData="refreshList"></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    import http from "../http-common";
    import {RideEventSourceService} from "@/services/RideEventSourceService";

    export default {
        name: "customers-list",
        data() {
            return {
                customers: [],
                lastRide: {
                    score: 0,
                    song: {
                        songName: '',
                        songDuration: '',
                        songArtist: ''
                    },
                    customer: {}
                },
            };
        },
        methods: {
            /* eslint-disable no-console */
            retrieveCustomers() {
                this.customers = [];
                http
                    .get("/customers")
                    .then(response => {
                        this.customers = response.data; // JSON are parsed automatically.

                        const selectedCustomerId = this.$router.currentRoute.params['id'];

                        if (this.customers && this.customers.length > 0
                            && this.lastRide.customer && this.lastRide.customer.id === selectedCustomerId) {

                            const customer = this.customers.find(customer => customer.id === selectedCustomerId);
                            console.log('customer', customer);

                            if (customer) {
                                console.log('pushing route to customer details');

                                this.$router.replace({
                                    name: 'customer-list',
                                });

                                this.$router.push({
                                    name: 'customer-details',
                                    params: {customer: customer, id: selectedCustomerId}
                                });
                            }
                        }
                        this.$emit("refreshData");
                    })
                    .catch(e => {
                        console.error("Error refreshing player list", e);
                    });
            },
            refreshList() {
                this.retrieveCustomers();
            },
            getLastRide() {
                http
                    .get("/last-ride")
                    .then(response => {
                        this.lastRide = response.data; // JSON are parsed automatically.
                    })
                    .catch(e => {
                        console.error("Error getting last ride", e);
                    });
            },
            listenEvent() {

                RideEventSourceService.init();

                RideEventSourceService.$on('lastRide', lastRide => {
                    console.log('Last Ride Event Received', lastRide);
                    this.lastRide = JSON.parse(lastRide);
                    this.refreshList();
                });
            }

            /* eslint-enable no-console */
        },
        mounted() {
            this.retrieveCustomers();
            this.getLastRide();
            this.listenEvent();
        },
        destroyed() {
            console.log("destroyed");
            RideEventSourceService.$off('lastRide');
        }
    };
</script>
