package defaultdata;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import defaultdata.stage.*;

//ステージデータ
public class DefaultStage {
	//配置マス画像ファイル
	public final static List<String> PLACEMENT_NAME_LIST = Arrays.asList(
			"image/field/near placement.png",
			"image/field/far placement.png",
			"image/field/all placement.png"
			);
	
	//ステージの種類
	public final static int STAGE_SPECIES = 2;
	
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
	
	//画像取込み
	public List<BufferedImage> getPlacementImage(int ratio){
		return new EditImage().inputList(PLACEMENT_NAME_LIST, ratio);
	}
}