var Log_type = 0;   //登陆方式

//0为账户密码登录，1为验证码登录
function selectForm(type) {
    if (type == 0) {

        $("#AccountLogin").addClass("selected");
        $("#AccountLoginA").addClass("selectedA");
        $("#AccountForm").css({
            display: "block"
        });
        $("#CodeLogin").removeClass("selected");
        $("#CodeLoginA").removeClass("selectedA");
        $("#CodeForm").css({
            display: "none"
        });

        Log_type = type;

    } else if (type == 1) {
        $("#AccountLogin").removeClass("selected");
        $("#AccountLoginA").removeClass("selectedA");
        $("#AccountForm").css({
            display: "none"
        });
        $("#CodeLogin").addClass("selected");
        $("#CodeLoginA").addClass("selectedA");
        $("#CodeForm").css({
            display: "block"
        });

        Log_type = type;
    }
}

$(function () {


});

//登录
function login() {

    if (Log_type == 0) {
        //账号密码登录
        var username = $("#username").val();
        var password = $("#password").val();

        $.post("user/login", {username: username, password: password}, function (data) {

            if (data[1] == "error") {
                $("#show_error").text("您提供的用户名或密码有误");
            } else if (data[1] == "pass") {
                window.top.location.href = "index.html";
            }

        });

    } else {
        //验证码登录

        var email = $("#phone_email").val();
        var dynamicCode = $("#dynamicCode").val();

        $.post("user/findByEmail", {email: email}, function (data) {

            if (data[1] == "error") {
                $("#show_error").text("该账户不存在");
            } else if (data[1] == "pass") {
                //找到账户了
                //判断动态码是否正确

                var username = data[0];

                $.post("user/checkDynamicCode", {dynamicCode: dynamicCode,username:username}, function (data) {

                    if (data[1] == "error") {
                        $("#show_error").text("邮箱验证码错误");
                    } else if (data[1] == "pass") {
                        //动态码正确,允许登录
                        window.top.location.href = "index.html";
                    }
                });


            }
        });

    }
}

//刷新验证码
function refreshCode() {
    document.getElementById("captchaImg").onclick = function () {
        this.src = "user/checkCode?time=" + new Date().getTime();
        $("#show_error").text("");
    }

}

//校验邮箱
function checkEmail(Email) {
    //1.获取邮箱
    var email = Email;
    console.log("haha"+email);
    //2.定义正则		itcast@163.com	\w+字符最少出现一次  \.转义代表"."
    var reg_email = /^\w+@\w+\.\w+$/;

    //3.判断
    var flag = reg_email.test(email);
    if (flag) {

    } else {
        $("#show_error").text("邮箱格式错误");
    }

    return flag;
}

//检验图形验证码和发送邮件
function checkCaptchaAndSendEmail() {

    var captcha = $("#captcha").val();

    $.post("user/checkCaptcha", {captcha: captcha}, function (data) {

        if (data[1] == "error") {
            $("#show_error").text("图形验证码错误,请刷新");
        } else if (data[1] == "pass") {
            sendEmail($("#phone_email").val());
        }

    });
}

//发送邮件
function sendEmail(Email) {

    if (checkEmail(Email)) {

        $.post("user/findByEmail", {email: Email}, function (data) {

            if (data[1] == "error") {
                $("#show_error").text("该账户不存在");
            } else if (data[1] == "pass") {
                //格式正确且找到账户了
                //发送邮件
                $("#show_error").text("邮件验证码已发送");
                $.post("user/sendEmail", {email: Email}, function () {

                });

            }

        });
    }
}

//发送邮件，但不检查是否在数据库中
function sendEmail_withoutFind(Email) {

    console.log("start");
    if (checkEmail(Email)) {
        $("#show_error").text("邮件验证码已发送");

        $.post("user/sendEmail", {email: Email}, function () {

        });
    }
}

