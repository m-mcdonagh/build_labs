<template lang="html">
    <div class="main" id="learn-main">
        <div id="assigned-labs-container" class="container section row cyan darken-1">
            <!-- v-for cards gotten via axios/ajax -->
        </div>
    </div>
</template>

<script>
export default  {
  name: 'learn'
}
</script>

<style scoped lang="scss">
#learn-main{
    justify-content: center;
    align-items: center;
    #assigned-labs-container {
        padding-left: 10px;
        height: 90%;
        overflow-x: hidden;
        overflow-y: scroll;
    }
}
</style>