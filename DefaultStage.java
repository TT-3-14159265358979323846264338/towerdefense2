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
		ELEMENT_MAP.put(11,"支援");
	*/
	
	//ステージコード番号
	public final static int STAGE1 = 0;
	public final static int STAGE2 = 1;
		
	//全ステージコード統合
	public final static Map<Integer, StageData> STAGE_DATA_MAP = new HashMap<>();
	static {
		STAGE_DATA_MAP.put(STAGE1, new No0000Stage1());
		STAGE_DATA_MAP.put(STAGE2, new No0001Stage2());
	}
	
	//設備コード番号
	public final static int CASTLE = 0;
	public final static int GATE = 1;
		
	//全設備コード統合
	public final static Map<Integer, FacilityData> FACILITY_DATA_MAP = new HashMap<>();
	static {
		FACILITY_DATA_MAP.put(STAGE1, new No0000Castle());
		FACILITY_DATA_MAP.put(STAGE2, new No0001Gate());
	}
	
	/*
	新規で追加する時はコード番号と全コード統合の両方に追加すること
	*/
	
	//配置マス画像ファイル
		public final static List<String> PLACEMENT_NAME_LIST = Arrays.asList(
				"image/field/near placement.png",
				"image/field/far placement.png",
				"image/field/all placement.png"
				);
		
	public List<BufferedImage> getPlacementImage(double ratio){
		return new EditImage().input(PLACEMENT_NAME_LIST, ratio);
	}
}