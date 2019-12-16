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
                        <i class="material-icons left">edit</i>Edit
                    </a>
                </li>
                <li>
                    <a v-on:click="$emit('remove')">
                        <i class="material-icons left">delete_forever</i>Delete
                    </a>
                </li>
            </ul>
            <div v-bind:id="'step-modal-' + id" class="modal modal-fixed-footer indigo lighten-5">
                <div class="modal-content" ref="max_dimensions">
                    <div class="inputs">
                        <div class="input-field col s6">
                            <input id="step-name" type="text" v-model="newname">
                            <label for="step-name">Step Name</label>
                        </div>
                        <div class="input-field col s12">
                            <textarea id="step-instruction" class="materialize-textarea" v-model="newinstruction"></textarea>
                            <label for="step-instruction">Instruction</label>
                        </div>
                        <span class="col s6 flow-text"><b>Part:</b> {{part.name}}</span>
                    </div>
                    <a class="new-part-img-btn modal-trigger" v-bind:href="'#part-selector-' + id">
                        <img v-bind:src="part.img_src" v-bind:style="img_dimensions">
                    </a>
                </div>
                <div class="modal-footer indigo lighten-4">
                    <a href="#!" class="modal-close btn-flat" v-on:click="$emit('infochange', newname, newinstruction)">Done</a>
                    <a href="#!" class="modal-close btn-flat" v-on:click="newpart(oldpart)">Cancel</a>
                </div>
            </div>
            <part-selector v-bind:id="'part-selector-' + id" 
                v-bind:listofparts="listofparts"
                v-on:newpart="newpart">
            </part-selector>
        </div>
    </div>
</template>

<script>
import partSelector from './PartSelector.vue';

export default {
    name: 'Step',
    props: [
        'id', 'index', 'name', 'instruction', 'part', 'listofparts'
    ],
    components : {
        'part-selector': partSelector
    },
    data() {
        return {newname: '', newinstruction: '', img_dimensions: {}, oldpart:null}
    },
    mounted() {
        let resizeImg = this.resizeImg;
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth:false});
        M.Modal.init($(this.$el).find('.modal').get(0), {
            onOpenEnd(){
                M.updateTextFields();
                resizeImg();
            }
        });
        M.updateTextFields();
    },
    methods: {
        saveState() {
            this.newname = this.name;
            this.newinstruction = this.instruction;
            this.oldpart = this.part;
        },
        newpart(part) {
            this.$emit('partchange', part);
        },
        resizeImg() {
            let aspectRatio = this.part.dimensions.width / this.part.dimensions.height;
            let maxWidth = this.$refs.max_dimensions.offsetWidth * .475;
            let maxHeight = this.$refs.max_dimensions.offsetHeight * .95;
            let width = maxHeight * aspectRatio;
            if (width <= maxWidth) {
                this.img_dimensions = {
                    width: width + 'px',
                    height: maxHeight + 'px'
                }
            }
            else {
                this.img_dimensions = {
                    width: maxWidth + 'px',
                    height: maxWidth / aspectRatio + 'px'
                }
            }
        }
    },
    watch: {
        part(){
            this.resizeImg();
        }
    }
}
</script>

<style scoped lang="scss">
.step {
    color: black;
    background-color: #FFFFFFCC;
}
.modal-content {
    display: flex;
    flex-direction: row;
    .inputs {
        width: 50%;
        height: 100%;
        overflow-y: scroll;
        input, textarea {
            color: black !important;
        }
    }
    .new-part-img-btn {
        display: contents;

        img {
            max-height: 100%;
            max-width: 50%;
            cursor: pointer;
        }
        img:hover {
            opacity: .75;
        }
    }
}
</style>