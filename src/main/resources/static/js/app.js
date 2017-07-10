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


});