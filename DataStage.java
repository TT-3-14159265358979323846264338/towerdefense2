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
	
	
	
	
	
	//画像取込み
	public List<BufferedImage> getPlacementImage(int ratio){
		return new EditImage().inputList(PLACEMENT_NAME_LIST, ratio);
	}
	
	
	
	
}
