<template lang="html">
  <div class="main row" id="lab-builder-main">
    <div id="workspace" class="col s9">
      <div id="build-so-far"></div>
      <button v-if="!newStepToggle" id="new-step-btn" data-target="part-selector" class="modal-trigger" v-on:click="newStepToggle=true">
        <img src="../assets/img/add-icon.svg">
      </button>
    </div>


    <div id="steps-side-bar" class="indigo col s3">
      <h1 class="indigo flow-text">STEPS</h1>
      <div id="step-list" class="collection">
        <step-component
          v-for="(step, index ) in steps"
          v-bind:key="step.id"
          v-bind:id="step.id"
          v-bind:index="index"
          v-bind:name="step.name"
          v-bind:instruction="step.instruction">
        </step-component>
      </div>
    </div>
    
    <div id="save-exit-btns" class="row">
      <button class="btn-large indigo lighten-3 waves-effect col s12 m6" id="save">SAVE</button>
      <button class="btn-large indigo lighten-3 waves-effect col s12 m6" id="exit">EXIT</button>
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
          <button class="btn-floating indigo lighten-3 waves-effect" id="cancel" v-on:click="newStepToggle=false">
            <i class="material-icons">close</i>
          </button>
        </div>
      </div>
    </div>


    <div id="part-selector" class="modal modal-fixed-footer">
      <div class="modal-content indigo lighten-3">
        <ul class="collection">
          <li v-for="part in listofparts" class="collection-item">
            <a class="modal-close btn-flat" v-on:click="addpart(part.id)">{{ part.name }}</a>
          </li>
        </ul>
      </div>
      <div class="modal-footer indigo lighten-4">
        <a id="part-cancel" class="modal-close btn-flat" v-on:click="newStepToggle=false">CANCEL</a>
      </div>
    </div>
  </div>
</template>

<script>
import stepComponent from '../components/LabBuilder/Step.vue';

export default  {
  name: 'lab-builder',
  components: {
    'step-component': stepComponent
  },
  created() {
    this.$store.commit('changeNav', 'indigo lighten-1');
  },
  data() {
    return {
      minimizeToggle: false,
      minimizeHeight: null,
      newStepToggle: false,
      stepCounter: 0,
      steps: [],
      // TODO axios this.parts
      listofparts: [
        {id:0, name:'part0'},
        {id:1, name:'part1'},
        {id:2, name:'part2'},
        {id:3, name:'part3'},
        {id:4, name:'part4'},
        {id:5, name:'part5'}
      ],
      buildparts: [],
    }
  },
  mounted() {
    $('.modal').modal();
  },
  methods: {
    addpart(id) {
      // TODO axios for the full part component and add to #build-so-far
    },
    addstep() {
      // TODO check if part was connected
      this.steps.push({
        id: this.stepCounter++,
        name: $('#step-name').val(),
        instruction: $('#step-instruction').val()
      });
      this.newStepToggle = false;
    },
    minimize(e) {
      if (this.minimizeToggle){
        $('#control-btns').show();
        $('#step-details .row').show()
        $('#step-details').animate({
          width: '800px',
          height: this.minimizeHeight + 'px',
        });
        $('#step-details').css({
          'overflow-y': 'visible'
        });
        this.minimizeToggle=false;
      }
      else {
        $('#control-btns').hide();
        this.minimizeHeight = $('#step-details').height() + 19;
        $('#step-details').animate({
          width: $(e.target).width() + 'px',
          height: $(e.target).height() + 'px',
        }, function(){
          $('#step-details .row').hide();
        });
        $('#step-details').css({
          'overflow-y': 'scroll'
        });
        this.minimizeToggle=true;
      }
    }
  }
}
</script>

<style scoped lang="scss">
#lab-builder-main {
  #workspace {
    position: relative;
    height: 100%;

    #build-so-far {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
      width: 75%;

      img {
        max-height: 80vh;
      }
    }
    #new-step-btn {
      display: contents;

      img {
        position: absolute;
        left: 85%;
        top: 50%;
        width: 25%;
        transform: translate(-50%, -50%);
        cursor: pointer;
      }
      img:hover{
        opacity: .75;
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

      .row{
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

    h1{
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
