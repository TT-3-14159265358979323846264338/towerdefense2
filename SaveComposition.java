package savecomposition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//現在の編成状況の保存用
public class SaveComposition implements Serializable{
	public transient final static String COMPOSITION_FILE = "composition data.dat";
	public transient final static List<List<Integer>> DEFAULT = Arrays.asList(
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1),
			Arrays.asList(-1, 0, -1));
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	int selectNumber;
	List<List<Integer>> selectCompositionList = new ArrayList<>();
	
	public SaveComposition() {
		newComposition();
	}
	
	public SaveComposition(List<List<List<Integer>>> allCompositionList, List<String> compositionNameList, int selectNumber,  List<List<Integer>> selectCompositionList) {
		this.allCompositionList = allCompositionList;
		this.compositionNameList = compositionNameList;
		this.selectNumber = selectNumber;
		this.selectCompositionList = selectCompositionList;
	}
	
	public void newComposition() {
		allCompositionList.add(new ArrayList<>());
		for(List<Integer> i :DEFAULT) {
			allCompositionList.get(allCompositionList.size() - 1).add(new ArrayList<>(i));
		}
		compositionNameList.add("編成 " + allCompositionList.size());
		selectNumber = allCompositionList.size() - 1;
	}
	
	public void removeComposition(int number) {
		allCompositionList.remove(number);
		compositionNameList.remove(number);
		selectNumber = (number == 0)? 0: number - 1;
	}
	
	public List<List<List<Integer>>> getAllCompositionList(){
		return allCompositionList;
	}
	
	public List<String> getCompositionNameList(){
		return compositionNameList;
	}
	
	public int getSelectNumber() {
		return selectNumber;
	}
	
	public List<List<Integer>> getSelectCompositionList(){
		return selectCompositionList;
	}
}