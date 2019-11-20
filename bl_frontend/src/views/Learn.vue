<template lang="html">
    <div class="main" id="learn-main">
        <div id="labs-container" class="container section row cyan darken-1">
            <div id="lab-header" class="row">
                <h2 class="col s12">Labs</h2>
                <div class="col s10 offset-s1 divider"></div>
            </div>
            <div id="labs">
                <!-- v-for cards gotten via axios/ajax -->
            </div>
        </div>
    </div>
</template>

<script>
export default  {
  name: 'learn',
  created() {
    this.$store.commit('changeNav', 'cyan');
  },
  mounted() {
    $('#labs').height($('#labs-container').height() - $('#lab-header').height());
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
            }
            div {
                margin-bottom: 0;
            }
        }
        #labs {
            overflow-x: hidden;
            overflow-y: scroll;
            margin: 0;
            max-height: 100%;
        }
    }
}
</style>