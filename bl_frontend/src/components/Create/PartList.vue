<template>
    <li class="part collection-item" v-bind:class="ispublished?'blue-grey lighten-5':''">
        <a class="dropdown-trigger btn-floating btn-small waves-effect waves-light" 
           v-bind:data-target="'part-dropdown-' + id"
           v-bind:class="ispublished?'teal darken-1':'teal lighten-2'">
            <i class="material-icons">keyboard_arrow_down</i>
        </a>
        <ul v-bind:id="'part-dropdown-' + id" class='dropdown-content'>
            <li v-if="!ispublished">
                <a v-bind:href="'/partbuilder?id=' + id">
                    <i class="material-icons left">edit</i>Edit
                </a>
            </li>
            <li v-if="!ispublished">
                <a v-on:click="$emit('remove')">
                    <i class="material-icons left">delete_forever</i>Delete
                </a>
            </li>
            <li v-if="!ispublished">
                <a v-on:click="$emit('publish')">
                    <i class="material-icons left">publish</i>Publish
                </a>
            </li>
            <!-- SOMEDAY MAYBE: display a published part (since it can only be viewed in lab builder) -->
            <!-- <li v-if="ispublished">
                <a>
                    <i class="material-icons">info</i>Info
                </a>
            </li> -->
            <li>
                <a v-on:click="$emit('copy')">
                    <i class="material-icons">content_copy</i>Copy
                </a>
            </li>
        </ul>
        <span>{{ name }}</span>
    </li>
</template>

<script>
export default {
    data() {
    return{
        partList :[]
    
    }
  },
  //template:'<button v-on:click="postButton">Reverse Message</button>',
  methods: {
    
  },

    name: 'Part',
    props: ['name', 'ispublished', 'id'],
    mounted(){
        M.Dropdown.init($(this.$el).find('.dropdown-trigger').get(0), {constrainWidth: false, coverTrigger: false});
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