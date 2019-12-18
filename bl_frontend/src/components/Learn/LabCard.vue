<template>
    <div class="card cyan lighten-4 col s11 l5">
        <div class="card-content">
            <span class="card-title">
                <i class="material-icons left">assignment</i>
                <span class="lab-name">{{name}}</span>
            </span>
            <p>
                <b>Owner: </b>
                <span class="lab-owner">{{owner}}</span>
            </p>
        </div>
        <div class="card-action">
            <a v-if="!complete && !inprogress" v-bind:href="'/lab?id='+lab_id+'&s=0'" class="start">Start</a>
            <a v-if="complete || inprogress" v-bind:href="'/lab?id='+lab_id+'&s=0'" class="start">Restart</a>
            <a v-if="inprogress" v-bind:href="'/lab?&id='+lab_id+'&s='+current_step" class="continue right">Continue</a>
        </div>
        <span v-if="!complete" class="check-box tooltipped" data-position="left" data-tooltip="INCOMPLETE">&#9744;</span>
        <span v-if="complete" class="check-box">&#9744;</span>
        <img v-if="complete" class="check tooltipped" data-position="left" data-tooltip="COMPLETE" src="../../assets/img/check.png">
    </div>
</template>

<script>
export default {
    name: 'lab-card',
    props: ['lab_id', 'name', 'owner', 'complete', 'inprogress', 'current_step'],
    mounted() {
        $(this.$el).find('.tooltipped').tooltip();
    }
}
</script>

<style lang="scss" scoped>
.card {
    position: relative;
    color: #313639;
    margin-left: 4.16% !important;
    margin-right: 4.16%;

    i.material-icons {
        cursor: default;
    }
    .card-action {
        font-weight: bold;
        a {
            color: #f57c00;
        }
        a:hover{
            color: #ffa726;
        }
    }
    .check-box {
        position: absolute;
        right: 8px;
        top: 0;
        font-size: 2rem;
        cursor: default;
    }
    .check {
        position: absolute;
        right: 0;
        top: 0;
        width: 30px;
    }
}
</style>