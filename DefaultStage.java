package defaultdata;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import defaultdata.facility.*;
import defaultdata.stage.*;

//ステージデータ
public class DefaultStage {
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
		UNIT_MAP.put(4, "足止め数");
	}
	public final static Map<Integer, String> ELEMENT_MAP = DefaultUnit.ELEMENT_MAP;
	/*ELEMENT_MAPの実際の中身
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
	
	//配置マス画像ファイル
	public final static List<String> PLACEMENT_NAME_LIST = Arrays.asList(
			"image/field/near placement.png",
			"image/field/far placement.png",
			"image/field/all placement.png"
			);
	
	//ステージの種類
	public final static int STAGE_SPECIES = 2;
	
	//設備の種類
	public final static int FACILITY_SPECIES = 2;
	
	//デフォルトデータ収集
	public StageData getStageData(int number) {
		switch(number) {
		case 0:
			return new No0000Stage1();
		case 1:
			return new No0001Stage2();
		default:
			return null;
		}
		/*
		新たなデータを追加したらSTAGE_SPECIESにも加算すること
		 */
	}
	
	public FacilityData getFacilityData(int number) {
		switch(number) {
		case 0:
			return new No0000Castle();
		case 1:
			return new No0001Gate();
		default:
			return null;
		}
		/*
		新たなデータを追加したらFACILITY_SPECIESにも加算すること
		 */
	}
	
	//画像取込み
	public List<BufferedImage> getPlacementImage(double ratio){
		return new EditImage().input(PLACEMENT_NAME_LIST, ratio);
	}
}