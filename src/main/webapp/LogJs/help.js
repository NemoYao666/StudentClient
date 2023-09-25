//url解析:根据传递过来的参数name获取对应的值
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return null;
}

$(function () {
    //根据请求help页面参数，设置页面布局
    var type = getParameter("type");

    if (type == "forgetPassword") {

        $("#AccountLoginA").text("忘记密码");
        $("#forgetPassword_Form").css({
            display: "block"
        });

        $("#register_Form1").css({
            display: "none"
        });

        $("#register_Form2").css({
            display: "none"
        });

        $("#delete_Form").css({
            display: "none"
        });

        $("#info_Form").css({
            display: "none"
        });

    } else if (type == "modifyPassword") {

        $("#AccountLoginA").text("修改密码");
        $("#forgetPassword_Form").css({
            display: "block"
        });

        $("#register_Form1").css({
            display: "none"
        });

        $("#register_Form2").css({
            display: "none"
        });

        $("#delete_Form").css({
            display: "none"
        });

        $("#info_Form").css({
            display: "none"
        });

    } else if (type == "registerAccount") {

        $("#AccountLoginA").text("注册账户");

        $("#forgetPassword_Form").css({
            display: "none"
        });

        $("#register_Form1").css({
            display: "block"
        });

        $("#register_Form2").css({
            display: "none"
        });

        $("#delete_Form").css({
            display: "none"
        });

        $("#info_Form").css({
            display: "none"
        });

    } else if (type == "registerAccount2") {

        $("#AccountLoginA").text("注册账户");

        $("#forgetPassword_Form").css({
            display: "none"
        });

        $("#register_Form1").css({
            display: "none"
        });

        $("#register_Form2").css({
            display: "block"
        });

        $("#delete_Form").css({
            display: "none"
        });

        $("#info_Form").css({
            display: "none"
        });

    } else if (type == "cancelAccount") {

        $("#AccountLoginA").text("注销账户");

        $("#forgetPassword_Form").css({
            display: "none"
        });

        $("#register_Form1").css({
            display: "none"
        });

        $("#register_Form2").css({
            display: "none"
        });

        $("#delete_Form").css({
            display: "block"
        });

        $("#info_Form").css({
            display: "none"
        });

    } else if (type == "modifyInfo") {

        $("#AccountLoginA").text("修改信息");

        $("#forgetPassword_Form").css({
            display: "none"
        });

        $("#register_Form1").css({
            display: "none"
        });

        $("#register_Form2").css({
            display: "none"
        });

        $("#delete_Form").css({
            display: "none"
        });

        $("#info_Form").css({
            display: "block"
        });

        //查询登录状态,显示数据库信息入前端表单
        $.post("user/findLogInfo", {}, function (data) {
            if (data[1] == "error") {
                window.top.location.href = "login.html";
            } else if (data[1] == "pass") {

                //开始查数据库
                $.post("user/findUserByUsername2", {username:data[0]}, function (data2) {
                    $("#info_username").val(data2[0]);
                    $("#info_gender").val(data2[1]);
                    $("#info_age").val(data2[2]);
                    $("#info_phone").val(data2[3]);

                })


            }

        })

    }

})

//动态向年龄select里添加元素
$(function () {

    // 注册表单的年龄
    for (let i = 12; i <= 100; i++) {
        $("#age").append("<option value='" + i + "'>" + i + "</option>");
    }

    // 修改表单的年龄
    for (let i = 12; i <= 100; i++) {
        $("#info_age").append("<option value='" + i + "'>" + i + "</option>");
    }
})

//根据页面请求参数，设置提交的表单
function confirm_help() {

    var type = getParameter("type");

    //对于忘记密码和修改密码的提交请求
    if (type == "forgetPassword" || type == "modifyPassword") {

        var forgetPassword_email = $("#forgetPassword_email").val();
        var forgetPassword_dynamicCode = $("#forgetPassword_dynamicCode").val();
        var forgetPassword_password = $("#forgetPassword_password").val();

        if (forgetPassword_password == null || forgetPassword_password.trim() == "") {
            $("show_error").text("密码不能为空");
        } else {

            $.post("user/findByEmail", {email: forgetPassword_email}, function (data) {

                if (data[1] == "error") {
                    $("#show_error").text("该账户不存在");
                } else if (data[1] == "pass") {
                    //找到账户了
                    //判断动态码是否正确

                    var username = data[0];

                    $.post("user/checkDynamicCode", {
                        dynamicCode: forgetPassword_dynamicCode,
                        username: username
                    }, function (data) {

                        if (data[1] == "error") {
                            $("#show_error").text("邮箱验证码错误");
                        } else if (data[1] == "pass") {
                            //动态码正确,允许修改密码
                            $.post("user/modifyPassword", {
                                email: forgetPassword_email,
                                password: forgetPassword_password
                            }, function (data) {

                                if (data[1] == "error") {
                                    $("#show_error").text("修改密码失败");
                                } else if (data[1] == "pass") {
                                    $("#show_error").text("修改密码成功");
                                }

                            });

                        }
                    });


                }
            });

        }

    } else if (type == "registerAccount") {
        // 先查找用户名是否存在
        var register_username = $("#register_username").val();
        var register_password = $("#register_password").val();
        var register_gender = $("#gender").val();
        var register_age = $("#age").val();
        var register_phone = $("#register_phone").val();

        if (register_username == null || register_username.trim() == "") {
            $("#show_error").text("用户名不能为空");
        } else if (register_password == null || register_password.trim() == "") {
            $("#show_error").text("密码不能为空");
        } else if (register_phone == null || register_phone.trim() == "") {
            $("#show_error").text("手机号不能为空");
        } else {
            $.post("user/findUserByUsername", {username: register_username}, function (data) {

                if (data[1] == "pass") {
                    $("#show_error").text("用户名已存在");
                } else if (data[1] == "error") {
                    //用户名不存在，再查找手机号是否存在

                    $.post("user/findUserByPhone", {phone: register_phone}, function (data) {

                        if (data[1] == "pass") {
                            $("#show_error").text("手机号已存在");
                        } else if (data[1] == "error") {
                            //手机号不存在，保存保单信息，并跳转到邮箱验证
                            $.post("user/saveRegisterForm",
                                {
                                    username: register_username, phone: register_phone,
                                    password: register_password, gender: register_gender,
                                    age: register_age
                                }, function () {
                                    window.top.location.href = "help.html?type=registerAccount2";
                                });
                        }

                    });

                }

            });

        }


    } else if (type == "registerAccount2") {
        //验证邮箱动态码是否正确并提交表单注册

        var register_dynamicCode = $("#register_dynamicCode").val();
        var register_email = $("#register_email").val();

        $.post("user/checkDynamicCode", {dynamicCode: register_dynamicCode}, function (data) {

            if (data[1] == "error") {
                $("#show_error").text("邮箱验证码错误");
            } else if (data[1] == "pass") {
                //动态码正确,允许注册
                $.post("user/registerAccount", {email: register_email}, function (data) {

                    if (data[1] == "error") {
                        $("#show_error").text("注册失败");
                    } else if (data[1] == "pass") {
                        $("#show_error").text("用户" + data[0] + " 注册成功");
                    }

                });

            }
        });

    } else if (type == "cancelAccount") {

        //注销账户

        var email = $("#delete_email").val();
        var dynamicCode = $("#delete_dynamicCode").val();

        $.post("user/findByEmail", {email: email}, function (data) {

            if (data[1] == "error") {
                $("#show_error").text("该账户不存在");
            } else if (data[1] == "pass") {
                //找到账户了
                //判断动态码是否正确

                var username = data[0];

                $.post("user/checkDynamicCode", {dynamicCode: dynamicCode, username: username}, function (data) {

                    if (data[1] == "error") {
                        $("#show_error").text("邮箱验证码错误");
                    } else if (data[1] == "pass") {
                        //动态码正确,允许注销or删除账户

                        $.post("user/deleteAccount", {email: email}, function (data) {

                            if (data[1] == "error") {
                                $("#show_error").text("注销失败");
                            } else if (data[1] == "pass") {
                                //删除成功
                                $("#show_error").text("用户" + data[0] + " 注销成功");
                            }
                        });


                    }
                });


            }
        });


    } else if (type == "modifyInfo") {
        //修改用户
        var username = $("#info_username").val();
        var gender = $("#info_gender").val();
        var age = $("#info_age").val();
        var phone = $("#info_phone").val();

        if (username == null || username.trim() == ""){
            $("#show_error").text("用户名不能为空");
        }else if (phone == null|| phone.trim() == ""){
            $("#show_error").text("手机号不能为空");
        }else {

            //检查用户名是否已经存在
            $.post("user/findUserByUsername3", {username:username}, function (data) {

                if (data[1] == "error") {
                    $("#show_error").text("用户名已被注册");
                } else if (data[1] == "pass") {
                    //不重名，检查手机号是否重复

                    $.post("user/findUserByPhone2", {phone:phone}, function (data) {

                        if (data[1] == "error") {
                            $("#show_error").text("手机号已被注册");
                        } else if (data[1] == "pass") {
                            //不重复，可修改信息

                            $.post("user/modifyInfo", {username:username,gender:gender,
                                age:age,phone:phone}, function (data) {

                                if (data[1] == "error") {
                                    $("#show_error").text("修改失败");
                                } else if (data[1] == "pass") {
                                    $("#show_error").text("修改成功");
                                }
                            });


                        }
                    });


                }
            });

        }

    }

}

//刷新验证码:忘记密码
function refreshCode_forgetPassword() {
    document.getElementById("forgetPassword_captchaImg").onclick = function () {
        this.src = "user/checkCode?time=" + new Date().getTime();
        $("#show_error").text("");
    }

}

//检验图形验证码和发送邮件：忘记密码
function checkCaptchaAndSendEmail_forgetPassword() {

    var captcha = $("#forgetPassword_captcha").val();

    $.post("user/checkCaptcha", {captcha: captcha}, function (data) {

        if (data[1] == "error") {
            $("#show_error").text("图形验证码错误,请刷新");
        } else if (data[1] == "pass") {
            sendEmail($("#forgetPassword_email").val());
        }

    });
}

//刷新验证码:注册账号
function refreshCode_register() {
    document.getElementById("register_captchaImg").onclick = function () {
        this.src = "user/checkCode?time=" + new Date().getTime();
        $("#show_error").text("");
    }

}

//检验图形验证码和发送邮件：注册账户
function checkCaptchaAndSendEmail_register() {

    // 首先查找邮箱是否已经被注册过了
    var register_email = $("#register_email").val();
    var captcha = $("#register_captcha").val();

    $.post("user/findByEmail", {email: register_email}, function (data) {

        if (data[1] == "pass") {
            $("#show_error").text("该邮箱已被注册");
        } else if (data[1] == "error") {
            //没找到，邮箱没被注册，可以发送邮件
            $.post("user/checkCaptcha", {captcha: captcha}, function (data) {

                if (data[1] == "error") {
                    $("#show_error").text("图形验证码错误,请刷新");
                } else if (data[1] == "pass") {
                    sendEmail_withoutFind(register_email);
                }

            });

        }

    });
}

//刷新验证码:注销账户
function refreshCode_delete() {
    document.getElementById("delete_captchaImg").onclick = function () {
        this.src = "user/checkCode?time=" + new Date().getTime();
        $("#show_error").text("");
    }

}

//检验图形验证码和发送邮件：注销账户
function checkCaptchaAndSendEmail_delete() {

    var captcha = $("#delete_captcha").val();

    $.post("user/checkCaptcha", {captcha: captcha}, function (data) {

        if (data[1] == "error") {
            $("#show_error").text("图形验证码错误,请刷新");
        } else if (data[1] == "pass") {
            sendEmail($("#delete_email").val());
        }

    });
}
