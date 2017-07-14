/**
 * Created by qianbaofeng on 2017/7/13.
 */

// 随机生成一个二维数组

function randomFunc() {
    return Math.floor((Math.random()*4))
}

var Arr = []

for (var i = 0;i<10;i++){
    var insideArr = []
    for (j=0; j<3;j++){
        var bb = randomFunc();
        insideArr.push(bb)
    }
    Arr.push(insideArr)
}

console.log(Arr)


// 使用递归方式将二维数组转 json
// 算法思路: 从后往前一次转换数组成对象，并添加到前一个数组的元素，直到第数组的第一个元素
var ajson = {};
getJsonFromArray(0,Arr);
function getJsonFromArray(n,alist) {
    if(n==0){
        for (var i =0;i<alist.length;i++) {
            if (ajson[alist[0]]) {
                console.log("对象中已经有该键了");
            }
            else {
                console.log("对象中没有这个键");
            }
        }
    }
    else {
        for (var j=0;j<alist.length;j++)
        {
            getJsonFromArray(1,alist[j].slice(1,alist.length))
        }
    }
}
