var flat_state = 0 //未展开

function toFlat(){
    var button_toFlat = document.getElementById("flat_button");
    if(flat_state===0){
        button_toFlat.style.background = "url(./imgs/i-q21.png) no-repeat 0px 0px";
        button_toFlat.style.backgroundSize = "40px";
        flat_state = 1;

        var lis = document.getElementById("right_nav_ul").getElementsByTagName('li');
        for (let i = 0; i < lis.length; i++) {
            lis[i].classList.add("flat_on");
        }

        document.getElementById("right_nav").style.right = "-25px";

    }else if(flat_state===1){
        button_toFlat.style.background = "url(./imgs/i-q21-1.png) no-repeat 40px 0px";
        button_toFlat.style.backgroundSize = "40px";
        flat_state = 0;

        var lis = document.getElementById("right_nav_ul").getElementsByTagName('li');
        for (let i = 0; i < lis.length; i++) {
            lis[i].classList.remove("flat_on");
        }

        document.getElementById("right_nav").style.right = "-125px";
    }
}
