<template>
    <div class="step collection-item">
        {{ name }}
        <div class="right">
            <a class="dropdown-trigger btn-floating btn-small waves-effect waves-light" 
            v-bind:data-target="'step-dropdown-' + id">
                <i class="material-icons">keyboard_arrow_down</i>
            </a>
            <ul v-bind:id="'step-dropdown-' + id" class='dropdown-content'>
                <li>
                    <a class="modal-trigger" v-bind:href="'#step-modal-' + id">
                        <i class="material-icons left">info</i>Instruction
                    </a>
                </li>
                <li>
                    <a v-on:click="$emit('remove')">
                        <i class="material-icons left">delete_forever</i>Delete
                    </a>
                </li>
            </ul>
            <div v-bind:id="'step-modal-' + id" class="modal">
                <div class="modal-content indigo lighten-5">
                    <h4>{{ name }}</h4>
                    <p>{{ instruction }}</p>
                </div>
                <div class="modal-footer indigo lighten-4">
                    <a href="#!" class="modal-close btn-flat">Close</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Step',
    props: [
        'id', 'name', 'instruction'
    ],
    mounted() {
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth:false});
        $(this.$el).find('.modal').modal();
    }
}
</script>

<style scoped lang="scss">
.step {
    color: black;
    background-color: #FFFFFFCC;
}
</style>