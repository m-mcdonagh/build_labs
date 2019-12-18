<template lang="html">
  <div class="main row" id="part-builder-main">
    <div id="settings-side-bar" class="section left indigo col s3 hide-on-small-only">
      <div class="row">
        <h2 class="col s10 offset-s1 flow-text">Part Name:</h2>
        <div class="input-field col s12">
          <input id="part-name" type="text" placeholder="Part Name" v-model ="part.part_name">
        </div>
        <h2 class="col s10 offset-s1 flow-text">Dimensions:</h2>
        <div class="input-field col s6">
          <input id="width" class="dimension" type="number" v-model="part.width" active>
          <label for="width">Width</label>
        </div>
        <div class="input-field col s6">
          <input id="height" class="dimension" type="number" v-model="part.height" active>
          <label for="height">Height</label>
        </div>
      </div>
      <div class="row">
        <a href="#img-file-modal" id="add-img" class="icon-btn modal-trigger">
          <img src="../assets/img/add-image.svg" class="col s4 tooltipped" data-position="bottom" data-tooltip="Add Image">
        </a>
        <button id="add-slot" class="icon-btn" v-on:click="toggleSlotAdd">
          <img
            src="../assets/img/add-slot.svg"
            class="col s4 tooltipped"
            data-position="bottom"
            data-tooltip="Add Slot"
            v-bind:style="slotAdd ? {opacity: '.67'} : {}">
        </button>
        <button id="add-connector" class="icon-btn" v-on:click="toggleConnectorAdd">
          <img
            src="../assets/img/connector.svg"
            class="col s4 tooltipped"
            data-position="bottom"
            data-tooltip="Add Connector"
            v-bind:style="connectorAdd ? {opacity: '.67'} : {}">
        </button>
      </div>
      <div id="control-btns" class="row">
        <button v-on:click="submitButton()" class="btn-large indigo lighten-3 waves-effect col s6" id="save">SAVE</button>
        <button v-on:click="exit()" class="btn-large indigo lighten-3 waves-effect col s6" id="exit">EXIT</button>
      </div>
    </div>

    <div id="workspace" class="col s12 m9">
      <div id="part">
        <img
          v-bind:src="part.img_src"
          v-on:click="addComponent"
          v-bind:style="slotAdd || connectorAdd ? {width: displaywidth, height: displayheight, cursor: 'pointer'} : {width: displaywidth, height: displayheight}">
        <div id="slots">
          <slot-component
            v-for="(slot, index) in part.slots"
            v-bind:key="slot.id"
            v-bind:x="slot.x"
            v-bind:y="slot.y"
            v-on:remove="part.slots.splice(index, 1)">
          </slot-component>
          <connector-component
            v-if="part.connector"
            v-bind:x="part.connector.x"
            v-bind:y="part.connector.y"
            v-on:remove="part.connector = null">
          </connector-component>
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
    <div id="toast-template">
      <span>Unsaved Work</span>
      <button v-on:click="submitButton();window.location='/create';"class="btn-flat toast-action">Save and Exit</button>
      <a href="/create" class="btn-flat toast-action">Discard Changes</a>
    </div>
  </div>
</template>

<script lang="js">
import Vue from 'vue'
import slot from '../components/PartBuilder/Slot.vue';
import connector from '../components/PartBuilder/Connector.vue';

export default  {
   data(){
    return {
      // TODO: set up axios for this.part

      username :"",
      img_id:"",

      part: {
        part_name:"",
        height: 12,
        width: 12,
        img_src: null, // used by dom img component, set when user uploads a file. Needed whether from file on frontend or file hosted at some route on backend
        img_file: null, // actual uploaded file. Needs to be sent to backend only when changed. Doesn't need to come back to frontend
        slots: [], // Slots need unique IDs, despite not having any in the mongo database. Needed for v-for
        connector: null
      },
      nextSlotId: 0, // Used to generate Slot IDs
      slotAdd: false,
      connectorAdd: false,
      displaywidth: 0,
      displayheight: 0,
      editedSinceLastSave: 0
    }
  },
  name: 'partbuilder',
  components: {
    'slot-component' : slot,
    'connector-component' : connector
  },
  created() {
    this.$store.commit('changeNav', 'indigo lighten-1');
  },
  mounted () {
    this.redirect();
    $('.tooltipped').tooltip();
    $('.modal').modal();
    M.updateTextFields();
    document.addEventListener('click', () => {this.editedSinceLastSave += 1});

    var urlParams = new URLSearchParams(location.search);
    var id = urlParams.get('id');
    console.log(id);

    if(id != null){
      this.populateData(id);
      console.log("DATA WAS POPULATED");
      M.toast({displayLength:2000, html:'Part Loaded'});
    }

    this.resizePart();
    window.onresize = this.resizePart;

  },
  methods: {
    async redirect() {
      let isLoggedIn = false;
      try {
        let sessionUser = await axios.get("/api/accounts/session");
        isLoggedIn = sessionUser.data && sessionUser.data.length;
      } catch (err) {
        
      }
      if (!isLoggedIn) {
            window.location.replace("/login");
      }
    },
    submitButton(){
      if (this.part.connector == null) {
        M.toast({displayLength:2000, html:'Please specify a connector point'});
        return;
      }
      var urlParams = new URLSearchParams(location.search);
      var id = urlParams.get('id');

      if(id == null){
        console.log("SavePart EXECUTING");
        this.savePart();
      }
      else{
        console.log("UPDATE PART EXECUTED");
        this.updatePart();
      }
      this.editedSinceLastSave = -1;
    },

    async populateData(id) {
      let part_response = await axios({
        method: "get",
        url: "/api/parts/part",
        params:{
          id:id
        }
      });

      console.log("PART NAME",part_response);

        var prt = part_response.data;

        this.part.part_name = prt.name;
        var slotPointsCoord = [];

        this.img_id = prt.img;
        console.log("IMG ID",this.img_id)

       for (var j = 0; j < prt.slotPoints.length; j++) {
          slotPointsCoord[j] = {
            x: prt.slotPoints[j][0]*100,
            y: prt.slotPoints[j][1]*100
          };
        }

        let img_data = await axios.get(
          "http://130.245.170.131/api/parts/media?id=" + prt.img
        );
        this.part.img_src = img_data.config.url;
        this.part.slots = slotPointsCoord;
        this.part.connector = {x:prt.connectorPoint[0]*100,y:prt.connectorPoint[1]*100};
        this.part.width = prt.dimensions[0],
        this.part.height = prt.dimensions[1]


        // console.log("IMGAGE DATA: ", img_data.config);
        // this.listofparts.push({
        //   id: prt._id,
        //   name: prt.name,
        //   dimensions: {
        //     height: prt.dimensions[0],
        //     width: prt.dimensions[1]
        //   },
        //   //TODO : Fix img_src
        //   img_src: img_data.config.url, //require("../assets/img/motherboard.png"),
        //   slotPoints: slotPointsCoord,
          // connectorPoint: {
          //   x: prt.connectorPoint[0],
          //   y: prt.connectorPoint[1]
          // }
        // });

    },
    async updatePart(){

      var urlParams = new URLSearchParams(location.search);
      var id = urlParams.get('id');
      if(id != null){
        console.log("EDITING PART");
      }
      //check if image was replaced
      let media_response = await axios.get('http://130.245.170.131/api/parts/media?id='+this.img_id)//change
      .then(function(response){
        return 200;
      })
      .catch(error=>{
        console.log("THERE WAS AN ERROR");
        return 400;
      });
      console.log("Server response",media_response);

      if(media_response != 200){ //image doesn't exist yet
        let fd = new FormData();
        fd.append('content',this.part.img_file);

        let image_response = await axios.post('http://130.245.170.131/api/parts/addmedia', fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
      this.img_id = image_response.data;

      }

      //save rest of the lab now
      let userSessionData = await axios({
        method: "get",
        url: "/api/accounts/session"
      });
      let username = userSessionData.data;
      let response = await axios({
        method: "post",
        url: "/api/parts/part/updatepart",
        data: {
          _id: id,
          name: this.part.part_name,
          //TODO : CHANGE BACK TO imgage_response.data.id,
          img:this.img_id,
          dimensions:[this.part.width,this.part.height],
          slotPoints:this.part.slots,
          connectorPoint:this.part.connector
        },
        params:{
          username : username
          //TODO : GET USERNAME FROM SESSION
        }
      });
      M.toast({displayLength:2000, html:'Part Updated'});
      //if(media_resonse.status)

    },
    async savePart(){

      if(this.id == null){
        console.log("NULL ID");
      }
      console.log("Save part post request");
      // TODO: How is image saved?
      let fd = new FormData();
      fd.append('content',this.part.img_file);

      let image_response = await axios.post('http://130.245.170.131/api/parts/addMedia', fd, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).catch(function(err) {
        let appendum = '';
        if (err.response.status == 413) {
          appendum = ': Image too large';
          this.part.img_file = null;
          this.part.img_src = null;
        }
        M.toast({displayLength:2000, html:'Error saving image' + appendum});
      }.bind(this));
      console.log("addmedia resonse",image_response);
      let userSessionData = await axios({
        method: "get",
        url: "/api/accounts/session"
      });
      let username = userSessionData.data;
      console.log(username);
      let response = await axios({
        method: "post",
        url: "/api/parts/part",
        data: {
          name: this.part.part_name,
          //TODO : CHANGE BACK TO imgage_response.data.id
          img:image_response.data,
          dimensions:[ Number(this.part.width), Number(this.part.height) ],
          slotPoints:this.part.slots,
          connectorPoint:this.part.connector
        },
        params:{
          username : username
          //TODO : GET USERNAME FROM SESSION
        }
      });

      console.log("Post request response : ",response.data)

      let response3 = await axios({
        method: "get",
        url: "/api/parts/part",
        params:{
          id : response.data
        }
      });

      //TODO : Figure out why get request throws 400, also remove it after it works
      console.log("GET PART AXIOS REQUEST",response3.data);
      let response4 = await axios.get('http://130.245.170.131/api/parts/media?id='+response3.data.id);
      console.log(response4);
      M.toast({displayLength:2000, html:'Part Saved'});

    },
    toggleConnectorAdd() {
      this.part.connector = null;
      this.connectorAdd = !this.connectorAdd;
      this.slotAdd = false;
    },
    toggleSlotAdd(){
      this.slotAdd = !this.slotAdd;
      this.connectorAdd = false;
    },
    addComponent(e) {
      if (this.slotAdd) {
        this.part.slots.push({
          id: this.nextSlotId++,
          x: (e.offsetX / parseFloat(this.displaywidth)) * 100,
          y: (e.offsetY / parseFloat(this.displayheight)) * 100
        });
      }
      else if (this.connectorAdd) {
        this.connectorAdd = false;
        this.part.connector = {
          x: (e.offsetX / parseFloat(this.displaywidth)) * 100,
          y: (e.offsetY / parseFloat(this.displayheight)) * 100
        };
      }
    },
    resizePart() {
      let aspectRatio = this.part.width / this.part.height;
      let maxWidth = $('#workspace').width() * .95;
      let maxHeight = $('#workspace').height() * .95;
      let width = maxHeight * aspectRatio;

      if (width <= maxWidth) {
        this.displaywidth = width + 'px';
        this.displayheight = maxHeight + 'px';
      }
      else {
        this.displaywidth = maxWidth + 'px';
        this.displayheight = (maxWidth / aspectRatio) + 'px';
      }
    },
    uploadImg(e) {
      this.part.img_file = e.target.files[0];
      this.part.img_src = URL.createObjectURL(e.target.files[0]);
    },
    exit() {
      if (this.editedSinceLastSave <= 0) {
        window.location = '/create';
      }
      else {
        M.toast({html:$('#toast-template').html()});
      }
    }
  },
  watch: {
    'part.width': function(newWidth, oldWidth) {
      if (newWidth) {
        if (newWidth <= 0){
          this.part.width = oldWidth;
        }
        this.resizePart();
      }
    },
    'part.height': function(newHeight, oldHeight) {
      if (newHeight) {
        if (newHeight <= 0){
          this.part.height = oldHeight;
        }
        this.resizePart();
      }
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
        opacity: 0.9;
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

      img {
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
#toast-template {
  display: none;
}
</style>
