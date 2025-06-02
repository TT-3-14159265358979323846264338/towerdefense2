package defaultdata;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import editimage.EditImage;

//デフォルトデータ
public class DefaultData {
	//データコード変換
	public final static Map<Integer, String> CORE_WEAPON_MAP  = new HashMap<Integer, String>();{
		CORE_WEAPON_MAP.put(0, "攻撃倍率");
		CORE_WEAPON_MAP.put(1, "射程倍率");
		CORE_WEAPON_MAP.put(2, "攻撃速度倍率");
		CORE_WEAPON_MAP.put(3, "攻撃対象倍率");
	}
	public final static Map<Integer, String> CORE_UNIT_MAP  = new HashMap<Integer, String>();{
		CORE_UNIT_MAP.put(0, "最大HP倍率");
		CORE_UNIT_MAP.put(1, "HP倍率");
		CORE_UNIT_MAP.put(2, "防御倍率");
		CORE_UNIT_MAP.put(3, "回復倍率");
		CORE_UNIT_MAP.put(4, "足止め数倍率");
		CORE_UNIT_MAP.put(5, "配置コスト倍率");
	}
	public final static Map<Integer, String> WEAPON_WEAPON_MAP  = new HashMap<Integer, String>();{
		WEAPON_WEAPON_MAP.put(0, "攻撃");
		WEAPON_WEAPON_MAP.put(1, "射程");
		WEAPON_WEAPON_MAP.put(2, "攻撃速度");
		WEAPON_WEAPON_MAP.put(3, "攻撃対象");
	}
	public final static Map<Integer, String> WEAPON_UNIT_MAP  = new HashMap<Integer, String>();{
		WEAPON_UNIT_MAP.put(0, "最大HP");
		WEAPON_UNIT_MAP.put(1, "HP");
		WEAPON_UNIT_MAP.put(2, "防御");
		WEAPON_UNIT_MAP.put(3, "回復");
		WEAPON_UNIT_MAP.put(4, "足止め数");
		WEAPON_UNIT_MAP.put(5, "配置コスト");
	}
	public final static Map<Integer, String> DISTANCE_MAP = new HashMap<Integer, String>();{
		DISTANCE_MAP.put(0,"近接");
		DISTANCE_MAP.put(1,"遠隔");
		DISTANCE_MAP.put(2,"遠近");
	}
	public final static Map<Integer, String> HANDLE_MAP = new HashMap<Integer, String>();{
		HANDLE_MAP.put(0,"片手");
		HANDLE_MAP.put(1,"両手");
	}
	public final static Map<Integer, String> ELEMENT_MAP = new HashMap<Integer, String>();{
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
	
	
	
	//コア画像ファイル
	public final static List<String> CORE_NAME_LIST = Arrays.asList(
			"ノーマルコア",
			"ノーマルレッドコア",
			"ノーマルブラックコア",
			"ノーマルパープルコア",
			"ノーマルグリーンコア",
			"ノーマルイエローコア"
			);
	final static List<String> CORE_IMAGE_NAME_LIST = Arrays.asList(
			"image/soldier/normal core.png",
			"image/soldier/normal atack core.png",
			"image/soldier/normal defense core.png",
			"image/soldier/normal range core.png",
			"image/soldier/normal heal core.png",
			"image/soldier/normal speed core.png"
			);
	final static List<String> CENTER_IMAGE_CORE_NAME_LIST = Arrays.asList(
			"image/soldier/normal core center.png",
			"image/soldier/normal atack core center.png",
			"image/soldier/normal defense core center.png",
			"image/soldier/normal range core center.png",
			"image/soldier/normal heal core center.png",
			"image/soldier/normal speed core center.png"
			);
	//レアリティ
	public final static List<Integer> CORE_RARITY_LIST = Arrays.asList(
			1,
			1,
			1,
			1,
			1,
			1
			);
	//武器ステータスはCORE_WEAPON_MAPの順でリスト化
	public final static List<List<Double>> CORE_WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.1, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.1, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 0.9, 1.0)
			);
	//ユニットステータスはCORE_UNIT_MAPの順でリスト化
	public final static List<List<Double>> CORE_UNIT_STATUS_LIST = Arrays.asList(
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.1, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.1, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
			);
	//属性カット率はELEMENT_MAPの順でリスト化
	public final static List<List<Integer>> CORE_CUT_STATUS_LIST = Arrays.asList(
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
			);
	
	
	
	//武器画像ファイル
	public final static List<String> WEAPON_NAME_LIST = Arrays.asList(
			"日本刀",
			"弓"
			);
	final static List<String> WEAPON_IMAGE_NAME_LIST = Arrays.asList(
			"image/soldier/Japanese sword.png",
			"image/soldier/bow.png"
			);
	final static List<List<String>> RIGHT_IMAGE_WEAPON_NAME_LIST = Arrays.asList(
			Arrays.asList("image/soldier/Japanese sword right 0.png",
					"image/soldier/Japanese sword right 1.png",
					"image/soldier/Japanese sword right 2.png",
					"image/soldier/Japanese sword right 3.png",
					"image/soldier/Japanese sword right 4.png",
					"image/soldier/Japanese sword right 5.png"),
			Arrays.asList()
			);
	final static List<List<String>> LEFT_WEAPON_NAME_LIST = Arrays.asList(
			Arrays.asList("image/soldier/Japanese sword left 0.png",
					"image/soldier/Japanese sword left 1.png",
					"image/soldier/Japanese sword left 2.png",
					"image/soldier/Japanese sword left 3.png",
					"image/soldier/Japanese sword left 4.png",
					"image/soldier/Japanese sword left 5.png"),
			Arrays.asList("image/soldier/bow left 0.png",
					"image/soldier/bow left 1.png",
					"image/soldier/bow left 2.png",
					"image/soldier/bow left 3.png",
					"image/soldier/bow left 4.png",
					"image/soldier/bow left 5.png")
			);
	//レアリティ
	public final static List<Integer> WEAPON_RARITY_LIST = Arrays.asList(
			1,
			1
			);
	//武器タイプは ① 距離(DISTANCE_MAP) ② 装備位置(HANDLE_MAP)で登録
	public final static List<List<Integer>> WEAPON_TYPE = Arrays.asList(
			Arrays.asList(0, 0),
			Arrays.asList(1, 1)
			);
	//全ての武器属性(ELEMENT_MAP)を登録
	public final static List<List<Integer>> WEAPON_ELEMENT = Arrays.asList(
			Arrays.asList(0),
			Arrays.asList(1)
			);
	//武器ステータスはWEAPON_WEAPON_MAPの順でリスト化
	public final static List<List<Integer>> WEAPON_WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(100, 30, 1000, 1),
			Arrays.asList(200, 150, 1000, 1)
			);
	//ユニットステータスはWEAPON_UNIT_MAPの順でリスト化
	public final static List<List<Integer>> WEAPON_UNIT_STATUS_LIST = Arrays.asList(
			Arrays.asList(1000, 1000, 100, 10, 1, 5),
			Arrays.asList(1000, 1000, 100, 10, 0, 10)
			);
	//属性カット率はELEMENT_MAPの順でリスト化
	public final static List<List<Integer>> WEAPON_CUT_STATUS_LIST = Arrays.asList(
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
			);
	
	//ガチャ画像ファイル
	final static String BALL = "image/gacha/ball full.png";
	final static List<String> HALF_BALL = Arrays.asList("image/gacha/ball bottom.png", "image/gacha/ball top.png");
	final static String HANDLE = "image/gacha/machine handle.png";
	final static List<String> MACHINE = Arrays.asList("image/gacha/machine bottom.png", "image/gacha/machine top.png");
	final static String TURN = "image/gacha/turn.png";
	final static String EFFECT = "image/gacha/effect.png";
	
	//画像取込み
	public List<BufferedImage> getCoreImage(int ratio){
		return new EditImage().inputList(CORE_IMAGE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> getCenterCoreImage(int ratio){
		return new EditImage().inputList(CENTER_IMAGE_CORE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> getWeaponImage(int ratio){
		return new EditImage().inputList(WEAPON_IMAGE_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> getRightWeaponImage(int ratio){
		return new EditImage().inputList2(RIGHT_IMAGE_WEAPON_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> getLeftWeaponImage(int ratio){
		return new EditImage().inputList2(LEFT_WEAPON_NAME_LIST, ratio);
	}
	
	public BufferedImage getBallImage(int ratio) {
		return new EditImage().input(BALL, ratio);
	}
	
	public List<BufferedImage> getHalfBallImage(int ratio){
		return new EditImage().inputList(HALF_BALL, ratio);
	}
	
	public BufferedImage getHandleImage(int ratio) {
		return new EditImage().input(HANDLE, ratio);
	}
	
	public List<BufferedImage> getMachineImage(int ratio){
		return new EditImage().inputList(MACHINE, ratio);
	}
	
	public BufferedImage getTurnImage(int ratio) {
		return new EditImage().input(TURN, ratio);
	}
	
	public BufferedImage getEffectImage(int ratio) {
		return new EditImage().input(EFFECT, ratio);
	}
}