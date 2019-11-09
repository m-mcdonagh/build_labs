$(function() {
    $('.hamburger').on('click', function() {
        $('#dropdown-menu').slideToggle(100);
    });
    var timeout;
    $('#hover-zone').mouseleave(function(){
        timeout = setTimeout(function() {
            $('#dropdown-menu').slideUp(100);
        }, 250);
    });
    $('#hover-zone').mouseover(function() {
        clearTimeout(timeout);
    });
})