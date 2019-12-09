<template lang="html">
<div class="main" id="login-main">
  <div id="login" class="container light-blue darken-1">
    <form id="login-form"  name="loginForm" class="section">
      <h1 class="center">Login</h1>
      <div class="row">
        <div class="input-field col s12 m10 offset-m1">
          <input id="email" name="email" type="email" required v-model="login.email">
          <label for="email">Email</label>
        </div>
        <div class="input-field col s12 m10 offset-m1 ">
          <input id="password" name="password" type="password" required v-model="login.password">
          <label for="password">Password</label>
        </div>
        <button v-on:click="loginUser()" class="submit btn light-blue accent-1 waves-effect col s6 m4 l2 offset-s3 offset-m4 offset-l5" type="submit"  >
          <i class="material-icons right">send</i>
          Submit
        </button>
      </div>
    </form>
    <div class="divider row"></div>
    <div>
      <div class="row">
      <button id="show-register" class="btn light-blue accent-1 waves-effect col s12 m4 l3 offset-l3 offset-m2" type="button">
        <i class="material-icons left">account_circle</i>
        Register Account
      </button>
      <button id="show-reset-password" class="btn light-blue accent-1 waves-effect col s12 m4 l3" type="button">
        Forgot Password?
      </button>
      </div>
    </div>
  </div>
  <div id="register" class="container light-blue darken-1">
    <form name="registerForm">
      <h1 class="center">Register</h1>
      <div class="row">
      <div class="input-field col s12 m10 offset-m1">
        <input id="username" name="username" type="text" required v-model="reg.username">
        <label for="username">Username</label>
      </div>

      <div class="input-field col s12 m10 offset-m1">
        <input id="email" name="email" type="email" v-model="reg.email">
        <label for="email">Email</label>
      </div>

        <div class="input-field col s12 m10 offset-m1">
          <input id="password" name="password" type="password" required>
          <label class="password-label" for="password">Password</label>
      </div>
        <div class="input-field col s12 m10 offset-m1">
          <input id="password-confirm" name="passconf" type="password" required v-model="reg.password">
          <label class="password-label" for="password-confirm">Confirm Password</label>
        </div>
      </div>
      <div class="row">
        <button id="hide-register" class="btn light-blue accent-1 waves-effect col s1 m3 l2 offset-s1 offset-m2 offset-l3" type="button">
          <i class="material-icons left">arrow_back</i>Cancel
        </button>
        <button v-on:click="registerButton()" class="btn light-blue accent-1 waves-effect col s8 m3 l2 offset-s1 offset-m2 offset-l2" type="submit">
          <i class="material-icons right">send</i>Submit
        </button>
      </div>
    </form>
  </div>
  <div id="reset-password" class="container light-blue darken-1">
    <form name="resetPassword">
      <h1 class="center">Reset Password</h1>
      <div class="row">
        <div class="input-field col s12 m10 offset-m1">
          <input id="email" name="email" type="email" required>
          <label for="email">Email</label>
        </div>
      </div>
      <div class="row">
        <button id="hide-reset-password" class="btn light-blue accent-1 waves-effect col s1 m3 l2 offset-s1 offset-m2 offset-l3" type="button">
          <i class="material-icons left">arrow_back</i>Cancel
        </button>
        <button class="btn light-blue accent-1 waves-effect col s8 m3 l2 offset-s1 offset-m2 offset-l2" type="submit">
          <i class="material-icons right">send</i>Submit
        </button>
      </div>
      </form>
    </div>
  </div>
</template>

<script>
export default  {

  data() {
    return{
    login:{
      password:"",
      email:""

    },
    reg:{
      username:"",
      email:"",
      password:""
    }
    }
  },
  //template:'<button v-on:click="postButton">Reverse Message</button>',
  methods: {
    async registerButton(){
      let resp =  await axios({
        method: "post",
        url: "http://localhost:8080/register",
        params: {
          username: this.reg.username,
          password: this.reg.password,
          email: this.reg.email,
        }
      }).then(()=>{
        alert("ok!")
      }).catch((err)=>{
        alert(err)
      })
      // alert(JSON.stringify(resp))
    },
    async loginUser(){
      // const config = { headers: { 'Content-Type': 'multipart/form-data' } };
        axios.post("http://localhost:8080/login", {
                "email":"cc@cc.c",
                "password":"cc@cc.c"
          })
          .then((ff)=>{
            alert("ok")
          })
          .catch((err)=>{
            alert(err)
          })

    // axios({
    //     method: "post",
    //     url: "http://localhost:8080/login",
    //     params: {
    //       password: this.login.password,
    //       email: this.login.email,
    //     }
    //   }).then(function(response) {
    //       console.log("EXIT POST REQUEST");
    //       //console.log(response);
    //       alert("ok!")
    //       // location.href='/'
    //     })
    //     .catch(function(error) {
    //       alert(JSON.stringify(error));
    //     });
    }
  },
  name: 'login',
  mounted (){
    $('#show-register').on('click', function() {
      $('#login').slideUp();
      $('#register').slideDown();
    });

    let test = $('#show-reset-password');
    test.on('click', function() {
      $('#login').slideUp();
      $('#reset-password').slideDown();
    });

    $('#hide-register').on('click', function() {
      $('#register').slideUp();
      $('#login').slideDown();
    });

    $('#hide-reset-password').on('click', function() {
      $('#reset-password').slideUp();
      $('#login').slideDown();
    });

    $('#register').hide();
    $('#reset-password').hide();
  }
}
</script>

<style scoped lang="scss">
#login-main {
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .input-field input + label {
    color: #CCC;
  }
  .input-field input:focus {
    border-bottom: 1px solid #CCC;
    box-shadow: 0 1px 0 0 #CCC;
  }
  .input-field input.valid {
    border-bottom: 1px solid #CCC;
    box-shadow: 0 1px 0 0 #CCC;
  }
  .input-field input.invalid {
    border-bottom: 1px solid #CCC;
    box-shadow: 0 1px 0 0 #CCC;
  }
  .input-field .prefix.active {
    color: #CCC;
  }

  .btn {
    overflow: hidden;
  }

  #hide-register {
    @media screen and (max-width: 400px){
      padding: 0;
    }
  }
}
</style>
