<template lang="html">
  <div class="main row" id="lab-builder-main">
    <div id="workspace" class="col s9">
      
      <build-so-far
        v-bind:buildWidth="buildWidth"
        v-bind:buildHeight="buildHeight"
        v-bind:displayWidth="displayWidth"
        v-bind:displayHeight="displayHeight"
        v-bind:parts="buildparts"
        v-on:slotclick="addpart">
      </build-so-far>

      <button id="new-step-btn" class="step-btn modal-trigger" data-target="part-selector" v-on:click="newstep">
        <img v-bind:src="firststep ? swapicon : (selectedPart ? selectedPart.img_src : addicon)"
             v-bind:style="selectedPart && selectedPart.connectedAt ? {display: 'none'} : {}">
      </button>
      <button v-if="selectedPart && selectedPart.connectedAt" class="step-btn" v-on:click="selectedPart.connectedAt = null; buildparts.pop()">
        <img src="../assets/img/detach.svg">
      </button>
    </div>


    <div id="steps-side-bar" class="indigo col s3">
      <h1 class="indigo flow-text">STEPS</h1>
      <div id="step-list" class="collection">
        <step-component
          v-for="(step, index) in steps"
          v-bind:key="step.id"
          v-bind:id="step.id"
          v-bind:index="index"
          v-bind:name="step.name"
          v-bind:instruction="step.instruction">
        </step-component>
      </div>
    </div>
    
    <div id="save-exit-btns" class="row">
      <button v-on:click="saveLab()" class="btn-large indigo lighten-3 waves-effect col s12 m6" id="save">SAVE</button>
      <a href="/create" class="btn-large indigo lighten-3 waves-effect col s12 m6" id="exit">EXIT</a>
    </div>

    <div v-if="newStepToggle" id="controls">
      <div id="step-details" class="transparent">
        <div class='row indigo'>
          <div class="input-field col s6">
            <input id="step-name" type="text">
            <label for="step-name">Step Name</label>
          </div>
          <div class="input-field col s12">
            <textarea id="step-instruction" class="materialize-textarea"></textarea>
            <label for="step-instruction">Instruction</label>
          </div>
        </div>
        <button id="minimize" class="btn-floating indigo lighten-3 waves-effect" v-on:click="minimize">
          <i class="material-icons">arrow_left</i>
        </button>
        <div id="control-btns">
          <button class="btn-floating indigo lighten-3 waves-effect" id="done" v-on:click="addstep">
            <i class="material-icons">check</i>
          </button>
          <button class="btn-floating indigo lighten-3 waves-effect" id="cancel" v-on:click="newStepToggle=false; selectedPart=null; firststep=false;">
            <i class="material-icons">close</i>
          </button>
        </div>
      </div>
    </div>


    <div id="part-selector" class="modal modal-fixed-footer">
      <div class="modal-content indigo lighten-3">
        <ul class="collection">
          <li v-for="part in listofparts" class="collection-item">
            <a class="modal-close btn-flat" v-on:click="buildparts.length ? selectpart(part) : addfirstpart(part)">
              {{ part.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="modal-footer indigo lighten-4">
        <a id="part-cancel" class="modal-close btn-flat" v-on:click="modalcancel">CANCEL</a>
      </div>
    </div>
  </div>
</template>

<script>
import buildSoFar from "../components/Build.vue";
import stepComponent from "../components/LabBuilder/Step.vue";

export default {
  name: "lab-builder",
  components: {
    "build-so-far": buildSoFar,
    "step-component": stepComponent
  },
  created() {
    this.$store.commit("changeNav", "indigo lighten-1");
  },
  data() {
    return {
      name: '',
      minimizeToggle: false,
      minimizeHeight: null,
      newStepToggle: false,
      stepCounter: 0,
      steps: [], // Steps need IDs, despite not having any in the mongo database. Needed for v-for
      buildWidth: null,
      buildHeight: null,
      displayWidth: null,
      displayHeight: null,
      // TODO axios this.listofparts
      listofparts: [
        {
          id: 0,
          name: "Motherboard",
          img_src: require("../assets/img/motherboard.png"), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
          dimensions: { width: 12, height: 12 },
          slotPoints: [{ x: 0.55, y: 0.35 }],
          connectorPoint: null
        },
        {
          id: 1,
          name: "CPU",
          img_src: require("../assets/img/cpu.png"),
          dimensions: { width: 2, height: 2 },
          slotPoints: [{ x: 0.25, y: 0.25 }],
          connectorPoint: { x: 0.5, y: 0.5 }
        },
        {
          id: 2,
          name: "CPU smol",
          img_src: require("../assets/img/cpu.png"),
          dimensions: { width: 1, height: 1 },
          slotPoints: [],
          connectorPoint: { x: 0.5, y: 0.5 }
        }
        // These IDs can be same as ID's in the mongo database. Need unique IDs for v-for
      ],
      selectedPart: null,
      buildparts: [],
      addicon: require("../assets/img/add-icon.svg"),
      swapicon: require("../assets/img/swap.svg"),
      firststep: false
    };
  },
  mounted() {
    $(".modal").modal();
    this.getListOfParts();
  },
  methods: {
    async saveLab() {
      let response = await axios({
        method: "post",
        url: "http://localhost:8080/labs/lab",
        params: {
          username: "test2"
        },
        data: {
          name:"lab name",
          //labCreator_id: "some id",
          //_id:"some id",
          partsList:[],
          //assignedTo_ids:[],
          steps:[],
          ispublished:false
        }
      });
    },
    async getListOfParts() {
      let part_response = await axios({
        method: "get",
        url: "http://localhost:8080/parts/allparts"
      });
      console.log("PARTS DATA", part_response.data);

      let i = 0;
      for (i = 0; i < part_response.data.length; i++) {
        let prt = part_response.data[i];
        this.listofparts.push({
          id: prt._id,
          name: prt.name,
          dimensions: prt.dimensions,
          //TODO : Fix img_src
          img_src: require("../assets/img/motherboard.png"),
          slotPoints: prt.slotPoints,
          connectorPoint: prt.connectorPoint
        });
      }
    },
    selectpart(part) {
      this.selectedPart = this.clonepart(part);
    },
    newstep() {
      this.newStepToggle = true;
      if (this.firststep) this.buildparts.pop();
    },
    addfirstpart(part) {
      this.firststep = true;
      this.buildWidth = part.dimensions.width;
      this.buildHeight = part.dimensions.height;
      this.resizebuild();
      window.onresize = this.resizebuild;
      let newPart = this.clonepart(part);
      newPart.parent = null;
      newPart.connectorPoint = { x: 0.5, y: 0.5 };
      newPart.connectedAt = { left: 0.5, top: 0.5 };
      this.buildparts.push(newPart);
    },
    addpart(parentPartVue, slot, i) {
      if (this.selectedPart == null) {
        return;
      }
      this.selectedPart.connectedAt = { left: slot.x, top: slot.y };
      this.selectedPart.parent = parentPartVue;
      this.buildparts.push(this.selectedPart);
    },
    addstep() {
      this.firststep = false;
      // TODO check if part was connected
      this.steps.push({
        id: this.stepCounter++,
        name: $("#step-name").val(),
        instruction: $("#step-instruction").val()
      });
      this.newStepToggle = false;
      this.selectedPart = null;
    },
    minimize(e) {
      if (this.minimizeToggle) {
        $("#control-btns").show();
        $("#step-details .row").show();
        $("#step-details").animate({
          width: "800px",
          height: this.minimizeHeight + "px"
        });
        $("#step-details").css({
          "overflow-y": "visible"
        });
        this.minimizeToggle = false;
      } else {
        $("#control-btns").hide();
        this.minimizeHeight = $("#step-details").height() + 19;
        $("#step-details").animate(
          {
            width: $(e.target).width() + "px",
            height: $(e.target).height() + "px"
          },
          function() {
            $("#step-details .row").hide();
          }
        );
        $("#step-details").css({
          "overflow-y": "scroll"
        });
        this.minimizeToggle = true;
      }
    },
    modalcancel() {
      if (!this.selectedPart && !this.firststep) this.newStepToggle = false;
    },
    resizebuild() {
      let aspectRatio = this.buildWidth / this.buildHeight;
      let maxWidth = $("#workspace").width() * 0.75;
      let maxHeight = $("#workspace ").height() * 0.95 - 80;
      let width = maxHeight * aspectRatio;
      if (width <= maxWidth) {
        this.displayWidth = width + "px";
        this.displayHeight = maxHeight + "px";
      } else {
        this.displayWidth = maxWidth + "px";
        this.displayHeight = maxWidth / aspectRatio + "px";
      }
    },
    clonepart(part) {
      let slotPoints = [];
      for (let i = 0; i < part.slotPoints.length; i++) {
        slotPoints.push({
          x: part.slotPoints[i].x,
          y: part.slotPoints[i].y
        });
      }
      return {
        id: part.id,
        name: part.name,
        img_src: part.img_src,
        dimensions: {
          width: part.dimensions.width,
          height: part.dimensions.height
        },
        slotPoints: slotPoints,
        connectorPoint: part.connectorPoint
          ? { x: part.connectorPoint.x, y: part.connectorPoint.y }
          : null
      };
    }
  }
};
</script>

<style scoped lang="scss">
#lab-builder-main {
  #workspace {
    display: flex;
    align-items: center;
    position: relative;
    height: 100%;

    .step-btn {
      display: contents;

      img {
        position: absolute;
        left: 74.5%;
        top: 50%;
        width: 25%;
        transform: translate(0, -50%);
        cursor: pointer;
      }
      img:hover {
        opacity: 0.75;
      }
    }
  }

  #save-exit-btns {
    position: fixed;
    bottom: 0;
    right: 0;
    width: 25%;
    margin: 0;
  }

  #controls {
    display: flex;
    margin: 1%;
    position: absolute;
    bottom: 0;
    width: 73%;
    align-content: center;
    justify-content: right;

    #step-details {
      position: fixed;
      left: 0;
      bottom: 0;
      margin: 1vh 1vw;
      padding: 1vh 1vw;
      width: 800px;
      max-width: 70vw;
      max-height: 25%;

      .row {
        overflow-x: visible;
        overflow-y: scroll;
        max-height: 100%;
      }
      #minimize {
        position: absolute;
        top: 0;
        right: 0;
      }
      #control-btns {
        position: absolute;
        display: flex;
        flex-direction: column;
        bottom: 25px;
        right: 0;
      }
    }
  }

  #steps-side-bar {
    position: relative;
    margin: 0;
    padding: 0;
    overflow-x: hidden;
    overflow-y: scroll;

    h1 {
      position: absolute;
      top: 0;
      width: 100%;
      margin: 0;
      padding-top: 20px;
      text-align: center;
    }
    #step-list {
      position: relative;
      min-height: 75%;
      margin-top: 0;
      margin-left: 10px;
      word-wrap: break-word;
      top: 87px;
      padding-bottom: 65px;
      border: none;
      background-color: #00000000;
    }
  }

  #part-selector {
    position: absolute;
    max-width: 90vw;
    width: 1000px;
    overflow: hidden;
    margin-top: 5vh;
    color: black;

    .collection-item {
      padding: 0;

      a {
        width: 100%;
        height: 100%;
        text-transform: none;
      }
    }
  }
}
</style>
