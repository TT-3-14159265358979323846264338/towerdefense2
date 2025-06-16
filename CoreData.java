package defaultdata.core;

import java.util.List;

public abstract class CoreData {
	//コアの名前
	public abstract String getName();
	
	//通常時のコア画像ファイル名
	public abstract String getImageName();
	
	//攻撃時のコア画像ファイル名
	public abstract String getActionImageName();
	
	//レアリティ
	public abstract int getRarity();
	
	//DefaultUnit.CORE_WEAPON_MAPの順にステータスをリスト化
	public abstract List<Double> getWeaponStatus();
	
	//DefaultUnit.CORE_UNIT_MAPの順にステータスをリスト化
	public abstract List<Double> getUnitStatus();
	
	//DefaultUnit.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
