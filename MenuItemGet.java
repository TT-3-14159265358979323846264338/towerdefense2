package menuitemget;

import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import defaultdata.DefaultData;
import drawstatus.DrawStatus;
import mainframe.MainFrame;
import saveholditem.SaveHoldItem;

//ガチャ本体
public class MenuItemGet extends JPanel implements ActionListener{
	JButton gachaDetailButton = new JButton();
	JButton repeatButton = new JButton();
	JButton returnButton = new JButton();
	BufferedImage ballImage = new DefaultData().getBallImage(2);
	List<BufferedImage> halfBallImage = new ArrayList<>(new DefaultData().getHalfBallImage(2));
	BufferedImage handleImage = new DefaultData().getHandleImage(2);
	List<BufferedImage> machineImage = new ArrayList<>(new DefaultData().getMachineImage(2));
	BufferedImage turnImage = new DefaultData().getTurnImage(2);
	BufferedImage effectImage = new DefaultData().getEffectImage(1);
	int[] gachaMode = {0, 0};
	Map<Integer, String> modeMap = new HashMap<Integer, String>();{
		modeMap.put(0, "1連");
		modeMap.put(1, "5連");
		modeMap.put(2, "10連");
	}
	String[] selectGachaName = {
			"通常闇鍋ガチャ",
			"通常コアガチャ",
			"通常武器ガチャ"
	};
	JList<String> selectGachaJList = new JList<String>(selectGachaName);
	JScrollPane selectGachaScroll = new JScrollPane();
	OpenBallMotion OpenBallMotion = new OpenBallMotion(this, gachaMode);
	BallMotion BallMotion = new BallMotion(OpenBallMotion);
	HandleMotion HandleMotion = new HandleMotion(this, BallMotion);
	Timer timer = new Timer(50, this);
	double angle;
	boolean canPlay = true;
	
	public MenuItemGet(MainFrame MainFrame) {
		addGachaDetailButton();
		addRepeatButton();
		addReturnButton(MainFrame);
		addGachaScroll();
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setGachaDetailButton();
		setRepeatButton();
		setReturnButton();
		setGachaScroll();
		drawGachaImage(g);
	}
	
	private void addGachaDetailButton() {
		add(gachaDetailButton);
		gachaDetailButton.addActionListener(e->{
			new GachaLineup(gachaMode[1]);
		});
	}
	
	private void setGachaDetailButton() {
		gachaDetailButton.setText("ガチャ詳細");
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
		repeatButton.setText(modeMap.get(gachaMode[0]));
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
		returnButton.setText("戻る");
		returnButton.setBounds(460, 400, 100, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 22));
		button.setFocusable(false);
	}
	
	private void addGachaScroll() {
		selectGachaScroll.getViewport().setView(selectGachaJList);
    	add(selectGachaScroll);
    	selectGachaJList.setSelectedIndex(gachaMode[1]);
	}
	
	private void setGachaScroll() {
		selectGachaJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		selectGachaScroll.setPreferredSize(new Dimension(210, 300));
    	selectGachaScroll.setBounds(350, 20, 210, 300);
    	gachaMode[1] = selectGachaJList.getSelectedIndex();
	}

	protected void activatePanel() {
		returnButton.setEnabled(true);
		gachaDetailButton.setEnabled(true);
		repeatButton.setEnabled(true);
		selectGachaJList.setEnabled(true);
		canPlay = true;
	}
	
	protected void deactivatePanel() {
		returnButton.setEnabled(false);
		gachaDetailButton.setEnabled(false);
		repeatButton.setEnabled(false);
		selectGachaJList.setEnabled(false);
		canPlay = false;
	}
	
	private void drawGachaImage(Graphics g) {
		g.drawImage(machineImage.get(0), 55, 20, null);
		Point point = BallMotion.getBallPosition();
		g.drawImage(new EditImage().getImage(ballImage, BallMotion.getBallAngel()), point.x, point.y, null);
		g.drawImage(machineImage.get(1), 55, 20, null);
		g.drawImage(new EditImage().getImage(handleImage, HandleMotion.getAngle()), 145, 220, null);
		if(OpenBallMotion.getTimerStatus()) {
			List<Double> angle = OpenBallMotion.getBallAngle();
			List<Point> position = OpenBallMotion.getBallPosition();
			Consumer<Integer> drawBallOpen = (i) -> {
				g.drawImage(new EditImage().getImage(halfBallImage.get(i), angle.get(i)), position.get(i).x, position.get(i).y, null);
			};
			drawBallOpen.accept(0);
			drawBallOpen.accept(1);
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
	int[] gachaMode;
	Timer timer = new Timer(40, this);
	double bottomAngle;
	double topAngle;
	Point bottomPoint;
	Point topPoint;
	int color;
	int expansion;
	
	protected OpenBallMotion(MenuItemGet MenuItemGet, int[] gachaMode) {
		this.MenuItemGet = MenuItemGet;
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
		new GachaResult(gachaMode);
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
	DefaultLineup DefaultLineup;
	SaveHoldItem SaveHoldItem;
	List<Integer> getCore = new ArrayList<>();
	List<Point> corePosition = new ArrayList<>();
	List<Integer> getWeapon = new ArrayList<>();
	List<Point> weaponPosition = new ArrayList<>();
	double total;
	int position;
	List<BufferedImage> coreImageList = new DefaultData().getCoreImage(2);
	List<BufferedImage> weaponImageList = new DefaultData().getWeaponImage(2);
	final static int SIZE = 80;
	
	protected DrawResult(int[] gachaMode){
		addMouseListener(this);
		setBackground(new Color(240, 170, 80));
		DefaultLineup = new DefaultLineup(gachaMode[1]);
		switch(gachaMode[0]) {
		case 0:
			position = 435;
			gacha();
			break;
		case 1:
			position = 255;
			for(int i = 0; i < 5; i++) {
				gacha();
			}
			break;
		case 2:
			position = 20;
			for(int i = 0; i < 10; i++) {
				gacha();
			}
			break;
		default:
			break;
		}
		save();
	}
	
	private void gacha() {
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
	
	private void save() {
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
	}
	
	private List<Integer> getItemList(List<Integer> dataList, List<Integer> getList){
		List<Integer> finalList = new ArrayList<>();
		int[] count = new int[dataList.size()];
		for(int i: getList) {
			count[i]++;
		}
		for(int i = 0; i < count.length; i++) {
			finalList.add(dataList.get(i) + count[i]);
		}
		return finalList;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g, getCore, coreImageList, corePosition);
		draw(g, getWeapon, weaponImageList, weaponPosition);
	}
	
	private void draw(Graphics g, List<Integer> getList, List<BufferedImage> imageList, List<Point> position) {
		if(getList.size() != 0) {
			for(int i = 0; i < getList.size(); i++) {
				g.drawImage(imageList.get(getList.get(i)), position.get(i).x, position.get(i).y, null);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int selectNumber = selectCheck(e.getPoint(), corePosition);
		if(0 <= selectNumber) {
			new DrawStatus().core(coreImageList.get(getCore.get(selectNumber)), getCore.get(selectNumber));
			return;
		}
		selectNumber = selectCheck(e.getPoint(), weaponPosition);
		if(0 <= selectNumber) {
			new DrawStatus().weapon(weaponImageList.get(getWeapon.get(selectNumber)), getWeapon.get(selectNumber));
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
	
	private int selectCheck(Point point, List<Point> positionList) {
		for(int i = 0; i < positionList.size(); i++) {
			if(ValueRange.of(positionList.get(i).x, positionList.get(i).x + SIZE).isValidIntValue(point.x)
					&& ValueRange.of(positionList.get(i).y, positionList.get(i).y + SIZE).isValidIntValue(point.y)){
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
		for(int i = 0; i < coreLineup.size(); i++) {
			String coreName = getRarity.apply(DefaultData.CORE_RARITY_LIST.get(coreLineup.get(i))) + DefaultData.CORE_NAME_LIST.get(coreLineup.get(i));
			lineup.addElement(getName.apply(coreName) + getRatio.apply(coreRatio.get(i)));
		}
		if(getTotal(coreRatio) != 0) {
			lineup.addElement(" ");
		}
		lineup.addElement("【武器確率】 " + getRatio.apply(getTotal(weaponRatio)));
		lineup.addElement(" ");
		for(int i = 0; i < weaponLineup.size(); i++) {
			String weaponName = getRarity.apply(DefaultData.WEAPON_RARITY_LIST.get(weaponLineup.get(i))) + DefaultData.WEAPON_NAME_LIST.get(weaponLineup.get(i));
			lineup.addElement(getName.apply(weaponName) + getRatio.apply(weaponRatio.get(i)));
		}
		JList<String> lineupJList = new JList<String>(lineup);
		lineupJList.setEnabled(false);
		return new JScrollPane(lineupJList);
	}
	
	private double getTotal(List<Double> list) {
		double total = 0;
		for(double i: list) {
			total += i;
		}
		return total;
	}
}

//ガチャのラインナップ
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
			coreLineup.addAll(coreLineupSet1);
			coreRatio.addAll(getRatioList(50, coreLineup.size()));
			weaponLineup.addAll(weaponLineupSet1);
			weaponRatio.addAll(getRatioList(50, weaponLineup.size()));
			break;
		case 1:
			coreLineup.addAll(coreLineupSet1);
			coreRatio.addAll(getRatioList(100, coreLineup.size()));
			break;
		case 2:
			weaponLineup.addAll(weaponLineupSet1);
			weaponRatio.addAll(getRatioList(100, weaponLineup.size()));
			break;
		default:
			break;
		}
	}
	
	private List<Double> getRatioList(double totalRatio, int size){
		List<Double> ratioList = new ArrayList<>();
		double ratio = (double) (totalRatio / size);
		for(int i = 0; i < size; i++) {
			ratioList.add(ratio);
		}
		return ratioList;
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