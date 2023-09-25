// 轮播
var index = 0;

var img_src = ['imgs/20220923qiufen.jpg', 'imgs/20220826kaixueji.png',
    'imgs/20220331fangyi.jpg', 'imgs/jieyuebanner20210422.jpg'];

//效果
function ChangeImg() {
    index++;
    var cur_img = document.getElementById("banner_img");
    var li = document.getElementById("btn_ul").getElementsByTagName('li');

    // 延迟 删除动画类 disappear appear
    setTimeout(function () {
        cur_img.classList.remove("disappear");
        cur_img.classList.remove("appear");
    }, 2000);


    if (index >= img_src.length) {
        index = 0;
    }
    for (let i = 0; i < img_src.length; i++) {
        li[i].classList.remove("active");
    }
    li[index].classList.add("active");

    // 增加动画类 disappear appear
    cur_img.classList.add("disappear");
    cur_img.src = img_src[index];
    cur_img.classList.add("appear");

}

//设置定时器，每隔两秒切换一张图片
var timer = setInterval(ChangeImg, 5000);

//按钮
function Click_active(j) {

    index = j - 1;
    ChangeImg();
    // 切换重新计时
    clearInterval(timer);
    timer = setInterval(ChangeImg, 5000);
}

//鼠标移入，清除定时器
function mouse_hover() {
    clearInterval(timer);
}

//鼠标移出，重新设置定时器
function mouse_out() {
    timer = setInterval(ChangeImg, 5000);
}



