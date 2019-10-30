$(function() {
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
        $('#part-selector').show();
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
        $('#build-so-far img').hide();
        $('#step-name').val('');
        $('#step-instruction').val('');
    });
    
    
    $('#parts span').on('click', function() {
        $('.lab-overview-btn').prop('disabled', false);
        $('.lab-overview-btn').hide();
        $('.step-btns').show();
        $('#step-details').show();
        $('#part-selector').hide();
        $('#build-so-far img').show();
    });
    $('#part-cancel').on('click', function() {
        $('#new-step-btn').show();
        $('.lab-overview-btn').prop('disabled', false);
        $('#part-selector').hide();
    });   
    
    
    var toggle = false;
    var height;
    $('#minimize').on('click', function(){
        if (toggle){
            $('#step-name').show();
            $('#step-instruction').show();
            $('#step-details').animate({
                width: '800px',
                height: height
            })
            toggle=false;
        }
        else {
            height = $('#step-details').height();
            $('#step-details').animate({
                width: $(this).width(),
                height: $(this).height()
            }, function(){
                $('#step-name').hide();
                $('#step-instruction').hide();
            });
            toggle=true;
        }
    })
});