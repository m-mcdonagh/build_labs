var part = {
    width: 12,
    height: 12,
    slots: {},
    connector: null
};

function resizePart() {
    var width, height;
    let $workspace = $('#workspace');
    var maxpx = Math.min($workspace.height(), $workspace.width()) * .95;
    if (Math.max(part.width, part.height) == part.width) {
        width = maxpx;
        height = (part.height / part.width) * maxpx;
    }
    else {
        height = maxpx;
        width = (part.width / part.height) * maxpx;
    }
    $('#part, #part img').css({
        width: width + 'px',
        height: height + 'px'
    })
}

var counter = 0;
function addSlot(x, y) {
    let newSlot = $('<div></div>').attr('id', 'slot'+counter).addClass('slot').addClass('teal').addClass('accent-4').addClass('tooltipped').css({
        left: x,
        top: y,
    }).attr('data-position', 'right').attr('data-tooltip', 'Slot');
    $('#slots').append(newSlot);
    newSlot.tooltip();
    part.slots['slot'+counter] = {
        x: x,
        y: y
    };
    counter++;
}

function addConnector(x, y){
    if (part.connector){
        $('#connector').remove();
    }
    let connector = $('<div></div>').attr('id', 'connector').addClass('teal').addClass('darken-5').addClass('tooltipped').css({
        left: x,
        top: y
    }).attr('data-position', 'bottom').attr('data-tooltip', 'Connector');
    $('#part').append(connector);
    connector.tooltip();
    part.connector = {
        x: x,
        y: y
    }
}

$(function() {
    window.onresize = resizePart;

    $('.tooltipped').tooltip();
    $('.modal').modal();

    $('#add-slot').on('click', function() {
        let $this = $(this);
        $this.prop('disabled', true);
        let $main = $('#part-builder-main');
        $main.css({cursor: 'crosshair'});
        setTimeout(function(){
            $('#part').on('click', function(e){
                addSlot(e.offsetX, e.offsetY);
            });
            $($main).on('click', function() {
                setTimeout(function(){
                    $this.prop('disabled', false);
                    $main.css({cursor: 'auto'});
                    $main.off('click');
                    $('#part').off('click');
                },1);
            });
        }, 1);
    });

    $('#add-connector').on('click', function() {
        let $this = $(this);
        $this.prop('disabled', true);
        let $main = $('#part-builder-main');
        $main.css({cursor: 'crosshair'});
        setTimeout(function(){
            $('#part').on('click', function(e){
                addConnector(e.offsetX, e.offsetY);
            });
            $($main).on('click', function() {
                setTimeout(function(){
                    $this.prop('disabled', false);
                    $main.css({cursor: 'auto'});
                    $main.off('click');
                    $('#part').off('click');
                },1);
            });
        }, 1);
    });

    $('.dimension').on('input', function() {
        let $this = $(this);
        let val = $this.val();
        if (!val){
            resizePart();
            return;
        }
        if (isNaN(val) || (val = parseFloat(val)) <= 0) {
            $this.val(part[$this.attr('id')]);
        }
        else {
            part[$this.attr('id')] = val;
        }
        resizePart();
    });
    $('#img-file').on('input', function(){
        let source = URL.createObjectURL(this.files[0]);
        part.file = this.files[0];
        $('#part img').attr('src', source);
        resizePart();
    });
    
    let imgFile = $('#img-file').get(0).files[0];
    if (imgFile) {
        $('#part img').attr('src', URL.createObjectURL(imgFile));
        part.width = parseFloat($('#width').val());
        part.height = parseFloat($('#height').val());
        resizePart();
    }
});