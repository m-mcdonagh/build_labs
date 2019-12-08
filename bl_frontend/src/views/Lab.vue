<template lang="html">
  <div class="main row" id="lab-main">
    <div id="parts-side-bar" class="cyan darken-2 col s3 l2">
      <h4 class="center">PARTS</h4>
      <ul id="parts">
        <part-list v-for="part in partsList"
                   v-bind:key="part._id" 
                   v-bind:id="part._id" 
                   v-bind:name="part.name" 
                   v-bind:img_src="part.img_src" 
                   v-on:selectthis="selectthis">  
        </part-list>
      </ul>
      <div id="padding"></div>
    </div>

    <div id="workspace" class="col s9 l10">

      <build-so-far
        v-bind:buildWidth="buildWidth"
        v-bind:buildHeight="buildHeight"
        v-bind:displayWidth="displayWidth"
        v-bind:displayHeight="displayHeight"
        v-bind:parts="buildparts"
        v-on:slotclick="">
      </build-so-far>

      <div id="instruction-card" class="card-panel cyan darken-1">
        <span v-if="currentStep < steps.length" id="instruction" class="flow-text">{{ steps[currentStep].instructions }}</span>
      </div>
    </div>

    <div id="controls">
      <button class="btn cyan lighten-3 waves-effect" id="save">SAVE</button>
      <a href="/learn" class="btn cyan lighten-3 waves-effect" id="exit">EXIT</a>
    </div>

    <div v-if="currentStep == steps.length" id="success">
      <div class="card cyan lighten-5 hoverable">
        <div class="card-content black-text">
          <span class="card-title">Congratulations!</span>
            <p>You successfully completed {{name}}</p>
          </div>
          <div class="card-action">
            <a v-bind:href="'/lab?restart=true&id=' + id">Restart</a>
            <a href="/learn" class="right">Exit</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import buildSoFar from '../components/Build.vue';
import partlist from '../components/Lab/PartList';

export default  {
  name: 'lab',
  components: {
    'build-so-far': buildSoFar,
    'part-list': partlist
  },
  data() {
    return {
      id: "0", // lab _id needs to be filled in by axios
      name: "test", // needs to be filled in by axios
      selectedPartID: null,
      partsList: [ //needs to be filled in by axios
        {
          _id: 0,
          name: "Motherboard",
          img_src: require('../assets/img/motherboard.png'), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
          dimensions: {width: 12, height: 12},
          slotPoints: [{x:.55, y:.35}],
          connectorPoint: null
        },
        {
          _id: 1,
          name: "CPU",
          img_src: require('../assets/img/cpu.png'), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
          dimensions: {width: 2, height: 2},
          slotPoints: [],
          connectorPoint: {x:.5, y:.5}
        },
      ],
      currentStep: 0,
      steps : [ //needs to be filled in by axios
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
    selectthis(id) {
      this.selectedPartID= id
    },
    resizebuild() {
      let aspectRatio = this.buildWidth / this.buildHeight;
      let maxWidth = $('#workspace').width() * .95;
      let maxHeight = $('#workspace').height() * .95;
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
    justify-content: center;
    height: 85%;
    padding: 0;

    #instruction-card {
      position: absolute;
      width: 95%;
      height: 15%;      
      top: 100%;
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
    @media screen and (min-width: 1115px) {
      width: 17%;
    }
    z-index: 1;

    .btn {
      width: 90%;
    }
    @media screen and (min-width: 800px){
      flex-direction: row;
      .btn {
          width: 45%;
          font-size: 2rem;
      }
    }
  }
  #success {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    width: 100vw;
    height: 100vh;
    z-index: 2;
    background-color: #00000044;
    .card {
      max-width: 80%;
      max-height: 90%;
      .card-content {
        max-height: 70vh;
        overflow: hidden;
        p {
          word-break: break-word;
        }
      }
    }
  }
}
</style>
