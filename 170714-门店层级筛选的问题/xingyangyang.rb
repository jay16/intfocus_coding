require 'json'

data=[
  '商行||华北||龙华西路店',
  '商行||华北||龙华北路店',
  '农行||华东||龙华北路店',
  '农行||总店'
]

arr = []

data.each do |item|
 item = item.split("||")
 arr += [item]
end

result = []


def add_children_to_parent(i,parent_node)
  child_node =  parent_node.select{ |j| j[:title] == i[0]}

  if !i.empty?

    if child_node.empty?

      parent_node << {
          :title => i[0],
          :items => add_children_to_parent(i[1..-1],[])
      }
    else

      add_children_to_parent(i[1..-1],child_node[0][:items])
    end
  else
    []
  end
end

arr.each do |i|
  add_children_to_parent(i,result)
end


puts  JSON.pretty_unparse(result)


#上述代码实现的思路：
#1.将原始的一维数组通过"||"分割成多个二维数组
#2.创建递归方法，将相应的内容插入到对应的节点
#3.将二维数组中的每一个一维数组进行遍历
#4.第一次调用递归方法时，传入的参数是第一个一维数组和空的result数组，此时经过条件判断确定result数组中的title和items，并且去掉一维数组的第一项元素
# 将该一维数组剩下的元素和新的result数组作为参数，调用递归方法，进行新一轮的条件判断，从而在相应的节点插入内容
#5.如果一维数组为空，则在items后面加入空数组[]
#6.当循环遍历完第一个一维数组后，接下来循环遍历下一个一维数组，重复上述步骤
