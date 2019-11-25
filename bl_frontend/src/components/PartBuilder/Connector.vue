<template>
    <div class="fixed-action-btn" id="connector-wrapper">
        <a id="connector" class="btn-floating teal darken-5 tooltipped" data-position="right" data-tooltip="Connector" v-bind:style="offset"></a>
        <ul v-bind:style="offset">
            <li>
                <button class="delete btn-floating waves-effect red" v-on:click="$emit('remove')">
                    <i class="material-icons">delete_forever</i>
                </button>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: 'slot-component',
    props: [
        'x',
        'y'
    ],
    data() {
        return {
            offset: {
                left: this.x + 'px',
                top: this.y + 'px'
            }
        }
    },
    mounted(){
        $(this.$el).find('.tooltipped').tooltip();
        M.FloatingActionButton.init($(this.$el).get(0), {
            hoverEnabled: false
        });
    }
}
</script>

<style scoped lang='scss'>
#connector-wrapper {
    display: contents;

    #connector{
        position: absolute;
        transform: translate(-50%, -50%);
        border: 6px solid white;
        height: 50px;
        width: 50px;
        border-radius: 100%;
    }
    ul {
        position: absolute;
        width: 40px;
        height: 40px;
        transform: translate(-50%, -175%);
        z-index: 1;

        * {
            height: 100%;
            width: 100%;
        }
    }
}
</style>