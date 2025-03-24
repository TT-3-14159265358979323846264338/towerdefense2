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
	List<List<Integer>> compositionList = new ArrayList<>();
	List<List<List<Integer>>> weaponStatusList = new ArrayList<>();
	List<List<Integer>> unitStatusList = new ArrayList<>();
	List<Integer> typeList = new ArrayList<>();
	
	public SaveComposition() {
		newComposition();
	}
	
	public SaveComposition(List<List<List<Integer>>> allCompositionList, List<String> compositionNameList, int selectNumber,  List<List<Integer>> compositionList, List<List<List<Integer>>> weaponStatusList, List<List<Integer>> unitStatusList, List<Integer> typeList) {
		this.allCompositionList = allCompositionList;
		this.compositionNameList = compositionNameList;
		this.selectNumber = selectNumber;
		this.compositionList = compositionList;
		this.weaponStatusList = weaponStatusList;
		this.unitStatusList = unitStatusList;
		this.typeList = typeList;
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
	
	public List<List<Integer>> getcompositionList(){
		return compositionList;
	}
	
	public List<List<List<Integer>>> getWeaponStatusList(){
		return weaponStatusList;
	}
	
	public List<List<Integer>> getUnitStatusList(){
		return unitStatusList;
	}
	
	public List<Integer> setTypeList(){
		return typeList;
	}
}