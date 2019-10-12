<template>
    <div class="player-details">
        <div class="d-flex flex-column justify-content-center container" v-if="this.customer">
            <div class="d-flex flex-row">
                <h4 class="col mt-4 mb-4 text-left">{{this.customer.firstName}} {{this.customer.lastName}}</h4>
            </div>
            <div class="d-flex flex-row">
                <label class="col text-left">Email: </label>
                <label class="col text-left">{{this.customer.email}}</label>
            </div>
            <div class="d-flex flex-row">
                <label class="col text-left">Dernier Score: </label>
                <label class="col text-left">
                    <span v-if="this.customer.lastScore">{{this.customer.lastScore}}</span>
                    <span v-else>0</span>
                    pt(s)
                </label>
            </div>
            <div class="d-flex flex-row">
                <label class="col text-left">Meilleur Score: </label>
                <label class="col text-left">
                    <span v-if="this.customer.bestScore">{{this.customer.bestScore}}</span>
                    <span v-else>0</span>
                    pt(s)
                </label>
            </div>
            <div class="d-flex flex-row">
                <label class="col text-left">Active: </label>

                <label class="col text-left">
                    <!-- Default checked -->
                    <span class="custom-control custom-switch">
                        <input type="checkbox" class="custom-control-input" id="customSwitch1"
                               :checked="this.customer.active" v-on:change="updateActive(!customer.active)">
                        <label class="custom-control-label" for="customSwitch1"></label>
                    </span>
                </label>

            </div>
            <div class="d-flex flex-row mb-4 mt-4">
                <div class="col justify-content-center">
                    <button class="btn" v-on:click="deleteCustomer()">Supprimer</button>
                </div>
            </div>
        </div>
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
