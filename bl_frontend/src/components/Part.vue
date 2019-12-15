<template>
    <div class="part" v-bind:style="{
        width: (dimensions.width / buildWidth) * 100 + '%', 
        height: (dimensions.height / buildHeight) * 100 + '%',
        left: offsetX + '%',
        top: offsetY + '%',
        transform: connectorPoint ? 'translate(' + (connectorPoint.x * -100) + '%, ' + (connectorPoint.y * -100) + '%)' : 'translate(-50%, -50%)'
    }">
        <img v-bind:src="img_src">
        <button class="slot" 
             v-for="(slot, i) in slotPoints"
             v-bind:key="i"
             v-bind:style="slot.connected ? {display: 'none'} : (slot.width && slot.height && connectorPoint? 
                {
                    left: (slot.x * 100) + '%', 
                    top: (slot.y * 100) + '%', 
                    width: (slot.width / buildWidth) * 100 + '%', 
                    height: (slot.height / buildHeight) * 100 + '%',
                    transform: 'translate(' + (slot.connectorPoint.x * -100) + '%, ' + (slot.connectorPoint.y * -100) + '%)'
                } 
                : {left: (slot.x * 100) + '%', top: (slot.y * 100) + '%', transform: 'translate(-50%, -50%)'})"
             v-on:click="slotclick(slot, i)"
        ></button>
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
        'parent',
        'part', 
        'buildWidth', 
        'buildHeight',
    ],
    created() {
        this.part.vue = this;
    },
    computed: {
        offsetX() {
            if (this.parent) {
                return this.connectedAt.left * (this.parent.dimensions.width / this.buildWidth) * 100 + 
                    ((this.parent.offsetX * this.buildWidth - (this.parent.connectorPoint.x * this.parent.dimensions.width * 100)) / this.buildWidth);
            }
            else {
                return this.connectedAt.left * 100;
            }
        },
        offsetY() {
            if (this.parent) {
                return this.connectedAt.top * (this.parent.dimensions.height / this.buildHeight) * 100 + 
                    ((this.parent.offsetY * this.buildHeight - (this.parent.connectorPoint.y * this.parent.dimensions.height * 100)) / this.buildHeight);
            }
            else {
                return this.connectedAt.top * 100;
            }
        }
    },
    methods: {
        slotclick(slot, i) {
            this.$emit('slotclick', this, slot, i)
        }
    }
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
        cursor: pointer;
    }
}
</style>