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
	//自軍データ
	public final static int SIZE = 60;
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
	//武器ステータスは 攻撃, 射程, 攻撃速度 の順でリスト化
	public final static List<List<Double>> CORE_WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(1.0, 1.0, 1.0),
			Arrays.asList(1.1, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.1, 1.0),
			Arrays.asList(1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 0.9)
			);
	//ユニットステータスは 最大HP, 防御, 回復, (味方: 足止め数 敵: 移動速度), (味方: 配置コスト 敵: 撃破時コスト増加) の順でリスト化
	public final static List<List<Double>> CORE_UNIT_STATUS_LIST = Arrays.asList(
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.1, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.1, 1.0, 1.0),
			Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0)
			);
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
	//武器タイプは① 0: 近接, 1: 遠隔, 2: 遠近 ② 0: 片手, 1: 両手 で登録
	public final static List<List<Integer>> WEAPON_TYPE = Arrays.asList(
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
	//武器ステータスは 攻撃, 射程, 攻撃速度 の順でリスト化
	public final static List<List<Integer>> WEAPON_WEAPON_STATUS_LIST = Arrays.asList(
			Arrays.asList(100, 30, 1000),
			Arrays.asList(200, 150, 1000)
			);
	//ユニットステータスは 最大HP, 防御, 回復, (味方: 足止め数 敵: 移動速度), (味方: 配置コスト 敵: 撃破時コスト増加) の順でリスト化
	public final static List<List<Integer>> WEAPON_UNIT_STATUS_LIST = Arrays.asList(
			Arrays.asList(1000, 100, 10, 1, 5),
			Arrays.asList(1000, 100, 10, 0, 10)
			);
	
	//敵軍データ
	
	//ガチャデータ
	final static String BALL = "image/gacha/ball full.png";
	final static List<String> HALF_BALL = Arrays.asList("image/gacha/ball bottom.png", "image/gacha/ball top.png");
	final static String HANDLE = "image/gacha/machine handle.png";
	final static List<String> MACHINE = Arrays.asList("image/gacha/machine bottom.png", "image/gacha/machine top.png");
	final static String TURN = "image/gacha/turn.png";
	final static String EFFECT = "image/gacha/effect.png";
	
	//画像取込み
	public List<BufferedImage> getCoreImage(int ratio){
		return new InputImage().inputList(CORE_IMAGE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> getCenterCoreImage(int ratio){
		return new InputImage().inputList(CENTER_IMAGE_CORE_NAME_LIST, ratio);
	}
	
	public List<BufferedImage> getWeaponImage(int ratio){
		return new InputImage().inputList(WEAPON_IMAGE_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> getRightWeaponImage(int ratio){
		return new InputImage().inputList2(RIGHT_IMAGE_WEAPON_NAME_LIST, ratio);
	}
	
	public List<List<BufferedImage>> getLeftWeaponImage(int ratio){
		return new InputImage().inputList2(LEFT_WEAPON_NAME_LIST, ratio);
	}
	
	public BufferedImage getBallImage(int ratio) {
		return new InputImage().input(BALL, ratio);
	}
	
	public List<BufferedImage> getHalfBallImage(int ratio){
		return new InputImage().inputList(HALF_BALL, ratio);
	}
	
	public BufferedImage getHandleImage(int ratio) {
		return new InputImage().input(HANDLE, ratio);
	}
	
	public List<BufferedImage> getMachineImage(int ratio){
		return new InputImage().inputList(MACHINE, ratio);
	}
	
	public BufferedImage getTurnImage(int ratio) {
		return new InputImage().input(TURN, ratio);
	}
	
	public BufferedImage getEffectImage(int ratio) {
		return new InputImage().input(EFFECT, ratio);
	}
}

//画像処理
class InputImage{
	protected List<List<BufferedImage>> inputList2(List<List<String>> imageNameList, int ratio){
		List<List<BufferedImage>> imageList = new ArrayList<>();
		for(List<String> i: imageNameList) {
			imageList.add(inputList(i, ratio));
		}
		return imageList;
	}
	
	protected List<BufferedImage> inputList(List<String> imageNameList, int ratio){
		List<BufferedImage> imageList = new ArrayList<>();
		for(String i : imageNameList) {
			imageList.add(input(i, ratio));
		}
		return imageList;
	}
	
	protected BufferedImage input(String imageName, int ratio) {
		BufferedImage image = null;
		try{
			image = editImage(ImageIO.read(new File(imageName)), ratio);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private BufferedImage editImage(BufferedImage originalImage, int ratio) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage image = getBlankImage(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(originalImage.getRGB(x, y) != new Color(255, 255, 255).getRGB()) {
					image.setRGB(x, y, originalImage.getRGB(x, y));
				}
			}
		}
		int resizeWidth = width / ratio;
		int resizeHeight = height / ratio;
		BufferedImage resizeImage = getBlankImage(resizeWidth, resizeHeight);
		resizeImage.createGraphics().drawImage(
	    	image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_AREA_AVERAGING),
	        0, 0, resizeWidth, resizeHeight, null);
		return resizeImage;
	}
	
	private BufferedImage getBlankImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setRGB(x, y, 0);
			}
		}
		return image;
	}
}