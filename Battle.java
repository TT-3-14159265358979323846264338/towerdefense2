package battle;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;

import defaultdata.DefaultStage;
import defaultdata.EditImage;
import defaultdata.stage.StageData;
import defendthecastle.MainFrame;
import savedata.SaveComposition;
import screendisplay.DisplayStatus;

//バトル画面制御
public class Battle extends JPanel implements MouseListener, MouseMotionListener{
	JButton rangeDrawButton = new JButton();
	JButton targetButton = new JButton();
	JButton pauseButton = new JButton();
	JButton returnButton = new JButton();
	StageData StageData;
	BufferedImage stageImage;
	List<BufferedImage> placementImage = new DefaultStage().getPlacementImage(4);
	BattleUnit[] unitMainData;//右武器/コア用　攻撃・被弾などの判定はこちらで行う
	BattleUnit[] unitLeftData;//左武器用
	BattleFacility[] facilityData;
	BattleEnemy[] enemyData;
	int targetCode = 0;
	Map<Integer, String> targetMap = new HashMap<>();{
		targetMap.put(0, "HP割合 低");
		targetMap.put(1, "HP割合 高");
		targetMap.put(2, "距離 近");
		targetMap.put(3, "距離 遠");
	}
	Point mouse;
	int select;
	boolean canSelect;
	final static int SIZE = 28;
	
	public Battle(MainFrame MainFrame, StageData StageData, int difficultyCode) {
		addMouseListener(this);
    	addMouseMotionListener(this);
    	this.StageData = StageData;
		install(StageData);
		addRangeDrawButton();
		addTargetButton();
		addPauseButton();
		addReturnButton(MainFrame);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setButton(rangeDrawButton, "射程表示", 1010, 465, 95, 40);
		setButton(targetButton, targetMap.get(targetCode), 1110, 465, 95, 40);
		setButton(pauseButton, "一時停止", 1010, 515, 95, 40);
		setButton(returnButton, "降参", 1110, 515, 95, 40);
		drawField(g);
		drawBackground(g);
		drawUnit(g);
		drawSelectUnit(g);
		requestFocus();
	}
	
	private void install(StageData StageData) {
		stageImage = new EditImage().input(StageData.getImageName(), 2);
		SaveComposition SaveComposition = new SaveComposition();
		SaveComposition.load();
		List<List<Integer>> composition = SaveComposition.getAllCompositionList().get(SaveComposition.getSelectNumber());
		unitMainData = IntStream.range(0, composition.size()).mapToObj(i -> new BattleUnit(composition.get(i), new Point(initialX(i), initialY(i)))).toArray(BattleUnit[]::new);
		unitLeftData = IntStream.range(0, composition.size()).mapToObj(i -> new BattleUnit(composition.get(i))).toArray(BattleUnit[]::new);;
		facilityData = IntStream.range(0, StageData.getFacility().size()).mapToObj(i -> new BattleFacility(StageData, i)).toArray(BattleFacility[]::new);
		//enemyData = IntStream.range(0, StageData.getEnemy().size()).mapToObj(i -> new BattleEnemy(StageData, i)).toArray(enemyData[]::new);
		
		
		
	}
	
	private void addRangeDrawButton() {
		add(rangeDrawButton);
		rangeDrawButton.addActionListener(e->{
			showMessageDialog(null, "現在調整中");
		});
	}
	
	private void addTargetButton() {
		add(targetButton);
		targetButton.addActionListener(e->{
			targetCode = (targetCode < targetMap.size() - 1)? targetCode + 1: 0;
		});
	}
	
	private void addPauseButton() {
		add(pauseButton);
		pauseButton.addActionListener(e->{
			showMessageDialog(null, "現在調整中");
		});
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.selectStageDraw();
		});
	}
	
	private void setButton(JButton button, String name, int x, int y, int width, int height) {
		button.setText(name);
		button.setBounds(x, y, width, height);
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 12));
	}
	
	private int initialX(int i) {
		return 1015 + i % 2 * 100;
	}
	
	private int initialY(int i) {
		return 55 + i / 2 * 100;
	}
	
	private void drawField(Graphics g) {
		g.drawImage(stageImage, 0, 0, this);
		IntStream.range(0, StageData.getPlacementPoint().size()).forEach(i -> StageData.getPlacementPoint().get(i).stream().forEach(j -> g.drawImage(placementImage.get(i), j.x, j.y, this)));
		IntStream.range(0, facilityData.length).forEach(i -> g.drawImage(facilityData[i].getActivate()? facilityData[i].getActionImage(): facilityData[i].getBreakImage(), facilityData[i].getPosition().x, facilityData[i].getPosition().y, this));
	}
	
	private void drawBackground(Graphics g) {
		IntStream.range(0, 8).forEach(i -> {
			switch(unitMainData[i].getType()) {
			case 0:
				g.setColor(new Color(255, 220, 220));
				g.fillRect(initialX(i) - 5, initialY(i) - 5, 100, 100);
				break;
			case 1:
				g.setColor(new Color(220, 220, 255));
				g.fillRect(initialX(i) - 5, initialY(i) - 5, 100, 100);
				break;
			case 2:
				g.setColor(new Color(255, 220, 220));
				g.fillRect(initialX(i) - 5, initialY(i) - 5, 50, 100);
				g.setColor(new Color(220, 220, 255));
				g.fillRect(initialX(i) + 45, initialY(i) - 5, 50, 100);
				break;
			default:
				break;
			}
		});
		g.setColor(Color.BLACK);
		IntStream.range(0, 3).forEach(i -> g.drawLine(1010 + i * 100, 50, 1010 + i * 100, 450));
		IntStream.range(0, 5).forEach(i -> g.drawLine(1010, 50 + i * 100, 1210, 50 + i * 100));
	}
	
	private void drawUnit(Graphics g) {
		IntStream.range(0, 8).forEach(i -> {
			Point position = unitMainData[i].getPosition();
			try {
				g.drawImage(unitMainData[i].getActionImage(), position.x, position.y, this);
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			g.drawImage(unitMainData[i].getCoreImage(), position.x, position.y, this);
			try {
				g.drawImage(unitLeftData[i].getActionImage(), position.x, position.y, this);
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		});
	}
	
	private void drawSelectUnit(Graphics g) {
		if(canSelect) {
			try {
				g.drawImage(unitMainData[select].getDefaultImage(), mouse.x - 45, mouse.y - 45, this);
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			g.drawImage(unitMainData[select].getCoreImage(), mouse.x - 45, mouse.y - 45, this);
			try {
				g.drawImage(unitLeftData[select].getDefaultImage(), mouse.x - 45, mouse.y - 45, this);
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(canSelect) {
			mouse = e.getPoint();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		IntStream.range(0, unitMainData.length).forEach(i -> {
			int x = unitMainData[i].getPosition().x + 30;
			int y = unitMainData[i].getPosition().y + 30;
			if(ValueRange.of(x, x + SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + SIZE).isValidIntValue(e.getY())) {
				new DisplayStatus().unit(unitMainData[i], unitLeftData[i]);
			}
		});
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mouse = e.getPoint();
		IntStream.range(0, unitMainData.length).filter(i -> !unitMainData[i].getActivate()).forEach(i -> {
			int x = initialX(i) + 30;
			int y = initialY(i) + 30;
			if(ValueRange.of(x, x + SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + SIZE).isValidIntValue(e.getY())) {
				select = i;
				canSelect = true;
			}
		});
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(canSelect) {
			mouse = e.getPoint();
			switch(unitMainData[select].getType()) {
			case 0:
				placeUnit(0);
				placeUnit(2);
				break;
			case 1:
				placeUnit(1);
				placeUnit(2);
				break;
			case 2:
				placeUnit(0);
				placeUnit(1);
				placeUnit(2);
				break;
			default:
				break;
			}
			canSelect = false;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	private void placeUnit(int placementCode) {
		Function<Integer, Integer> correctPosition = (position) -> {
			return position - SIZE;
		};
		Predicate<Point> positionCheck = (point) -> {
			return Stream.of(unitMainData).noneMatch(i -> i.getPosition().x == correctPosition.apply(point.x)
					&& i.getPosition().y == correctPosition.apply(point.y));
		};
		StageData.getPlacementPoint().get(placementCode).stream().filter(i -> positionCheck.test(i)).forEach(i -> {
			if(ValueRange.of(i.x, i.x + SIZE).isValidIntValue(mouse.x)
					&& ValueRange.of(i.y, i.y + SIZE).isValidIntValue(mouse.y)) {
				unitMainData[select].activate(new Point(correctPosition.apply(i.x), correctPosition.apply(i.y)));
			}
		});
	}
	
	
	
	
	
	
	
}