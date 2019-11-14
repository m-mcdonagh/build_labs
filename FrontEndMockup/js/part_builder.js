var part = {
    width: 12,
    height: 12
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
    $('#part img').css({
        width: width + 'px',
        height: height + 'px'
    })
}

function addSlot(x, y) {
    let newSlot = $('<div></div>').addClass('slot').addClass('tooltipped').css({
        left: x,
        top: y,
    }).attr('data-position', 'bottom').attr('data-tooltip', 'Slot');
    $('#slots').append(newSlot);
    newSlot.tooltip();
    
}

$(function() {
    $('.tooltipped').tooltip();
    $('.modal').modal();

    $('#add-slot').on('click', function() {
        let $this = $(this);
        $this.prop('disabled', true);
        let $main = $('#part-builder-main');
        $main.css({cursor: 'crosshair'});
        setTimeout(function(){
            $main.on('click', function(e){
                $this.prop('disabled', false);
                $main.css({cursor: 'auto'});
                $main.off('click');
                addSlot(e.clientX, e.clientY);
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