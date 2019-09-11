<template>
    <div id="app" class="container-fluid">
        <div class="sidebar">
            <section class="daveo">
                <img class="logo" src="./assets/logo_devfest.png"/>
            </section>
            <section>
                <nav>
                    <router-link class="item-menu" to="/">Liste des joueurs</router-link>
                    <router-link class="item-menu" to="/ajouterScanner">Scanner un joueur</router-link>
                    <router-link class="item-menu" to="/ajouterManuellement">Ajouter un joueur manuellement</router-link>
                    <router-link class="item-menu" to="/rechercher">Rechercher un joueur</router-link>
                </nav>
            </section>
        </div>
        <router-view/>
    </div>
</template>

<script>
export default {
    firstName: "app"
};
</script>
