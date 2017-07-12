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

});