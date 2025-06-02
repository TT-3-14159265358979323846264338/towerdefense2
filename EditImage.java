package editimage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

//画像処理
public class EditImage{
	public BufferedImage input(String imageName, int ratio) {
		BufferedImage image = null;
		try{
			image = getImage(ImageIO.read(new File(imageName)), ratio);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public List<BufferedImage> inputList(List<String> imageNameList, int ratio){
		return imageNameList.stream().map(i -> input(i, ratio)).toList();
	}
	
	public List<List<BufferedImage>> inputList2(List<List<String>> imageNameList, int ratio){
		return imageNameList.stream().map(i -> inputList(i, ratio)).toList();
	}
	
	private BufferedImage getImage(BufferedImage originalImage, int ratio) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage image = getBlankImage(width, height);
		IntStream.range(0, height).forEach(y -> IntStream.range(0, width).forEach(x -> {
			if(originalImage.getRGB(x, y) != new Color(255, 255, 255).getRGB()) {
				image.setRGB(x, y, originalImage.getRGB(x, y));
			}
		}));
		return scalingImage(image, ratio);
	}
	
	public BufferedImage scalingImage(BufferedImage originalImage, int ratio) {
		int resizeWidth = originalImage.getWidth() / ratio;
		int resizeHeight = originalImage.getHeight() / ratio;
		BufferedImage resizeImage = getBlankImage(resizeWidth, resizeHeight);
		resizeImage.createGraphics().drawImage(
				originalImage.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_AREA_AVERAGING),
	        0, 0, resizeWidth, resizeHeight, null);
		return resizeImage;
	}
	
	public BufferedImage compositeImage(List<BufferedImage> originalImage) {
		int width = originalImage.get(1).getWidth();
		int height = originalImage.get(1).getHeight();
		BufferedImage image = getBlankImage(width, height);
		Graphics g = image.getGraphics();
		originalImage.stream().forEach(i -> g.drawImage(i, 0, 0, null));
		g.dispose();
		return image;
	}
	
	public BufferedImage rotateImage(BufferedImage originalImage, double angle) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage image = getBlankImage(width, height);
		Graphics2D g2 =  (Graphics2D) image.getGraphics();
		AffineTransform rotate = new AffineTransform();
		rotate.setToRotation(angle, width / 2, height / 2);
		g2.setTransform(rotate);
		g2.drawImage(originalImage, 0, 0, null);
		g2.dispose();
		return image;
	}
	
	public BufferedImage effectImage(BufferedImage originalImage, int expansion, int color) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage image = getBlankImage(width, height);
		IntStream.range(0, height).forEach(y -> IntStream.range(0, width).forEach(x -> {
			if(originalImage.getRGB(x, y) == new Color(0, 0, 0).getRGB()) {
				image.setRGB(x, y, color);
			}
		}));
		int resizeWidth = width + expansion;
		int resizeHeight = height + expansion;
		BufferedImage resizeImage = getBlankImage(resizeWidth, resizeHeight);
		resizeImage.createGraphics().drawImage(
	    	image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_AREA_AVERAGING),
	        0, 0, resizeWidth, resizeHeight, null);
		int pixel = 9;
		float[] matrix = new float[pixel * pixel];
		IntStream.range(0, matrix.length).forEach(i -> matrix[i] = 1.5f / (pixel * pixel));//透過度あるため、色濃いめの1.5f
		ConvolveOp blur = new ConvolveOp(new Kernel(pixel, pixel, matrix), ConvolveOp.EDGE_NO_OP, null);
		return blur.filter(resizeImage, null);
	}
	
	private BufferedImage getBlankImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		IntStream.range(0, height).forEach(y -> IntStream.range(0, width).forEach(x -> image.setRGB(x, y, 0)));
		return image;
	}
}


