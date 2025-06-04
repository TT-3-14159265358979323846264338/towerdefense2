package saveholditem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//現在保有しているアイテムの保存用
public class SaveHoldItem implements Serializable{
	public transient final static String HOLD_FILE = "hold data.dat";
	List<Integer> coreNumberList = new ArrayList<>();
	List<Integer> weaponNumberList = new ArrayList<>();
	
	/*
	非セーブデータの構造
	HOLD_FILE: セーブデータ保存ファイルの名称
	
	セーブデータの構造
	coreNumberList: 各コアの所持数
	weaponNumberList: 各武器の所持数
	これらのListのsize()は、DataUnitに新規追加されると、TowerDefense2で自動的に追加される
	*/
	
	public SaveHoldItem() {
		coreNumberList = new ArrayList<>(Arrays.asList(8));
		weaponNumberList = new ArrayList<>(Arrays.asList(2, 2));
	}
	
	public SaveHoldItem(List<Integer> coreNumberList, List<Integer> weaponNumberList) {
		this.coreNumberList = coreNumberList;
		this.weaponNumberList = weaponNumberList;
	}
	
	public List<Integer> getCoreNumberList(){
		return coreNumberList;
	}
	
	public List<Integer> getWeaponNumberList(){
		return weaponNumberList;
	}
}
