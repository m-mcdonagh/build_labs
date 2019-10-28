$(function() {
    
    $('#show-register').on('click', function() {
        $('#login').hide();
        $('#register').show();
    });

    $('#show-reset-password').on('click', function() {
        $('#login').hide();
        $('#reset-password').show();
    });

    $('#hide-register').on('click', function() {
        $('#register').hide();
        $('#login').show();
    });

    $('#hide-reset-password').on('click', function() {
        $('#reset-password').hide();
        $('#login').show();
    });

    $('#register').hide();
    $('#reset-password').hide();
});