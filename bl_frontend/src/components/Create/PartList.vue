<template>
    <div class="part valign-wrapper">
        <span>{{ name }}</span>
        <a v-if="!ispublished" v-bind:href="link" class="btn-small waves-effect indigo accent-2">
            <i class="material-icons left">edit</i>Edit
        </a>
        <button v-if="!ispublished" class="btn-small waves-effect indigo accent-2" v-on:click="remove">
            <i class="material-icons left">delete_forever</i>Delete
        </button>
        <button v-if="!ispublished" class="btn-small waves-effect indigo accent-2" v-on:click="publish">
            <i class="material-icons left">publish</i>Publish
        </button>
        <button v-if="ispublished" class="btn-small waves-effect indigo accent-2">
            <i class="material-icons left">info</i>Info
        </button>
        <button class="btn-small waves-effect indigo accent-2" v-on:click="copy">
            <i class="material-icons left">content_copy</i>Copy
        </button>
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
        margin: 5px 20px;
    }
    .btn-small {
        margin: 0 1px;
    }
}
</style>