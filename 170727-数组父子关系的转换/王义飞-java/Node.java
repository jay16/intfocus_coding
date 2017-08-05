package com.sy.testShift;

import java.util.ArrayList;
import java.util.List;
public class Node{
	private List<Object> mainData = new ArrayList<Object>();
	private List<Node> subData ;
	public List<Object> getMainData() {
		return mainData;
	}
	public void setMainData(List<Object> mainData) {
		this.mainData = mainData;
	}
	public List<Node> getSubData() {
		return subData;
	}
	public void setSubData(List<Node> subData) {
		this.subData = subData;
	}
}
