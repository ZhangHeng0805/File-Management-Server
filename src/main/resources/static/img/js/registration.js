
function submit() {
    var t=5;
    var i;
    var login=document.forms["Form"]["submit"]
    login.disabled="disabled";
    function run() {
        if (t>0) {
            login.value=t+"秒后可操作";
            t--;
        }else {
            login.disabled="";
            clearInterval(i);
            login.value="再次注册";
        }
    }
    i=setInterval(run,1000);
}

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
    var d=document.forms["Form"]["time"];
    d.value=year+'年'+monthText+'月'+daText+'日'+' '+hoursText+':'+minutesText+':'+secondText;
    //d.value=date.toLocaleString()+' 星期' + '日一二三四五六'.charAt(day);
},1000);

function isb() {

    // var name=document.forms["Form"]["name"].value
    var name=document.Form.name.value;
    var phonenum=document.Form.phonenum.value;
    // var gender=document.forms["Form"]["gender"].value;
    var gender=document.Form.gender.value;
    var email=document.forms["Form"]["email"].value;
    var account=document.forms["Form"]["account"].value;
    var password=document.forms["Form"]["password"].value;
    var password1=document.forms["Form"]["password1"].value;
    var store_name=document.forms["Form"]["store_name"].value;
    var address=document.forms["Form"]["address"].value;
    var store_introduce=document.forms["Form"]["store_introduce"].value;
if (phonenum.length==11) {
    if (name.length >= 1 && name.length <= 10) {
        if (email.length >= 5 && email.length <= 30) {
            if (account.length >= 1 && account.length <= 18) {
                if (password.length >= 6 && password.length <= 20) {
                    if (password1 == password) {
                        if (store_name.length >= 1 && store_name.length <= 15) {
                            if (address.length >= 2 && address.length <= 20) {
                                if (store_introduce.length >= 1 && store_introduce.length <= 100) {
                                    alert("提交注册中。。。");
                                    return true;
                                } else {
                                    alert("商铺介绍长度范围：1~100");
                                    return false;
                                }
                            } else {
                                alert("商铺地址长度范围：2~20");
                                return false;
                            }
                        } else {
                            alert("商铺名称长度范围：1~15");
                            return false;
                        }
                    } else {
                        alert("输入的两次密码不一致");
                        document.forms["Form"]["password"].value = "";
                        return false;
                    }
                } else {
                    alert("密码长度范围：6~20");
                    return false;
                }
            } else {
                alert("用户名长度范围：1~18");
                return false;
            }
        } else {
            alert("邮箱长度范围：5~30");
            return false;
        }
    } else {
        alert("姓名长度范围：1~10");
        return false;
    }
}else {
    alert("电话号码长度为11位");
    return false;
}
}