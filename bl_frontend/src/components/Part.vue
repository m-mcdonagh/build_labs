<template>
    <div class="part" v-bind:style="{
        width: (dimensions.width / buildWidth) * 100 + '%', 
        height: (dimensions.height / buildHeight) * 100 + '%',
        left: connectedAt.left * 100 + '%',
        top: connectedAt.top * 100 + '%',
        transform: connectorPoint ? 'translate(' + (connectorPoint.x * -100) + '%, ' + (connectorPoint.y * -100) + '%)' : 'translate(-50%, -50%)'
    }">
        <img v-bind:src="img_src">
        <div class="slot teal accent-4" 
             v-for="(slot, i) in slotPoints"
             v-bind:key="i"
             v-bind:style="{left: (slot.x * 100) + '%', top: (slot.y * 100) + '%'}"
             v-on:click="$emit('slotclick', part, slot)"
        ></div>
    </div>
</template>

<script>
export default {
    name: 'part',
    props: [
        'name', 
        'img_src', 
        'dimensions', 
        'slotPoints', 
        'connectorPoint', 
        'connectedAt', 
        'part', 
        'buildWidth', 
        'buildHeight',
    ],
}
</script>

<style lang="scss" scoped>
.part {
    position: absolute;

    img {
        width: 100%;
        height: 100%;
    }

    .slot {
        position: absolute;
        width: 30px;
        height: 30px;
        border: 4px solid white;
        border-radius: 100%;
        transform: translate(-50%, -50%);
        opacity: .5;
    }
    .slot:hover {
        opacity: 1;
    }
}
</style>