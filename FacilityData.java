package defaultdata.facility;

import java.util.List;

public abstract class FacilityData {
	//設備の名前
	public abstract String getName();
	
	
	//攻撃時の画像ファイル名
	public abstract List<String> getActionFrontImageName();
	public abstract List<String> getActionSideImageName();
	
	//設備の破損時画像ファイル名
	public abstract String getBreakImageName();
	
	//武器属性はその武器の全ての属性(DefaultStage.ELEMENT_MAP)を登録　攻撃しない時は空のlist
	public abstract List<Integer> getElement();
	
	//DefaultStage.WEAPON_MAPの順にステータスをリスト化　攻撃しない時は空のlist
	public abstract List<Integer> getWeaponStatus();
	
	//DefaultStage.UNIT_MAPの順にステータスをリスト化　足止め数∞の時は-1
	public abstract List<Integer> getUnitStatus();
	
	//DefaultStage.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
