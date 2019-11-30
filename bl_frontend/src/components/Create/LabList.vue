<template>
    <li class="lab collection-item" v-bind:class="ispublished?'blue-grey lighten-5':''">
        <a class="dropdown-trigger btn-floating btn-small waves-effect waves-light"
           v-bind:data-target="'lab-dropdown-' + id"
           v-bind:class="ispublished?'teal darken-1':'teal lighten-2'">
            <i class="material-icons">keyboard_arrow_down</i>
        </a>
        <ul v-bind:id="'lab-dropdown-' + id" class='dropdown-content'>
            <li v-if="!ispublished">
                <a v-bind:href="link">
                    <i class="material-icons left">edit</i>Edit
                </a>
            </li>
            <li v-if="!ispublished">
                <a v-on:click="remove">
                    <i class="material-icons left">delete_forever</i>Delete
                </a>
            </li>
            <li v-if="!ispublished">
                <a v-on:click="publish">
                    <i class="material-icons left">publish</i>Publish
                </a>
            </li>
            <li v-if="ispublished" class="modal-trigger" v-bind:href="'#lab-modal-' + id">
                <a>
                    <i class="material-icons">info</i>Info
                </a>
            </li>
            <li>
                <a v-on:click="copy">
                    <i class="material-icons">content_copy</i>Copy
                </a>
            </li>
        </ul>
        <span>{{ name }}</span>
        <div v-if="ispublished" v-bind:id="'lab-modal-' + id" class="modal">
            <div class="modal-content indigo lighten-4">
                <h4><i class="material-icons">assignment</i>{{ name }}</h4>
                <div class="collection">
                    <div v-for="i in info" v-bind:key="i.user_id" class="collection-item info">
                        {{i.username}}
                        <span v-if="!i.complete" 
                              class="tooltipped"
                              data-position="left"
                              data-tooltip="INCOMPLETE">
                            &#9744;
                        </span>
                        <span v-if="i.complete">&#9744;</span>
                        <img v-if="i.complete" 
                             class="check tooltipped"
                             data-position="left"
                             data-tooltip="COMPLETE"
                             src="../../assets/img/check.png"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer indigo lighten-3">
                <button class="btn modal-close waves-effect waves-light indigo accent-4">Close</button>
            </div>
        </div>
    </li>
</template>

<script>
export default {
    name: 'Lab',
    props: ['name', 'ispublished', 'id'],
    data() {
        return {
            link: '/labbuilder?id=' + this.id,
            // TODO: set up axios for this.info
            info: [
                {user_id:0, username:'not complete', complete:false}, 
                {user_id:1, username:'complete', complete:true}
            ]
        }
    },
    mounted(){
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth: false, coverTrigger: false});
        M.Modal.init($(this.$el).find('.modal').get(0), {});
        M.Tooltip.init($(this.$el).find('.tooltipped').get(0), {position: 'left'});
    },
    methods: {
        copy() {
            //TODO axios
            $emit('copy'); // The create page's lab array needs to be updated (should be moved to axios.then)
        },
        publish() {
            //TODO axios
            $emit('publish'); // Properties can only be changed by parent (should be moved to axios.then)
        },
        remove() {
            //TODO axios
            $emit('remove'); // The create page needs to know when items are deleted/removed (should be moved to axios.then)
        },
    }
}
</script>

<style scoped lang="scss">
.lab{
    display: flex;
    flex-direction: row;

    span {
        margin: 5px;
    }
    .btn{
        color: white !important;
    }
    .info{ 
        position: relative;
        span{
            position: absolute;
            right: 10px;
            cursor: default;
        }
        img{
            position: absolute;
            height: 80%;
            top: 0;
            right: 5px;
        }
    }
}
</style>