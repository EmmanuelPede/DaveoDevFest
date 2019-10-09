import Vue from "vue";
import Router from "vue-router";
import CustomersList from "./components/ListeDesJoueurs.vue";
import AddCustomer from "./components/AjouterJoueur.vue";
import ScanCustomer from "./components/ScannerJoueur.vue";
import SearchCustomers from "./components/Recherche.vue";
import Customer from "./components/Joueur.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "customer-list",
      alias: "/joueur",
      component: CustomersList,
      children: [
        {
          path: "/joueur/:id",
          name: "customer-details",
          component: Customer,
          props: true
        }
      ]
    },
    {
      path: "/ajouterManuellement",
      name: "add",
      component: AddCustomer
    },
    {
      path: "/ajouterScanner",
      name: "scan",
      component: ScanCustomer
    },
    {
      path: "/rechercher",
      name: "search",
      component: SearchCustomers
    }
  ]
});
