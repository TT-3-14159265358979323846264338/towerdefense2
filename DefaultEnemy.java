package defaultdata;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import defaultdata.enemy.*;

//敵兵データ
public class DefaultEnemy {
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
	*/
	
	//敵の種類
	public final static int ENEMY_SPECIES = 2;
	
	//デフォルトデータ収集
	public EnemyData getEnemyData(int number) {
		switch(number) {
		case 0:
			return new No0000BlueSlime();
		case 1:
			return new No0001RedSlime();
		default:
			return null;
		}
		/*
		新たなデータを追加したらENEMY_SPECIESにも加算すること
		 */
	}
	
	//画像取込み
	public List<BufferedImage> getEnemyImage(double ratio){
		return new EditImage().input(IntStream.range(0, ENEMY_SPECIES).mapToObj(i -> getEnemyData(i).getImageName()).toList(), ratio);
	}
}