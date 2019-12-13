<template lang="html">
    <div class="main" id="learn-main">
        <div id="labs-container" class="container section cyan darken-1">
            <div id="lab-header" class="row">
                <h2 class="col s12">
                    Labs
                    <i class="material-icons">assignment</i>
                </h2>
                <div class="col s10 offset-s1 divider"></div>
            </div>
            <div id="labs" class="row">
                <lab-card
                    v-for="lab in labs"
                    v-bind:key="lab.id"
                    v-bind:id="lab.id"
                    v-bind:name="lab.name"
                    v-bind:owner="lab.owner"
                    v-bind:complete="lab.complete"
                    v-bind:inprogress="lab.inprogress">
                </lab-card>
            </div>
        </div>
    </div>
</template>

<script>
import labcard from '../components/Learn/LabCard.vue'

export default  {
    name: 'learn',
    components:{
        'lab-card': labcard
    },
    created() {
        this.$store.commit('changeNav', 'cyan');
    },
    data(){
        return {
            // TODO: set up axios for this.labs
            labs: [
                {id:0, name: 'Not complete; not in progress', owner:'You', complete: false, inprogress: false},
                {id:1, name: 'Complete; not in progress', owner:'Me', complete: true, inprogress: false},
                {id:2, name: 'Not complete; in progress', owner:'and', complete: false, inprogress: true},
                {id:3, name: 'Complete; in progress', owner:'Dupree', complete: true, inprogress: true}
            ]
            // These IDs can be same as ID's in the mongo database. Need unique IDs for v-for
        }
    },
  mounted() {
    this.sizeContent();
    window.resize = this.sizeContent;
    this.getAllLabs()
  },
  methods: {
    async getAllLabs(){
      let lab_response = (await axios.get("http://localhost:8080/labs/getassignedlabs",{params:{username:"test2"}})).data;
      lab_response.forEach((lab)=>{

        this.labs.push({
          id:lab._id,
          name:lab.name,
          complete: false,
          inprogress: false
        })
      })
    },
    sizeContent(){
        $('#labs').height($('#labs-container').height() - $('#lab-header').height());
    }
  }
}


</script>

<style scoped lang="scss">
#learn-main{
    justify-content: center;
    align-items: center;
    #labs-container {
        position: relative;
        padding: 0;
        margin-bottom: 0;
        height: 90%;

        #lab-header {
            width: 100%;
            margin: 0;
            h2 {
                text-align: center;
                margin: 0;
                i{
                    position: relative;
                    top: 7px;
                    font-size: 1em;
                }
            }
            div {
                margin-bottom: 0;
            }
        }
        #labs {
            overflow-x: hidden;
            overflow-y: scroll;
            max-height: 100%;
            width: 90%;
            margin-left: 5%;
            padding-left: 1%;
        }
    }
}
</style>
