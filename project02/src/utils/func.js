//防抖
var debounce=(func,delay)=>{
    var timer = null;
    return function() {
        var context = this;  //这里的this指向func对象
        var args = arguments;
        if (timer) {
            clearTimeout(timer)
        }
        timer = setTimeout(function() {//timer只要计时器存在就会为1
            func.apply(context, args);
            timer = null;//这里我认为是没必要给timer赋值为null作用域都不一样
        }, delay);
    }
}
export default debounce;
