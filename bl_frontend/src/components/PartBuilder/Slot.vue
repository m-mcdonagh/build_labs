<template>
    <div v-bind:id="'slot' + id" class="fixed-action-btn slot-wrapper">
        <div class="padding" v-bind:style="{left: x, top: y}"></div>
        <a class="slot btn-floating teal accent-4 tooltipped" data-position="right" data-tooltip="Slot" v-bind:style="{left: x, top: y}"></a>
        <ul v-bind:style="{left: x, top: y}">
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
        'id',
        'x',
        'y'
    ],
    mounted(){
        $(this.$el).find('.tooltipped').tooltip();
        M.FloatingActionButton.init($(this.$el).get(0), {
            hoverEnabled: false
        });
    }
}
</script>

<style scoped lang="scss">
.slot-wrapper {
    display: contents;

    .padding {
        position: absolute;
        height: 40px;
        width: 30px;
        transform: translate(-50%, -75%);
    }
    .slot {
        position: absolute;
        width: 30px;
        height: 30px;
        transform: translate(-50%, -50%);
        border: 4px solid white;
    }
    ul {
        position: absolute;
        width: 40px;
        height: 40px;
        transform: translate(-50%, -150%);

        * {
            height: 100%;
            width: 100%;
        }
    }
}
</style>