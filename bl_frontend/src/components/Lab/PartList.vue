<template>
    <li class="partlist tooltipped" data-position="right" v-bind:data-tooltip="name">
        <img v-bind:src="img_src" v-on:click="select">
    </li>
</template>

<script>
export default {
    name: 'partlist',
    props: ['name', 'img_src'],
    mounted() {
        $(this.$el).tooltip();
    },
    methods: {
        select() {
            
        }
    }
}
</script>

<style lang="scss" scoped>
img {
    width: 100%;
}
.selected {
    opacity: .1;
}
</style>