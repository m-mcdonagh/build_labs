$(function(){
    placechecks();
    window.onresize = placechecks;
});

function placechecks() {
    var left = $('#check-col').width()/2 - $('#check-col').width()/7;
    var height = $('#check-col span').height();
    $('.check').each(function(i, elem){
        $(elem).css({
            height: height,  
            left: left,
            top: $($(elem).attr('data-lab')).position().top - height/4.5
        })
    });
}