<template>
  <div id="index-main" class="main">
    <div id="front">
      <div id="cover">
        <img id="cover-img" src="../assets/img/cover.png">
      </div>
      <div id="buttons">
        <button id="learn-link-btn" class="clip-btn btn cyan lighten-2 waves-effect" data-url="/learn" data-toggler="#learn-toggler">
          LEARN
        </button>
        <button id="create-link-btn" class="clip-btn btn indigo lighten-3 waves-effect" data-url="/create" data-toggler="#create-toggler">
          CREATE
        </button>
      </div>
    </div>
    <input id="learn-toggler" class="clip-toggler" type="checkbox">
    <input id="create-toggler" class="clip-toggler" type="checkbox">
    <div id="bg"></div>
    <a id="logout" href="/login">
      <i class="material-icons">power_settings_new</i>
    </a>
</div>
</template>

<script>
$(function(){
  $('.clip-btn').on('click', function() {
    $($(this).attr('data-toggler')).attr('checked', true);
    
    let $coverimg = $('#cover-img');
    $coverimg.clone().css({
      'position':'fixed',
      'left' : $coverimg.offset().left,
      'top' : $coverimg.offset().top,
      'height': $coverimg.height()
    }).appendTo('.main').animate({
      'left': '10px',
      'top' : '0',
      'height' : '5vh'
    }, 600);
    $coverimg.remove();
    $('.clip-btn').remove();

    let url = $(this).attr('data-url');
    setTimeout(function(){
      window.location = url;
    }, 750);
  });
});

export default {
  created() {
    this.$store.commit('changeNav', 'hidden-nav');
  }
}
</script>

<style lang="scss">
.hidden-nav {
  display: none;
}
#index-main{
  position: relative;
  margin-top: 5vh;
  justify-content: center;

  #logout{   
    display: contents;

    i{
      position: fixed;
      right: 10px;
      top: 10px;
      color: #03a9f4;
      transition: color .75s;
      font-size: 2rem;
      cursor: pointer;
    }
  }
  .clip-toggler:checked ~ #logout i {
    color: white;
  }
  #bg {
    position: fixed;
    left: 0;
    top: 0;
    height: 100vh;
    width: 100vw;
    margin: 0;
    clip-path: polygon(0 0, 80% 0, 78.5% 5%, 50% 100%, 0 100%);
    background-color: #03a9f4;
    transition: clip-path .75s, background-color .75s;
  }
  .clip-toggler:checked ~ #bg {
    clip-path: polygon(0 0, 100% 0, 100% 5%, 100% 5%, 0 5%);
  }
  #learn-toggler:checked ~ #bg {
    background-color: #00bcd4;
  }
  #create-toggler:checked ~ #bg {
    background-color: #5c6bc0;
  }
  .clip-toggler {
    position: fixed;
    left: -100px;
  }

  #front {
    position: relative;
    display: flex;
    flex-direction: column;
    height: 100%;
    justify-content: center;
    align-items: center;
    z-index: 1;

    #cover img {
      max-height: 75vh;
      max-width: 100vw;
    }

    #buttons {
      position: relative;
      display: flex;
      flex-wrap: wrap;
      max-width: 500px;
      width: 50vw;
      justify-content: center;
      
      button {
        min-width: 150px;
        width: 45%;
        margin-left: 0;
      }
    }
  }
}
</style>