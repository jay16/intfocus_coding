import java.util.HashMap;
import java.util.Map;

public class StoreLevel {

	public static void main(String[] args) {
		
		StoreLevel sl = new StoreLevel();
		Integer[][] is = sl.generatorDimensional();
		Integer[] two = null ;
		System.out.println("随机生成的二维数组如下：");

		for(int i = 0 ; i < is.length;i++ ){
			two = is[i];
			for(int j= 0;j<two.length;j++){
				if(null != is[i][j])
				System.out.print(is[i][j]+",");
			}
			System.out.println("");
		}
		
		sl.testStore(is);
	}
	
	public Integer[][] generatorDimensional(){
		Integer[][] twoDim = new Integer[3 + (new Random().nextInt(10))][100];
		Integer[] sec = null;
		int random = 0 ;

		for(int i = 0 ; i < twoDim.length ; i++){
			sec = twoDim[i];
			random = new Random().nextInt(10) + 3;
			for(int j =0 ; j < random ; j++){
				twoDim[i][j] = new Random().nextInt(10);
			}
		}

		return twoDim ;
	}
	
	@SuppressWarnings("unchecked")
	public void testStore(Integer[][] twoDimensional){

		Map<Integer , Object> root = new HashMap<Integer,Object>() ;
		Map<Integer , Object> parentMap = null ;
		Map<Integer , Object> parentMapTmp = null ;
		Integer parentValue = null;
		String tree = "" ;
		
		for(int i = 0 ; i < twoDimensional.length ; i ++){
			parentMap = null ;
			for(int j = 0 ; j < twoDimensional[i].length; j ++){
				if(null == twoDimensional[i][j]){
					continue;
				}
				if( null == parentMap){
					// parentMap 为空说明是第一层遍历是森林中每棵树的根节点
					if(!root.containsKey(twoDimensional[i][j])){
						root.put(twoDimensional[i][j], null);
					}
					parentValue = twoDimensional[i][j];
					parentMap = root;
				}else{
					if(null ==parentMap.get(parentValue)){
						// n 节点下没有孩子的情况
						parentMapTmp = new HashMap<Integer,Object>();
						parentMapTmp.put(twoDimensional[i][j], null);
						parentMap.put(parentValue, parentMapTmp);
						parentValue = twoDimensional[i][j];
						parentMap = parentMapTmp;
					}else{
						// n 节点下有孩子的情况
						parentMap = (HashMap<Integer,Object>)parentMap.get(parentValue);
						parentValue = twoDimensional[i][j];
						if(!parentMap.containsKey(parentValue)){
							parentMap.put(parentValue,null);
						}
					}
				  }
				}
			}
		System.out.println("------------森林结构-----------");
		tree = root.toString();
		System.out.println(tree.replace("=", ":"));
	}
}
