# encoding: utf-8
# 
# usage: ruby jay-ruby.rb
# 
require 'json'

def convert_table_data_with_level(data, parent_node = "")
  level_1_data = data.select { |arr| arr[1] == parent_node }
  data -= level_1_data
  level_1_data.map do |arr|
    {
      main_data: arr[2..-1],
      sub_data: convert_table_data_with_level(data, arr[0])
    }
  end
end

data = [
  ['自身ID1', '', '标题1', 1, 2, 3],
  ['自身ID2', '', '标题2', 4, 5, 6],
  ['自身ID11', '自身ID1', '标题3', 7, 8, 9],
  ['自身ID21', '自身ID2', '标题4', 10, 11, 12],
  ['自身ID22', '自身ID2', '标题5', 13, 14, 15],
  ['自身ID111', '自身ID11', '标题6', 16, 17, 18],
  ['自身ID211', '自身ID21', '标题7', 19, 20, 21],
  ['自身ID2111', '自身ID211', '标题8', 22, 23, 24]
]
data_with_level = convert_table_data_with_level(data, "")

puts data_with_level.to_json
# output:
# [
#   {
#     "main_data": ["标题1", 1, 2, 3],
#     "sub_data": [
#       {
#         "main_data": ["标题3", 7, 8, 9],
#         "sub_data": [
#           {
#             "main_data": ["标题6", 16, 17, 18],
#             "sub_data": []
#           }
#         ]
#       }
#     ]
#   },
#   {
#     "main_data": ["标题2", 4, 5, 6],
#     "sub_data": [
#       {
#         "main_data": ["标题4", 10, 11, 12],
#         "sub_data": [
#           {
#             "main_data": ["标题7", 19, 20, 21],
#             "sub_data": [
#               {
#                 "main_data": ["标题8", 22, 23, 24],
#                 "sub_data": []
#               }
#             ]
#           }
#         ]
#       },
#       {
#         "main_data": ["标题5", 13, 14, 15],
#         "sub_data": []
#       }
#     ]
#   }
# ]