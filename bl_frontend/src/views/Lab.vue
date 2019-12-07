<template lang="html">
  <div class="main row" id="lab-main">
    <div id="parts-side-bar" class="cyan darken-1 col s3">
      <h4 class="center">PARTS</h4>
      <ul id="parts" class="collapsible">
        <!-- <part-list>
        </part-list> -->
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
  components: {
  },
  data() {
    return {
      currentStep: 0,
      steps : [
        {
          index: 0,
          parentIndex: null,
          parentSlot: null,
          newPart: {
            _id: 0,
            name: "Motherboard",
            img_src: require('../assets/img/motherboard.png'), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
            dimensions: {width: 12, height: 12},
            slotPoints: [{x:.55, y:.35}],
            connectorPoint: null
          },
          instructions: "Place the motherboard"
        },
        {
          index: 1,
          parent: 0,
          parentSlot: 0,
          newPart: {
            _id: 0,
            name: "CPU",
            img_src: require('../assets/img/cpu.png'), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
            dimensions: {width: 2, height: 2},
            slotPoints: [],
            connectorPoint: {x:.5, y:.5}
          },
          instructions: "Place the CPU in the CPU slot"
        }
      ],
      buildparts: []
    }
  },
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
