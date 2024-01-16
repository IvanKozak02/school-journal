$(document).ready(function(){
    $(".sliderbig").slick({
        arrows:false,
        slidesToShow: 1,
        asNavFor: ".slider",
        centerMode:true,

    })
    $(".slider").slick({

        slidesToShow: 1,
        slidesToScroll: 1,
        dots:true,
        adaptiveHeight:true,
        centerMode:true,
        asNavFor:".sliderbig"
    });
});