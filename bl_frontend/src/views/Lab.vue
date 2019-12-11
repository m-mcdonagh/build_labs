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
      id: "0", // lab _id needs to be filled in by axios
      name: "test", // needs to be filled in by axios
      selectedPartID: null,
      partsList: [ //needs to be filled in by axios

      ],
      currentStep: 0,
      steps : [ //needs to be filled in by axios

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
      var urlParams = new URLSearchParams(location.search);
      this.id = urlParams.get('id')
      this.loadLab(urlParams.get('id'));


      //var queryString = location.search;


    //
  },
  methods: {
    async loadLab(id){
      let lab_response = (await axios.get("/api/labs/lab?id="+id)).data;
      await lab_response.steps.forEach(async (step)=>{


          let part = step.newPart;

          let slotPointsCoord = [];
          for (let j = 0; j < part.slotPoints.length; j++) {
            slotPointsCoord[j] = {
              x: part.slotPoints[j][0],
              y: part.slotPoints[j][1]
            };

          }

          var dimensions = {
            height: part.dimensions[0],
            width: part.dimensions[1]
          }

          let connectorPoint = {
              x: part.connectorPoint[0],
              y: part.connectorPoint[1]
          }

          let url = ('http://130.245.170.216:3003/media/'+step.newPart.img)

          let img_data = url;

          step.newPart.slotPoints = slotPointsCoord;
          step.newPart.dimensions = dimensions;
          step.newPart.connectorPoint = connectorPoint;
          step.newPart.img_src = img_data;
          this.steps.push(step)



      })

      await lab_response.partsList.forEach(async (part)=>{
        let slotPointsCoord = [];
        for (let j = 0; j < part.slotPoints.length; j++) {
          slotPointsCoord[j] = {
            x: part.slotPoints[j][0],
            y: part.slotPoints[j][1]
          };
        }
        var dimensions = {
          height: part.dimensions[0],
          width: part.dimensions[1]
        }
        let connectorPoint = {
            x: part.connectorPoint[0],
            y: part.connectorPoint[1]
        }


        let img_data = (await axios.get('http://130.245.170.216:3003/media/'+part.img)).config.url;

        this.partsList.push({
            _id: part._id,
            name: part.name,
            img_src: img_data, // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
            dimensions: dimensions,
            slotPoints: slotPointsCoord,
            connectorPoint: connectorPoint
          })
        })


        this.buildWidth = this.steps[0].newPart.dimensions.width;
        this.buildHeight = this.steps[0].newPart.dimensions.height;
        this.resizebuild();
        window.onresize = this.resizebuild;
        for (var i=0; i<this.steps.length; i++) {
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
        var toggle = true;
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
      else if (this.selectedPartID == this.steps[this.currentStep].newPart._id) {
        this.steps[this.currentStep].newPart.connectorPoint = {x:.5, y:.5};
        this.steps[this.currentStep].newPart.connectedAt = {left: .5, top: .5};
        this.buildparts.push(this.steps[this.currentStep].newPart);
        this.currentStep++;
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
      if (this.selectedPartID != this.steps[this.currentStep].newPart._id && slotIndex != this.steps[this.currentStep].parentSlot){
        M.toast({displayLength:2000, html:'Wrong part and wrong slot. Try again'});
      }
      else if (this.selectedPartID != this.steps[this.currentStep].newPart._id) {
        M.toast({displayLength:2000, html:'Wrong part. Try again'});
      }
      else if (slotIndex != this.steps[this.currentStep].parentSlot){
        M.toast({displayLength:2000, html:'Wrong slot. Try again'});
      }
      else {
        this.steps[this.currentStep].newPart.connectedAt = {left: slot.x, top: slot.y};
        this.steps[this.currentStep].newPart.parent = parentPartVue;
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
      background-color: #607d8b77 !important;
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
}
.blink {
  background-color: #607d8b40 !important;
}
.slot:hover {
  background-color: #607d8b77 !important;
}

</style>
