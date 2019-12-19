<template>
    <div class="collection-item info">
        {{username}}
        <span v-if="!complete" class="tooltipped" data-position="left" data-tooltip="INCOMPLETE" >&#9744;</span>
        <span v-if="complete">&#9744;</span>
        <img v-if="complete" class="check tooltipped" data-position="left" data-tooltip="COMPLETE" src="../../assets/img/check.png"/>
    </div>
</template>

<script>
export default {
    name: 'labinfo',
    props: ['username', 'complete'],
    mounted() {
        $(this.$el).find('.tooltipped').tooltip();
    }
}
</script>