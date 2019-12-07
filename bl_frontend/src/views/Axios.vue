<template lang="html">
<div class="main axios" id="axios-main">
    <h1>HELLO WORLD</h1>
    
    <li v-for ="infox in info">
        {{infox}}

    </li>
    
    <h1>MESSAGE</h1>

    <div id="app">
            <p>hello</p>
            <button v-on:click="postButton">Reverse Message</button>
        </div>
  </div>
</template>



<script>
export default {
  data() {
    return {
      info: [],
      errors: []
    }
  },
  mounted() {
    console.log("IM HERE");
    axios
      .get("http://localhost:8080/axios/axios")
      .then(response => {
        // JSON responses are automatically parsed.
        this.info = response.data;
        console.log(this.info);
      })
      .catch(e => {
        console.log("THERE WAS AN ERROR");
        console.log(e);
        this.errors.push(e);
      });
  },
  data: {
    firstName: "",
    lastName: "",
  },
  //template:'<button v-on:click="postButton">Reverse Message</button>',
  methods: {
    postButton: function() {
      console.log("Inside post button");

      axios({
        method: "post",
        url: "http://localhost:8080/axios/postPost",
        data: {
          firstName: "First Name",
          lastName: "Last Name"
        }
      })
        .then(function(response) {
          console.log(response);
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>



<!--
// <style scoped lang="scss">
// #login-main {
//   flex-direction: column;
//   align-items: center;
//   justify-content: center;

//   .input-field input + label {
//     color: #ccc;
//   }
//   .input-field input:focus {
//     border-bottom: 1px solid #ccc;
//     box-shadow: 0 1px 0 0 #ccc;
//   }
//   .input-field input.valid {
//     border-bottom: 1px solid #ccc;
//     box-shadow: 0 1px 0 0 #ccc;
//   }
//   .input-field input.invalid {
//     border-bottom: 1px solid #ccc;
//     box-shadow: 0 1px 0 0 #ccc;
//   }
//   .input-field .prefix.active {
//     color: #ccc;
//   }

//   .btn {
//     overflow: hidden;
//   }

//   #hide-register {
//     @media screen and (max-width: 400px) {
//       padding: 0;
//     }
//   }
// }
// </style>
//  */
-->