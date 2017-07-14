package intfocus.test.storelevel;

import java.util.HashMap;
import java.util.Map;

public class StoreLevel {

	
	
	public static void main(String[] args) {
		new StoreLevel().testStore();
	}
	
	@SuppressWarnings("unchecked")
	public void testStore(){
		String[] oneDimensional = {
				"商行||华北||龙华西路店", 
				"商行||华北||龙华北路店",
				"农行||华东||龙华北路店",
				"工行||浦东||金桥||枣庄路||久金广场店",
				"农行||总店"
		};
		
		Map<String , Object> root  = new HashMap<String , Object>();
		int maxColumnIndex = 0 ;
		int maxColumnIndexTmp = 0 ;
		
		for(int k =0 ;k < oneDimensional.length;k++){
			maxColumnIndexTmp = oneDimensional[k].split("\\|\\|").length;
			
			if(maxColumnIndex <= maxColumnIndexTmp){
				maxColumnIndex = maxColumnIndexTmp;
			}
			
		}
		
		String[][] twoDimensional = new String[oneDimensional.length][maxColumnIndex] ;
		String swp[] = new String[maxColumnIndex];
		for(int i = 0 ; i < oneDimensional.length ; i++){
			swp = oneDimensional[i].split("\\|\\|");
			for(int j =0 ; j < swp.length;j++){
				twoDimensional[i][j] = swp[j];
			}
		}
		Map<String , Object> parentMap = null ;
		Map<String , Object> parentMapTmp = null ;
		String parentValue = "";
//		"华北||商行||龙华西路店",
//		"华北||商行||龙华北路店",
		for(int i = 0 ; i < twoDimensional.length ; i ++){
			parentMap = null ;
			for(int j = 0 ; j < twoDimensional[i].length; j ++){

				if( null == parentMap){
					// parentMap 为空说明是第一层遍历是森林中每棵树的根节点
					if(!root.containsKey(twoDimensional[i][j])){
						root.put(twoDimensional[i][j], null);
					}
					parentValue = twoDimensional[i][j];
					parentMap = root;
				}else{ 
					if(null == parentMap.get(parentValue)){
						// n 节点下没有孩子的情况
						parentMapTmp = new HashMap<String,Object>();
						parentMapTmp.put(twoDimensional[i][j], null);
						parentMap.put(parentValue, parentMapTmp);
						parentValue = twoDimensional[i][j];
						parentMap = parentMapTmp;
					}else{
						// n 节点下有孩子的情况
						parentMap = (HashMap<String,Object>)parentMap.get(parentValue);
						parentValue = twoDimensional[i][j];
						if(!parentMap.containsKey(parentValue)){
							parentMap.put(parentValue,null);
						}
					}
				}
				
			}
		}
		
		System.out.println("------------森林结构-----------");
		System.out.println(root);
		//输出结果
		/*
		[["商行","华北","龙华西路店"],["商行","华北","龙华北路店"],["农行","华东","龙华北路店"],["工行","浦东","金桥路店"],["农行","总店",null]]
{农行={总店={null=null}, 华东={龙华北路店=null}}, 商行={华北={龙华北路店=null, 龙华西路店=null}}, 工行={浦东={金桥路店=null}}}
		 */
		
	}
}
