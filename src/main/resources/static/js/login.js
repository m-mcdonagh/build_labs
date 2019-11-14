$(function() {
    
    $('#show-register').on('click', function() {
        $('#login').slideUp();
        $('#register').slideDown();
    });

    $('#show-reset-password').on('click', function() {
        $('#login').slideUp();
        $('#reset-password').slideDown();
    });

    $('#hide-register').on('click', function() {
        $('#register').slideUp();
        $('#login').slideDown();
    });

    $('#hide-reset-password').on('click', function() {
        $('#reset-password').slideUp();
        $('#login').slideDown();
    });

    $('#register').hide();
    $('#reset-password').hide();
});