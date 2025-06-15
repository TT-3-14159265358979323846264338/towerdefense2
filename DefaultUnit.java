package defaultdata;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import defaultdata.core.*;
import defaultdata.weapon.*;

//ユニットデータ
public class DefaultUnit {
	//データコード変換
	public final static Map<Integer, String> CORE_WEAPON_MAP = new HashMap<>();{
		CORE_WEAPON_MAP.put(0, "攻撃倍率");
		CORE_WEAPON_MAP.put(1, "射程倍率");
		CORE_WEAPON_MAP.put(2, "攻撃速度倍率");
		CORE_WEAPON_MAP.put(3, "攻撃対象倍率");
	}
	public final static Map<Integer, String> CORE_UNIT_MAP = new HashMap<>();{
		CORE_UNIT_MAP.put(0, "最大HP倍率");
		CORE_UNIT_MAP.put(1, "HP倍率");
		CORE_UNIT_MAP.put(2, "防御倍率");
		CORE_UNIT_MAP.put(3, "回復倍率");
		CORE_UNIT_MAP.put(4, "足止め数倍率");
		CORE_UNIT_MAP.put(5, "配置コスト倍率");
	}
	public final static Map<Integer, String> WEAPON_WEAPON_MAP = new HashMap<>();{
		WEAPON_WEAPON_MAP.put(0, "攻撃");
		WEAPON_WEAPON_MAP.put(1, "射程");
		WEAPON_WEAPON_MAP.put(2, "攻撃速度");
		WEAPON_WEAPON_MAP.put(3, "攻撃対象");
	}
	public final static Map<Integer, String> WEAPON_UNIT_MAP = new HashMap<>();{
		WEAPON_UNIT_MAP.put(0, "最大HP");
		WEAPON_UNIT_MAP.put(1, "HP");
		WEAPON_UNIT_MAP.put(2, "防御");
		WEAPON_UNIT_MAP.put(3, "回復");
		WEAPON_UNIT_MAP.put(4, "足止め数");
		WEAPON_UNIT_MAP.put(5, "配置コスト");
	}
	public final static Map<Integer, String> DISTANCE_MAP = new HashMap<>();{
		DISTANCE_MAP.put(0,"近接");
		DISTANCE_MAP.put(1,"遠隔");
		DISTANCE_MAP.put(2,"遠近");
	}
	public final static Map<Integer, String> HANDLE_MAP = new HashMap<>();{
		HANDLE_MAP.put(0,"片手");
		HANDLE_MAP.put(1,"両手");
	}
	public final static Map<Integer, String> ELEMENT_MAP = new HashMap<>();{
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
	}
	
	//コアの種類
	public final static int CORE_SPECIES = 6;
	
	//武器の種類
	public final static int WEAPON_SPECIES = 2;
	
	//デフォルトデータ収集
	public CoreData getCoreData(int number) {
		switch(number) {
		case 0:
			return new No0000NormalCore();
		case 1:
			return new No0001NormalAtackCore();
		case 2:
			return new No0002NormalDefenseCore();
		case 3:
			return new No0003NormalRangeCore();
		case 4:
			return new No0004NormalHealCore();
		case 5:
			return new No0005NormalSpeedCore();
		default:
			return null;
		}
		/*
		新たなデータを追加したらCORE_SPECIESにも加算すること
		 */
	}
	
	public WeaponData getWeaponData(int number) {
		switch(number) {
		case 0:
			return new No0000JapaneseSword();
		case 1:
			return new No0001Bow();
		default:
			return null;
		}
		/*
		新たなデータを追加したらWEAPON_SPECIESにも加算すること
		 */
	}
	
	//画像取込み
	public List<BufferedImage> getCoreImage(int ratio){
		return new EditImage().inputList(IntStream.range(0, CORE_SPECIES).mapToObj(i -> getCoreData(i).getCoreImageName()).toList(), ratio);
	}
	
	public List<BufferedImage> getCenterCoreImage(int ratio){
		return new EditImage().inputList(IntStream.range(0, CORE_SPECIES).mapToObj(i -> getCoreData(i).getCoreActionImageName()).toList(), ratio);
	}
	
	public List<BufferedImage> getWeaponImage(int ratio){
		return new EditImage().inputList(IntStream.range(0, WEAPON_SPECIES).mapToObj(i -> getWeaponData(i).getWeaponImageName()).toList(), ratio);
	}
	
	public List<BufferedImage> getRightWeaponImage(int ratio){
		return new EditImage().inputList(IntStream.range(0, WEAPON_SPECIES).mapToObj(i -> getWeaponData(i).getRightWeaponActionImageName()).map(i -> (i.isEmpty())? null: i.get(0)).toList(), ratio);
	}
	
	public List<BufferedImage> getLeftWeaponImage(int ratio){
		return new EditImage().inputList(IntStream.range(0, WEAPON_SPECIES).mapToObj(i -> getWeaponData(i).getLeftWeaponActionImageName().get(0)).toList(), ratio);
	}
}