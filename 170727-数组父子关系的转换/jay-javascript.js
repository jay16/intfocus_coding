function convert_table_data_with_level(data, parent_node = "") {
  var level_1_data = [], new_data = [];
	for(var i = 0, len = data.length; i < len; i ++) {
    data[i][1] === parent_node ? level_1_data.push(data[i]) : new_data.push(data[i]);
	}

	return level_1_data.map(function(arr) {
		parent_node = arr[0];
    return {
      main_data: arr.splice(2, arr.length - 2),
      sub_data: convert_table_data_with_level(new_data, parent_node)
    }
	});
}

var data = [
	  ['自身ID1', '', '标题1', 1, 2, 3],
	  ['自身ID2', '', '标题2', 4, 5, 6],
	  ['自身ID11', '自身ID1', '标题3', 7, 8, 9],
	  ['自身ID21', '自身ID2', '标题4', 10, 11, 12],
	  ['自身ID22', '自身ID2', '标题5', 13, 14, 15],
	  ['自身ID111', '自身ID11', '标题6', 16, 17, 18],
	  ['自身ID211', '自身ID21', '标题7', 19, 20, 21],
	  ['自身ID2111', '自身ID211', '标题8', 22, 23, 24]
	],
    data_with_level = convert_table_data_with_level(data, "");

console.log(JSON.stringify(data_with_level))
/*
// output:
[
  {
    "main_data": ["标题1", 1, 2, 3],
    "sub_data": [
      {
        "main_data": ["标题3", 7, 8, 9],
        "sub_data": [
          {
            "main_data": ["标题6", 16, 17, 18],
            "sub_data": []
          }
        ]
      }
    ]
  },
  {
    "main_data": ["标题2", 4, 5, 6],
    "sub_data": [
      {
        "main_data": ["标题4", 10, 11, 12],
        "sub_data": [
          {
            "main_data": ["标题7", 19, 20, 21],
            "sub_data": [
              {
                "main_data": ["标题8", 22, 23, 24],
                "sub_data": []
              }
            ]
          }
        ]
      },
      {
        "main_data": ["标题5", 13, 14, 15],
        "sub_data": []
      }
    ]
  }
]
*/