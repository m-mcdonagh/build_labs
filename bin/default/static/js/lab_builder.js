$(function() {
    $('.modal').modal();
    
    $('#step-details').hide();
    $('.step-btns').hide();
    $('#part-selector').hide();
    $('#build-so-far img').hide();

    
    $('#exit').on('click', function() {
        window.location = "/create.html";
    });
    $('#new-step-btn').on('click', function() {
        $('#new-step-btn').hide();
        $('.lab-overview-btn').prop('disabled', true);
    });
    
    
    
    $('.step-btns').on('click', function() {
        $('#step-details').hide();
        $('.step-btns').hide();
        $('.lab-overview-btn').show();
        $('#new-step-btn').show()
    });
    $('#done').on('click', function() {
        let name = $('#step-name').val();
        let instruction = $('#step-instruction').val();
        $('#step-name').val('');
        $('#step-instruction').val('');
        $('#step-list').append($('<li>').text(name));
    });
    $('#cancel').on('click', function() {
        $('#step-name').val('');
        $('#step-instruction').val('');
    });
    
    
    $('#parts span').on('click', function() {
        $('.lab-overview-btn').prop('disabled', false);
        $('.lab-overview-btn').hide();
        $('.step-btns').show();
        $('#step-details').show();
        $('#part-selector').modal('close');
        $('#build-so-far img').show();
    });
    $('#part-cancel').on('click', function() {
        $('#new-step-btn').show();
        $('.lab-overview-btn').prop('disabled', false);
        $('#part-selector').modal('close');
    });   
    
    
    var toggle = false;
    var height;
    $('#minimize').on('click', function(){
        if (toggle){
            $('#step-details .row').show()
            $('#step-details').animate({
                width: '800px',
                height: height + 'px',
            });
            $('#step-details').css({
                'overflow-y': 'visible'
            });
            toggle=false;
        }
        else {
            height = $('#step-details').height() + 19;
            $('#step-details').animate({
                width: $(this).width() + 'px',
                height: $(this).height() + 'px',
            }, function(){
                $('#step-details .row').hide();
            });
            $('#step-details').css({
                'overflow-y': 'scroll'
            });
            toggle=true;
        }
    });
});