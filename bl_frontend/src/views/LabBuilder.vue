<template lang="html">
  <div class="main row" id="lab-builder-main">
    <div id="workspace" class="col s9">
      <div id="build-so-far"></div>
      <button id="new-step-btn" data-target="part-selector" class="modal-trigger">
        <img src="../assets/img/add-icon.svg">
      </button>
    </div>


    <div id="steps-side-bar" class="indigo col s3">
      <h1 class="indigo flow-text">STEPS</h1>
      <ol id="step-list"></ol>
    </div>


    <div id="controls">
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
        <button id="minimize" class="btn-floating indigo lighten-3 waves-effect">
          <i class="material-icons">arrow_left</i>
        </button>
      </div>
      <div id="control-btns" class="row">
        <button class="lab-overview-btn btn-large indigo lighten-3 waves-effect col s12 m6" id="save">SAVE</button>
        <button class="lab-overview-btn btn-large indigo lighten-3 waves-effect col s12 m6" id="exit">EXIT</button>
        <button class="step-btns btn-large indigo lighten-3 waves-effect col s12 m6" id="done">DONE</button>
        <button class="step-btns btn-large indigo lighten-3 waves-effect col s12 m6" id="cancel">CANCEL</button>
      </div>
    </div>


    <div id="part-selector" class="modal indigo">
      <div id="scroll" class="row">
        <div id="parts-content" class="content col s12">
          <div id="parts">
            <!-- v-for parts -->
          </div>
        </div>
      </div>
      <button id="part-cancel" class="modal-close btn indigo lighten-3 waves-effect col s4 offset-s4">CANCEL</button>
    </div>
  </div>
</template>

<script lang="js">
export default  {
  name: 'lab-builder',
  created() {
    this.$store.commit('changeNav', 'indigo lighten-1');
  },
  mounted () {
    console.log($('.modal'));
    $('.modal').modal();
  },
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
    }
    #control-btns {
        position: fixed;
        bottom: 0;
        right: 0;
        width: 25%;
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
      font-size: 1.5rem;
      margin-top: 0;
      margin-left: 10px;
      word-wrap: break-word;
      top: 87px;
      padding-bottom: 65px;
    }
  }


  #part-selector {
    position: absolute;
    max-width: 90vw;
    width: 1000px;
    height: 90vh;
    overflow: hidden;
    margin-top: 5vh;
    padding: 10px;

    #scroll {
      margin: 0;
      margin-top: 2.5%;
      height: 90%;

      #parts-content {
        margin: 0;

        span {
            width: min-content;
            cursor: pointer;
        }
      }
    }
    #part-cancel {
      margin-top: 5px;
    }
  }
}
</style>
