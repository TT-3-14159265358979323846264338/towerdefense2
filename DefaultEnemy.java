package defaultdata;

import java.util.HashMap;
import java.util.Map;

import defaultdata.enemy.*;

//敵兵データ
public class DefaultEnemy {
	//データコード変換
	public final static Map<Integer, String> WEAPON_MAP = new HashMap<>();
	static {
		WEAPON_MAP.put(0, "攻撃");
		WEAPON_MAP.put(1, "射程");
		WEAPON_MAP.put(2, "攻撃速度");
		WEAPON_MAP.put(3, "攻撃対象");
	}
	public final static Map<Integer, String> UNIT_MAP = new HashMap<>();
	static {
		UNIT_MAP.put(0, "最大HP");
		UNIT_MAP.put(1, "HP");
		UNIT_MAP.put(2, "防御");
		UNIT_MAP.put(3, "回復");
		UNIT_MAP.put(4, "移動速度");
		UNIT_MAP.put(5, "撃破コスト");
	}
	public final static Map<Integer, String> MOVE_MAP = new HashMap<>();
	static {
		MOVE_MAP.put(0, "地上");
		MOVE_MAP.put(1, "飛行");
		MOVE_MAP.put(2, "水上");
	}
	public final static Map<Integer, String> TYPE_MAP = new HashMap<>();
	static {
		TYPE_MAP.put(0, "一般");
		TYPE_MAP.put(1, "ボス");
	}
	public final static Map<Integer, String> ELEMENT_MAP = DefaultUnit.ELEMENT_MAP;
	/*
	実際のMapの中身
		ELEMENT_MAP.put(0,"斬撃");
		ELEMENT_MAP.put(1,"刺突");
		ELEMENT_MAP.put(2,"殴打");
		ELEMENT_MAP.put(3,"衝撃");
		ELEMENT_MAP.put(4,"炎");
		ELEMENT_MAP.put(5,"水");
		ELEMENT_MAP.put(6,"風");
		ELEMENT_MAP.put(7,"土");
		ELEMENT_MAP.put(8,"雷");
		ELEMENT_MAP.put(9,"聖");
		ELEMENT_MAP.put(10,"闇");
		ELEMENT_MAP.put(11,"支援");
	*/
	
	//コード番号
	public final static int BLUE_SLIME = 0;
	public final static int RED_SLIME = 1;
	
	//全コード統合
	public final static Map<Integer, EnemyData> DATA_MAP = new HashMap<>();
	static {
		DATA_MAP.put(BLUE_SLIME, new No0000BlueSlime());
		DATA_MAP.put(RED_SLIME, new No0001RedSlime());
	}
	
	/*
	新規で追加する時はコード番号と全コード統合の両方に追加すること
	*/
}