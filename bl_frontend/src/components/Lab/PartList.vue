<template>
    <li class="partlist tooltipped" data-position="right" v-bind:data-tooltip="name">
        <img v-bind:src="img_src" v-on:click="select">
    </li>
</template>

<script>
export default {
    name: 'partlist',
    props: ['id', 'name', 'img_src'],
    mounted() {
        $(this.$el).tooltip();
    },
    methods: {
        select() {
            this.$emit('selectthis');
            $('.partlist').removeClass('selected')
            $(this.$el).addClass('selected', this.id);
        }
    }
}
</script>

<style lang="scss" scoped>
.partlist {
    border: 10px solid transparent;
    margin-left: 10px;
    cursor: pointer;

    img {
        width: 100%;
    }
}
.selected {
    border: 10px solid #ef5350;
    background-color: #ef5350;
    border-radius: 5%;
}
</style>