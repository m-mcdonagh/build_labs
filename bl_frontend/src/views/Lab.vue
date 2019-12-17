<template lang="html">
  <div class="main row" id="lab-main">
    <div id="parts-side-bar" class="cyan darken-2 col s3 l2">
      <h4 class="center">PARTS</h4>
      <ul id="parts">
        <part-list v-for="part in partsList"
                   v-bind:key="part.id"
                   v-bind:id="part.id"
                   v-bind:name="part.name"
                   v-bind:img_src="part.img_src"
                   v-on:selectthis="selectthis">
        </part-list>
      </ul>
      <div id="padding"></div>
    </div>

    <div id="workspace" class="col s9 l10">

      <build-so-far
        v-if="buildparts.length > 0"
        v-bind:buildWidth="buildWidth"
        v-bind:buildHeight="buildHeight"
        v-bind:displayWidth="displayWidth"
        v-bind:displayHeight="displayHeight"
        v-bind:parts="buildparts"
        v-on:slotclick="addpart">
      </build-so-far>

      <div id="firstslot"
           v-if="buildparts.length == 0"
           v-bind:style="{width: displayWidth, height: displayHeight}"
           v-on:click="addfirstpart">
      </div>

      <div id="instruction-card" class="card-panel cyan darken-1">
        <span v-if="currentStep < steps.length" id="instruction" class="flow-text">{{ steps[currentStep].instruction }}</span>
      </div>
    </div>

    <div id="controls">
      <button class="btn cyan lighten-3 waves-effect" id="save">SAVE</button>
      <a href="/learn" class="btn cyan lighten-3 waves-effect" id="exit">EXIT</a>
    </div>

    <div v-if="currentStep == steps.length && steps.length != 0" id="success">
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
      id: "0",
      name: "test", // needs to be filled in by axios
      selectedPartID: null,
      partsList: [],
      currentStep: 0,
      steps : [],
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
    var urlParams = new URLSearchParams(location.search);
    this.id = urlParams.get('id')
    this.loadLab(this.id);
  },
  methods: {
    async loadLab(id){
      let lab_response = (await axios.get("/api/labs/lab?id="+id)).data;
      this.name = lab_response.name;
      
      await lab_response.steps.forEach(async (step)=>{
        let slotPoints = [];
        for (let i=0; i<step.newPart.slotPoints.length; i++) {
          slotPoints[i] = {
            x: step.newPart.slotPoints[i][0],
            y: step.newPart.slotPoints[i][1],
            connected: false
          };
        }
        step.newPart.slotPoints = slotPoints;
        step.newPart.dimensions = {
          width: step.newPart.dimensions[0],
          height: step.newPart.dimensions[1]
        };
        if (step.newPart.connectorPoint && 
                              (step.newPart.connectorPoint[0] || step.newPart.connectorPoint[0] === 0) && 
                              (step.newPart.connectorPoint[1] || step.newPart.connectorPoint[1] === 0)) {
          step.newPart.connectorPoint = {
              x: step.newPart.connectorPoint[0],
              y: step.newPart.connectorPoint[1]
          };
        }
        else {
          step.newPart.connectorPoint = {
              x: .5,
              y: .5
          };
        }
        step.newPart.img_src = 'http://130.245.170.216:3003/media/'+step.newPart.img;
        this.steps.push(step)
      });

      await lab_response.partsList.forEach(async (part)=>{
        let slotPoints = [];
        for (let i=0; i<part.slotPoints.length; i++) {
          slotPoints[i] = {
            x: part.slotPoints[i][0],
            y: part.slotPoints[i][1]
          };
        }
        let connectorPoint = {};
        if (part.connectorPoint && (part.connectorPoint[0] || part.connectorPoint[0] === 0) && (part.connectorPoint[1] || part.connectorPoint[1] === 0)){
          connectorPoint = {
              x: part.connectorPoint[0],
              y: part.connectorPoint[1]
          };
        }
        else {
          connectorPoint = {
              x: .5,
              y: .5
          };
        }
        this.partsList.push({
          id: part._id,
          name: part.name,
          slotPoints: slotPoints,
          dimensions: {
            width: part.dimensions[0],
            height: part.dimensions[1]
          },
          connectorPoint: connectorPoint,
          img_src: (await axios.get('http://130.245.170.216:3003/media/'+part.img)).config.url,
        });
      });

      this.buildWidth = lab_response.steps[0].newPart.dimensions.width;
      this.buildHeight = lab_response.steps[0].newPart.dimensions.height;
      this.resizebuild();
      window.onresize = this.resizebuild;
      for (let i=0; i<this.steps.length; i++) {
        for(let j=0; j<this.steps[i].children.length; j++) {
          let child = this.steps[this.steps[i].children[j]]

          this.steps[i].newPart.slotPoints[child.parentSlot].width = child.newPart.dimensions.width;
          this.steps[i].newPart.slotPoints[child.parentSlot].height = child.newPart.dimensions.height;
          this.steps[i].newPart.slotPoints[child.parentSlot].connectorPoint = {
            x: child.newPart.connectorPoint.x,
            y: child.newPart.connectorPoint.y
          }
        }
      }
      let toggle = true;
      setInterval(function() {
        if (toggle){
          $('.slot, #firstslot').addClass('blink');
          toggle = false;
        }
        else {
          $('.slot, #firstslot').removeClass('blink');
          toggle = true;
        }
      }, 1000);
    },
    selectthis(id) {
      if (this.selectedPartID == id) {
        this.selectedPartID = null;
        $('.partlist').removeClass('selected');
      }
      else {
        this.selectedPartID = id;
      }
    },
    addfirstpart() {
      if (this.selectedPartID === null) {
        M.toast({displayLength:2000, html:'Please select a part from the sidebar'});
      }
      else if (this.selectedPartID == this.steps[this.currentStep].newPart.id) {
        this.steps[this.currentStep].newPart.connectorPoint = {x:.5, y:.5};
        this.steps[this.currentStep].newPart.connectedAt = {left: .5, top: .5};
        this.buildparts.push(this.steps[this.currentStep].newPart);
        this.currentStep++;
        $('#firstslot').hide();
      }
      else {
        M.toast({displayLength:2000, html: 'Wrong part. Try again'});
      }
    },
    addpart(parentPartVue, slot, slotIndex) {
      if (this.selectedPartID === null) {
        M.toast({displayLength:2000, html:'Please select a part from the sidebar'});
        return;
      }
      if (this.selectedPartID != this.steps[this.currentStep].newPart.id && slotIndex != this.steps[this.currentStep].parentSlot){
        M.toast({displayLength:2000, html:'Wrong part and wrong slot. Try again'});
      }
      else if (this.selectedPartID != this.steps[this.currentStep].newPart.id) {
        M.toast({displayLength:2000, html:'Wrong part. Try again'});
      }
      else if (slotIndex != this.steps[this.currentStep].parentSlot){
        M.toast({displayLength:2000, html:'Wrong slot. Try again'});
      }
      else {
        this.steps[this.currentStep].newPart.connectedAt = {left: slot.x, top: slot.y};
        this.steps[this.currentStep].newPart.parent = parentPartVue;
        let parent = this.steps[this.steps[this.currentStep].parentIndex];
        let parentSlot = this.steps[this.currentStep].parentSlot;
        parent.newPart.slotPoints[parentSlot].connected = true;
        this.buildparts.push(this.steps[this.currentStep].newPart);
        this.currentStep++;
      }
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
    #firstslot {
      background-image: radial-gradient(#607d8bAA, #607d8b55 67%);
      transition: background-color .5s;
      background-color: #607d8b20;
      cursor: pointer;
    }
    #firstslot:hover {
      background-color: #28e6c663 !important;
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

<style lang="scss">
.slot {
  position: absolute;
  background-image: radial-gradient(#607d8bAA, #607d8b44 67%);
  transition: background-color .5s;
  background-color: #607d8b20;
  border: none;
  min-width: 2.5vh;
  min-height: 2.5vh;
}
.blink {
  background-color: #282be663 !important;
}
.slot:hover {
  background-color: #3143a593 !important;
}

</style>
