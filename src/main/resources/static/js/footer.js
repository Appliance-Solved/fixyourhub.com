/**
 * Created by Carlos on 7/10/17.
 */
$(window).bind("load", function() {

    var footerHeight = 0,
        footerTop = 0,
        $footer = $(".footer");

    positionFooter();

    function positionFooter() {

        footerHeight = $footer.height();
        footerTop = ($(window).scrollTop()+$(window).height()-footerHeight)+"px";

        if ( ($(document.body).height()+footerHeight) < $(window).height()) {
            $footer.css({
                position: "absolute"
            }).animate({
                bottom: 0
            }, 1)
        } else {
            $footer.css({
                position: "static"
            })
        }

    }
    $(window)
        .scroll(positionFooter)
        .resize(positionFooter)

});