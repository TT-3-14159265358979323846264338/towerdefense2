package defaultdata;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

//デフォルトデータ
public class DefaultData {
	final static List<String> CORE_NAME_LIST = Arrays.asList(
			"image/soldier/normal core.png",
			"image/soldier/normal atack core.png",
			"image/soldier/normal defense core.png",
			"image/soldier/normal range core.png",
			"image/soldier/normal heal core.png",
			"image/soldier/normal speed core.png"
			);
	final static List<String> WEAPON_NAME_LIST = Arrays.asList(
			"image/soldier/Japanese sword.png",
			"image/soldier/Japanese sword.png"
			);
	final static List<List<String>> RIGHT_WEAPON_NAME_LIST = Arrays.asList(
			Arrays.asList("image/soldier/Japanese sword right 0.png",
					"image/soldier/Japanese sword right 1.png",
					"image/soldier/Japanese sword right 2.png",
					"image/soldier/Japanese sword right 3.png",
					"image/soldier/Japanese sword right 4.png",
					"image/soldier/Japanese sword right 5.png"),
			Arrays.asList("image/soldier/Japanese sword right 0.png",
					"image/soldier/Japanese sword right 1.png",
					"image/soldier/Japanese sword right 2.png",
					"image/soldier/Japanese sword right 3.png",
					"image/soldier/Japanese sword right 4.png",
					"image/soldier/Japanese sword right 5.png")
			);
	final static List<List<String>> LEFT_WEAPON_NAME_LIST = Arrays.asList(
			Arrays.asList("image/soldier/Japanese sword left 0.png",
					"image/soldier/Japanese sword left 1.png",
					"image/soldier/Japanese sword left 2.png",
					"image/soldier/Japanese sword left 3.png",
					"image/soldier/Japanese sword left 4.png",
					"image/soldier/Japanese sword left 5.png"),
			Arrays.asList("image/soldier/Japanese sword left 0.png",
					"image/soldier/Japanese sword left 1.png",
					"image/soldier/Japanese sword left 2.png",
					"image/soldier/Japanese sword left 3.png",
					"image/soldier/Japanese sword left 4.png",
					"image/soldier/Japanese sword left 5.png")
			);
	
	public List<BufferedImage> coreImage(int ratio){
		return new InputImage().inputList(CORE_NAME_LIST, ratio);
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