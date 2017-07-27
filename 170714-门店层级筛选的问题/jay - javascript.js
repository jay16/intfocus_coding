/*
 * 代码逻辑同 `解决方案 - jay - ruby.rb`
 */
Array.prototype.flatten = function() {
  var ret = [];
  for(var i = 0; i < this.length; i++) {
    if(Array.isArray(this[i])) {
      ret = ret.concat(this[i].flatten());
    } else {
      ret.push(this[i]);
    }
  }
  return ret;
};

window.StoreChoice = {
  rand_array: function() {
    var array = [];
    for(var i = 0, len = parseInt(Math.random() * 10) + 3; i < len; i ++) {
      array.push(parseInt(Math.random() * 10))
    }
    return array.sort(function(a, b) { return a - b; });
  },
  rand_datas: function() {
    var array = [];
    for(var i = 0, len = parseInt(Math.random() * 10) + 3; i < len; i ++) {
      array.push(window.StoreChoice.rand_array());
    }
    return array.sort(function(a, b) { return a.join(',').localeCompare(b.join(',')) });
  },
  middleware: function(items) {
    var constructed_items = [], constructed_item, item, is_find;
    for(var i = 0, len = items.length; i < len; i++) {
      item = items[i];
      is_find = false;
      for(var j = 0, jlen = constructed_items.length; j < jlen; j ++) {
        constructed_item = constructed_items[j];
        if(constructed_item['titles'] === item[0]) {
          is_find = true;
          item.shift();
          constructed_item['infos'].push([item].flatten());
        }
      }
      if(!is_find) { constructed_items.push({titles: item.shift(), infos: [item]}); }
    }
    return constructed_items;
  },
  convert_with_recursion: function(constructed_items) {
    return constructed_items.map(function(constructed_item) {
      if(constructed_item['infos'].flatten().length === 0) {
        constructed_item['infos'] = [];
      } else {
        constructed_item['infos'] = window.StoreChoice.middleware(constructed_item['infos']);
        constructed_item['infos'] = window.StoreChoice.convert_with_recursion(constructed_item['infos']);
      }
      return constructed_item
    });
  }
}

var rand_datas = window.StoreChoice.rand_datas(),
    constructed_items = window.StoreChoice.middleware(rand_datas);
constructed_items = window.StoreChoice.convert_with_recursion(constructed_items);
console.log(JSON.stringify(rand_datas));
console.log(JSON.stringify(constructed_items));
