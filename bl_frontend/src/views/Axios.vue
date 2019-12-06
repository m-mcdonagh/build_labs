<template lang="html">
<div class="main axios" id="axios-main">
    <h1>HELLO WORLD</h1>
    <li v-for ="infox in info">
        {{infox}}

    </li>

    <h1>MESSAGE</h1>
  </div>
</template>

<script>
import axios from 'axios';
 
export default {
  data() {
    return {
      info: [],
      errors: []
    }
  },

  // Fetches posts when the component is created.https://api.coindesk.com/v1/bpi/currentprice.json'
  mounted() {
    console.log('IM HERE'); 
    axios.get('http://localhost:8080/axios/axio')
    .then(response => {
      // JSON responses are automatically parsed.
      this.info = response.data
      console.log(this.info);
    })
    .catch(e => {
    console.log("THERE WAS AN ERROR");
    console.log(e);
      this.errors.push(e)
    })

  }
}




// </script>

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