window.addEventListener('scroll', function () {
    var nav_up_left = document.getElementById("nav-up-left");
    var nav_up_right = document.getElementById("nav-up-right");
    var nav = document.getElementById("nav");
    var logo_img = document.getElementById("logo_img");

    if (window.scrollY > 0) {
        nav_up_left.style.display = "none";
        nav_up_right.style.display = "none";
        nav.classList.add("scrolled_nav");
        logo_img.classList.add("scrolled_img");
    } else {
        nav_up_left.style.display = "block";
        nav_up_right.style.display = "block";
        nav.classList.remove("scrolled_nav");
        logo_img.classList.remove("scrolled_img");
    }
})


$(function () {
//查询登录状态

    $.post("user/findLogInfo", {}, function (data) {
        if (data[1] == "error") {
            window.top.location.href = "login.html";
        } else if (data[1] == "pass") {
            document.getElementById("LogName").innerText = data[0];
        }

    })

});

function logout() {
    $.post("user/logout", {}, function (data) {

        alert("登出成功!");
        window.top.location.href = "login.html";

    })
}




