
window.addEventListener('scroll', function () {
    var right_back = document.getElementById("right_back");
    if (window.scrollY > 200) {
        right_back.style.display='block';
    } else {
        right_back.style.display='none';
    }
})

function Go_top() {
    // 获取到当前滚动过的距离
    var t = document.documentElement.scrollTop || document.body.scrollTop;
    // 通过定时器慢慢回到顶部
    var timerId = setInterval(function(){
        // 让上面获取到的滚动过的距离减小
        t -= 10;
        // 将减小后的数字设置给滚动过的距离
        document.documentElement.scrollTop = document.body.scrollTop = t;
        // 如果减小到0就让定时器停止
        if(t<=0){
            clearInterval(timerId)
        }
    })
}


