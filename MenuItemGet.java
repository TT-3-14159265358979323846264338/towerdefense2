package menuitemget;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import dataother.DataOther;
import dataunit.DataUnit;
import displaystatus.DisplayStatus;
import editimage.EditImage;
import mainframe.MainFrame;
import savegameprogress.SaveGameProgress;
import saveholditem.SaveHoldItem;

//ガチャ本体
public class MenuItemGet extends JPanel implements ActionListener{
	JLabel medalLabel = new JLabel();
	JButton gachaDetailButton = new JButton();
	JButton repeatButton = new JButton();
	JButton returnButton = new JButton();
	BufferedImage ballImage = new DataOther().getBallImage(2);
	List<BufferedImage> halfBallImage = new ArrayList<>(new DataOther().getHalfBallImage(2));
	BufferedImage handleImage = new DataOther().getHandleImage(2);
	List<BufferedImage> machineImage = new ArrayList<>(new DataOther().getMachineImage(2));
	BufferedImage turnImage = new DataOther().getTurnImage(2);
	BufferedImage effectImage = new DataOther().getEffectImage(1);
	int[] gachaMode = {0, 0};//① ガチャ回数コード, ② ガチャ種類コード (詳細はDefaultLineupで定義)でリスト化
	final static Map<Integer, Integer> modeMap = new HashMap<>();{
		modeMap.put(0, 1);
		modeMap.put(1, 5);
		modeMap.put(2, 10);
	}
	String[] selectGachaName = {
			"通常闇鍋ガチャ",
			"通常コアガチャ",
			"通常武器ガチャ"
	};
	JList<String> selectGachaJList = new JList<>(selectGachaName);
	JScrollPane selectGachaScroll = new JScrollPane();
	HoldMedal HoldMedal = new HoldMedal(gachaMode);
	OpenBallMotion OpenBallMotion = new OpenBallMotion(this, HoldMedal, gachaMode);
	BallMotion BallMotion = new BallMotion(OpenBallMotion);
	HandleMotion HandleMotion = new HandleMotion(this, HoldMedal, BallMotion);
	Timer timer = new Timer(50, this);
	double angle;
	boolean canPlay = true;
	
	public MenuItemGet(MainFrame MainFrame) {
		addMedalLabel();
		addGachaDetailButton();
		addRepeatButton();
		addReturnButton(MainFrame);
		addGachaScroll();
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setMedalLabel();
		setGachaDetailButton();
		setRepeatButton();
		setReturnButton();
		setGachaScroll();
		drawGachaImage(g);
		requestFocus();
	}
	
	private void addMedalLabel() {
		add(medalLabel);
		medalLabel.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void setMedalLabel() {
		medalLabel.setText("メダル: " + HoldMedal.getMedal() + "枚");
		medalLabel.setBounds(0, 0, 200, 30);
		medalLabel.setBounds(350, 20, 200, 30);
		medalLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addGachaDetailButton() {
		add(gachaDetailButton);
		gachaDetailButton.addActionListener(e->{
			new GachaLineup(gachaMode[1]);
		});
	}
	
	private void setGachaDetailButton() {
		gachaDetailButton.setText("<html>ガチャ詳細");
		gachaDetailButton.setBounds(350, 330, 210, 60);
		setButton(gachaDetailButton);
	}
	
	private void addRepeatButton() {
		add(repeatButton);
		repeatButton.addActionListener(e->{
			gachaMode[0] = (gachaMode[0] < modeMap.size() - 1)? gachaMode[0] + 1: 0;
		});
	}
	
	private void setRepeatButton() {
		repeatButton.setText("<html>&nbsp;" + modeMap.get(gachaMode[0]) + "連<br>" + HoldMedal.useMedal() + "枚");
		repeatButton.setBounds(350, 400, 100, 60);
		setButton(repeatButton);
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("<html>戻る");
		returnButton.setBounds(460, 400, 100, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
	}
	
	private void addGachaScroll() {
		selectGachaScroll.getViewport().setView(selectGachaJList);
    	add(selectGachaScroll);
    	selectGachaJList.setSelectedIndex(gachaMode[1]);
	}
	
	private void setGachaScroll() {
		selectGachaJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		selectGachaScroll.setBounds(350, 60, 210, 260);
		selectGachaScroll.setPreferredSize(selectGachaScroll.getSize());
    	gachaMode[1] = selectGachaJList.getSelectedIndex();
	}

	protected void activatePanel() {
		setPanel(true);
	}
	
	protected void deactivatePanel() {
		setPanel(false);
	}
	
	private void setPanel(boolean canActivate) {
		returnButton.setEnabled(canActivate);
		gachaDetailButton.setEnabled(canActivate);
		repeatButton.setEnabled(canActivate);
		selectGachaJList.setEnabled(canActivate);
		canPlay = canActivate;
	}
	
	private void drawGachaImage(Graphics g) {
		g.drawImage(machineImage.get(0), 55, 20, null);
		Point point = BallMotion.getBallPosition();
		g.drawImage(new EditImage().rotateImage(ballImage, BallMotion.getBallAngel()), point.x, point.y, null);
		g.drawImage(machineImage.get(1), 55, 20, null);
		g.drawImage(new EditImage().rotateImage(handleImage, HandleMotion.getAngle()), 145, 220, null);
		if(OpenBallMotion.getTimerStatus()) {
			List<Double> angle = OpenBallMotion.getBallAngle();
			List<Point> position = OpenBallMotion.getBallPosition();
			Consumer<Integer> drawBallOpen = (i) -> {
				g.drawImage(new EditImage().rotateImage(halfBallImage.get(i), angle.get(i)), position.get(i).x, position.get(i).y, null);
			};
			drawBallOpen.accept(0);
			drawBallOpen.accept(1);
			int expansion = OpenBallMotion.getExpansion();
			int color = OpenBallMotion.getColor();
			g.drawImage(new EditImage().effectImage(effectImage, expansion, new Color(255, 255, color, color).getRGB()), 30 - expansion / 2, 210 - expansion / 2, null);
		}
		if(canPlay && HoldMedal.checkMedal()) {
			g.drawImage(new EditImage().rotateImage(turnImage, angle), 105, 180, null);
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

//保有メダル
class HoldMedal{
	SaveGameProgress SaveGameProgress;
	int[] gachaMode;
	int medal;
	final static int USE = 100;
	
	protected HoldMedal(int[] gachaMode) {
		try {
			ObjectInputStream loadProgressData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(savegameprogress.SaveGameProgress.PROGRESS_FILE)));
			SaveGameProgress = (SaveGameProgress) loadProgressData.readObject();
			loadProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		this.gachaMode = gachaMode;
		medal = SaveGameProgress.getMedal();
	}
	
	protected void save() {
		try {
			ObjectOutputStream saveProgressData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(savegameprogress.SaveGameProgress.PROGRESS_FILE)));
			saveProgressData.writeObject(new SaveGameProgress(SaveGameProgress.getClearStatus(), SaveGameProgress.getMeritStatus(), medal, SaveGameProgress.getSelectStage()));
			saveProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected int getMedal() {
		return medal;
	}
	
	protected void recountMedal() {
		medal -= useMedal();
	}
	
	protected boolean checkMedal() {
		return useMedal() <= medal;
	}
	
	protected int useMedal() {
		return MenuItemGet.modeMap.get(gachaMode[0]) * USE;
	}
}

//ガチャハンドルの調整
class HandleMotion implements MouseListener, MouseMotionListener, ActionListener{
	MenuItemGet MenuItemGet;
	HoldMedal HoldMedal;
	BallMotion BallMotion;
	Timer timer = new Timer(20, this);
	int startPointX;
	int startPointY;
	int activePointX;
	int activePointY;
	double angle;
	
	protected HandleMotion(MenuItemGet MenuItemGet, HoldMedal HoldMedal, BallMotion BallMotion) {
		this.MenuItemGet = MenuItemGet;
		this.HoldMedal = HoldMedal;
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
			BiFunction<Double, Double, Double> pow = (x1, x2) -> {
				return Math.pow(x1 - x2, 2);
			};
			BiFunction<Integer, Integer, Double> sqrt = (i, j) -> {
				return Math.sqrt(pow.apply(x[i], x[j]) + pow.apply(y[i], y[j]));
			};
			distance[0] = sqrt.apply(1, 2);
			distance[1] = sqrt.apply(0, 2);
			distance[2] = sqrt.apply(0, 1);
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
		if(HoldMedal.checkMedal()) {
			activePointX = e.getX();
			activePointY = e.getY();
			if(Math.PI / 2.0 < getAngle()) {
				autoTurnStart();
			}
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
	HoldMedal HoldMedal;
	HandleMotion HandleMotion;
	int[] gachaMode;
	Timer timer = new Timer(40, this);
	double bottomAngle;
	double topAngle;
	Point bottomPoint;
	Point topPoint;
	int color;
	int expansion;
	
	protected OpenBallMotion(MenuItemGet MenuItemGet, HoldMedal HoldMedal, int[] gachaMode) {
		this.MenuItemGet = MenuItemGet;
		this.HoldMedal = HoldMedal;
		this.gachaMode = gachaMode;
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
		if(new DefaultLineup(gachaMode[1]).aptitudeTest()) {
			HoldMedal.recountMedal();
			new GachaResult(gachaMode);
		}
	}
	
	protected boolean getTimerStatus() {
		return timer.isRunning();
	}
	
	protected List<Double> getBallAngle(){
		return Arrays.asList(bottomAngle, topAngle);
	}
	
	protected List<Point> getBallPosition(){
		return Arrays.asList(bottomPoint, topPoint);
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
		bottomPoint = new Point(160, 345);
		topPoint = new Point(160, 335);
		color = 0;
		expansion = -250;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		bottomAngle += 0.04;
		topAngle += 0.1;
		bottomPoint.x -= 2;
		bottomPoint.y += 1;
		topPoint.x += 2;
		topPoint.y -= 2;
		color += 5;
		expansion += 20;
		if(1 < bottomAngle) {
			timerStop();
		}
	}
}

//ガチャ結果画面
class GachaResult extends JDialog{
	protected GachaResult(int[] gachaMode) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ガチャ結果");
		setSize(970, 300);
		setLocationRelativeTo(null);
		add(new DrawResult(gachaMode));
		setVisible(true);
	}
}

//ガチャ結果表示
class DrawResult extends JPanel implements MouseListener{
	SaveHoldItem SaveHoldItem;
	List<Integer> getCore = new ArrayList<>();
	List<Point> corePosition = new ArrayList<>();
	List<Integer> getWeapon = new ArrayList<>();
	List<Point> weaponPosition = new ArrayList<>();
	double total;
	int position;
	List<BufferedImage> coreImageList = new DataUnit().getCoreImage(2);
	List<BufferedImage> weaponImageList = new DataUnit().getWeaponImage(2);
	int unitSize = 80;
	
	protected DrawResult(int[] gachaMode){
		addMouseListener(this);
		setBackground(new Color(240, 170, 80));
		int number = MenuItemGet.modeMap.get(gachaMode[0]);
		DefaultLineup DefaultLineup = new DefaultLineup(gachaMode[1]);
		switch(gachaMode[0]) {
		case 0:
			position = 435;
			gacha(DefaultLineup);
			break;
		case 1:
			position = 255;
			IntStream.range(0, number).forEach(i -> gacha(DefaultLineup));
			break;
		case 2:
			position = 20;
			IntStream.range(0, number).forEach(i -> gacha(DefaultLineup));
			break;
		default:
			break;
		}
		save(gachaMode);
	}
	
	private void gacha(DefaultLineup DefaultLineup) {
		Consumer<List<Point>> addPosition = (list) -> {
			list.add(new Point(position, 90));
		};
		double value = Math.random() * 100;
		total = 0;
		if(result(value, DefaultLineup.getCoreLineup(), DefaultLineup.getCoreRatio(), getCore)) {
			addPosition.accept(corePosition);
		}else if(result(value, DefaultLineup.getWeaponLineup(), DefaultLineup.getWeaponRatio(), getWeapon)) {
			addPosition.accept(weaponPosition);
		}
		position += 90;
	}
	
	private boolean result(double value, List<Integer> lineupList, List<Double> ratioList, List<Integer> getList) {
		if(ratioList.size() != 0) {
			for(int i = 0; i < ratioList.size(); i++) {
				total += ratioList.get(i);
				if(value < total) {
					getList.add(lineupList.get(i));
					return true;
				}
			}
		}
		return false;
	}
	
	private void save(int[] gachaMode) {
		//保有アイテムの更新
		try {
			ObjectInputStream loadItemData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			SaveHoldItem = (SaveHoldItem) loadItemData.readObject();
			loadItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		List<Integer> coreNumberList = getItemList(SaveHoldItem.getCoreNumberList(), getCore);
		List<Integer> weaponNumberList = getItemList(SaveHoldItem.getWeaponNumberList(), getWeapon);
		try {
			ObjectOutputStream saveItemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			saveItemData.writeObject(new SaveHoldItem(coreNumberList, weaponNumberList));
			saveItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		//保有メダルの更新
		HoldMedal HoldMedal = new HoldMedal(gachaMode);
		HoldMedal.recountMedal();
		HoldMedal.save();
	}
	
	private List<Integer> getItemList(List<Integer> dataList, List<Integer> getList){
		int[] count = new int[dataList.size()];
		getList.stream().forEach(i -> count[i]++);
		return IntStream.range(0, count.length).mapToObj(i -> dataList.get(i) + count[i]).collect(Collectors.toList());
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g, getCore, coreImageList, corePosition);
		draw(g, getWeapon, weaponImageList, weaponPosition);
	}
	
	private void draw(Graphics g, List<Integer> getList, List<BufferedImage> imageList, List<Point> position) {
		if(getList.size() != 0) {
			IntStream.range(0, getList.size()).forEach(i -> g.drawImage(imageList.get(getList.get(i)), position.get(i).x, position.get(i).y, null));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int selectNumber = getSelectNumber(e.getPoint(), corePosition);
		if(0 <= selectNumber) {
			new DisplayStatus().core(coreImageList.get(getCore.get(selectNumber)), getCore.get(selectNumber));
			return;
		}
		selectNumber = getSelectNumber(e.getPoint(), weaponPosition);
		if(0 <= selectNumber) {
			new DisplayStatus().weapon(weaponImageList.get(getWeapon.get(selectNumber)), getWeapon.get(selectNumber));
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	private int getSelectNumber(Point point, List<Point> positionList) {
		for(int i = 0; i < positionList.size(); i++) {
			if(ValueRange.of(positionList.get(i).x, positionList.get(i).x + unitSize).isValidIntValue(point.x)
					&& ValueRange.of(positionList.get(i).y, positionList.get(i).y + unitSize).isValidIntValue(point.y)){
				return i;
			}
		}
		return -1;
	}
}

//ガチャ詳細
class GachaLineup extends JDialog{
	protected GachaLineup(int mode) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ガチャラインナップ");
		setSize(300, 600);
		setLocationRelativeTo(null);
		add(getLineupScrollPane(new DefaultLineup(mode)));
		setVisible(true);
	}
	
	private JScrollPane getLineupScrollPane(DefaultLineup DefaultLineup) {
		DefaultLineup.aptitudeTest();
		Function<Integer, String> getRarity = (count) -> {
			return "★" + count + " ";
		};
		Function<String, String> getName = (name) -> {
			return String.format("%-" + (60 - name.getBytes().length) + "s", name);
		};
		Function<Double, String> getRatio = (value) -> {
			return String.format("%.1f", value) + "%";
		};
		List<Integer> coreLineup = DefaultLineup.getCoreLineup();
		List<Double> coreRatio = DefaultLineup.getCoreRatio();
		List<Integer> weaponLineup = DefaultLineup.getWeaponLineup();
		List<Double> weaponRatio = DefaultLineup.getWeaponRatio();
		DefaultListModel<String> lineup = new DefaultListModel<String>();
		lineup.addElement("【コア確率】 " + getRatio.apply(getTotal(coreRatio)));
		lineup.addElement(" ");
		IntStream.range(0, coreLineup.size()).forEach(i -> {
			String coreName = getRarity.apply(DataUnit.CORE_RARITY_LIST.get(coreLineup.get(i))) + DataUnit.CORE_NAME_LIST.get(coreLineup.get(i));
			lineup.addElement(getName.apply(coreName) + getRatio.apply(coreRatio.get(i)));
		});
		if(getTotal(coreRatio) != 0) {
			lineup.addElement(" ");
		}
		lineup.addElement("【武器確率】 " + getRatio.apply(getTotal(weaponRatio)));
		lineup.addElement(" ");
		IntStream.range(0, weaponLineup.size()).forEach(i -> {
			String weaponName = getRarity.apply(DataUnit.WEAPON_RARITY_LIST.get(weaponLineup.get(i))) + DataUnit.WEAPON_NAME_LIST.get(weaponLineup.get(i));
			lineup.addElement(getName.apply(weaponName) + getRatio.apply(weaponRatio.get(i)));
		});
		JList<String> lineupJList = new JList<String>(lineup);
		lineupJList.setEnabled(false);
		return new JScrollPane(lineupJList);
	}
	
	private double getTotal(List<Double> list) {
		return list.stream().mapToDouble(i -> i).sum();
	}
}

/*ガチャのラインナップ
 * LineupSetには、そのセットで排出されるユニット番号をリスト化
 * その後、各ガチャのmodeでどのLineupSetと排出確率を使用するか指定する(addCore(), addWeapon())
 * 排出確率は、1つのLineupSet全体の排出確率を指定する
 * 各ガチャ全体の排出確率を100に必ずすること
 */
class DefaultLineup{
	final List<Integer> coreLineupSet1 = Arrays.asList(1, 2, 3, 4, 5);
	final List<Integer> coreLineupSet2 = Arrays.asList();
	final List<Integer> coreLineupSet3 = Arrays.asList();
	final List<Integer> weaponLineupSet1 = Arrays.asList(0, 1);
	final List<Integer> weaponLineupSet2 = Arrays.asList();
	final List<Integer> weaponLineupSet3 = Arrays.asList();
	
	List<Integer> coreLineup = new ArrayList<>();
	List<Double> coreRatio = new ArrayList<>();
	List<Integer> weaponLineup = new ArrayList<>();
	List<Double> weaponRatio = new ArrayList<>();
	
	protected DefaultLineup(int mode) {
		switch(mode) {
		case 0:
			addCore(coreLineupSet1, 50);
			addWeapon(weaponLineupSet1, 50);
			break;
		case 1:
			addCore(coreLineupSet1, 100);
			break;
		case 2:
			addWeapon(weaponLineupSet1, 100);
			break;
		default:
			break;
		}
	}
	
	private void addCore(List<Integer> lineupSet, double totalRatio) {
		coreLineup.addAll(lineupSet);
		coreRatio.addAll(getRatioList(lineupSet.size(), totalRatio));
	}
	
	private void addWeapon(List<Integer> lineupSet, double totalRatio) {
		weaponLineup.addAll(lineupSet);
		weaponRatio.addAll(getRatioList(lineupSet.size(), totalRatio));
	}
	
	private List<Double> getRatioList(int size, double totalRatio){
		return IntStream.range(0, size).mapToObj(i -> (double) (totalRatio / size)).toList();
	}
	
	protected boolean aptitudeTest() {
		double sum = coreRatio.stream().mapToDouble(Double::doubleValue).sum() + weaponRatio.stream().mapToDouble(Double::doubleValue).sum();
		if(Math.round(sum) != 100) {
			showMessageDialog(null, "このガチャモードは使用できません");
			return false;
		}
		return true;
	}
	
	protected List<Integer> getCoreLineup(){
		return coreLineup;
	}
	
	protected List<Double> getCoreRatio(){
		return coreRatio;
	}
	
	protected List<Integer> getWeaponLineup(){
		return weaponLineup;
	}
	
	protected List<Double> getWeaponRatio(){
		return weaponRatio;
	}
}