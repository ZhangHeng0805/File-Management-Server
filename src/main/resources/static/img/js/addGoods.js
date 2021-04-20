
setInterval(function(){
    var date = new Date();
    var year = date.getFullYear();    //获取当前年份
    var mon = date.getMonth()+1;      //获取当前月份   js从0开始取
    var da = date.getDate();          //获取当前日
    var day = date.getDay();          //获取当前星期几
    var h = date.getHours();          //获取小时
    var m = date.getMinutes();        //获取分钟
    var s = date.getSeconds();        //获取秒
    monthText = mon < 10 ? "0" + mon : mon
    daText = da < 10 ? "0" + da : da;
    hoursText = h < 10 ? "0" + h : h;
    minutesText = m < 10 ? "0" + m : m;
    secondText = s < 10 ? "0" + s : s;
    var d=document.addGoodsFrom.time;
    d.value=year+'年'+monthText+'月'+daText+'日'/*+' '+hoursText+':'+minutesText+':'+secondText*/;
    //d.value=date.toLocaleString()+' 星期' + '日一二三四五六'.charAt(day);
},1000);

function flag() {
    var s_name=document.addGoodsFrom.store_name.value;
    var s_intro=document.addGoodsFrom.goods_introduction.value;
    var s_price=document.addGoodsFrom.goods_price.value;
if (s_name.length>0&&s_name.length<=15){
    if (s_intro.length>0&&s_intro.length<=30){
        if (s_price.length>0){
            if (s_price<999999999) {
                return true
            }else {
                alert("商品价格最高999999999");
                return false;
            }
        } else {
            alert("商品价格不能为空");
            return false;
        }
    } else {
        alert("商品介绍字数限制1~30");
        return false;
    }
} else {
    alert("商品名字数限制1~15");
    return false;
}
}