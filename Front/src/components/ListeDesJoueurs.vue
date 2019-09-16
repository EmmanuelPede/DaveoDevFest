<template>
    <div id="resultats">
        <h2>Résultats</h2>
        <h3>Dernier résultat : {{lastRide.score}}</h3>
        <div class="list">
            <div v-for="(customer, index) in customers" :key="index">
                <router-link class="item-list" :to="{
                        name: 'customer-details',
                        params: { customer: customer, id: customer.id }
                    }">
                    {{customer.firstName}} {{customer.lastName}} - {{customer.lastRide}}
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
                customers: [],
                lastRide: {
                    score: 0,
                    song: {
                        songName: '',
                        songDuration: '',
                        songArtist: ''
                    }
                }
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
            },
            listenEvent() {
                const eventSource = new EventSource('http://localhost:8080/api/score');

                eventSource.onmessage = e => {
                    this.lastRide = JSON.parse(e.data);
                };

                eventSource.onopen = e => console.log('open');

                eventSource.onerror = e => {
                    if (e.readyState == EventSource.CLOSED) {
                        console.log('close');
                    } else {
                        console.log(e);
                    }
                };

                eventSource.addEventListener('second', function (e) {
                    console.log('second', e.data);
                }, false);
            }

            /* eslint-enable no-console */
        },
        mounted() {
            this.retrieveCustomers();
            this.listenEvent();
        }
    };
</script>
