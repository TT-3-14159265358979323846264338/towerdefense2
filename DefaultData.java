package defaultdata;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

//デフォルトデータ
public class DefaultData {
	public final static int SIZE = 60;
	final static List<String> CORE_NAME_LIST = Arrays.asList(
			"image/soldier/normal core.png",
			"image/soldier/normal atack core.png",
			"image/soldier/normal defense core.png",
			"image/soldier/normal range core.png",
			"image/soldier/normal heal core.png",
			"image/soldier/normal speed core.png"
			);
	final static List<String> CENTER_CORE_NAME_LIST = Arrays.asList(
			"image/soldier/normal core center.png",
			"image/soldier/normal atack core center.png",
			"image/soldier/normal defense core center.png",
			"image/soldier/normal range core center.png",
			"image/soldier/normal heal core center.png",
			"image/soldier/normal speed core center.png"
			);
	//ステータスは 最大HP, 残存HP, 攻撃, 防御, 射程, 回復, 攻撃速度, (味方: 足止め数 敵: 移動速度), (味方: 配置コスト 敵: 撃破時コスト増加) の順でリスト化
	public final static List<List<Double>> CORE_STATUS_LIST = Arrays.asList(
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.1, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.1, 1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.1, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.1, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.1, 1.0, 1.0)
			);
	final static List<String> WEAPON_NAME_LIST = Arrays.asList(
			"image/soldier/Japanese sword.png",
			"image/soldier/bow.png"
			);
	final static List<List<String>> RIGHT_WEAPON_NAME_LIST = Arrays.asList(
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
	//武器タイプは① 0: 近接, 1: 遠隔, 2: 遠近 ② 0: 片手, 1: 両手 で登録
	public final static List<List<Integer>> WEAPON_TYPE= Arrays.asList(
			Arrays.asList(0, 0),
			Arrays.asList(1, 1)
			);
	public final static Map<Integer, String> DISTANCE_MAP = new HashMap<Integer, String>();{
		DISTANCE_MAP.put(0,"近接");
		DISTANCE_MAP.put(1,"遠隔");
		DISTANCE_MAP.put(2,"遠近");
	}
	public final static Map<Integer, String> HANDLE_MAP = new HashMap<Integer, String>();{
		HANDLE_MAP.put(0,"片手");
		HANDLE_MAP.put(1,"両手");
	}
	//ステータスは 最大HP, 残存HP, 攻撃, 防御, 射程, 回復, 攻撃速度, (味方: 足止め数 敵: 移動速度), (味方: 配置コスト 敵: 撃破時コスト増加) の順でリスト化
	public final static List<List<Integer>> WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(1000, 1000, 100, 100, 30, 10, 1000, 1, 5),
			Arrays.asList(1000, 1000, 200, 100, 150, 10, 1000, 0, 10)
			);
	
	public List<BufferedImage> coreImage(int ratio){
		return new InputImage().inputList(CORE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> centerCoreImage(int ratio){
		return new InputImage().inputList(CENTER_CORE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> weaponImage(int ratio){
		return new InputImage().inputList(WEAPON_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> rightWeaponImage(int ratio){
		return new InputImage().inputList2(RIGHT_WEAPON_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> leftWeaponImage(int ratio){
		return new InputImage().inputList2(LEFT_WEAPON_NAME_LIST, ratio);
	}
}

//画像処理
class InputImage{
	protected BufferedImage input(String imageName, int ratio) {
		BufferedImage image = null;
		try{
			image = editImage(ImageIO.read(new File(imageName)), ratio);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	protected List<BufferedImage> inputList(List<String> imageNameList, int ratio){
		List<BufferedImage> imageList = new ArrayList<>();
		for(String i : imageNameList) {
			imageList.add(input(i,2));
		}
		return imageList;
	}
	
	protected List<List<BufferedImage>> inputList2(List<List<String>> imageNameList, int ratio){
		List<List<BufferedImage>> imageList = new ArrayList<>();
		for(List<String> i: imageNameList) {
			imageList.add(inputList(i,2));
		}
		return imageList;
	}
	
	//画像処理
	private BufferedImage editImage(BufferedImage originalImage, int ratio) {
		int resizeWidth = originalImage.getWidth() / ratio;
		int resizeHeight = originalImage.getHeight() / ratio;
		BufferedImage resizeImage = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_3BYTE_BGR);
		resizeImage.createGraphics().drawImage(
	    	originalImage.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_AREA_AVERAGING),
	        0, 0, resizeWidth, resizeHeight, null);
		
		BufferedImage finalImage = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < resizeHeight; y++) {
			for (int x = 0; x < resizeWidth; x++) {
				if (resizeImage.getRGB(x, y) == new Color(255, 255 ,255).getRGB()) {
					finalImage.setRGB(x, y, 0);
				}else {
					finalImage.setRGB(x, y, resizeImage.getRGB(x, y));
                }
			}
		}
		return finalImage;
	}
}