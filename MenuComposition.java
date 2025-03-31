package menucomposition;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import defaultdata.DefaultData;
import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

//編成
public class MenuComposition extends JPanel implements MouseListener{
	JLabel compositionNameLabel = new JLabel();
	JLabel compositionLabel = new JLabel();
	JLabel coreLabel = new JLabel();
	JLabel weaponLabel = new JLabel();
	JButton newButton = new JButton();
	JButton removeButton = new JButton();
	JButton swapButton = new JButton();
	JButton nameChangeButton = new JButton();
	JButton saveButton = new JButton();
	JButton loadButton = new JButton();
	JButton resetButton = new JButton();
	JButton returnButton = new JButton();
	DefaultListModel<String> compositionListModel = new DefaultListModel<String>();
	JList<String> compositionJList = new JList<String>(compositionListModel);
	JScrollPane compositionScroll = new JScrollPane();
	JScrollPane coreScroll = new JScrollPane();
	JScrollPane weaponScroll = new JScrollPane();
	ImagePanel CoreImagePanel = new ImagePanel(new DefaultData().getCoreImage(2), true);
	ImagePanel WeaponImagePanel = new ImagePanel(new DefaultData().getWeaponImage(2), false);
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<List<BufferedImage>> rightWeaponList = new ArrayList<>(new DefaultData().getRightWeaponImage(2));
	List<BufferedImage> ceterCoreList = new ArrayList<>(new DefaultData().getCenterCoreImage(2));
	List<List<BufferedImage>> leftWeaponList = new ArrayList<>(new DefaultData().getLeftWeaponImage(2));
	List<Integer> coreNumberList = new ArrayList<>();
	List<Integer> weaponNumberList = new ArrayList<>();
	List<Integer> nowCoreNumberList = new ArrayList<>();
	List<Integer> nowWeaponNumberList = new ArrayList<>();
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	int selectNumber;
	
	public MenuComposition(MainFrame MainFrame) {
		addMouseListener(this);
		setBackground(new Color(240, 170, 80));
		load();
		addCompositionNameLabel();
		addCompositionLabel();
		addCoreLabel();
		addWeaponLabel();
		addNewButton();
		addRemoveButton();
		addSwapButton();
		addNameChangeButton();
		addSaveButton();
		addLoadButton();
		addResetButton();
		addReturnButton(MainFrame);
		addCompositionScroll();
		addCoreScroll();
		addWeaponScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCompositionNameLabel();
		setCompositionLabel();
		setCoreLabel();
		setWeaponLabel();
		setNewButton();
		setRemoveButton();
		setSwapButton();
		setNameChangeButton();
		setSaveButton();
		setLoadButton();
		setResetButton();
		setReturnButton();
		setCompositionScroll();
		setCoreScroll();
		setWeaponScroll();
		drawComposition(g);
		countNumber();
	}
	
	private void load() {
		try {
			ObjectInputStream itemData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			SaveHoldItem = (SaveHoldItem) itemData.readObject();
			itemData.close();
			ObjectInputStream compositionData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(savecomposition.SaveComposition.COMPOSITION_FILE)));
			SaveComposition = (SaveComposition) compositionData.readObject();
			compositionData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		input();
	}
	
	private void input() {
		coreNumberList = SaveHoldItem.getCoreNumberList();
		weaponNumberList = SaveHoldItem.getWeaponNumberList();
		allCompositionList = SaveComposition.getAllCompositionList();
		compositionNameList = SaveComposition.getCompositionNameList();
		compositionListModel.clear();
		for(String i: compositionNameList) {
			compositionListModel.addElement(i);
		}
		selectNumber = SaveComposition.getSelectNumber();
		compositionJList.setSelectedIndex(selectNumber);
		new DelaySelect(compositionJList, selectNumber).start();
	}
	
	private void save() {
		List<List<List<Integer>>> weaponStatusList = new ArrayList<>();
		List<List<Integer>> unitStatusList = new ArrayList<>();
		List<Integer> typeList = new ArrayList<>();
		for(List<Integer> i : allCompositionList.get(selectNumber)) {
			StatusCalculation StatusCalculation = new StatusCalculation(i);
			weaponStatusList.add(StatusCalculation.getWeaponStatus());
			unitStatusList.add(StatusCalculation.getUnitStatus());
			typeList.add(StatusCalculation.getType());
		}
		try {
			ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(savecomposition.SaveComposition.COMPOSITION_FILE)));
			compositionData.writeObject(new SaveComposition(allCompositionList, compositionNameList, selectNumber, allCompositionList.get(selectNumber), weaponStatusList, unitStatusList, typeList));
			compositionData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addCompositionNameLabel() {
		add(compositionNameLabel);
	}
	
	private void setCompositionNameLabel() {
		compositionNameLabel.setText("編成名");
		compositionNameLabel.setBounds(10, 10, 130, 30);
		setLabel(compositionNameLabel);
	}
	
	private void addCompositionLabel() {
		add(compositionLabel);
	}
	
	private void setCompositionLabel() {
		compositionLabel.setText("ユニット編成");
		compositionLabel.setBounds(230, 10, 130, 30);
		setLabel(compositionLabel);
	}
	
	private void addCoreLabel() {
		add(coreLabel);
	}
	
	private void setCoreLabel() {
		coreLabel.setText("コア");
		coreLabel.setBounds(570, 10, 130, 30);
		setLabel(coreLabel);
	}
	
	private void addWeaponLabel() {
		add(weaponLabel);
	}
	
	private void setWeaponLabel() {
		weaponLabel.setText("武器");
		weaponLabel.setBounds(700, 10, 130, 30);
		setLabel(weaponLabel);
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addNewButton(){
		add(newButton);
		newButton.addActionListener(e->{
			SaveComposition.newComposition();
			input();
		});
	}
	
	private void setNewButton(){
		newButton.setText("編成追加");
		newButton.setBounds(10, 250, 100, 60);
		setButton(newButton);
	}
	
	private void addRemoveButton() {
		add(removeButton);
		removeButton.addActionListener(e->{
			if(1 < allCompositionList.size()) {
				int select = showConfirmDialog(null, "選択中の編成を全て削除しますか", "編成削除確認", YES_NO_OPTION, QUESTION_MESSAGE);
				switch(select) {
				case 0:
					int[] number = compositionJList.getSelectedIndices();
					for(int i = number.length - 1; 0 <= i; i--) {
						SaveComposition.removeComposition(number[i]);
					}
					input();
				default:
					break;
				}
			}else {
				showMessageDialog(null, "全ての編成を削除できません");
			}
		});
	}
	
	private void setRemoveButton() {
		removeButton.setText("編成削除");
		removeButton.setBounds(120, 250, 100, 60);
		setButton(removeButton);
	}
	
	private void addSwapButton(){
		add(swapButton);
		swapButton.addActionListener(e->{
			int max = compositionJList.getMaxSelectionIndex();
			int min = compositionJList.getMinSelectionIndex();
			if(max == min) {
				showMessageDialog(null, "入れ替える2つの編成を選択してください");
			}else {
				int select = showConfirmDialog(null, "選択中の編成を入れ替えますか", "入替確認", YES_NO_OPTION, QUESTION_MESSAGE);
				switch(select) {
				case 0:
					List<List<Integer>> maxList = allCompositionList.get(max);
					List<List<Integer>> minList = allCompositionList.get(min);
					String maxName = compositionNameList.get(max);
					String minName = compositionNameList.get(min);
					allCompositionList.set(max, minList);
					allCompositionList.set(min, maxList);
					compositionNameList.set(max, minName);
					compositionNameList.set(min, maxName);
					compositionListModel.set(max, minName);
					compositionListModel.set(min, maxName);
				default:
					break;
				}
			}
		});
	}
	
	private void setSwapButton(){
		swapButton.setText("編成入替");
		swapButton.setBounds(10, 320, 100, 60);
		setButton(swapButton);
	}
	
	private void addNameChangeButton() {
		add(nameChangeButton);
		nameChangeButton.addActionListener(e->{
			String newName = showInputDialog(null, "変更後の編成名を入力してください", "名称変更", INFORMATION_MESSAGE);
			if(newName != null && !newName.isEmpty()) {
				if(!newName.startsWith(" ") && !newName.startsWith("　")) {
					compositionListModel.set(selectNumber, newName);
					compositionNameList.set(selectNumber, newName);
				}else {
					showMessageDialog(null, "スペースで始まる名称は使用できません");
				}
			}
		});
	}
	
	private void setNameChangeButton() {
		nameChangeButton.setText("名称変更");
		nameChangeButton.setBounds(120, 320, 100, 60);
		setButton(nameChangeButton);
	}
	
	private void addSaveButton() {
		add(saveButton);
		saveButton.addActionListener(e->{
			int select = showConfirmDialog(null, "現在の編成を保存しますか?", "保存確認", YES_NO_OPTION, QUESTION_MESSAGE);
			switch(select) {
			case 0:
				save();
			default:
				break;
			}
		});
	}
	
	private void setSaveButton() {
		saveButton.setText("セーブ");
		saveButton.setBounds(10, 390, 100, 60);
		setButton(saveButton);
	}
	
	private void addLoadButton() {
		add(loadButton);
		loadButton.addActionListener(e->{
			int select = showConfirmDialog(null, "保存せずに元のデータをロードしますか?", "ロード確認", YES_NO_OPTION, QUESTION_MESSAGE);
			switch(select) {
			case 0:
				load();
			default:
				break;
			}
		});
	}
	
	private void setLoadButton() {
		loadButton.setText("ロード");
		loadButton.setBounds(120, 390, 100, 60);
		setButton(loadButton);
	}
	
	private void addResetButton() {
		add(resetButton);
		resetButton.addActionListener(e->{
			int select = showConfirmDialog(null, "現在の編成をリセットしますか", "リセット確認", YES_NO_OPTION, QUESTION_MESSAGE);
			switch(select) {
			case 0:
				allCompositionList.set(selectNumber, savecomposition.SaveComposition.DEFAULT);
			default:
				break;
			}
		});
	}
	
	private void setResetButton() {
		resetButton.setText("リセット");
		resetButton.setBounds(10, 460, 100, 60);
		setButton(resetButton);
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			int select = showConfirmDialog(null, "保存して戻りますか?", "実行確認", YES_NO_CANCEL_OPTION, QUESTION_MESSAGE);
			switch(select) {
			case 0:
				save();
				MainFrame.mainMenuDraw();
				break;
			case 1:
				MainFrame.mainMenuDraw();
				break;
			default:
				break;
			}
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(120, 460, 100, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		button.setFocusable(false);
	}
	
	private void addCompositionScroll() {
		compositionScroll.getViewport().setView(compositionJList);
    	add(compositionScroll);
	}
	
	private void setCompositionScroll() {
		compositionJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		compositionScroll.setPreferredSize(new Dimension(210, 200));
    	compositionScroll.setBounds(10, 40, 210, 200);
    	selectNumber = compositionJList.getSelectedIndex();
	}
	
	private void addCoreScroll() {
		CoreImagePanel.setImagePanel(nowCoreNumberList, WeaponImagePanel);
		coreScroll.getViewport().setView(CoreImagePanel);
    	add(coreScroll);
	}
	
	private void setCoreScroll() {
		coreScroll.setPreferredSize(new Dimension(120, 480));
		coreScroll.setBounds(570, 40, 120, 480);
	}
	
	private void addWeaponScroll() {
		WeaponImagePanel.setImagePanel(nowWeaponNumberList, CoreImagePanel);
		weaponScroll.getViewport().setView(WeaponImagePanel);
    	add(weaponScroll);
	}
	
	private void setWeaponScroll() {
		weaponScroll.setPreferredSize(new Dimension(120, 480));
		weaponScroll.setBounds(700, 40, 120, 480);
	}
	
	private void drawComposition(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 40, 330, 480);
		for(int i = 0; i < allCompositionList.get(selectNumber).size(); i++) {
			try {
				g.drawImage(rightWeaponList.get(allCompositionList.get(selectNumber).get(i).get(0)).get(0), getPositionX(i), getPositionY(i), this);
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			g.drawImage(ceterCoreList.get(allCompositionList.get(selectNumber).get(i).get(1)), getPositionX(i), getPositionY(i), this);
			try {
				g.drawImage(leftWeaponList.get(allCompositionList.get(selectNumber).get(i).get(2)).get(0), getPositionX(i), getPositionY(i), this);
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		}
	}
	
	private int getPositionX(int i) {
		return 230 + i % 2 * 150;
	}
	
	private int getPositionY(int i) {
		return 40 + i / 2 * 100;
	}
	
	private void countNumber() {
		int[] core = new int[coreNumberList.size()];
    	int[] weapon = new int[weaponNumberList.size()];
    	for(List<Integer> i: allCompositionList.get(selectNumber)) {
    		core[i.get(1)]++;
    		try {
    			weapon[i.get(0)]++;
    		}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
    		try {
    			weapon[i.get(2)]++;
    		}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
    	}
    	nowCoreNumberList.clear();
    	for(int i = 0; i < coreNumberList.size(); i++) {
    		nowCoreNumberList.add(coreNumberList.get(i) - core[i]);
    	}
    	nowWeaponNumberList.clear();
    	for(int i = 0; i < weaponNumberList.size(); i++) {
    		nowWeaponNumberList.add(weaponNumberList.get(i) - weapon[i]);
    	}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < allCompositionList.get(selectNumber).size(); i++) {
			int x = getPositionX(i) + 60;
			int y = getPositionY(i) + 60;
			if(ValueRange.of(x, x + DefaultData.SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + DefaultData.SIZE).isValidIntValue(e.getY())){
				int selectCore = CoreImagePanel.getSelectNumber();
				int selectWeapon = WeaponImagePanel.getSelectNumber();
				if(0 <= selectCore) {
					if(0 < nowCoreNumberList.get(selectCore)) {
						changeCore(i, selectCore);
					}
				}else if(0 <= selectWeapon) {
					if(0 < nowWeaponNumberList.get(selectWeapon)) {
						changeWeapon(i, selectWeapon);
					}
				}else {
					removeWeapon(i);
				}
			}
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
	
	private void changeCore(int number, int selectCore) {
		allCompositionList.get(selectNumber).get(number).set(1, selectCore);
	}
	
	private void changeWeapon(int number, int selectWeapon) {
		if(DefaultData.WEAPON_TYPE.get(selectWeapon).get(1) == 1) {
			allCompositionList.get(selectNumber).get(number).set(2, selectWeapon);
			allCompositionList.get(selectNumber).get(number).set(0, -1);
		}else if(allCompositionList.get(selectNumber).get(number).get(2) == -1) {
			changeConfirmation(number, selectWeapon);
		}else {
			switch(DefaultData.WEAPON_TYPE.get(allCompositionList.get(selectNumber).get(number).get(2)).get(1)) {
			case 0:
				changeConfirmation(number, selectWeapon);
				break;
			case 1:
				if(changeConfirmation(number, selectWeapon) == 1) {
					allCompositionList.get(selectNumber).get(number).set(2, -1);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private int changeConfirmation(int number, int selectWeapon) {
		String[] menu = {"左", "右", "戻る"};
		int select = showOptionDialog(null, "左右どちらの武器を変更しますか", "武器変更", OK_CANCEL_OPTION, PLAIN_MESSAGE, null, menu, menu[0]);
		switch(select) {
		case 0:
			allCompositionList.get(selectNumber).get(number).set(2, selectWeapon);
			break;
		case 1:
			allCompositionList.get(selectNumber).get(number).set(0, selectWeapon);
			break;
		default:
			break;
		}
		return select;
	}
	
	private void removeWeapon(int number) {
		boolean existsRight = (allCompositionList.get(selectNumber).get(number).get(0) == -1)? false: true;
		boolean existsLeft = (allCompositionList.get(selectNumber).get(number).get(2) == -1)? false: true;
		String[] menu = new String[3];
		menu[0] = existsLeft? "左": "";
		menu[1] = existsRight? "右": "";
		menu[2] = "戻る";
		int select = showOptionDialog(null, getComment(number, existsRight, existsLeft), "ステータスと武器解除", OK_CANCEL_OPTION, PLAIN_MESSAGE, getIcon(allCompositionList.get(selectNumber).get(number)), menu, menu[0]);
		switch(select) {
		case 0:
			allCompositionList.get(selectNumber).get(number).set(2, -1);
			break;
		case 1:
			allCompositionList.get(selectNumber).get(number).set(0, -1);
			break;
		default:
			break;
		}
	}
	
	private String getComment(int number, boolean existsRight, boolean existsLeft) {
		StatusCalculation StatusCalculation = new StatusCalculation(allCompositionList.get(selectNumber).get(number));
		List<List<Integer>> weaponStatusList =  StatusCalculation.getWeaponStatus();
		List<Integer> unitStatusList = StatusCalculation.getUnitStatus();
		String comment = "【武器ステータス】\n";
		if(existsLeft && existsRight) {
			comment += "右武器\n" + getWeaponComment(weaponStatusList.get(0));
			comment += "左武器\n" + getWeaponComment(weaponStatusList.get(1));
		}else if(!existsLeft && !existsRight){
			comment += "武器無し\n\n";
		}else {
			comment += existsRight? getWeaponComment(weaponStatusList.get(0)): getWeaponComment(weaponStatusList.get(1));
		}
		comment += "【ユニットステータス】\n"
				+ "HP: " + unitStatusList.get(0) + "\n"
				+ "防御力: " + unitStatusList.get(1) + "\n"
				+ "回復: " + unitStatusList.get(2) + "\n"
				+ "足止め数: " + unitStatusList.get(3) + "\n"
				+ "配置コスト: " + unitStatusList.get(4) + "\n"
				+ "\n"
				+ "【武器タイプ】\n"
				+ DefaultData.DISTANCE_MAP.get(StatusCalculation.getType()) + "\n"
				+ "\n"
				+ "↓武器解除する位置を選択してください↓"
				+ "\n";
		return comment;
	}
	
	private String getWeaponComment(List<Integer> weaponStatusList) {
		String comment =((0 <= weaponStatusList.get(0))? "攻撃力: ": "回復力: ") + Math.abs(weaponStatusList.get(0)) + "\n"
					+ "射程: " + weaponStatusList.get(1) + "\n"
					+ "攻撃速度: " + weaponStatusList.get(2) + " ms\n"
					+ "\n";
		return comment;
	}
	
	private ImageIcon getIcon(List<Integer> imageList) {
		int width = ceterCoreList.get(imageList.get(1)).getWidth();
		int height = ceterCoreList.get(imageList.get(1)).getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				image.setRGB(x, y, 0);
			}
		}
		Graphics graphics = image.getGraphics();
		try {
			graphics.drawImage(rightWeaponList.get(imageList.get(0)).get(0), 0, 0, null);
		}catch(Exception ignore) {
			//右武器を装備していないので、無視する
		}
		graphics.drawImage(ceterCoreList.get(imageList.get(1)), 0, 0, null);
		try {
			graphics.drawImage(leftWeaponList.get(imageList.get(2)).get(0), 0, 0, null);
		}catch(Exception ignore) {
			//左武器を装備していないので、無視する
		}
		graphics.dispose();
		return new ImageIcon(image);
	}
}

//ユニット表示
class ImagePanel extends JPanel implements MouseListener{
	List<BufferedImage> imageList;
	List<Integer> numberList;
	ImagePanel ImagePanel;
	boolean existsWhich;
	int selectNumber = -1;
	
	protected ImagePanel(List<BufferedImage> imageList, boolean existsWhich) {
		this.imageList = imageList;
		this.existsWhich = existsWhich;
		addMouseListener(this);
		setPreferredSize(new Dimension(100, imageList.size() * 100));
	}
	
	protected void setImagePanel(List<Integer> numberList, ImagePanel ImagePanel) {
		this.numberList = numberList;
		this.ImagePanel = ImagePanel;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < imageList.size(); i++) {
			if(selectNumber == i) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 100 * i, 90, 90);
			}
			g.drawImage(imageList.get(i), 0, i * 100, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("" + numberList.get(i), 80, 80 + i * 100);
		}
	}
	
	protected int getSelectNumber() {
		return selectNumber;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < imageList.size(); i++) {
			if(ValueRange.of(10, 10 + DefaultData.SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(10 + 100 * i, 10 + DefaultData.SIZE + 100 * i).isValidIntValue(e.getY())){
				if(selectNumber == i) {
					if(existsWhich) {
						coreStatus();
					}else {
						weaponStatus();
					}
				}else {
					selectNumber = i;
					ImagePanel.selectNumber = -1;
				}
				break;
	    	}
			if(i == imageList.size() - 1) {
				selectNumber = -1;
				ImagePanel.selectNumber = -1;
			}
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
	
	private void coreStatus() {
		List<Double> weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST.get(selectNumber);
		List<Double> unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【武器ステータス倍率】\n"
					+ "攻撃倍率: " + weaponStatusList.get(0) + "倍\n"
					+ "射程倍率: " + weaponStatusList.get(1) + "倍\n"
					+ "攻撃速度倍率: " + weaponStatusList.get(2) + "倍\n"
					+ "\n"
					+"【ユニットステータス倍率】\n"
					+ "HP倍率: " + unitStatusList.get(0) + "倍\n"
					+ "防御倍率: " + unitStatusList.get(1) + "倍\n"
					+ "回復倍率: " + unitStatusList.get(2) + "倍\n"
					+ "足止め数倍率: " + unitStatusList.get(3) + "倍\n"
					+ "配置コスト倍率: " + unitStatusList.get(4)+ "倍";
		showMessageDialog(null, comment);
	}
	
	private void weaponStatus() {
		List<Integer> weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(selectNumber);
		List<Integer> unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【武器ステータス】\n"
					+ ((0 <= weaponStatusList.get(0))? "攻撃力: ": "回復力: ") + Math.abs(weaponStatusList.get(0)) + "\n"
					+ "射程: " + weaponStatusList.get(1) + "\n"
					+ "攻撃速度: " + weaponStatusList.get(2) + " ms\n"
					+ "\n"
					+ "【ユニットステータス】\n"
					+ "HP: " + unitStatusList.get(0) + "\n"
					+ "防御力: " + unitStatusList.get(1) + "\n"
					+ "回復: " + unitStatusList.get(2) + "\n"
					+ "足止め数: " + unitStatusList.get(3) + "\n"
					+ "配置コスト: " + unitStatusList.get(4) + "\n"
					+ "\n"
					+ "【武器タイプ】\n"
					+ "距離タイプ: " + DefaultData.DISTANCE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(0)) + "\n"
					+ "装備タイプ: " + DefaultData.HANDLE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(1));
		showMessageDialog(null, comment);
	}
}

//JListの選択項目を可視化 (初回処理時ensureIndexIsVisibleが入らないため)
class DelaySelect extends Thread{
	JList<String> compositionJList;
	int selectNumber;
	
	protected DelaySelect(JList<String> compositionJList, int selectNumber) {
		this.compositionJList = compositionJList;
		this.selectNumber = selectNumber;
	}
	
	public void run() {
		try {
			Thread.sleep(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		compositionJList.ensureIndexIsVisible(selectNumber);
	}
}

//ステータス計算
class StatusCalculation{
	int rightType;
	List<Integer> rightWeaponStatus;
	List<Integer> rightUnitStatus;
	int leftType;
	List<Integer> leftWeaponStatus;
	List<Integer> leftUnitStatus;
	List<Double> coreWeaponStatus;
	List<Double> coreUnitStatus;
	
	protected StatusCalculation(List<Integer> unitData) {
		try {
			rightType = DefaultData.WEAPON_TYPE.get(unitData.get(0)).get(0);
			rightWeaponStatus = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(unitData.get(0));
			rightUnitStatus = DefaultData.WEAPON_UNIT_STATUS_LIST.get(unitData.get(0));
		}catch(Exception e) {
			rightType = defaultType();
			rightWeaponStatus = defaultWeaponStatus();
			rightUnitStatus = defaultUnitStatus();
		}
		try {
			leftType = DefaultData.WEAPON_TYPE.get(unitData.get(2)).get(0);
			leftWeaponStatus = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(unitData.get(2));
			leftUnitStatus = DefaultData.WEAPON_UNIT_STATUS_LIST.get(unitData.get(2));
		}catch(Exception e) {
			leftType = defaultType();
			leftWeaponStatus = defaultWeaponStatus();
			leftUnitStatus = defaultUnitStatus();
		}
		coreWeaponStatus = DefaultData.CORE_WEAPON_STATUS_LIST.get(unitData.get(1));
		coreUnitStatus = DefaultData.CORE_UNIT_STATUS_LIST.get(unitData.get(1));
	}
	
	private int defaultType() {
		return -1;
	}
	
	private List<Integer> defaultWeaponStatus(){
		return Arrays.asList(0, 0, 0);
	}
	
	private List<Integer> defaultUnitStatus(){
		return Arrays.asList(1000, 0, 0, 0, 0);
	}
	
	protected int getType() {
		if(rightType == -1 && leftType == -1) {
			return 2;
		}else if(rightType == leftType) {
			return leftType;
		}else if(0 <= rightType && 0 <= leftType){
			return 2;
		}else {
			return (rightType <= leftType)? leftType: rightType;
		}
	}
	
	protected List<List<Integer>> getWeaponStatus(){
		List<List<Integer>> weaponStatus = new ArrayList<>();
		weaponStatus.add(getEachWeaponStatus(rightWeaponStatus));
		weaponStatus.add(getEachWeaponStatus(leftWeaponStatus));
		return weaponStatus;
	}
	
	private List<Integer> getEachWeaponStatus(List<Integer> weapontStatus){
		List<Integer> statusList = new ArrayList<>();
		double status;
		for(int i = 0; i < weapontStatus.size(); i++) {
			status = (double) weapontStatus.get(i) * coreWeaponStatus.get(i);
			statusList.add((int) status);
		}
		return statusList;
	}
	
	protected List<Integer> getUnitStatus(){
		List<Integer> statusList = new ArrayList<>();
		double status;
		for(int i = 0; i < coreUnitStatus.size(); i++) {
			status = (double) (rightUnitStatus.get(i) + leftUnitStatus.get(i)) * coreUnitStatus.get(i);
			statusList.add((int) status);
		}
		return statusList;
	}
}