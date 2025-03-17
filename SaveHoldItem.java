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
	
	public SaveHoldItem() {
		coreNumberList = Arrays.asList(8, 0, 0, 0, 0, 0);
		weaponNumberList = Arrays.asList(2, 2);
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
