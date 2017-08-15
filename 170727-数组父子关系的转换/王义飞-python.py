#!/bin/env python
# coding: utf-8

import json

list1 = [
		  ['ID1', '', 't1', 1, 2, 3],
		  ['ID2', '', 't2', 4, 5, 6],
		  ['ID11', 'ID1', 't3', 7, 8, 9],
		  ['ID21', 'ID2', 't4', 10, 11, 12],
		  ['ID22', 'ID2', 't5', 13, 14, 15],
		  ['ID111', 'ID11', 't6', 16, 17, 18],
		  ['ID211', 'ID21', 't7', 19, 20, 21],
		  ['ID2111', 'ID211', 't8', 22, 23, 24]
		]


class node(object):
 	def __init__(self):
 		self.main_data = []
		self.sub_data = []

def getChildren(key):

	list = []
	if '' == key:
		for n in list1:
			if '' == n[1]:
				node1 = node()
				node1.main_data = n[2:5]
				node1.sub_data = getChildren(n[0])
				list.append(node1)
	else:
		for n in list1:
			if n[1] == key:
				node1 = node();
				node1.main_data = n[2:5]
				node1.sub_data = getChildren(n[0])
				list.append(node1)

	return list

json_str = json.dumps(getChildren(''), default=lambda o: o.__dict__, sort_keys=True, indent=4)

print json_str
