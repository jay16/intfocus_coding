#encoding: utf-8
#
# $ ruby 170714\ -\ 门店层级筛选的问题/解决方案\ -\ jay\ -\ ruby.rb
#
# 解决思路：
#
# step 1: 中间件数据结构
#
# {
#   title: title, // parent item
#   info: [] // childre_array
# }
#
# step 2: 数据结果转换思路
#
# step 2.1: 中间件集合
#
# []
#
# step 2.2: 遍历中间件集合查找二维数组的首个元素，有则放至子节点集合，无则创建中件间
#
# [
#   [1, 2, 3, 4, 5],
#   [1, 2, 4, 5, 6],
#   [0, 1, 2]
# ]
#
# =>
#
# [
#   [1, [2, 3, 4, 5]],
#   [1, [2, 4, 5, 6]],
#   [0, [1, 2]]
# ]
#
# =>
#
# [
#   {
#     title: 1,
#     infos: [
#       [2, 3, 4, 5]
#     ]
#   }
# ]
#
# =>
#
# [
#   {
#     title: 1,
#     infos: [
#       [2, 3, 4, 5],
#       [2, 4, 5, 6]
#     ]
#   }
# ]
#
# =>
#
# [
#   {
#     title: 1,
#     infos: [
#       [2, 3, 4, 5],
#       [2, 4, 5, 6]
#     ]
#   },
#   {
#     title: 0,
#     infos: [
#       [1, 2]
#     ]
#   }
# ]
#
# step 3: 遍历 & 递归
#
require 'json'

def middleware(two_dim_items)
  two_dim_items.each_with_object([]) do |dim_items, constructed_items|
    if constructed_item = constructed_items.find { |h| h[:titles] == dim_items.first }
      constructed_item[:infos].push(dim_items[1..-1])
    else
      constructed_items.push({titles: dim_items[0], infos: [dim_items[1..-1]]})
    end
  end
end

def convert_with_recursion(constructed_items)
  constructed_items.map do |constructed_item|
    constructed_item[:infos] = (constructed_item[:infos].flatten.empty? ? [] : convert_with_recursion(middleware(constructed_item[:infos])))
    constructed_item
  end
end

rand_datas = Array.new(rand(5) + 3) { Array.new(rand(5) + 3) { rand(10) }}.map(&:sort).sort.uniq
puts rand_datas.to_s
puts convert_with_recursion(middleware(rand_datas)).to_json
