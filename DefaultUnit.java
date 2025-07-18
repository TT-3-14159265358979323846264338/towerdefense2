package defaultdata;

import java.util.HashMap;
import java.util.Map;

import defaultdata.core.*;
import defaultdata.weapon.*;

//ユニットデータ
public class DefaultUnit {
	//データコード変換
	public final static Map<Integer, String> CORE_WEAPON_MAP = new HashMap<>();
	static {
		CORE_WEAPON_MAP.put(0, "攻撃倍率");
		CORE_WEAPON_MAP.put(1, "射程倍率");
		CORE_WEAPON_MAP.put(2, "攻撃速度倍率");
		CORE_WEAPON_MAP.put(3, "攻撃対象倍率");
	}
	public final static Map<Integer, String> CORE_UNIT_MAP = new HashMap<>();
	static {
		CORE_UNIT_MAP.put(0, "最大HP倍率");
		CORE_UNIT_MAP.put(1, "HP倍率");
		CORE_UNIT_MAP.put(2, "防御倍率");
		CORE_UNIT_MAP.put(3, "回復倍率");
		CORE_UNIT_MAP.put(4, "足止め数倍率");
		CORE_UNIT_MAP.put(5, "配置コスト倍率");
	}
	public final static Map<Integer, String> WEAPON_WEAPON_MAP = new HashMap<>();
	static {
		WEAPON_WEAPON_MAP.put(0, "攻撃");
		WEAPON_WEAPON_MAP.put(1, "射程");
		WEAPON_WEAPON_MAP.put(2, "攻撃速度");
		WEAPON_WEAPON_MAP.put(3, "攻撃対象");
	}
	public final static Map<Integer, String> WEAPON_UNIT_MAP = new HashMap<>();
	static {
		WEAPON_UNIT_MAP.put(0, "最大HP");
		WEAPON_UNIT_MAP.put(1, "HP");
		WEAPON_UNIT_MAP.put(2, "防御");
		WEAPON_UNIT_MAP.put(3, "回復");
		WEAPON_UNIT_MAP.put(4, "足止め数");
		WEAPON_UNIT_MAP.put(5, "配置コスト");
	}
	public final static Map<Integer, String> DISTANCE_MAP = new HashMap<>();
	static {
		DISTANCE_MAP.put(0,"近接");
		DISTANCE_MAP.put(1,"遠隔");
		DISTANCE_MAP.put(2,"遠近");
	}
	public final static Map<Integer, String> HANDLE_MAP = new HashMap<>();
	static {
		HANDLE_MAP.put(0,"片手");
		HANDLE_MAP.put(1,"両手");
	}
	public final static Map<Integer, String> ELEMENT_MAP = new HashMap<>();
	static {
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
	}
	
	//コアコード番号
	public final static int NORMAL_CORE = 0;
	public final static int ATACK_CORE = 1;
	public final static int DEFENCE_CORE = 2;
	public final static int RANGE_CORE = 3;
	public final static int HEAL_CORE = 4;
	public final static int SPEED_CORE = 5;
	
	//全コアコード統合
	public final static Map<Integer, CoreData> CORE_DATA_MAP = new HashMap<>();
	static {
		CORE_DATA_MAP.put(NORMAL_CORE, new No0000NormalCore());
		CORE_DATA_MAP.put(ATACK_CORE, new No0001NormalAtackCore());
		CORE_DATA_MAP.put(DEFENCE_CORE, new No0002NormalDefenseCore());
		CORE_DATA_MAP.put(RANGE_CORE, new No0003NormalRangeCore());
		CORE_DATA_MAP.put(HEAL_CORE, new No0004NormalHealCore());
		CORE_DATA_MAP.put(SPEED_CORE, new No0005NormalSpeedCore());
	}
	
	//武器コード番号
	public final static int SWORD = 0;
	public final static int BOW = 1;
	
	//全武器コード統合
	public final static Map<Integer, WeaponData> WEAPON_DATA_MAP = new HashMap<>();
	static {
		WEAPON_DATA_MAP.put(SWORD, new No0000JapaneseSword());
		WEAPON_DATA_MAP.put(BOW, new No0001Bow());
	}
	
	/*
	新規で追加する時はコード番号と全コード統合の両方に追加すること
	*/
}