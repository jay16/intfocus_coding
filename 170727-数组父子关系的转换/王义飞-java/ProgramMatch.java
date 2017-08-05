package com.sy.testShift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
public class ProgramMatch {
	
	private static List<List<Object>> two = new	ArrayList<List<Object>>();
	
    public static void main(String[] args) {
    
		    List<Object> one = new ArrayList<Object>();
		    one.add("自身ID1");
		    one.add("");
		    one.add("标题1");
		    one.add(1);
		    one.add(2);
		    one.add(3);
		    two.add(one);
		    
		    one = new ArrayList<Object>();
		    
		    one.add("自身ID2");
		    one.add("");
		    one.add("标题2");
		    one.add(4);
		    one.add(5);
		    one.add(6);
		    two.add(one);	
		
		    one = new ArrayList<Object>();
			one.add("自身ID11");
			one.add("自身ID1");
			one.add("标题3");
			one.add(7);
			one.add(8);
			one.add(9);
			two.add(one);
			
			one = new ArrayList<Object>();
			one.add("自身ID21");
			one.add("自身ID2");
			one.add("标题4");
			one.add(10);
			one.add(11);
			one.add(12);
			two.add(one);
		
			one = new ArrayList<Object>();
			one.add("自身ID22");
			one.add("自身ID2");
			one.add("标题5");
			one.add(13);
			one.add(14);
			one.add(15);
			two.add(one);
		
			one = new ArrayList<Object>();
			one.add("自身ID111");
			one.add("自身ID11");
			one.add("标题6");
			one.add(16);
			one.add(17);
			one.add(18);
			two.add(one);
		
			one = new ArrayList<Object>();
			one.add("自身ID211");
			one.add("自身ID21");
			one.add("标题7");
			one.add(19);
			one.add(20);
			one.add(21);
			two.add(one);
		
			one = new ArrayList<Object>();
			one.add("自身ID2111");
			one.add("自身ID211");
			one.add("标题8");
			one.add(22);
			one.add(23);
			one.add(24);
			two.add(one);
	System.out.println(JSONObject.toJSONString(getChildren("")));
 }	
	
public static List<Node> getChildren(String key){
	
	Node n = null;
	List<Node> ns = new ArrayList<Node>();
	List<Object> one = null ;
	Iterator<List<Object>> it = null ;
	if("".equalsIgnoreCase(key)){
		it = two.iterator();
		while(it.hasNext()){
			 one = it.next();
			 if("".equalsIgnoreCase(one.get(1).toString())){
				 n = new Node();
		    	 n.setMainData(one.subList(2, 5));
		    	 n.setSubData(getChildren(one.get(0).toString()));
		    	 ns.add(n);
			 }
		}
	}else{
		it = two.iterator();
		while(it.hasNext()){
			one = it.next();
			if(key.equalsIgnoreCase(one.get(1).toString())){
			  n = new Node();
			  n.setMainData(one.subList(2,5));
			  n.setSubData(getChildren(one.get(0).toString()));
				ns.add(n);
			}	
		}
	}
	
	return ns;
}
	
}
