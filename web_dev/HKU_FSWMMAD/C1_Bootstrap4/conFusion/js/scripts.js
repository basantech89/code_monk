/*$(document).ready(function () {
            $('[data-toggle="tooltip"]').tooltip();
        })*/
$(document).ready(function () {
    $('#mycarousel').carousel( { interval: 5000 } )
    $('#carouselButton').click(function () {

        // if the span tag has the pause class set, then we know that it's acting as a pause button
        if ($('#carouselButton').children('span').hasClass('fa-pause')) {
            // pause the carousel
            $('#mycarousel').carousel('pause');
            // flip the icon from pause button to play button
            $('#carouselButton').children('span').removeClass('fa-pause');
            $('#carouselButton').children('span').addClass('fa-play');
        }
        else {
            $('#mycarousel').carousel('cycle');
            $('#carouselButton').children('span').removeClass('fa-play');
            $('#carouselButton').children('span').addClass('fa-pause');
        }
    });

    $('#login').click(function () {
        $('#loginModal').modal();
    });

    $('#reserve').click(function () {
        $('#reserveModal').modal();
    });

});