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
        <a v-bind:href="'/lab?restart=true&id=' + id">
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
          <div v-for="i in info" v-bind:key="i.user_id" class="collection-item info">
            {{i.username}}
            <span
              v-if="!i.complete"
              class="tooltipped"
              data-position="left"
              data-tooltip="INCOMPLETE"
            >&#9744;</span>
            <span v-if="i.complete">&#9744;</span>
            <img
              v-if="i.complete"
              class="check tooltipped"
              data-position="left"
              data-tooltip="COMPLETE"
              src="../../assets/img/check.png"
            />
          </div>
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
        <form class="row" @submit="assignLab">
          <!-- <div class="email-input input-field inline col s11">
            <input id="email" name="email" type="text" required />
            <label for="email">Send to mail</label>
          </div> -->
          <div class="input-field col s12 m10 offset-m1">
            <input id="username" name="username" type="text" required v-model="assignee" />
            <label for="username">Username</label>
          </div>
          <button  class="email-submit col s1" type="submit">
            <div>
              <i class="material-icons">send</i>
            </div>
          </button>
        </form>
      </div>
      <div class="modal-footer indigo lighten-3">
        <button class="btn modal-close waves-effect waves-light indigo accent-4">Close</button>
      </div>
    </div>
  </li>
</template>

<script>
export default {
  name: "Lab",
  props: ["name", "ispublished", "id"],
  data() {
    return {
      labList: [],
      id: "",
      username:"",
      // TODO: set up axios for this.info
      info: [
        { user_id: 0, username: "not complete", complete: false },
        { user_id: 1, username: "complete", complete: true }
      ]
    };
  },
  mounted() {
    M.Dropdown.init(
      $(this.$el)
        .find(".dropdown-trigger")
        .get(0),
      { constrainWidth: false, coverTrigger: false }
    );
    $(this.$el)
      .find(".modal")
      .modal();
    $(this.$el)
      .find(".tooltipped")
      .tooltip();
  },
  methods: {
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

        });
    },
    async getLab() {
      console.log("Get all parts get request");

      await axios({
        method: "get",
        url: "/api/labs/",
        params:{
            id : "someidea"
        }
      })
        .then(function(response) {
          console.log("EXIT POST REQUEST");
          console.log(response);
          this.labList = respnse.data;
        })
        .catch(function(error) {
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