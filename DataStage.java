package datastage;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import editimage.EditImage;

//ステージデータ
public class DataStage {
	//配置マス画像ファイル
	public final static List<String> PLACEMENT_NAME_LIST = Arrays.asList(
			"image/field/near placement.png",
			"image/field/far placement.png",
			"image/field/all placement.png"
			);
	//ステージ画像ファイル
	public final static List<List<String>> STAGE_NAME_LIST = Arrays.asList(
			Arrays.asList("image/field/stage 1-1.png",
					"image/field/stage 1-2.png",
					"image/field/stage 1-3.png",
					"image/field/stage 1-4.png"),
			Arrays.asList("image/field/stage 2-1.png",
					"image/field/stage 2-2.png",
					"image/field/stage 2-3.png",
					"image/field/stage 2-4.png",
					"image/field/stage 2-5.png",
					"image/field/stage 2-6.png",
					"image/field/stage 2-7.png",
					"image/field/stage 2-8.png")
			);
	
	
	
	//画像取込み
	public List<BufferedImage> getPlacementImage(int ratio){
		return new EditImage().inputList(PLACEMENT_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> getStageImage(int number, int ratio){
		return new EditImage().inputList(STAGE_NAME_LIST.get(number), ratio);
	}
	
	public List<BufferedImage> getStageImage(int ratio){
		return new EditImage().inputList(STAGE_NAME_LIST.stream().map(i -> i.get(0)).toList(), ratio);
	}
}
