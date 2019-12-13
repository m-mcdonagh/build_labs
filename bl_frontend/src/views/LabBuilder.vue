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
      <button v-if="selectedPart && selectedPart.connectedAt" class="step-btn" v-on:click="detach">
        <img src="../assets/img/detach.svg">
      </button>
    </div>


    <div id="steps-side-bar" class="indigo col s3">
      <div id="side-bar-top" class="indigo">
        <div class="input-field col s12">
          <input id="lab-name" type="text" v-model="name">
          <label for="lab-name">Lab Name</label>
        </div>
        <h1 class="flow-text">STEPS</h1>
      </div>
      <div id="step-list" class="collection">
        <step-component
          v-for="(step, index) in steps"
          v-bind:key="step.id"
          v-bind:id="step.id"
          v-bind:index="index"
          v-bind:name="step.name"
          v-bind:instruction="step.instruction"
          v-bind:img_src="step.newPart.img_src"
          v-bind:rotation="step.rotation"
          v-on:remove="deletestep(index)">
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
          <button class="btn-floating indigo lighten-3 waves-effect" id="cancel" v-on:click="cancelstep">
            <i class="material-icons">close</i>
          </button>
        </div>
      </div>
    </div>


    <div id="part-selector" class="modal modal-fixed-footer">
      <div class="modal-content indigo lighten-3">
        <ul class="collection">
          <li v-for="part in listofparts" class="collection-item">
            <a class="modal-close btn-flat" v-on:click="steps.length ? selectpart(part) : addfirstpart(part)">
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
      name: "", //Lab Name
      minimizeToggle: false,
      minimizeHeight: null,
      newStepToggle: false,
      stepCounter: 0, // Needs to be set to steps.length when using loading with axios
      steps: [], // step.newPart needs attributes not included in the Part Model before being added to this.buildparts, when loading
      // Those attributes are added in addpart() and addfirstpart(), but need to be derived from the step
      // addstep() in Labuilder.vue and mounted() in Lab.vue might be helpful :D
      buildWidth: null,
      buildHeight: null,
      displayWidth: null,
      displayHeight: null,
      listofparts: [
        // TODO axios this.listofparts (it should be all parts that CAN be added, not the ones already added)
        {
          id: 'motherboard',
          name: "Motherboard",
          img_src: require("../assets/img/motherboard.png"), // Needs require since test imgs are in assets folder. If the Java hosts the images, all it needs is the url, no require
          dimensions: { width: 12, height: 12 },
          slotPoints: [{ x: 0.55, y: 0.35 }, {x: .2, y: .8}],
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
    M.updateTextFields();
    this.resizesteps();
    window.onresize = this.resizesteps;
    this.getListOfParts();
  },
  methods: {
    async saveLab() {
      console.log("CURRENT PARTS BUILT", this.buildparts);

      var part_ids = [];

      var steps_copy = this.steps.slice();
      console.log("COPY", steps_copy);

      steps_copy.forEach(function(item, index) {
        //console.log("ITME",item.newPart.id,"AND", index);
        // part_ids.push(item.newPart.id);
        item.newPart = item.newPart.id;
      });

      console.log("THIS IS PART ID", steps_copy);
      let userSessionData = await axios({
        method: "get",
        url: "/api/accounts/session"
      });
      let username = userSessionData.data;
      let response = await axios({
        method: "post",
        url: "/api/labs/lab",
        params: {
          username: username
        },
        data: {
          name: this.name,
          steps: steps_copy
          //partList: part_ids //Causes circular reference
        }
      });
    },
    async getListOfParts() {
      let part_response = await axios({
        method: "get",
        url: "/api/parts/allparts"
      });
      for (var i = 0; i < part_response.data.length; i++) {
        var prt = part_response.data[i];
        var slotPointsCoord = [];

        for (var j = 0; j < prt.slotPoints.length; j++) {
          slotPointsCoord[j] = {
            x: prt.slotPoints[j][0],
            y: prt.slotPoints[j][1]
          };
        }

        // let img_data = await axios.get(
        //   "http://130.245.170.216:3003/media/" + prt.img
        // );
        console.log(prt.img);

        let img_data = await axios.get(
          "http://130.245.170.216:3003/media/" + prt.img
        );

        console.log("IMAGE DATA: ", img_data.config);
        this.listofparts.push({
          id: prt._id,
          name: prt.name,
          dimensions: {
            height: prt.dimensions[0],
            width: prt.dimensions[1]
          },
          //TODO : Fix img_src
          img_src: img_data.config.url, //require("../assets/img/motherboard.png"),
          slotPoints: slotPointsCoord,
          connectorPoint: {
            x: prt.connectorPoint[0],
            y: prt.connectorPoint[1]
          }
        });
      }
    },
    selectpart(part) {
      this.selectedPart = this.clonepart(part);
    },
    newstep() {
      this.newStepToggle = true;
    },
    detach() {
      this.buildparts.pop();
      this.selectedPart.connectedAt = null;
      this.selectedPart.parent = null;
      this.selectedPart.parentSlot = null;
    },
    addfirstpart(part) {
      if (this.buildparts.length) this.buildparts.pop();
      this.firststep = true;
      this.buildWidth = part.dimensions.width;
      this.buildHeight = part.dimensions.height;
      this.resizebuild();
      window.onresize = function resize() {
        this.resizebuild();
        this.resizesteps();
      }.bind(this);
      let newPart = this.clonepart(part);
      newPart.parent = null;
      newPart.connectorPoint = { x: 0.5, y: 0.5 };
      newPart.connectedAt = { left: 0.5, top: 0.5 };
      newPart.parentSlot = null;
      newPart.stepID = this.stepCounter++;
      this.buildparts.push(newPart);
    },
    addpart(parentPartVue, slot, i) {
      if (this.selectedPart == null) {
        return;
      }
      this.selectedPart.connectedAt = { left: slot.x, top: slot.y };
      this.selectedPart.parent = parentPartVue;
      this.selectedPart.parentSlot = i;
      this.selectedPart.stepID = this.stepCounter++;
      this.buildparts.push(this.selectedPart);
    },
    addstep() {
      let index = this.steps.length;
      let newPart = this.firststep ? this.buildparts[0] : this.selectedPart;
      newPart.stepIndex = index;
      let parentIndex = newPart.parent && newPart.parent.part ? newPart.parent.part.stepIndex : null;
      if (parentIndex !== null) {
        this.steps[parentIndex].children.push(index)
      }
      if (!this.firststep && (parentIndex === undefined || newPart.parentSlot === undefined || parentIndex === null || newPart.parentSlot === null )) {
        M.toast({displayLength:2000, html:'Please specify a slot'});
        return;
      }
      this.firststep = false;
      this.steps.push({
        id: newPart.stepID,
        index: index,
        parentIndex: parentIndex,
        parentSlot: newPart.parentSlot,
        children: [],
        newPart: newPart,
        name: $("#step-name").val(),
        instruction: $("#step-instruction").val(),
        rotation: 0 //TODO rotation
      });
      console.log("NEW STEP ADDED",this.steps)
      this.newStepToggle = false;
      this.selectedPart = null;
    },
    cancelstep() {
      if (this.firststep || this.selectedPart && this.selectedPart.connectedAt) {
        this.buildparts.pop();
      }
      this.newStepToggle = false;
      this.selectedPart = null;
      this.firststep = false;
    },
    deletestep(index) {
      if (!this.steps[index]){
        return;
      }
      if (this.steps[index].children.length > 0){
        let msg = 'Cannot Delete. The following steps are dependant on Step #' + (index + 1) + ':<br>&#8195;Step #';
        for (var i=0; i<this.steps[index].children.length - 1; i++) {
          msg += (this.steps[index].children[i] + 1) + ',<br>&#8195;Step #'
        }
        msg += (this.steps[index].children[i] + 1) + '.'
        M.toast({displayLength:8000,classes:'big-toast', html:'<span>'+ msg +'</span>'});
        return;
      }
      if (this.selectedPart && this.selectedPart.connectedAt) {
        this.selectedPart.connectedAt = null;
        this.selectedPart.parent = null;
        this.selectedPart.parentSlot = null;
        this.buildparts.pop();
      }
      this.buildparts.splice(index, 1);
      if (this.steps.length == 1) {
        this.steps.pop();
        this.selectedPart = null;
        this.newStepToggle = false;
      }
      else{
        let parent = this.steps[this.steps[index].parentIndex];
        parent.children.splice([parent.children.indexOf(index)], 1);
        this.steps.splice(index, 1);
        for (var i=0; i<this.steps.length; i++) {
          this.steps[i].children = [];
          for (var j=0; j<this.steps.length; j++) {
            if (this.steps[j].parentIndex == this.steps[i].index) {
              this.steps[j].parentIndex = i;
              this.steps[i].children.push(j);
            }
          }
          this.steps[i].index = i;
        }
      }
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
    resizesteps() {
      $("#step-list").css({
        height:
          $("#steps-side-bar").height() -
          $("#side-bar-top").height() -
          $("#save-exit-btns").height() +
          "px"
      });
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
    z-index: 1;
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
    overflow: hidden;

    #side-bar-top {
      position: absolute;
      top: 0;
      width: 100%;
      margin: 0;
      height: 100px;
      text-align: center;
      h1 {
        position: absolute;
        bottom: 0;
        margin: 0;
        width: 100%;
      }
    }
    #step-list {
      position: relative;
      margin-top: 0;
      margin-left: 10px;
      top: 12.5%;
      word-wrap: break-word;
      border: none;
      background-color: #00000000;
      overflow-y: scroll;
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

<style lang="scss">
.slot {
  position: absolute;
  width: 30px;
  height: 30px;
  border: 4px solid white;
  border-radius: 100%;
  opacity: 0.67;
  background-color: #00bfa5;
}
.slot:hover {
  opacity: 1;
}
#toast-container {
  top: 50px;
  left: 25px;
  width: 525px;

  .toast {
    max-width: 50%;
    justify-content: center;
  }
  .big-toast {
    max-width: 100%;
  }
}
</style>