package defaultdata.enemy;

import java.util.List;

public abstract class EnemyData {
	//敵の名前
	public abstract String getName();
	
	//通常時の敵画像ファイル名
	public abstract String getImageName();
	
	//攻撃時の敵画像ファイル名
	public abstract List<String> getActionImageName();
	
	//移動経路(DefaultEnemy.MOVE_MAP)で登録
	public abstract int getMove();
	
	//種別(DefaultEnemy.TYPE_MAP)で登録
	public abstract int getType();
	
	//武器属性はその武器の全ての属性(DefaultEnemy.ELEMENT_MAP)を登録
	public abstract List<Integer> getElement();
	
	//DefaultEnemy.WEAPON_MAPの順にステータスをリスト化
	public abstract List<Integer> getWeaponStatus();
	
	//DefaultEnemy.UNIT_MAPの順にステータスをリスト化
	public abstract List<Integer> getUnitStatus();
	
	//DefaultEnemy.ELEMENT_MAPの順にステータスをリスト化
	public abstract List<Integer> getCutStatus();
}
