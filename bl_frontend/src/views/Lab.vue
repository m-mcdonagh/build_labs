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

      <build-so-far
        v-bind:buildWidth="buildWidth"
        v-bind:buildHeight="buildHeight"
        v-bind:displayWidth="displayWidth"
        v-bind:displayHeight="displayHeight"
        v-bind:parts="buildparts"
        v-on:slotclick="">
      </build-so-far>

      <div id="instruction-card" class="card-panel cyan darken-3">
        <span id="instruction" class="flow-text">{{ steps[currentStep].instructions }}</span>
      </div>
    </div>

    <div id="controls">
      <button class="btn cyan lighten-3 waves-effect" id="save">SAVE</button>
      <a href="/learn" class="btn cyan lighten-3 waves-effect" id="exit">EXIT</a>
    </div>
  </div>
</template>

<script>
import buildSoFar from '../components/Build.vue';

export default  {
  name: 'lab',
  components: {
    'build-so-far': buildSoFar
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
          parentIndex: 0,
          parentSlot: 0,
          newPart: {
            _id: 1,
            name: "CPU",
            img_src: require('../assets/img/cpu.png'), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
            dimensions: {width: 2, height: 2},
            slotPoints: [],
            connectorPoint: {x:.5, y:.5}
          },
          instructions: "Place the CPU in the CPU slot"
        }
      ],
      buildWidth: null,
      buildHeight: null,
      displayWidth: null,
      displayHeight: null,
      buildparts: []
    }
  },
  created() {
    this.$store.commit('changeNav', 'cyan');
  },
  mounted() {
    //This should be moved to axios then
      this.buildWidth = this.steps[0].newPart.dimensions.width;
      this.buildHeight = this.steps[0].newPart.dimensions.height;
      this.resizebuild();
      window.onresize = this.resizebuild;
    //
  },
  methods: {
    resizebuild() {
      let aspectRatio = this.buildWidth / this.buildHeight;
      let maxWidth = $('#workspace').width() * .95;
      let maxHeight = $('#workspace').height() * .75;
      let width = maxHeight * aspectRatio;
      if (width <= maxWidth) {
        this.displayWidth = width + 'px';
        this.displayHeight = maxHeight + 'px';
      }
      else {
        this.displayWidth = maxWidth + 'px';
        this.displayHeight = (maxWidth / aspectRatio) + 'px';
      }
    },
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
  #workspace {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;

    #instruction-card {
      position: absolute;
      width: 95%;
      height: 20%;      
      bottom: 2.5%;
      overflow-y: scroll;
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
