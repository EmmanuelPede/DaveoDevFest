<template>
    <div id="resultats">
        <h2>Leaderboard</h2>
        <!--<div class="container">
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
        </div>-->

        <div class="leaderboard">
            <h1>
                <svg class="ico-cup">
                    <use xlink:href="#cup"></use>
                </svg>
                Most active Players
            </h1>
            <ol>
                <li>
                    <mark>Jerry Wood</mark>
                    <small>315</small>
                </li>
                <li>
                    <mark>Brandon Barnes</mark>
                    <small>301</small>
                </li>
                <li>
                    <mark>Raymond Knight</mark>
                    <small>292</small>
                </li>
                <li>
                    <mark>Trevor McCormick</mark>
                    <small>245</small>
                </li>
                <li>
                    <mark>Andrew Fox</mark>
                    <small>203</small>
                </li>
                <li>
                    <mark>Andrew Fox</mark>
                    <small>202</small>
                </li>
                <li>
                    <mark>Andrew Fox</mark>
                    <small>201</small>
                </li>
                <li>
                    <mark>Andrew Fox</mark>
                    <small>200</small>
                </li>
                <li>
                    <mark>Andrew Fox</mark>
                    <small>199</small>
                </li>

                <li>
                    <mark>Andrew Fox</mark>
                    <small>199</small>
                </li>
            </ol>
        </div>
    </div>


</template>

<style>
/*--------------------
Leaderboard
--------------------*/
    .leaderboard {
        position: relative;
        background: -webkit-linear-gradient(top, #3a404d, #181c26);
        background: linear-gradient(to bottom, #3a404d, #181c26);
        border-radius: 10px;
        box-shadow: 0 7px 30px rgba(62, 9, 11, 0.3);
        margin-right: 100px;
        margin-left: 100px;
    }
    .leaderboard h1 {
        font-size: 18px;
        color: #e1e1e1;
        padding: 12px 13px 18px;
    }
    .leaderboard h1 svg {
        width: 25px;
        height: 26px;
        position: relative;
        top: 3px;
        margin-right: 6px;
        vertical-align: baseline;
    }
    .leaderboard ol {
        counter-reset: leaderboard;
        padding-left: 0;
    }
    .leaderboard ol li {
        position: relative;
        z-index: 1;
        font-size: 14px;
        counter-increment: leaderboard;
        padding: 18px 10px 18px 50px;
        cursor: pointer;
        -webkit-backface-visibility: hidden;
        backface-visibility: hidden;
        -webkit-transform: translateZ(0) scale(1, 1);
        transform: translateZ(0) scale(1, 1);
        list-style: none;
    }
    .leaderboard ol li::before {
        content: counter(leaderboard);
        position: absolute;
        z-index: 2;
        top: 15px;
        left: 15px;
        width: 20px;
        height: 20px;
        line-height: 20px;
        color: #c24448;
        background: #fff;
        border-radius: 20px;
        text-align: center;
    }
    .leaderboard ol li mark {
        position: absolute;
        z-index: 2;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        padding: 18px 10px 18px 50px;
        margin: 0;
        background: none;
        color: #fff;
    }
    .leaderboard ol li mark::before, .leaderboard ol li mark::after {
        content: '';
        position: absolute;
        z-index: 1;
        bottom: -11px;
        left: -9px;
        border-top: 10px solid #c24448;
        border-left: 10px solid transparent;
        -webkit-transition: all .1s ease-in-out;
        transition: all .1s ease-in-out;
        opacity: 0;
    }
    .leaderboard ol li mark::after {
        left: auto;
        right: -9px;
        border-left: none;
        border-right: 10px solid transparent;
    }
    .leaderboard ol li small {
        position: relative;
        z-index: 2;
        display: block;
        text-align: right;
    }
    .leaderboard ol li::after {
        content: '';
        position: absolute;
        z-index: 1;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #fa6855;
        box-shadow: 0 5px 0 rgba(0, 0, 0, 0.08);
        -webkit-transition: all .3s ease-in-out;
        transition: all .3s ease-in-out;
        opacity: 0;
    }
    /*1*/
    .leaderboard ol li:nth-child(1) {
        background: #fa6855;
    }
    .leaderboard ol li:nth-child(1)::after {
        background: #fa6855;
    }

    /*2*/
    .leaderboard ol li:nth-child(2) {
        background: #F46454 /*#e0574f*/;
    }
    .leaderboard ol li:nth-child(2)::after {
        background: #F46454;
        box-shadow: 0 4px 0 rgba(0, 0, 0, 0.08);
    }
    .leaderboard ol li:nth-child(2) mark::before, .leaderboard ol li:nth-child(2) mark::after {
        border-top: 8px solid #ba4741;
        bottom: -8px;
    }

    /*3*/
    .leaderboard ol li:nth-child(3) {
        background: #EE6052;
    }
    .leaderboard ol li:nth-child(3)::after {
        background: #EE6052;
        box-shadow: 0 3px 0 rgba(0, 0, 0, 0.11);
    }
    .leaderboard ol li:nth-child(3) mark::before, .leaderboard ol li:nth-child(3) mark::after {
        border-top: 6px solid #b0433f;
        bottom: -6px;
    }

    /*4*/
    .leaderboard ol li:nth-child(4) {
        background: #E75C51 /*#cd4b4b*/;
    }
    .leaderboard ol li:nth-child(4)::after {
        background: #E75C51 /*#cd4b4b*/;
        box-shadow: 0 2px 0 rgba(0, 0, 0, 0.15);
    }
    .leaderboard ol li:nth-child(4) mark::before, .leaderboard ol li:nth-child(4) mark::after {
        border-top: 4px solid #b0433f;
        bottom: -4px;
    }

    /*5*/
    .leaderboard ol li:nth-child(5) {
        background: #E1584F;
    }
    .leaderboard ol li:nth-child(5)::after {
        background: #E1584F;
        box-shadow: 0 1px 0 rgba(0, 0, 0, 0.12);
    }
    .leaderboard ol li:nth-child(5) mark::before, .leaderboard ol li:nth-child(5) mark::after {
        border-top: 2px solid #b0433f;
        bottom: -2px;
    }

    /*6 */
    .leaderboard ol li:nth-child(6) {
        background: #DB544E;
    }
    .leaderboard ol li:nth-child(6)::after {
        background: #DB544E;
        box-shadow: 0 -1px 0 rgba(0, 0, 0, 0.15);
    }
    .leaderboard ol li:nth-child(6) mark::before, .leaderboard ol li:nth-child(6) mark::after {
        top: -2px;
        bottom: auto;
        border-top: none;
        border-bottom: 1px solid #a63d3d;
    }

    /*7*/
    .leaderboard ol li:nth-child(7) {
        background: #D5504C /*#e0574f*/;
    }
    .leaderboard ol li:nth-child(7)::after {
        background: #D5504C /*#e0574f*/;
        box-shadow: 0 -2px 0 rgba(0, 0, 0, 0.15);
    }
    .leaderboard ol li:nth-child(7) mark::before, .leaderboard ol li:nth-child(7) mark::after {
        top: -4px;
        bottom: auto;
        border-top: none;
        border-bottom: 3px solid #a63d3d;
    }

    /*8*/
    .leaderboard ol li:nth-child(8) {
        background: #CE4C4B;
    }
    .leaderboard ol li:nth-child(8)::after {
        background: #CE4C4B;
        box-shadow: 0 -3px 0 rgba(0, 0, 0, 0.15);
    }
    .leaderboard ol li:nth-child(8) mark::before, .leaderboard ol li:nth-child(8) mark::after {
        top: -6px;
        bottom: auto;
        border-top: none;
        border-bottom: 5px solid #a63d3d;
    }

    /*9*/
    .leaderboard ol li:nth-child(9) {
        background: #C84849;
    }
    .leaderboard ol li:nth-child(9)::after {
        background: #C84849;
        box-shadow: 0 -4px 0 rgba(0, 0, 0, 0.15);
    }
    .leaderboard ol li:nth-child(9) mark::before, .leaderboard ol li:nth-child(9) mark::after {
        top: -8px;
        bottom: auto;
        border-top: none;
        border-bottom: 7px solid #a63d3d;
    }

    /*10*/
    .leaderboard ol li:nth-child(10) {
        background: #C24448;
        border-radius: 0 0 10px 10px;
    }
    .leaderboard ol li:nth-child(10)::after {
        background: #C24448;
        box-shadow: 0 -5px 0 rgba(0, 0, 0, 0.12);
        border-radius: 0 0 10px 10px;
    }
    .leaderboard ol li:nth-child(10) mark::before, .leaderboard ol li:nth-child(10) mark::after {
        top: -10px;
        bottom: auto;
        border-top: none;
        border-bottom: 9px solid #993639;
    }


    .leaderboard ol li:hover {
        z-index: 2;
        overflow: visible;
    }
    .leaderboard ol li:hover::after {
        opacity: 1;
        -webkit-transform: scaleX(1.06) scaleY(1.03);
        transform: scaleX(1.06) scaleY(1.03);
    }
    .leaderboard ol li:hover mark::before, .leaderboard ol li:hover mark::after {
        opacity: 1;
        -webkit-transition: all .35s ease-in-out;
        transition: all .35s ease-in-out;
    }
</style>

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
