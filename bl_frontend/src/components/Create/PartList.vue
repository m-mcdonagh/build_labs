<template>
    <div class="part valign-wrapper">
        <a class="dropdown-trigger btn-floating btn-small waves-effect waves-light" v-bind:data-target="'dropdown' + id">
            <i class="material-icons">keyboard_arrow_down</i>
        </a>
        <ul v-bind:id="'dropdown' + id" class='dropdown-content'>
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
            <li v-if="ispublished">
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
    </div>
</template>

<script>
export default {
    name: 'Part',
    props: ['name', 'ispublished', 'id'],
    data() {
        return {
            link: '/partbuilder?id=' + this.id
        }
    },
    mounted(){
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth: false, coverTrigger: false});
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
.part{
    display: flex;
    flex-direction: row;

    span {
        margin: 5px;
    }
}
</style>