package defaultdata.core;

import java.util.List;

public abstract class CoreData {
	//コアの名前
	public abstract String getCoreName();
	
	//通常時のコア画像ファイル名
	public abstract String getCoreImageName();
	
	//攻撃時のコア画像ファイル名
	public abstract String getCoreActionImageName();
	
	//レアリティ
	public abstract int getRarity();
	
	//DataUnit.CORE_WEAPON_MAPの順にステータスをリスト化
	public abstract List<Double> getWeaponStatus();
	
	//DataUnit.CORE_UNIT_MAPの順にステータスをリスト化
	public abstract List<Double> getUnitStatus();
	
	//DataUnit.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
