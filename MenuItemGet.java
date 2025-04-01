package menuitemget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import defaultdata.DefaultData;
import mainframe.MainFrame;

//ガチャ本体
public class MenuItemGet extends JPanel implements ActionListener{
	JButton returnButton = new JButton();
	JButton selectButton = new JButton();
	BufferedImage ballImage = new DefaultData().getBallImage(2);
	List<BufferedImage> halfBallImage = new ArrayList<>(new DefaultData().getHalfBallImage(2));
	BufferedImage handleImage = new DefaultData().getHandleImage(2);
	List<BufferedImage> machineImage = new ArrayList<>(new DefaultData().getMachineImage(2));
	BufferedImage turnImage = new DefaultData().getTurnImage(2);
	BufferedImage effectImage = new DefaultData().getEffectImage(1);
	boolean canRepeat;
	OpenBallMotion OpenBallMotion = new OpenBallMotion(this);
	BallMotion BallMotion = new BallMotion(OpenBallMotion);
	HandleMotion HandleMotion = new HandleMotion(this, BallMotion);
	Timer timer = new Timer(50, this);
	double angle;
	boolean canPlay = true;
	
	public MenuItemGet(MainFrame MainFrame) {
		addSelectButton();
		addReturnButton(MainFrame);
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSelectButton();
		setReturnButton();
		drawGachaImage(g);
	}
	
	private void addSelectButton() {
		add(selectButton);
		selectButton.addActionListener(e->{
			canRepeat = canRepeat? false: true;
		});
	}
	
	private void setSelectButton() {
		selectButton.setText(canRepeat? "10連": "1連");
		selectButton.setBounds(70, 400, 100, 60);
		setButton(selectButton);
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(180, 400, 100, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		button.setFocusable(false);
	}
	
	protected void activatePanel() {
		returnButton.setEnabled(true);
		selectButton.setEnabled(true);
		canPlay = true;
	}
	
	protected void deactivatePanel() {
		returnButton.setEnabled(false);
		selectButton.setEnabled(false);
		canPlay = false;
	}

	private void drawGachaImage(Graphics g) {
		g.drawImage(machineImage.get(0), 55, 20, null);
		Point point = BallMotion.getBallPosition();
		g.drawImage(new EditImage().getImage(ballImage, BallMotion.getBallAngel()), point.x, point.y, null);
		g.drawImage(machineImage.get(1), 55, 20, null);
		g.drawImage(new EditImage().getImage(handleImage, HandleMotion.getAngle()), 145, 220, null);
		if(OpenBallMotion.getTimerStatus()) {
			point = OpenBallMotion.getBottomBallPosition();
			g.drawImage(new EditImage().getImage(halfBallImage.get(0), OpenBallMotion.getBottomBallAngel()), point.x, point.y, null);
			point = OpenBallMotion.getTopBallPosition();
			g.drawImage(new EditImage().getImage(halfBallImage.get(1), OpenBallMotion.getTopBallAngel()), point.x, point.y, null);
			int expansion = OpenBallMotion.getExpansion();
			int color = OpenBallMotion.getColor();
			g.drawImage(new EditImage().getImage(effectImage, expansion, new Color(255, 255, color, color).getRGB()), 30 - expansion / 2, 210 - expansion / 2, null);
		}
		if(canPlay) {
			g.drawImage(new EditImage().getImage(turnImage, angle), 105, 180, null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.03;
		if(Math.PI * 10000 < angle) {
			angle = 0;
		}
	}
}

//ガチャハンドルの調整
class HandleMotion implements MouseListener, MouseMotionListener, ActionListener{
	MenuItemGet MenuItemGet;
	BallMotion BallMotion;
	Timer timer = new Timer(20, this);
	int startPointX;
	int startPointY;
	int activePointX;
	int activePointY;
	double angle;
	
	protected HandleMotion(MenuItemGet MenuItemGet, BallMotion BallMotion) {
		this.MenuItemGet = MenuItemGet;
		this.BallMotion = BallMotion;
		addListener();
	}
	
	protected void addListener() {
		MenuItemGet.addMouseListener(this);
		MenuItemGet.addMouseMotionListener(this);
	}
	
	private void removeListener() {
		MenuItemGet.removeMouseListener(this);
		MenuItemGet.removeMouseMotionListener(this);
	}
	
	protected double getAngle() {
		if(timer.isRunning()) {
			if(Math.PI * 2 < angle) {
				autoTurnStop();
			}
			return angle;
		}else {
			double[] x = {175, activePointX, startPointX};
			double[] y = {250, activePointY, startPointY};
			double[] distance = new double[3];
			distance[0] = Math.sqrt(Math.pow(x[1] - x[2], 2) + Math.pow(y[1] - y[2], 2));
			distance[1] = Math.sqrt(Math.pow(x[0] - x[2], 2) + Math.pow(y[0] - y[2], 2));
			distance[2] = Math.sqrt(Math.pow(x[0] - x[1], 2) + Math.pow(y[0] - y[1], 2));
			angle = Math.acos((Math.pow(distance[0], 2) - Math.pow(distance[1], 2) - Math.pow(distance[2], 2)) / (-2 * distance[1] * distance[2]));
			return angle;
		}
	}
	
	private void autoTurnStart() {
		MenuItemGet.deactivatePanel();
		removeListener();
		timer.start();
	}
	
	private void autoTurnStop() {
		timer.stop();
		BallMotion.timerStart(this);
		reset();
	}
	
	private void reset() {
		startPointX = 0;
		startPointY = 0;
		activePointX = 0;
		activePointY = 0;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		activePointX = e.getX();
		activePointY = e.getY();
		if(Math.PI / 2.0 < getAngle()) {
			autoTurnStart();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPointX = e.getX();
		startPointY = e.getY();
		activePointX = e.getX();
		activePointY = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		reset();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.1;
	}
}

//排出ボールの調整
class BallMotion implements ActionListener{
	OpenBallMotion OpenBallMotion;
	HandleMotion HandleMotion;
	Timer timer = new Timer(30, this);
	double angle;
	Point point;
	List<Integer> moveList = Arrays.asList(296, 310, 360, 320, 340);
	List<Integer> distanceList = Arrays.asList(3, 1, 2, -2, 1);
	int moveNumber;
	
	protected BallMotion(OpenBallMotion OpenBallMotion) {
		this.OpenBallMotion = OpenBallMotion;
		reset();
	}
	
	protected void timerStart(HandleMotion HandleMotion) {
		this.HandleMotion = HandleMotion;
		timer.start();
	}
	
	private void timerStop() {
		timer.stop();
		reset();
	}
	
	protected double getBallAngel() {
		return angle;
	}
	
	protected Point getBallPosition() {
		return point;
	}
	
	private void reset() {
		angle = 0;
		point = new Point(159, 275);
		moveNumber = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.2;
		point.y += moveDistance();
	}
	
	private int moveDistance() {
		try {
			if(point.y == moveList.get(moveNumber)) {
				moveNumber++;
			}
			return distanceList.get(moveNumber);
		}catch(Exception e) {
			timerStop();
			OpenBallMotion.timerStart(HandleMotion);
			return 0;
		}
	}
}

//ボール開封の調整
class OpenBallMotion implements ActionListener{
	MenuItemGet MenuItemGet;
	HandleMotion HandleMotion;
	Timer timer = new Timer(40, this);
	double bottomAngle;
	double topAngle;
	Point bottmPoint;
	Point topPoint;
	int color;
	int expansion;
	
	protected OpenBallMotion(MenuItemGet MenuItemGet) {
		this.MenuItemGet = MenuItemGet;
		reset();
	}
	
	protected void timerStart(HandleMotion HandleMotion) {
		this.HandleMotion = HandleMotion;
		timer.start();
	}
	
	private void timerStop() {
		timer.stop();
		reset();
		HandleMotion.addListener();
		MenuItemGet.activatePanel();
	}
	
	protected boolean getTimerStatus() {
		return timer.isRunning();
	}
	
	protected double getBottomBallAngel() {
		return bottomAngle;
	}
	
	protected double getTopBallAngel() {
		return topAngle;
	}
	
	protected Point getBottomBallPosition() {
		return bottmPoint;
	}
	
	protected Point getTopBallPosition() {
		return topPoint;
	}
	
	protected int getColor() {
		return color;
	}
	
	protected int getExpansion() {
		return expansion;
	}
	
	private void reset() {
		bottomAngle = 0;
		topAngle = 0;
		bottmPoint = new Point(160, 345);
		topPoint = new Point(160, 335);
		color = 0;
		expansion = -250;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		bottomAngle += 0.04;
		topAngle += 0.1;
		bottmPoint.x -= 2;
		bottmPoint.y += 1;
		topPoint.x += 2;
		topPoint.y -= 2;
		color += 5;
		expansion += 20;
		if(1 < bottomAngle) {
			timerStop();
		}
	}
}

//画像編集
class EditImage{
	protected BufferedImage getImage(BufferedImage originalImage, double angle) {
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
	
	protected BufferedImage getImage(BufferedImage originalImage, int expansion, int color) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage image = getBlankImage(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(originalImage.getRGB(x, y) == new Color(0, 0, 0).getRGB()) {
					image.setRGB(x, y, color);
				}
			}
		}
		int resizeWidth = width + expansion;
		int resizeHeight = height + expansion;
		BufferedImage resizeImage = getBlankImage(resizeWidth, resizeHeight);
		resizeImage.createGraphics().drawImage(
	    	image.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_AREA_AVERAGING),
	        0, 0, resizeWidth, resizeHeight, null);
		int pixel = 9;
		float[] matrix = new float[pixel * pixel];
		for(int i = 0; i < matrix.length; i++) {
			matrix[i] = 1.5f / (pixel * pixel);//透過度あるため、色濃いめの1.5f
		}
		ConvolveOp blur = new ConvolveOp(new Kernel(pixel, pixel, matrix), ConvolveOp.EDGE_NO_OP, null);
		return blur.filter(resizeImage, null);
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

//ガチャ結果画面
class GachaResult extends JFrame{
	
}

//ガチャ結果表示
class DrawResult extends JPanel{
	
}