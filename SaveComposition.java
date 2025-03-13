package savecomposition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//現在の編成状況の保存用
public class SaveComposition implements Serializable{
	public transient final static String COMPOSITION_FILE = "composition data.dat";
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	List<List<Integer>> selectCompositionList = new ArrayList<>();
	
	public SaveComposition() {
		allCompositionList = Arrays.asList(Arrays.asList(
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1),
				Arrays.asList(-1, 0, -1)));
		compositionNameList.add("編成 1");
		selectCompositionList = new ArrayList<>(allCompositionList.get(0));
	}
	
	public SaveComposition(List<List<List<Integer>>> allCompositionList, List<String> compositionNameList, List<List<Integer>> selectCompositionList) {
		this.allCompositionList = allCompositionList;
		this.compositionNameList = compositionNameList;
		this.selectCompositionList = selectCompositionList;
	}
	
	public List<List<List<Integer>>> getAllCompositionList(){
		return allCompositionList;
	}
	
	public List<String> getCompositionModel(){
		return compositionNameList;
	}
	
	public List<List<Integer>> getSelectCompositionList(){
		return selectCompositionList;
	}
}