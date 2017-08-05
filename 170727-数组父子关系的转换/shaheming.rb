require 'json'
rawData = [
  ['自身ID1', '', '标题1', 1, 2, 3],
  ['自身ID2', '', '标题2', 4, 5, 6],
  ['自身ID11', '自身ID1', '标题3', 7, 8, 9],
  ['自身ID21', '自身ID2', '标题4', 10, 11, 12],
  ['自身ID22', '自身ID2', '标题5', 13, 14, 15],
  ['自身ID111', '自身ID11', '标题6', 16, 17, 18],
  ['自身ID211', '自身ID21', '标题7', 19, 20, 21],
  ['自身ID2111', '自身ID211', '标题8', 22, 23, 24]
]


dataTree = [
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
    "main_data": ["标题2",4,5,6],
    "sub_data": [
      {
        "main_data": ["标题4",10,11,12],
        "sub_data": [
          {
            "main_data": ["标题7",19,20,21],
            "sub_data": [
              {
                "main_data": ["标题8",22,23,24],
                "sub_data": []
              }
            ]
          }
        ]
      },
      {
        "main_data": ["标题5",13,14,15],
        "sub_data": []
      }
    ]
  }
]

def generateTree(rawData,dataTree)
  #to ensure the items in the rawData is ordered by the tree level
  rawData.sort{|x,y|x[0]<=>y[0]}
  
  outTree = []
  pattern = /\d+/
  rawData.each do |item|
    parent_id = pattern.match(item[1]).nil? ? nil :  pattern.match(item[1])[0]
    item_id = pattern.match(item[0])[0]
    if parent_id.nil?
      outTree[item_id.to_i - 1] =  {"main_data": item[2..-1], "sub_data": []}
    else
      parent_pos = parent_id[0].to_i
      addChild(outTree[parent_pos-1][:sub_data],parent_id[1..-1],item_id[-1].to_i,item[2..-1])
    end
  end
  puts  JSON.pretty_unparse(outTree)
  
  compateTree(outTree,dataTree)
end




def addChild(outTree,parent_id,item_id,content)
  if parent_id.empty?
    outTree[item_id-1] = {"main_data": content, "sub_data": []}
  else
    pos = parent_id[0].to_i
    addChild(outTree[pos-1][:sub_data],parent_id[1..-1],item_id,content)
  end
end


def compateTree(outTree,dataTree)
  if outTree == dataTree
    puts "The two trees are same"
  else
    puts "The two trees are not same"
  end
end

generateTree(rawData,dataTree)


