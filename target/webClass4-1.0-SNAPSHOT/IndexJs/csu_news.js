// 轮播
var index_new = 0;

//效果
function ChangeNew() {
    index_new++;
    var box = document.getElementsByClassName("new_box");

    if (index_new >= box.length) {
        index_new = 0;
    }
    for (var i = 0; i < box.length; i++) {
        box[i].style.display = 'none';
    }
    box[index_new].style.display = 'block';
}

//设置定时器，每隔两秒切换一张图片
var timer_news = setInterval(ChangeNew, 5000);
