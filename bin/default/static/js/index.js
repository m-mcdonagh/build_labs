$(function() {
    $('.clip-btn').on('click', function() {
        $($(this).attr('data-toggler')).attr('checked', true);
        $('#blue-ham').fadeOut(500);
        
        $coverimg = $('#cover-img');
        $coverimg.clone().css({
            'position':'fixed',
            'left' : $coverimg.offset().left,
            'top' : $coverimg.offset().top,
        }).appendTo('.main').animate({
            'left': '10px',
            'top' : '0',
            'height' : '5vh'
        }, 600);
        $coverimg.remove();

        $('.clip-btn').remove();

        let url = $(this).attr('data-url');
        setTimeout(function(){
            window.location = url;
        }, 750);
    });
});