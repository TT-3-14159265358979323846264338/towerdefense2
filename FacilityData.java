package defaultdata.facility;

import java.awt.image.BufferedImage;
import java.util.List;

import defaultdata.EditImage;

public abstract class FacilityData {
	//名前
	public abstract String getName();
	
	
	//攻撃時の画像ファイル名
	public abstract List<String> getActionFrontImageName();
	public abstract List<String> getActionSideImageName();
	
	//攻撃時の画像
	public List<BufferedImage> getActionFrontImage(double ratio) {
		return new EditImage().input(getActionFrontImageName(), ratio);
	}
	public List<BufferedImage> getActionSideImage(double ratio) {
		return new EditImage().input(getActionSideImageName(), ratio);
	}
	
	//破損時画像ファイル名
	public abstract String getBreakImageName();
	
	//破損時画像
	public BufferedImage getBreakImage(double ratio) {
		return new EditImage().input(getBreakImageName(), ratio);
	}
	
	//武器属性はその武器の全ての属性(DefaultStage.ELEMENT_MAP)を登録　攻撃しない時は空のlist
	public abstract List<Integer> getElement();
	
	//DefaultAtackPatternのパターン番号
	public abstract int getAtackPattern();
	
	//DefaultStage.WEAPON_MAPの順にステータスをリスト化　攻撃しない時は空のlist
	public abstract List<Integer> getWeaponStatus();
	
	//DefaultStage.UNIT_MAPの順にステータスをリスト化　足止め数∞の時は-1
	public abstract List<Integer> getUnitStatus();
	
	//DefaultStage.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
