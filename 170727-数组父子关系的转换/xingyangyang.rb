require 'json' #调用selfson

data = [
  ['自身ID1', '', '标题1', 1, 3, 2],
  ['自身ID2', '', '标题2', 5, 4, 6],
  ['自身ID11', '自身ID1', '标题3', 7, 8, 9],
  ['自身ID21', '自身ID2', '标题4', 10, 11, 12],
  ['自身ID22', '自身ID2', '标题5', 13, 15, 14],
  ['自身ID111', '自身ID11', '标题6', 16, 17, 18],
  ['自身ID211', '自身ID21', '标题7', 19, 20, 21],
  ['自身ID2111', '自身ID211', '标题8', 22, 23, 24]
]


def convert(data)

  data=data.sort
    order = []
  data.each do |i|
    i =  i[0..2]+i[3..-1].sort
    order+= [i]
  end                           #以上，将数字进行由小到大排序,得到排序后的数据order

  result = []                  #建立数组result用来存储最终输出的结果
  pattern = /\d+/              #匹配0-9中出现一次或一次以上的数字
  order.each do |i|
      if !i[1].empty?
        parent_id = pattern.match(i[1]).to_s  #捞出所有非空ID的父节点,返回一个对象
      end
      self_id = pattern.match(i[0])[0]     #把遍历的每一个一维数组作为自身来处理，捞出自身id


      if parent_id.nil?                    #判断父节点是否是存在（因为match返回的是对象，match("")返回的是nil，因此这里用nil作为判断条件
        result[self_id.to_i - 1] =  {"main_data": i[2..-1], "sub_data": []}  #如果父节点为空，则将自身后四项内容写到数组result中,其中符合条件的只有ID1和ID2两个节点
      else
        parent_index = parent_id[0].to_i   #记录二叉树中最高的父节点，以此来区分接下来的内容是写入result数组中的第一项还是第二项
        add_self_into_parent(result[parent_index-1][:sub_data],parent_id[1..-1],self_id[-1].to_i,i[2..-1]) #将自身内容加入到父节点中

      end
    end
    puts  JSON.pretty_unparse(result)   #使用json的pretty_unparse模板进行代码输出的美化

  end




  def add_self_into_parent(result,parent_id,self_id,content)   #建立递归写入数据的方法
    if parent_id.empty?                            #如果父节点为空，则将main_data内容设置为自身后四项内容,sub_data设为空数组
      result[self_id-1] = {"main_data": content, "sub_data": []}
    else
      index = parent_id[0].to_i                    #否则，递归向上寻找父节点，将自身插入到父节点下正确的位置
      add_self_into_parent(result[index-1][:sub_data],parent_id[1..-1],self_id,content)
    end
  end



   puts"#{convert(data)}"
