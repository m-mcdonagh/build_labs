<template>
    <div id="build-so-far" v-bind:style="{width: displayWidth, height: displayHeight}">
        <part-component
          v-for="part in parts"
          v-bind:key="part.stepID"
          v-bind:name="part.name"
          v-bind:img_src="part.img_src"
          v-bind:dimensions="part.dimensions"
          v-bind:slotPoints="part.slotPoints"
          v-bind:connectorPoint="part.connectorPoint"
          v-bind:connectedAt="part.connectedAt"
          v-bind:parent="part.parent"
          v-bind:part="part"
          v-bind:buildWidth="buildWidth"
          v-bind:buildHeight="buildHeight"
          v-bind:displayWidth="displayWidth"
          v-bind:displayHeight="displayHeight"
          v-on:slotclick="slotclick">
        </part-component>
      </div>
</template>

<script>
import partComponent from './Part.vue'

export default {
    name: 'build',
    props: ['buildWidth', 'buildHeight', 'displayWidth', 'displayHeight', 'parts'],
    components: {
        'part-component': partComponent
    },
    methods: {
        slotclick(part, slot, i) {
            this.$emit('slotclick', part, slot, i);
        }
    }
}
</script>

<style lang="scss" scoped>
#build-so-far {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    max-height: 95%;
    max-width: 95%;

    img {
        max-height: 80vh;
    }
}
</style>