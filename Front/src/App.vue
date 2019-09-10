<template>
    <div id="app" class="container-fluid">
        <div class="site-info">
            <h1>Daveo</h1>
            <h3>DevFest 2019</h3>
        </div>
        <nav>
            <router-link class="btn btn-primary" to="/">Liste des joueurs</router-link>
            <router-link class="btn btn-primary" to="/ajouterScanner">Scanner un joueur</router-link>
            <router-link class="btn btn-primary" to="/ajouterManuellement">Ajouter un joueur manuellement</router-link>
            <router-link class="btn btn-primary" to="/rechercher">Rechercher un joueur</router-link>
        </nav>
        <br/>
        <router-view/>
    </div>
</template>

<script>
export default {
  name: "app"
};
</script>

<style>
.site-info {
  color: blue;
  margin-bottom: 20px;
}

.btn-primary {
  margin-right: 5px;
}

.container-fluid {
  text-align: center;
}
</style>
