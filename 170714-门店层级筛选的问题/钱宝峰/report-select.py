import random
from collections import  defaultdict

# 生成2维数组，行列均为 3*10
n = 3
m = 10
matrix = [None] *m

for i in range(len(matrix)):
    matrix[i] = [random.randint(1,3),random.randint(1,3),random.randint(1,20)]

print("生成的二维数组为:")
print(matrix)

ahashDict = defaultdict(list)
for li in matrix:
    dictkey = li[0]
    dictvalue = li[1:]
    ahashDict[dictkey].append(dictvalue)


dict2 = defaultdict(list)
for secondli in ahashDict[dictkey]:
    seconddictkey = secondli[0]
    seconddictvalue = secondli[1:]
    dict2[seconddictkey].append(seconddictvalue)


ahashDict[dictkey] = dict2

print("转换之后的结果为:")
print(ahashDict)

