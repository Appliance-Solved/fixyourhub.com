/**
 * Created by larryg on 7/6/17.
 */
"use strict";
$(document).ready(function () {
    "use strict";
    //  function to switch registration form and page content from standard user to servicer
    $(".serviceRegister").hide();
    $(".userRole").change(function(){
        if (this.value == "user"){
            $(".serviceRegister").hide();
            $(".userRegister").show();
        }else{
            $(".serviceRegister").show();
            $(".userRegister").hide();
        }
    })

    if(window.location.search){
        $('#servicer').attr('checked', 'checked');
        $(".serviceRegister").show();
        $(".userRegister").hide();
    }


    $("#date").datepicker();

    var currentCol;
    var isMouseDown = false;
   $(".d-0, .d-1, .d-2, .d-3, .d-4, .d-5, .d-6").mousedown(function(){
       isMouseDown = true;
       $(this).toggleClass("selecting");
       var current = $(this).attr("class");
       var classes = current.split(" ");
       currentCol = classes[0];
       console.log(currentCol);
       return false;
   })
       .mouseover(function () {
           if (isMouseDown) {
               if($(this).hasClass(currentCol)) {
                   $(this).toggleClass("selecting")
               }
           }

   });
    $(document)
        .mouseup(function () {
            isMouseDown = false;

        });



    function findSelectedDaynTime(selected){
        var dates = [];
        var times = [];
        $.each(selected, function(i, e){
            var classy = e.getAttribute("class");
            var classes = classy.split(" ");
            var time = classes[1].split("-");
            var date = classes[0].split("-");
            dates.push(date[1]);
            times.push(time[1]);
        });
        $("#date").val(dates);
        $("#time").val(times);
    }

    var selected = [];
    $("#add").click(function(){
        selected = $(".selecting");

        findSelectedDaynTime(selected);
        $("#setAvail").submit();
    })

    $(".time-frame").click(function(){
        var id = $(this).attr("id");
        $("#time-frame").val(id);
        $("#search-results").submit();
    });


    function rateService() {
        $(".rating > img").mouseenter(function () {
            var $curr = $(this);
            var star = $(this).attr("id");
            var starId = star.split("-");
            $(this).addClass("ratehover");
            for(var i = 1; i < starId[1]; i++ ){
                $curr =  $curr.prev();
                $curr.addClass("ratehover");
            }
        }).mouseleave(function () {
            $(".rating > img").removeClass("ratehover");
        });

        $(".rating > img").click(function () {
            var $curr = $(this);
            var star = $(this).attr("id");
            var starId = star.split("-");
            $(this).addClass("rated");
            for(var i = 1; i <= starId[1]; i++ ){
               $curr =  $curr.prev();
               $curr.addClass("rated");
            }
            var $thisdiv = $(this).parent();
            $thisdiv = $thisdiv.next();
            var myinput = $thisdiv.attr("id");
            $("#" + myinput + " > #rating").val(starId[1]);
        })
    }

        function undoRate(){
        $(".rating > img").click(function () {
            $(".rating > img").removeClass("rated");
        })
        }

    $.when(rateService()).done(undoRate());
    $.when(undoRate()).done(rateService());

    rateService();


    var ratingpercent = $("#myrating > p span").text();
    ratingpercent = (ratingpercent * 20);
    ratingpercent = ratingpercent + "%";
    $("#rateoverlay").css("width", ratingpercent);



    $(".selector > h3").click(function(){
        if($(".contentSelect:visible").parent().attr("id") !== $(this).parent().attr("id")){
            $(".contentSelect:visible").parent().children().find(".glyphicon").toggleClass("glyphicon-chevron-down");
            $(".contentSelect:visible").parent().children().find(".glyphicon").toggleClass("glyphicon-chevron-up");
        $(".contentSelect").hide();
    }
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-down");
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-up");
        $(this).next().slideToggle();
    });


    $("#serviceSubmit").click(function(){
        $("#serviceRecodForm").submit();
    });



    $(".proTab").hide();
    $("#availavility").show();

    $(".tablink").click(function(){
        $(".tablink").removeClass("active");
        $(".proTab").hide();
        $(this).addClass("active");
        var id = $(".active").attr("id");
        switch(id){
            case "avail":
                $("#availavility").slideDown();
                break;
            case "review":
                $("#myReviews").slideDown();
                break;
            case "team":
                $("#myTeam").slideDown();
                break;
            case "prof":
                $("#profile").slideDown();
                break;
        }
    });

    var urlTab = window.location.href;
    console.log(urlTab);
    if(urlTab.includes("#")){
    var tab = urlTab.split("#");
    console.log(tab[1]);
    switch(tab[1]){
        case "avail":
            $('.tablink').removeClass("active");
            $("#avail").addClass("active");
            $(".proTab").hide();
            $("#availavility").slideDown();
            break;
        case "review":
            $('.tablink').removeClass("active");
            $("#review").addClass("active");
            $(".proTab").hide();
            $("#myReviews").slideDown();
            break;
        case "myteam":
            $('.tablink').removeClass("active");
            $("#team").addClass("active");
            $(".proTab").hide();
            $("#myTeam").slideDown();
            break;
        case "prof":
            $('.tablink').removeClass("active");
            $("#prof").addClass("active");
            $(".proTab").hide();
            $("#profile").slideDown();
            break;
    }}

$(".techChoice").click(function(){
    $(".techChoice").removeClass("techSelected");
    $(this).addClass("techSelected");
})


    $(".finalConfirm").click(function(){
        var $selected = $(this).parent().prev();
        if($selected.find(".techSelected").children("span").attr("id") !== undefined) {
            var selectId = $selected.find(".techSelected").children("span").attr("id");
            var techId = selectId.split("-");
            console.log(techId[1]);
            $selected.children("form").children("#tech_id").val(techId[1]);
           $selected.children("form").submit();
        }else{alert("You must assign a technician to the appointment.")}
    })







});