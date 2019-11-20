<template>
  <div id="app">
    <nav :class="navclass">
        <div class="nav-wrapper">
            <a id="cover" class="left" href="/"><img class="brand-logo" src="./assets/img/cover.png"></a>
            <ul id="nav-mobile" class="right">
                <li><a href="/logout"><i class="material-icons">power_settings_new</i></a></li>
            </ul>
        </div>  
    </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  computed: {
    navclass() {
      return this.$store.state.navclass;
    }
  }
}
</script>

<style>
  @import './assets/css/materialize.css';
</style>
<style lang="scss">
#app{
    font-family: "Roboto", sans-serif;
    color: white;
    overflow: hidden;

    nav {
      position: fixed;
      height: 5vh;
      width: 100%;
      top: 0;
      z-index: 1;
    
      #cover {
        display: contents;
        
        img {
          position: absolute;
          left: 10px;
          height: 100%;
        }
      }
    }
    
    .main {
        display: flex;
        background-image: linear-gradient(#FFFFFF, #CFCFCF);
        width: 100vw;
        height: 95vh;
        margin: 0;
        padding: 0;
        margin-top: 5vh;
    }
    
    .btn {
        color: #313639;
        margin-right: 1px;
    }

    input {
        font-family: "Roboto", sans-serif;
        color: white;
    }

    textarea {
        font-family: "Roboto", sans-serif;
        color: white;
    }

    #scroll {
        width: 100%;
        height: 90%;
        overflow-x: hidden;
        overflow-y: scroll;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: flex-start;    

        .content {
            width: 95%;
            min-height: 100%;
            background-color: #FFFFFFF0;
            color: black;
            line-height: 150%;
            @media screen and (min-width: 775px){
                font-size: 1.5rem;
            }

            div {
                margin: 20px;
                display: flex;
                flex-direction: column;
            }
        }
    }
}
</style>