package defendthecastle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import defaultdata.DataOther;
import defaultdata.DataUnit;
import defaultdata.EditImage;

//トップメニュー画面
public class MenuMain extends JPanel implements ActionListener{
	final static int NUMBER = 20;
	Timer timer = new Timer(300, this);
	MainFrame MainFrame;
	FallMotion[] FallMotion = IntStream.range(0, NUMBER).mapToObj(i -> new FallMotion()).toArray(FallMotion[]::new);
	FinalMotion[] FinalMotion = IntStream.range(0, NUMBER).mapToObj(i -> new FinalMotion(i)).toArray(FinalMotion[]::new);
	JButton itemGetButton = new JButton();
	JButton itemDisposeButton = new JButton();
	JButton compositionButton = new JButton();
	JButton selectStageButton = new JButton();
	BufferedImage titleImage = new DataOther().getTitleImage(2);
	List<BufferedImage> coreImage = new DataUnit().getCoreImage(1);
	List<Integer> randamList = IntStream.range(0, NUMBER).mapToObj(i -> new Random().nextInt(coreImage.size())).toList();
	int count;
	
	protected MenuMain(MainFrame MainFrame) {
		this.MainFrame = MainFrame;
		setBackground(new Color(240, 170, 80));
		addItemGetButton();
		addItemDisposeButton();
		addCompositionButton();
		addBattleButton();
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setItemGetButton();
		setItemDisposeButton();
		setCompositionButton();
		setBattleButton();
		drawImage(g);
	}
	
	private void addItemGetButton() {
		add(itemGetButton);
		itemGetButton.addActionListener(e->{
			timerStop();
			MainFrame.itemGetMenuDraw();
		});
	}
	
	private void setItemGetButton() {
		itemGetButton.setText("ガチャ");
		itemGetButton.setBounds(10, 400, 130, 60);
		setButton(itemGetButton);
	}
	
	private void addItemDisposeButton() {
		add(itemDisposeButton);
		itemDisposeButton.addActionListener(e->{
			timerStop();
			MainFrame.itemDisposeMenuDraw();
		});
	}
	
	private void setItemDisposeButton() {
		itemDisposeButton.setText("リサイクル");
		itemDisposeButton.setBounds(150, 400, 130, 60);
		setButton(itemDisposeButton);
	}
	
	private void addCompositionButton() {
		add(compositionButton);
		compositionButton.addActionListener(e->{
			timerStop();
			MainFrame.compositionDraw();
		});
	}
	
	private void setCompositionButton() {
		compositionButton.setText("ユニット編成");
		compositionButton.setBounds(290, 400, 130, 60);
		setButton(compositionButton);
	}
	
	private void addBattleButton() {
		add(selectStageButton);
		selectStageButton.addActionListener(e->{
			timerStop();
			MainFrame.selectStageDraw();
		});
	}
	
	private void setBattleButton() {
		selectStageButton.setText("ステージ選択");
		selectStageButton.setBounds(430, 400, 130, 60);
		setButton(selectStageButton);
	}
	
	private void timerStop() {
		Stream.of(FallMotion).forEach(i -> i.timerStop());
		Stream.of(FinalMotion).forEach(i -> i.timerStop());
		timer.stop();
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
		button.setFocusable(false);
	}
	
	private void drawImage(Graphics g) {
		if(timer.isRunning()) {
			IntStream.range(0, NUMBER).filter(i -> FallMotion[i].getTimerStatus()).forEach(i -> g.drawImage(new EditImage().rotateImage(coreImage.get(randamList.get(i)), FallMotion[i].getAngle()), FallMotion[i].getX(), FallMotion[i].getY(), this));
		}else {
			IntStream.range(0, NUMBER).forEach(i -> g.drawImage(coreImage.get(randamList.get(i)), FinalMotion[i].getX(), FinalMotion[i].getY(), this));
			g.drawImage(titleImage, 40, 100, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			FallMotion[count].timerStart();
		}catch(Exception ignore) {
		}
		count++;
		if(Stream.of(FallMotion).noneMatch(i -> i.getTimerStatus())) {
			timer.stop();
			Stream.of(FinalMotion).forEach(i -> i.timerStart());
		}
	}
}

//落下コアの位置調整
class FallMotion implements ActionListener{
	Timer timer = new Timer(20, this);
	double angle = (double) (new Random().nextInt((int)(Math.PI * 2 * 100)) / 100);
	int x = new Random().nextInt(400);
	int y = -100;
	
	protected void timerStart() {
		timer.start();
	}
	
	protected void timerStop() {
		timer.stop();
	}
	
	protected boolean getTimerStatus() {
		return timer.isRunning();
	}
	
	protected double getAngle() {
		return angle;
	}
	
	protected int getX() {
		return x;
	}
	
	protected int getY() {
		return y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		angle += 0.1;
		y += 10;
		if(450 < y) {
			timerStop();
		}
	}
}

//最終画面の位置調整
class FinalMotion implements ActionListener{
	Timer timer = new Timer(50, this);
	int number;
	int x;
	int y;
	int count;
	
	protected FinalMotion(int number) {
		this.number = number;
		x = 100 * (number % 5);
		y = 300;
	}
	
	protected void timerStart() {
		timer.start();
	}
	
	protected void timerStop() {
		timer.stop();
	}
	
	protected int getX() {
		return x;
	}
	
	protected int getY() {
		return y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		y -= 10 * (number / 5);
		count ++;
		if(10 < count) {
			timerStop();
		}
	}
}