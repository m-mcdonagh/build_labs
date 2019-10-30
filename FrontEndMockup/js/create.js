$(function(){
    $('.tab-btn').on('click', function(){
        let $this = $(this);
        if ($this.hasClass('unselected-tab')){
            $('.tab-btn').addClass('unselected-tab').removeClass('selected-tab');
            $this.removeClass('unselected-tab').addClass('selected-tab');
            $('.content').hide();
            $($this.attr('data-content')).show();
        }
    });
});