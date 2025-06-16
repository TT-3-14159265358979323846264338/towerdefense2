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
	
	//距離(DefaultUnit.DISTANCE_MAP)で登録
	public abstract int getDistance();
	
	//装備位置(DefaultUnit.HANDLE_MAP)で登録
	public abstract int getHandle();
	
	//武器属性はその武器の全ての属性(DefaultUnit.ELEMENT_MAP)を登録
	public abstract List<Integer> getElement();
	
	//DefaultUnit.WEAPON_WEAPON_MAPの順にステータスをリスト化
	public abstract List<Integer> getWeaponStatus();
	
	//DefaultUnit.WEAPON_UNIT_MAPの順にステータスをリスト化
	public abstract List<Integer> getUnitStatus();
	
	//DefaultUnit.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
