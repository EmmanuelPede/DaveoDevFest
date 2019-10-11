<template>
    <div class="player-container">
        <div class="d-flex flex-column" v-if="this.customer">
            <div class="row">
                <h4>Joueur</h4>
            </div>
            <div class="row">
                <label>Nom: </label> {{this.customer.firstName}}
            </div>
            <div class="row">
                <label>Prénom: </label> {{this.customer.lastName}}
            </div>
            <div class="row">
                <label>Email: </label> {{this.customer.email}}
            </div>
            <div class="row">
                <label>Dernier Score: </label> {{this.customer.lastScore}}
            </div>
            <div class="row">
                <label>Meilleur Score: </label> {{this.customer.bestScore}}
            </div>
            <div class="row">
                <label>Active: </label> {{this.customer.active}}
            </div>
            <div class="row">
                <div v-if="this.customer.active" v-on:click="updateActive(false)" class="btn">Inactif</div>
                <div v-else v-on:click="updateActive(true)" class="btn">Actif</div>
                <div class="btn" v-on:click="deleteCustomer()">Supprimer</div>
            </div>
        </div>


        <div v-else>
            <br/>
            <p>Sélectionner sur un joueur</p>
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
