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
             v-on:copy="console.log(part, 'copied')"
             v-on:publish="console.log(part, 'published')"
             v-on:remove="console.log(part, 'removed')">
        </part-list>
      </ul>
    </div>
    <div id="labs" class="col s12 content-wrapper">
      <ul class="content flow-text collection">
        <lab-list 
             v-for="(lab, index) in labs"
             v-bind:key="lab.id"
             v-bind:id="lab.id"
             v-bind:name="lab.name"
             v-bind:ispublished="lab.ispublished"
             v-on:copy="console.log(lab, 'copied')"
             v-on:publish="console.log(lab, 'published')"
             v-on:remove="console.log(lab, 'removed')">
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
      parts: [{id:0, name:'test not published', ispublished:false}, {id:1, name:'test published', ispublished:true}],
      labs: [{id:0, name:'lab not published', ispublished:false}, {id:1, name:'lab published', ispublished:true}]
    }
  },
  mounted () {
    $('.tabs').tabs();
    $('.fixed-action-btn').floatingActionButton();
    $('.tooltipped').tooltip();

    this.sizeContent();
    window.onresize = this.sizeContent;
  },
  methods: {
    sizeContent() {
      let height = $('.container').height() - 58
      $('.content').css({
        height: height
      });
    }
  } 
}
</script>

<style scoped lang="scss">
#create-main {
  justify-content: center;
  align-items: center;

  .container {
    position: relative;
    height: 95%;

    .tabs{
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
        background-color: #FFFFFFF0;
        
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
