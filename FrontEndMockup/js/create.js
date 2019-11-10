$(function(){
    $('.tabs').tabs();
    $('.fixed-action-btn').floatingActionButton();
    $('.tooltipped').tooltip();

    sizeContent();
    window.onresize = sizeContent;  
});

function sizeContent() {
    let height = $('.container').height() - 58
    $('.content').css({
        height: height
    });
}