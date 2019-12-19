<template>
  <li class="lab collection-item" v-bind:class="ispublished?'blue-grey lighten-5':''">
    <a
      class="dropdown-trigger btn-floating btn-small waves-effect waves-light"
      v-bind:data-target="'lab-dropdown-' + id"
      v-bind:class="ispublished?'teal darken-1':'teal lighten-2'"
    >
      <i class="material-icons">keyboard_arrow_down</i>
    </a>
    <ul v-bind:id="'lab-dropdown-' + id" class="dropdown-content">
      <li>

        <a v-bind:href="'/lab?&id='+id+'&ret=create'">

          <i class="material-icons">play_circle_filled</i>Test
        </a>
      </li>
      <li v-if="!ispublished">
        <a v-bind:href="'/labbuilder?id=' + id">
          <i class="material-icons left">edit</i>Edit
        </a>
      </li>
      <li v-if="!ispublished">
        <a v-on:click="$emit('remove')">
          <i class="material-icons left">delete_forever</i>Delete
        </a>
      </li>
      <li v-if="!ispublished">
        <a v-on:click="$emit('publish')">
          <i class="material-icons left">publish</i>Publish
        </a>
      </li>
      <li v-if="ispublished">
        <a class="modal-trigger" v-bind:href="'#lab-info-modal-' + id">
          <i class="material-icons">info</i>Info
        </a>
      </li>
      <li v-if="ispublished">
        <a class="modal-trigger" v-bind:href="'#lab-assign-modal-' + id">
          <i class="material-icons left">assignment_ind</i>Assign
        </a>
      </li>
      <li>
        <a v-on:click="$emit('copy')">
          <i class="material-icons">content_copy</i>Copy
        </a>
      </li>
    </ul>
    <span>{{ name }}</span>
    <div v-bind:id="'lab-info-modal-' + id" class="modal">
      <div class="modal-content indigo lighten-4">
        <h4>
          <i class="material-icons">assignment</i>
          {{ name }}
        </h4>
        <div class="collection">
          <lab-info v-for="i in info" v-bind:key="i.user_id" v-bind:username="i.username" v-bind:complete="i.complete"></lab-info>
        </div>
      </div>
      <div class="modal-footer indigo lighten-3">
        <button class="btn modal-close waves-effect waves-light indigo accent-4">Close</button>
      </div>
    </div>
    <div v-bind:id="'lab-assign-modal-' + id" class="modal">
      <div class="modal-content indigo lighten-4">
        <h4>
          <i class="material-icons">assignment_ind</i>
          {{ name }}
        </h4>
        <!-- TODO Send to backend -->
        <div class="row">
          <!-- <div class="email-input input-field inline col s11">
            <input id="email" name="email" type="text" required />
            <label for="email">Send to mail</label>
          </div> -->
          <div class="input-field col s12 m10 offset-m1">
            <input id="username" name="username" type="text" required v-model="assignee" />
            <label for="username">Username</label>
          </div>
          <div>
            <button class="email-submit col s1" v-on:click="assignLab">
              <i class="material-icons">send</i>
            </button>
          </div>
        </div>
      </div>
      <div class="modal-footer indigo lighten-3">
        <button class="btn modal-close waves-effect waves-light indigo accent-4">Close</button>
      </div>
    </div>
  </li>
</template>

<script>
import labInfo from './LabInfo.vue';

export default {
  name: "Lab",
  props: ["name", "ispublished", "id"],
  components: {
    'lab-info': labInfo
  },
  data() {
    return {
      labList: [],
      username:'',
      assignee:'',
      // TODO: set up axios for this.info
      info: [
        // TEST VALUES
        // { user_id: 0, username: "not complete", complete: false },
        // { user_id: 1, username: "complete", complete: true }
      ]
    };
  },
  mounted() {
    M.Dropdown.init(
      $(this.$el).find(".dropdown-trigger").get(0), { 
        constrainWidth: false, 
        coverTrigger: false 
      }
    );
    $(this.$el).find(".modal").modal();
    $(this.$el).find(".tooltipped").tooltip();


    this.getLabAssignments(); 
    
  },
  methods: {

    async getLabAssignments(){
      let response = await axios({
        method: "get",
        url: "/api/labs/lab/getassignments",
        params: {
          lab_id :this.id,
        }
      });
      let i = 0;
      for(i = 0;i<response.data.length;i++){
        let labassign = response.data[i];
        this.info.push({
          username:labassign.username ,
          user_id:labassign.user_id,
          complete:labassign.Complete
        });
      }
    
      //{ user_id: 0, username: "not complete", complete: false }
    },
    async assignLab() {
      let userSessionData = await axios({
        method: "get",
        url: "/api/accounts/session"
      });
      let username = userSessionData.data;
      console.log("Assign lab axios request");
      console.log(this.id);
      let response = await axios({
        method: "post",
        url: "api/labs/assignlab",
        data:{
            labId:this.id,
            assignee:this.assignee,
            assigner:username
        }
      }).then(function(data){
        M.toast({ displayLength: 2000, html: "SUCCESS" });
      }).catch(function(err) {
        M.toast({ displayLength: 2000, html: "Unable to assign this lab to " + this.assignee });
      }.bind(this));
    },
    async getLab() {
      console.log("Get all parts get request");

      await axios({
        method: "get",
        url: "/api/labs/",
        params:{
          id : this.id
        }
      }).then(function(response) {
        console.log("EXIT POST REQUEST");
        console.log(response);
        this.labList = response.data;
      }).catch(function(error) {
        console.log(error);
      });
    }
  }
};
</script>

<style scoped lang="scss">
.lab {
  display: flex;
  flex-direction: row;

  span {
    margin: 5px;
  }
  .btn {
    color: white !important;
  }
  .info {
    position: relative;
    span {
      position: absolute;
      right: 10px;
      cursor: default;
    }
    img {
      position: absolute;
      height: 80%;
      top: 0;
      right: 5px;
    }
  }
  .row {
    display: flex;
    justify-content: center;
  }
  input {
    color: black;
  }
  .email-input {
    margin-left: 0;
  }
  .email-submit {
    display: contents;

    i {
      transform: translate(0, 125%);
      cursor: pointer;
      color: #009688;
    }
    i:hover {
      color: #4db6ac;
    }

    @media screen and (max-width: 400px) {
      i {
        font-size: 0.75em;
        transform: translate(0, 250%);
      }
    }
    @media screen and (max-width: 230px) {
      i {
        font-size: 0.5em;
        transform: translate(0, 400%);
      }
    }
  }
}
</style>
