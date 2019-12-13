<template lang="html">
<div class="main" id="create-main">
  <div class="container indigo row">
    <ul class="tabs">
      <li class="tab">
        <a href="#parts">
          <i class="material-icons">build</i>
          <span>Parts</span>
        </a>
      </li>
      <li class="tab">
        <a href="#labs">
          <i class="material-icons">assignment</i>
          <span>Labs</span>
        </a>
      </li>
    </ul>
    <div id="parts" class="col s12 content-wrapper">
      <ul class="content flow-text collection">
        <part-list 
             v-for="(part, index) in parts"
             v-bind:key="part.id"
             v-bind:id="part.id"
             v-bind:name="part.name"
             v-bind:ispublished="part.ispublished"
             v-on:copy="copypart(part)"
             v-on:publish="publishpart(part)"
             v-on:remove="removepart(part)">
        </part-list>
      </ul>
    </div>
    <div id="labs" class="col s12 content-wrapper">
      <ul class="content flow-text collection">
        <lab-list 
             v-for="lab in labs"
             v-bind:key="lab.id"
             v-bind:id="lab.id"
             v-bind:name="lab.name"
             v-bind:ispublished="lab.ispublished"
             v-on:copy="copylab(lab)"
             v-on:publish="publishlab(lab)"
             v-on:remove="removelab(lab)">
        </lab-list>
      </ul>
    </div>
    <div class="fixed-action-btn">
      <a class="btn-floating btn-large waves-effect waves-light pulse indigo accent-4">
        <i class="material-icons">add</i>
      </a>
      <ul>
        <li>
          <a href="/labbuilder" class="btn-floating waves-effect indigo accent-1 tooltipped" data-position="left" data-tooltip="Create New Lab">
            <i class="material-icons">assignment</i>
          </a>
        </li>
        <li>
          <a href="/partbuilder" class="btn-floating waves-effect indigo accent-1 tooltipped" data-position="left" data-tooltip="Create New Part">
            <i class="material-icons">build</i>
          </a>
        </li>
      </ul>
    </div>
  </div>
</div>
</template>

<script lang="js">
import partlist from '../components/Create/PartList.vue'
import lablist from '../components/Create/LabList.vue'

export default  {
  name: 'create',
  components:{
    'part-list': partlist,
    'lab-list': lablist
  },
  created() {
    this.$store.commit('changeNav', 'indigo lighten-1');
  },
  data() {
    return {
      // TODO: set up axios for this.parts and this.labs
      parts: [{id:0, name:'part not published', ispublished:false}, {id:1, name:'part published', ispublished:true}],
      labs: [{id:0, name:'lab not published', ispublished:false}, {id:1, name:'lab published', ispublished:true}]
      // These IDs can be same as ID's in the mongo database. Need unique IDs for v-for
    }
  },
  //soem comment
  mounted () {
    $('.tabs').tabs();
    $('.fixed-action-btn').floatingActionButton();
    $('.tooltipped').tooltip();

    this.sizeContent();
    window.onresize = this.sizeContent;
    this.getAllParts();
    this.getAllLabs();
  },
    
  methods: {

    async getAllParts(){
    let part_response = await axios({
        method: "get",
        url: "/api/parts/allparts"
        }
      );
    console.log("PARTS DATA",part_response.data);
    let i = 0;
    for(i = 0;i<part_response.data.length;i++){
        let prt = part_response.data[i];
       this.parts.push({id:prt._id,
                        name:prt.name,
                        ispublished:prt.ispublished
                        })
      }
    },
    async getAllLabs(){
      let userSessionData = await axios({
        method: "get",
        url: "/api/accounts/session"
      });
      let username = userSessionData.data;
    let lab_response = await axios({
        method: "get",
        url: "/api/labs/",
        params:{
          id :username,
        }
      },

      );
    console.log("LABS DATA",lab_response.data);
    let i = 0;
    for(i = 0;i<lab_response.data.length;i++){
        let lab = lab_response.data[i];
       this.labs.push({id:lab._id,
                        name:lab.name,
                        ispublished:lab.ispublished
                        })
      }
    },
    sizeContent() {
      let height = $('.container').height() - 58
      $('.content').css({
        height: height
      });
    },
    copypart(part) {
      console.log('copy part', part.id);
      // TODO: axios 
          // REQ->server with part.id
          // server->RES with new cloned ubpublished part JSON
          // then() appends this to this.parts 

    },
    copylab(lab){
      console.log('copy lab', lab.id);
      // TODO: axios
          // REQ->server with lab.id
          // server->RES with new cloned, ubpublished lab JSON
          // then appends this to this.labs
    },
    async publishpart(part){
      console.log('publish part', part.id);
      // TODO axios
          // REQ->server with part.id
          // server->RES with OK or ERR

      let publish_response = await axios({
        method: "post",
        url: "/api/parts/publishpart",

        params:{
          partId:part.id,
        }

        }
      );
      console.log(publish_response);
      if(publish_response.status == 200){
        part.ispublished = true; // move to axios.then
      }
    },
    async publishlab(lab){
      console.log('publish part', lab.id);
      // TODO axios
          // REQ->server with lab.id
          // server->RES with OK or ERR


      let publish_response = await axios({
        method: "post",
        url: "http://localhost:8080/labs/publishlab",

        params:{
          labId:lab.id,
        }

        }
      );
      console.log(publish_response);
      if(publish_response.status == 200){
        lab.ispublished = true; // move to axios.then
      }
      
    },
    async removepart(part){
      console.log('remove part', part.id);
      // TODO axios
          // REQ->server with part.id
          // server->RES with OK or ERR

      let delete_response = await axios({
        method: "post",
        url: "/api/parts/deletepart",

        params:{
          partId:part.id,
        }

        }
      );
      console.log(delete_response);
      if(delete_response.status == 200){
        console.log("delete successful at backend");
        this.parts.splice(this.parts.indexOf(part), 1); // move to axios.then
      }
      
    },
    async removelab(lab){
      console.log('remove lab', lab.id);
      //TODO axios
          // REQ->server with lab.id
          // server->RES with OK or ERR

      let delete_response = await axios({
        method: "post",
        url: "http://localhost:8080/labs/deletelab",params:{ labID:lab.id } });
      console.log(delete_response);
      if(delete_response.status == 200){
        console.log("delete successful at backend");
        this.labs.splice(this.labs.indexOf(lab), 1); //move to axios.then
        }
        
    }          
  } //methods 
}
</script>

<style scoped lang="scss">
#create-main {
  justify-content: center;
  align-items: center;

  .container {
    position: relative;
    height: 95%;

    .tabs {
      width: 100%;
      overflow: hidden;

      .tab {
        min-width: 50%;

        span {
          font-size: 2em;
        }
      }
    }
    .content-wrapper {
      margin-top: 1px;

      .content {
        overflow-x: hidden;
        overflow-y: scroll;
        display: flex;
        flex-direction: column;
        min-height: 100%;
        margin: 0;
        padding: 0;
        color: #313639;
        background-color: #fffffff0;

        span {
          margin: 0 0 0 10px;
        }
      }
    }

    .fixed-action-btn {
      position: absolute;
      right: 10px;
      bottom: 10px;
    }
  }
}
</style>
