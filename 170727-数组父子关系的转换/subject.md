## 场景

这又是项目中一个真实存在的场景。

报表中展示列表时经常会出现层级下钻，由于报表框架设计的局限性，下钻时并非再次发起新的数据请求，而是一次性下载完报表需要的所有数据，那么如何做到数据列表下钻时的父子关系？

H5 的报表解析引擎已使用 JS 实现，具体实现方式可再开新题交流；现使用移动端原生代码重写报表控件，数据的父子关系则必须提交处理，那么本次竞赛的题来了：

## 数据结构转化逻辑

- 第二维数组结构：**[自身ID, 父节点ID, 列表数组]**
- 前两个数据项（自身ID/父节点ID）只是用来处理父子关系，不作展示
- **第一层级的数据父节点ID 为空字符串**

```
[
  ['自身ID1', '', '标题1', 1, 2, 3],
  ['自身ID2', '', '标题2', 4, 5, 6],
  ['自身ID11', '自身ID1', '标题3', 7, 8, 9],
  ['自身ID21', '自身ID2', '标题4', 10, 11, 12],
  ['自身ID22', '自身ID2', '标题5', 13, 14, 15],
  ['自身ID111', '自身ID11', '标题6', 16, 17, 18],
  ['自身ID211', '自身ID21', '标题7', 19, 20, 21],
  ['自身ID2111', '自身ID211', '标题8', 22, 23, 24]
]

=>

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
```

## 需求

1. 把上述二维数组转化为预期数据结构
2. 在使用职位编程语言实现的基础上，使用其他语言提供解决方案加分（多多益善）
3. **题目相对简单，不可重复他人相同编程语言的相同解决方案，以 github 提交时间为准**

## 其他

- 奖金 1000
- 代码提交至：[IntfocusCoding](https://github.com/jay16/intfocus_coding)
- 考虑到近期工作较为饱和，截止时间下周六（17/08/05）
- 编写代码时遵循团队内部的编程规范
- 源代码文件名称格式
    - 单文件: `提交者-编程语言.语言后缀`
    - 项目文件夹：`提交者-编程语言`

- 鼓励封装代码逻辑为单一函数或类，调用接口传参，运行输出预期结果
- 整理文档时遵循团队内部 markdown 规范
- 提交时遵循团队内部 commit 格式
- 在职位语言实现基础上，鼓励使用多种语言实现
