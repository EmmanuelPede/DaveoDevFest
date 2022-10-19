import Vue from "vue";
import App from "./App.vue";
import router from "./router";

import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faAngleRight,
  faChevronCircleRight,
  faChevronRight,
  faThumbsUp,
  faSkullCrossbones,
  faUserCheck,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// register the plugin on vue
import Toasted from "vue-toasted";

import "../node_modules/event-source-polyfill/src/eventsource";

library.add(faAngleRight);
library.add(faChevronRight);
library.add(faChevronCircleRight);
library.add(faThumbsUp);
library.add(faSkullCrossbones);
library.add(faUserCheck);

Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false;

Vue.use(Toasted);

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
