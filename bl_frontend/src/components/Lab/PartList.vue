<template>
    <li class="partlist tooltipped" data-position="right" v-bind:data-tooltip="name">
        <img v-bind:src="img_src" v-on:click="select" v-bind:style="{width:img_dimensions.width, height:img_dimensions.height}">
    </li>
</template>

<script>
export default {
    name: 'partlist',
    props: ['id', 'name', 'img_src', 'width', 'height'],
    computed: {
        img_dimensions() {
            let aspectRatio = this.width / this.height;
            let maxWidth = $('#parts').width();
            let maxHeight = maxWidth * 2;
            let height = maxWidth / aspectRatio;
            if (height < maxHeight) {
                return {
                    width: maxWidth,
                    height: height
                }
            }
            else {
                return {
                    width: maxHeight * aspectRatio,
                    height: maxHeight
                }
            }
        }
    },
    mounted() {
        $(this.$el).tooltip();
    },
    methods: {
        select() {
            $('.partlist').removeClass('selected');
            $(this.$el).addClass('selected', this.id);
            this.$emit('selectthis', this.id);   
        },
    }
}
</script>

<style lang="scss" scoped>
.partlist {
    border: 10px solid transparent;
    margin-left: 10px;
    cursor: pointer;    

    img {
        min-width: 10%;
        min-height: 5vh;
    }
}
.selected {
    border: 10px solid #ef5350;
    background-color: #ef5350;
    border-radius: 5%;
}
</style>