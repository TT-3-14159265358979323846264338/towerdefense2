package savedata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

//現在の編成状況の保存用
public class SaveComposition implements Serializable{
	public transient final static String COMPOSITION_FILE = "composition data.dat";
	public transient final static List<Integer> DEFAULT = Arrays.asList(-1, 0, -1);
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	int selectNumber;
	List<List<Integer>> compositionList = new ArrayList<>();
	List<List<List<Integer>>> weaponStatusList = new ArrayList<>();
	List<List<List<Integer>>> unitStatusList = new ArrayList<>();
	List<Integer> typeList = new ArrayList<>();
	
	/*
	非セーブデータの構造
	COMPOSITION_FILE: セーブデータ保存ファイルの名称
	DEFAULT: 新規に作成された編成のデフォルト設定 (-1: 装備無し, 0: 初期コア の番号)
	
	セーブデータの構造
	allCompositionList: ①List 編成番号 ②List パーティ8体のデータ ③List 右武器番号, コア番号, 左武器番号 の順でリスト化
	compositionNameList: 各編成番号の名称
	selectNumber: 現在使用可能な編成番号
	compositionList: 現在使用可能なパーティデータ (allCompositionListの②③を抜き出してある)
	weaponStatusList: 現在使用可能な武器ステータス (① 各ユニット順 ② 各ユニットデータ (list.get(i).get(0): 右武器属性リスト, list.get(i).get(1): 右武器ステータスリスト, list.get(i).get(2): 左武器属性リスト, list.get(i).get(3): 左武器ステータスリスト) の順でリスト化)
	unitStatusList: 現在使用可能なユニットステータス (① 各ユニット順 ② 各ユニットデータ (list.get(i).get(0): 属性カットリスト, list.get(i).get(1): ユニットステータスリスト) の順でリスト化)
	typeList: 現在使用可能なユニットの配置できるマスの種類
	*/
	
	public SaveComposition() {
		newComposition();
	}
	
	public SaveComposition(List<List<List<Integer>>> allCompositionList, List<String> compositionNameList, int selectNumber,  List<List<Integer>> compositionList, List<List<List<Integer>>> weaponStatusList, List<List<List<Integer>>> unitStatusList, List<Integer> typeList) {
		this.allCompositionList = allCompositionList;
		this.compositionNameList = compositionNameList;
		this.selectNumber = selectNumber;
		this.compositionList = compositionList;
		this.weaponStatusList = weaponStatusList;
		this.unitStatusList = unitStatusList;
		this.typeList = typeList;
	}
	
	public void newComposition() {
		allCompositionList.add(new ArrayList<>(IntStream.range(0, 8).mapToObj(i -> new ArrayList<>(DEFAULT)).toList()));
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
	
	public List<List<List<Integer>>> getUnitStatusList(){
		return unitStatusList;
	}
	
	public List<Integer> setTypeList(){
		return typeList;
	}
}