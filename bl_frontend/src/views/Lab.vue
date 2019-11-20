<template lang="html">
  <div class="main row" id="lab-main">
    <div id="parts-side-bar" class="cyan darken-1 col s3">
      <h4 class="center">PARTS</h4>
      <ul id="parts" class="collapsible">
        <!-- v-for parts (possibly categorized)-->
      </ul>
      <div id="padding"></div>
    </div>
    <div id="workspace" class="col s9">
      <div id="build-so-far"></div>
    </div>
    <div id="controls">
      <button class="btn cyan lighten-3 waves-effect" id="save">SAVE</button>
      <a href="/learn" class="btn cyan lighten-3 waves-effect" id="exit">EXIT</a>
    </div>
  </div>
</template>

<script lang="js">

export default  {
  name: 'lab',
  created() {
    this.$store.commit('changeNav', 'cyan');
  },
  mounted () {

  }
}


</script>

<style scoped lang="scss">
#lab-main {
  #parts-side-bar {
    align-items: center;
    overflow-x: hidden;
    overflow-y: scroll;
    padding: 0;
    padding-bottom: 50px;

    #parts {
      margin: 0;
      width: 100%;
      box-shadow: none;
    }
    #padding {
      height: 50px;
    }
  }
  #controls {
    display: flex;
    flex-direction: column;
    position: fixed;
    left: 10px;
    bottom: 10px;
    width: 25%;
    z-index: 1;

    .btn {
      width: 95%;
    }

    @media screen and (min-width: 800px){
      flex-direction: row;
      .btn {
          width: 45%;
          font-size: 2rem;
      }
    }
  }
}
</style>
