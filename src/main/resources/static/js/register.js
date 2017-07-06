/**
 * Created by larryg on 7/6/17.
 */
"use strict";
$(document).ready(function () {
    "use strict";

    var userRole = $("#userRole");
    if (userRole == "user"){
        $(".serviceRegister").hide();
        $(".userRegister").show();
    }else{
        $(".serviceRegister").show();
        $(".userRegister").hide();
    }


});