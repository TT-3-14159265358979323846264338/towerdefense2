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

import defaultdata.DefaultOther;
import defaultdata.DefaultUnit;
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
	BufferedImage titleImage = new DefaultOther().getTitleImage(2);
	List<BufferedImage> coreImage = IntStream.range(0, DefaultUnit.CORE_DATA_MAP.size()).mapToObj(i -> DefaultUnit.CORE_DATA_MAP.get(i).getImage(1)).toList();
	List<Integer> randamList = IntStream.range(0, NUMBER).mapToObj(i -> new Random().nextInt(coreImage.size())).toList();
	int count;
	
	//テスト用
	JButton testButton = new JButton();
	
	protected MenuMain(MainFrame MainFrame) {
		this.MainFrame = MainFrame;
		setBackground(new Color(240, 170, 80));
		addItemGetButton();
		addItemDisposeButton();
		addCompositionButton();
		addBattleButton();
		addTestButton();
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setButton(itemGetButton, "ガチャ", 10, 400, 130, 60);
		setButton(itemDisposeButton, "リサイクル", 150, 400, 130, 60);
		setButton(compositionButton, "ユニット編成", 290, 400, 130, 60);
		setButton(selectStageButton, "ステージ選択", 430, 400, 130, 60);
		setButton(testButton, "セーブデータ編集", 410, 0, 160, 40);
		drawImage(g);
		requestFocus();
	}
	
	private void addItemGetButton() {
		add(itemGetButton);
		itemGetButton.addActionListener(e->{
			timerStop();
			MainFrame.itemGetMenuDraw();
		});
	}
	
	private void addItemDisposeButton() {
		add(itemDisposeButton);
		itemDisposeButton.addActionListener(e->{
			timerStop();
			MainFrame.itemDisposeMenuDraw();
		});
	}
	
	private void addCompositionButton() {
		add(compositionButton);
		compositionButton.addActionListener(e->{
			timerStop();
			MainFrame.compositionDraw();
		});
	}
	
	private void addBattleButton() {
		add(selectStageButton);
		selectStageButton.addActionListener(e->{
			timerStop();
			MainFrame.selectStageDraw();
		});
	}
	
	private void setButton(JButton button, String name, int x, int y, int width, int height) {
		button.setText(name);
		button.setBounds(x, y, width, height);
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
	}
	
	private void timerStop() {
		Stream.of(FallMotion).forEach(i -> i.timerStop());
		Stream.of(FinalMotion).forEach(i -> i.timerStop());
		timer.stop();
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
	
	//テスト用
	private void addTestButton() {
		add(testButton);
		testButton.addActionListener(e->{
			new TestDataEdit();
		});
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