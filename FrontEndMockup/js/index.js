$(function() {
    $('.clip-btn').on('click', function() {
        $('#clip-toggler').attr('checked', true);
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
        }, 650);
        $coverimg.remove();

        $('.clip-btn').remove();

        let url = $(this).attr('data-relocation');
        setTimeout(function(){
            window.location = url;
        }, 750);
    });
});