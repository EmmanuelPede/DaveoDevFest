<template>
    <div class="player-details">
        <div class="d-flex flex-column justify-content-center" v-if="this.customer">
            <div class="d-flex flex-row">
                <h4 class="col pt-4 pb-4 mb-4 text-center heading">{{this.customer.firstName}} {{this.customer.lastName}}</h4>
            </div>
            <div class="d-flex flex-column group">
                <label class="col">Email</label>
                <span class="col">{{this.customer.email}}</span>
            </div>
            <div class="d-flex flex-column group">
                <label class="col">Dernier Score</label>
                <span class="col">
                    <span v-if="this.customer.lastScore">{{this.customer.lastScore}}</span>
                    <span v-else>0</span>
                    pt(s)
                </span>
            </div>
            <div class="d-flex flex-column group">
                <label class="col">Meilleur Score</label>
                <span class="col">
                    <span v-if="this.customer.bestScore">{{this.customer.bestScore}}</span>
                    <span v-else>0</span>
                    pt(s)
                </span>
            </div>
            <div class="d-flex flex-column group">
                <label class="col">
                    <span v-if="this.customer.active">Actif</span>
                    <span v-else>Inactif</span>
                </label>

                <span class="col">
                    <!-- Default checked -->
                    <span class="custom-control custom-switch">
                        <input type="checkbox" class="custom-control-input" id="customSwitch1"
                               v-model="customer.active" v-on:change="updateActive()">
                        <label class="custom-control-label" for="customSwitch1"></label>
                    </span>
                </span>

            </div>
            <div class="d-flex flex-row mb-4 mt-2">
                <div class="col justify-content-center">
                    <button class="btn" v-on:click="deleteCustomer()">Supprimer</button>
                </div>
            </div>

            <div class="d-flex flex-row mb-4 mt-2">
                <div class="col justify-content-center">
                    <button class="btn close-btn" v-on:click="close()">
                        <font-awesome-icon icon="angle-right" />
                        <span class="close-btn-label">Fermer</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import http from "../http-common";
    import router from "../router"

    export default {
        name: "customer-details",
        props: ["customer"],
        methods: {
            /* eslint-disable no-console */
            updateActive() {
                const data = {
                    id: this.customer.id,
                    firstName: this.customer.firstName,
                    lastName: this.customer.lastName,
                    email: this.customer.email,
                    lastScore: this.customer.lastScore,
                    bestScore: this.customer.bestScore,
                    active: this.customer.active
                };

                http
                    .put("/customer/" + this.customer.id, data)
                    .then(response => {
                        console.log(response.data);

                        this.customer.active = response.data.active;
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
            },
            /* eslint-enable no-console */

            close() {
                router.push({name: 'customer-list'});
            }
        },


    };
</script>
