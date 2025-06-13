package defaultdata.weapon;

import java.util.List;

public abstract class WeaponData {
	//武器の名前
	public abstract String getWeaponName();
	
	//通常時の武器画像ファイル名
	public abstract String getWeaponImageName();
	
	//攻撃時の武器画像ファイル名
	//片手武器の時は右武器のlistにEmptyのlistを入れる
	public abstract List<String> getRightWeaponActionImageName();
	public abstract List<String> getLeftWeaponActionImageName();
	
	//レアリティ
	public abstract int getRarity();
	
	//武器タイプは ① 距離(DataUnit.DISTANCE_MAP) ② 装備位置(DataUnit.HANDLE_MAP)で登録
	public abstract List<Integer> getType();
	
	//武器属性はその武器の全ての属性(DataUnit.ELEMENT_MAP)を登録
	public abstract List<Integer> getElement();
	
	//DataUnit.WEAPON_WEAPON_MAPの順にステータスをリスト化
	public abstract List<Integer> getWeaponStatus();
	
	//DataUnit.WEAPON_UNIT_MAPの順にステータスをリスト化
	public abstract List<Integer> getUnitStatus();
	
	//DataUnit.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
