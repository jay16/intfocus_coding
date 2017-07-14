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

def middleware(items)
  constructed_items = []
  items.each do |item|
    is_find = false
    constructed_items.each do |obj|
      next if obj[:titles] != item[0]
      is_find = true
      obj[:infos].push(item[1..-1])
    end
    constructed_items.push({titles: item[0], infos: [item[1..-1]]}) if !is_find
  end
  constructed_items
end

def convert_with_recursion(constructed_items)
  constructed_items.map do |constructed_item|
    if constructed_item[:infos].flatten.empty?
      constructed_item[:infos] = []
    else
      constructed_item[:infos] = convert_with_recursion(middleware(constructed_item[:infos]))
    end
    constructed_item
  end
end

rand_datas = Array.new(rand(5) + 3) { Array.new(rand(5) + 3) { rand(10) }}.map(&:sort).sort.uniq
puts rand_datas.to_s
puts convert_with_recursion(middleware(rand_datas)).to_json
