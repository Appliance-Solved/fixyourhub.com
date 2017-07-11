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


    $("#date").datepicker();


    var isMouseDown = false;
   $(".d-0, .d-1, .d-2, .d-3, .d-4, .d-5, .d-6").mousedown(function(){
       isMouseDown = true;
       $(this).toggleClass("selecting");
       return false;
   })
       .mouseover(function () {
           if (isMouseDown) {
               $(this).toggleClass("selecting");
           }
   });
    $(document)
        .mouseup(function () {
            isMouseDown = false;
        });


});