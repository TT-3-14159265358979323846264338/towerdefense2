package menuitemget;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import defaultdata.DefaultData;
import mainframe.MainFrame;

//ガチャ
public class MenuItemGet extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	Timer timer = new Timer(20, this);
	JButton returnButton = new JButton();
	JButton selectButton = new JButton();
	BufferedImage ballImage = new DefaultData().getBallImage(2);
	List<BufferedImage> halfBallImage = new ArrayList<>(new DefaultData().getHalfBallImage(2));
	BufferedImage handleImage = new DefaultData().getHandleImage(2);
	List<BufferedImage> machineImage = new ArrayList<>(new DefaultData().getMachineImage(2));
	boolean canRepeat;
	boolean canTurn;
	boolean canPlay;
	int startPointX;
	int startPointY;
	int activePointX;
	int activePointY;
	int count = 0;
	
	public MenuItemGet(MainFrame MainFrame) {
		addMouseListener(this);
		addMouseMotionListener(this);
		editImage();
		addSelectButton();
		addReturnButton(MainFrame);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSelectButton();
		setReturnButton();
		setGachaImage(g);
	}
	
	private void editImage() {
		machineImage.set(1, machineImage.get(1).getSubimage(0, 0, machineImage.get(1).getWidth(), machineImage.get(1).getHeight() - 70));
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
	
	private void setGachaImage(Graphics g) {
		double angle = getAngle();
		autoTurn(angle);
		g.drawImage(machineImage.get(0), 55, 20, null);
		g.drawImage(machineImage.get(1), 55, 20, null);
		g.drawImage(gethandleImage(angle), 145, 220, null);
	}
	
	private BufferedImage gethandleImage(double angle) {
		int width = handleImage.getWidth();
		int height = handleImage.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setRGB(x, y, 0);
			}
		}
		Graphics2D g2 =  (Graphics2D) image.getGraphics();
		AffineTransform rotate = new AffineTransform();
		rotate.setToRotation(angle, width / 2, height / 2);
		g2.setTransform(rotate);
		g2.drawImage(handleImage, 0, 0, null);
		g2.dispose();
		return image;
	}
	
	private double getAngle() {
		if(canTurn) {
			double[] x = {175, activePointX, startPointX};
			double[] y = {250, activePointY, startPointY};
			double[] distance = new double[3];
			distance[0] = Math.sqrt(Math.pow(x[1] - x[2], 2) + Math.pow(y[1] - y[2], 2));
			distance[1] = Math.sqrt(Math.pow(x[0] - x[2], 2) + Math.pow(y[0] - y[2], 2));
			distance[2] = Math.sqrt(Math.pow(x[0] - x[1], 2) + Math.pow(y[0] - y[1], 2));
			double angle = Math.acos((Math.pow(distance[0], 2) - Math.pow(distance[1], 2) - Math.pow(distance[2], 2)) / (-2 * distance[1] * distance[2]));
			return angle;
		}
		if(Math.PI * 2 < (double) (count * 0.1)) {
			return 0;
		}else {
			return (double) (count * 0.1);
		}
	}
	
	private void autoTurn(double angle) {
		if(canPlay) {
			if(100 < count) {
				timer.stop();
				count = 0;
				canPlay = false;
				addMouseListener(this);
				addMouseMotionListener(this);
				returnButton.setEnabled(true);
				selectButton.setEnabled(true);
			}
		}else {
			if(Math.PI / 2 < angle) {
				timer.start();
				count = (int) (Math.PI / 0.1 / 2);
				canTurn = false;
				canPlay = true;
				removeMouseListener(this);
				removeMouseMotionListener(this);
				returnButton.setEnabled(false);
				selectButton.setEnabled(false);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		activePointX = e.getX();
		activePointY = e.getY();
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
		canTurn = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		canTurn = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		repaint();
	}

	
	
	
	
	
	
	
	
}