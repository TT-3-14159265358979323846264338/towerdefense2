package dataenemy;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import dataunit.DataUnit;
import editimage.EditImage;

//敵兵データ
public class DataEnemy {
	//データコード変換
	public final static Map<Integer, String> WEAPON_MAP = new HashMap<>();{
		WEAPON_MAP.put(0, "攻撃");
		WEAPON_MAP.put(1, "射程");
		WEAPON_MAP.put(2, "攻撃速度");
		WEAPON_MAP.put(3, "攻撃対象");
	}
	public final static Map<Integer, String> UNIT_MAP = new HashMap<>();{
		UNIT_MAP.put(0, "最大HP");
		UNIT_MAP.put(1, "HP");
		UNIT_MAP.put(2, "防御");
		UNIT_MAP.put(3, "回復");
		UNIT_MAP.put(4, "移動速度");
		UNIT_MAP.put(5, "撃破コスト");
	}
	public final static Map<Integer, String> MOVE_MAP = new HashMap<>();{
		MOVE_MAP.put(0, "地上");
		MOVE_MAP.put(1, "飛行");
		MOVE_MAP.put(2, "水上");
	}
	public final static Map<Integer, String> TYPE_MAP = new HashMap<>();{
		TYPE_MAP.put(0, "一般");
		TYPE_MAP.put(1, "ボス");
	}
	public final static Map<Integer, String> ELEMENT_MAP = DataUnit.ELEMENT_MAP;
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
	*/
	
	
	
	//敵画像ファイル
	public final static List<String> ENEMY_NAME_LIST = Arrays.asList(
			"ブルースライム",
			"レッドスライム"
			);
	final static List<String> ENEMY_IMAGE_NAME_LIST = Arrays.asList(
			"image/enemy/blue slime.png",
			"image/enemy/red slime.png"
			);
	public final static List<List<String>> ENEMY_MOTION_NAME_LIST = Arrays.asList(
			Arrays.asList("image/enemy/blue slime 0.png",
					"image/enemy/blue slime 1.png",
					"image/enemy/blue slime 2.png",
					"image/enemy/blue slime 3.png",
					"image/enemy/blue slime 4.png",
					"image/enemy/blue slime 5.png"),
			Arrays.asList("image/enemy/red slime 0.png",
					"image/enemy/red slime 1.png",
					"image/enemy/red slime 2.png",
					"image/enemy/red slime 3.png",
					"image/enemy/red slime 4.png",
					"image/enemy/red slime 5.png")
			);
	//タイプは ① 移動経路(MOVE_MAP) ② 種別(TYPE_MAP)で登録
	public final static List<List<Integer>> TYPE = Arrays.asList(
			Arrays.asList(0, 0),
			Arrays.asList(0, 0)
			);
	//全ての武器属性(ELEMENT_MAP)を登録
	public final static List<List<Integer>> ELEMENT = Arrays.asList(
			Arrays.asList(2),
			Arrays.asList(2)
			);
	//武器ステータスはWEAPON_MAPの順でリスト化
	public final static List<List<Integer>> WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(20, 30, 1000, 1),
			Arrays.asList(30, 30, 1000, 1)
			);
	//ユニットステータスはUNIT_MAPの順でリスト化
	public final static List<List<Integer>> UNIT_STATUS_LIST = Arrays.asList(
			Arrays.asList(1000, 1000, 10, 0, 100, 1),
			Arrays.asList(1000, 1000, 10, 0, 100, 1)
			);
	//属性カット率はELEMENT_MAPの順でリスト化
	public final static List<List<Integer>> CUT_STATUS_LIST = Arrays.asList(
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
			);
	
	
	
	//画像取込み
	public List<BufferedImage> getEnemyImage(int ratio){
		return new EditImage().inputList(ENEMY_IMAGE_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> getEnemyImage(List<List<Integer>> enemyList, int ratio){
		List<List<String>> extractionList = IntStream.range(0, ENEMY_MOTION_NAME_LIST.size()).mapToObj(i -> (enemyList.stream().anyMatch(j -> i == j.get(0)))? ENEMY_MOTION_NAME_LIST.get(i): null).toList();
		return new EditImage().inputList2(extractionList, ratio);
	}
}
