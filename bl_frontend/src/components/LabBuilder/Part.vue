<template>
    <div class="part" v-bind:style="{
        width: dimensions.width, 
        height: dimensions.height,
        left: connectorAt.left - connectorPoint.left,
        top: connectedAt.top - connectorPoint.top,
    }">
        <img v-bind:src="img_src">
        <div class="slot teal accent-4" v-for="(i, slot) in slots" v-bind:key="i" v-bind:style="{left: slot.x, top: slot.y}"></div>
    </div>
</template>

<script>
export default {
    name: 'Part',
    props: [
        'name', 'img_src', 'dimensions', 'slotPoints', 'connectorPoint', 'connectedAt'
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