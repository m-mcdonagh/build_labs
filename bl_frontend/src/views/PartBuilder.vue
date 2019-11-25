<template lang="html">
  <div class="main row" id="part-builder-main">
    <div id="settings-side-bar" class="section left indigo col s3 hide-on-small-only">
      <div class="row">
        <h2 class="col s10 offset-s1 flow-text">Part Name:</h2>
        <div class="input-field col s12">
          <input id="part-name" type="text" placeholder="Part Name">
        </div>
        <h2 class="col s10 offset-s1 flow-text">Dimensions:</h2>
        <div class="input-field col s6">
          <input id="width" class="dimension" type="number" value="12">
          <label for="width">Width</label>
        </div>
        <div class="input-field col s6">
          <input id="height" class="dimension" type="number" value="12">
          <label for="height">Height</label>
        </div>
      </div>
      <div class="row">
        <a href="#img-file-modal" id="add-img" class="icon-btn modal-trigger">
          <img src="../assets/img/add-image.svg" class="col s4 tooltipped" data-position="bottom" data-tooltip="Add Image">
        </a>
        <button id="add-slot" class="icon-btn" v-on:click="toggleSlotAdd">
          <img src="../assets/img/add-slot.svg" class="col s4 tooltipped" data-position="bottom" data-tooltip="Add Slot">
        </button>
        <button id="add-connector" class="icon-btn">
          <img src="../assets/img/connector.svg" class="col s4 tooltipped" data-position="bottom" data-tooltip="Add Connector">
        </button>
      </div>
      <div id="control-btns" class="row">
        <button class="btn-large indigo lighten-3 waves-effect col s6" id="save">SAVE</button>
        <a href="/create" class="btn-large indigo lighten-3 waves-effect col s6" id="exit">EXIT</a>
      </div>
    </div>
    
    <div id="workspace" class="col s12 m9">
      <div id="part">
        <img v-bind:src="part.img" v-on:click="addSlot" v-bind:style="{width: displaywidth, height: displayheight}">
        <div id="slots">
          <slot-component
            v-for="(slot, index) in part.slots"
            v-bind:id="slot.id"
            v-bind:x="slot.x"
            v-bind:y="slot.y"
            v-on:remove="part.slots.splice(index, 1)">
          </slot-component>
        </div>
      </div>
    </div>
    
    <div id="img-file-modal" class="modal indigo">
      <div class="section row">
        <h4 class="col s12 center">Add an Image</h4>
        <div class="file-field input-field col s10 offset-s1">
          <div class="btn indigo lighten-3 waves-effect">
            <span>Browse</span>
            <input type="file" id="img-file" v-on:input="uploadImg">
          </div>
          <div class="file-path-wrapper">
            <input class="file-path validate" type="text" placeholder="Upload file" accept="image/*">
          </div>
        </div>  
        <a id="img-file-cancel" class="modal-close btn indigo lighten-3 waves-effect col s4 offset-s4">CLOSE</a>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import slot from '../components/PartBuilder/Slot.vue';

export default  {
  name: 'partbuilder',
  components: {
    'slot-component' : slot,
  },
  created() {
    this.$store.commit('changeNav', 'indigo lighten-1');
  },
  data(){
    return {
      part: {
        slots: [], //test values: {id: 0, x:0, y:0}, {id: 1, x:20, y:20}
        height: 12,
        width: 12,
        img: null
      },
      nextSlotId: 0,
      slotAdd: false,
      displaywidth: 0,
      displayheight: 0
    }
  },
  mounted () {
    $('.tooltipped').tooltip();
    $('.modal').modal();
    M.updateTextFields();
    this.resizePart();
  },
  methods: {
    toggleSlotAdd(){
      this.slotAdd = !this.slotAdd;
    },
    addSlot(e) {
      if (this.slotAdd){
        this.slotAdd = false;
        this.part.slots.push({
          id: this.nextSlotId++,
          x: e.offsetX,
          y: e.offsetY
        });
      }
    },
    resizePart() {
      let aspectRatio = this.part.width / this.part.height;
      let maxWidth = $('#workspace').width() * .95;
      let maxHeight = $('#workspace').height() * .95;
      let width = maxHeight * aspectRatio;
      
      if (width <= maxWidth) {
        this.displaywidth = width + 'px';
        this.displayheight = height + 'px';
      }
      else {
        this.displaywidth = maxWidth + 'px';
        this.displayheight = (maxWidth / aspectRatio) + 'px';
      }
    },
    uploadImg(e) {
      this.part.img = URL.createObjectURL(e.target.files[0]);  
    }
  }
}
</script>

<style scoped lang="scss">
#part-builder-main {
  #settings-side-bar {
    position: relative;
    overflow-x: hidden;
    overflow-y: scroll;
      
    .icon-btn {
      display: contents;
      
      img {
        cursor: pointer;
      }
      img:hover {
        opacity: .9;
      }
      img:active {
        opacity: .75;
      }
    }
    #control-btns {
        position: absolute;
        width: 100%;
        left: 20px;
        bottom: 1px;
    }
  }
  #workspace {
    display: flex;
    align-items: center;
    justify-content: center;

    #part {
      position: relative;

      img{
        width: 100%;
        height: 100%;
      }
      #slots {
        ul {
          position: absolute;
          width: 40px;
          height: 40px;
          transform: translate(-50%, -150%);

          * {
            height: 100%;
            width: 100%;
          }
        }
      }
    }
  }
}
.btn {
    color: white;
}
</style>
