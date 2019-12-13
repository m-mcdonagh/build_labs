<template>
    <div class="step collection-item">
        {{ index + 1 }}: {{ name }}
        <div class="right">
            <a class="dropdown-trigger btn-floating btn-small waves-effect waves-light" 
            v-bind:data-target="'step-dropdown-' + id">
                <i class="material-icons">keyboard_arrow_down</i>
            </a>
            <ul v-bind:id="'step-dropdown-' + id" class='dropdown-content'>
                <li>
                    <a class="modal-trigger" v-bind:href="'#step-modal-' + id" v-on:click="saveState">
                        <i class="material-icons left">info</i>Instruction
                    </a>
                </li>
                <li>
                    <a v-on:click="$emit('remove')">
                        <i class="material-icons left">delete_forever</i>Delete
                    </a>
                </li>
            </ul>
            <div v-bind:id="'step-modal-' + id" class="modal modal-fixed-footer indigo lighten-5">
                <div class="modal-content">
                    <div class="input-field col s6">
                        <input id="step-name" type="text" v-model="name">
                        <label for="step-name">Step Name</label>
                    </div>
                    <div class="input-field col s12">
                        <textarea id="step-instruction" class="materialize-textarea" v-model="instruction"></textarea>
                        <label for="step-instruction">Instruction</label>
                    </div>
                </div>
                <div class="modal-footer indigo lighten-4">
                    <a href="#!" class="modal-close btn-flat">Done</a>
                    <a href="#!" class="modal-close btn-flat" v-on:click="cancel">Cancel</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Step',
    props: [
        'id', 'index', 'name', 'instruction'
    ],
    data() {
        return {oldname: '', oldinstruction: ''}
    },
    mounted() {
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth:false});
        $(this.$el).find('.modal').modal();
        M.updateTextFields();
    },
    methods: {
        saveState() {
            this.oldname = this.name;
            this.oldinstruction = this.instruction;
        },
        cancel() {
            this.name = this.oldname;
            this.instruction = this.oldinstruction;
        }
    }
}
</script>

<style scoped lang="scss">
.step {
    color: black;
    background-color: #FFFFFFCC;
}
input, textarea {
    color: black !important;
}
</style>