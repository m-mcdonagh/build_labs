<template>
    <div class="modal modal-fixed-footer">
      <div class="modal-content indigo lighten-3">
        <ul class="collection">
          <li v-for="part in listofparts" class="collection-item" v-bind:key="part.id">
            <a class="modal-close btn-flat" v-on:click="$emit('newpart', part)">
              {{ part.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="modal-footer indigo lighten-4">
        <a id="part-cancel" class="modal-close btn-flat" v-on:click="$emit('modalcancel')">CANCEL</a>
      </div>
    </div>
</template>

<script>
export default {
    name: 'partSelector',
    props: ['listofparts'],
    mounted() {
        $(this.$el).modal();
    }
}
</script>

<style lang="scss" scoped>
.modal {
    position: fixed;
    max-width: 90vw;
    width: 1000px;
    overflow: hidden;
    margin-top: 5vh;
    color: black;

    .collection-item {
        padding: 0;

        a {
        width: 100%;
        height: 100%;
        text-transform: none;
        }
    }
}
</style>