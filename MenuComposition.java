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
import java.util.List;

import javax.swing.DefaultListModel;
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
	ImagePanel CoreImagePanel = new ImagePanel(new DefaultData().coreImage(2), true);
	ImagePanel WeaponImagePanel = new ImagePanel(new DefaultData().weaponImage(2), false);
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<List<BufferedImage>> rightWeaponList = new ArrayList<>(new DefaultData().rightWeaponImage(2));
	List<BufferedImage> ceterCoreList = new ArrayList<>(new DefaultData().centerCoreImage(2));
	List<List<BufferedImage>> leftWeaponList = new ArrayList<>(new DefaultData().leftWeaponImage(2));
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
		setComposition(g);
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
		try {
			ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(savecomposition.SaveComposition.COMPOSITION_FILE)));
			compositionData.writeObject(new SaveComposition(allCompositionList, compositionNameList, selectNumber, allCompositionList.get(selectNumber)));
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
	
	private void setComposition(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 40, 330, 480);
		for(int i = 0; i < allCompositionList.get(selectNumber).size(); i++) {
			try {
				g.drawImage(rightWeaponList.get(allCompositionList.get(selectNumber).get(i).get(0)).get(0), positionX(i), positionY(i), this);
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			g.drawImage(ceterCoreList.get(allCompositionList.get(selectNumber).get(i).get(1)), positionX(i), positionY(i), this);
			try {
				g.drawImage(leftWeaponList.get(allCompositionList.get(selectNumber).get(i).get(2)).get(0), positionX(i), positionY(i), this);
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		}
	}
	
	private int positionX(int i) {
		return 230 + i % 2 * 150;
	}
	
	private int positionY(int i) {
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
			int x = positionX(i) + 60;
			int y = positionY(i) + 60;
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
						changeWeaponJudgment(i, selectWeapon);
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
	
	private void changeWeaponJudgment(int number, int selectWeapon) {
		if(DefaultData.WEAPON_TYPE.get(selectWeapon).get(1) == 1) {
			allCompositionList.get(selectNumber).get(number).set(2, selectWeapon);
			allCompositionList.get(selectNumber).get(number).set(0, -1);
		}else if(allCompositionList.get(selectNumber).get(number).get(2) == -1) {
			changeWeapon(number, selectWeapon);
		}else {
			switch(DefaultData.WEAPON_TYPE.get(allCompositionList.get(selectNumber).get(number).get(2)).get(1)) {
			case 0:
				changeWeapon(number, selectWeapon);
				break;
			case 1:
				if(changeWeapon(number, selectWeapon) == 1) {
					allCompositionList.get(selectNumber).get(number).set(2, -1);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private int changeWeapon(int number, int selectWeapon) {
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
		String[] menu = new String[3];
		menu[0] = (allCompositionList.get(selectNumber).get(number).get(2) == -1)? "": "左";
		menu[1] = (allCompositionList.get(selectNumber).get(number).get(0) == -1)? "": "右";
		menu[2] = "戻る";
		int select = showOptionDialog(null, "武器解除する部位を選択してください", "ステータス", OK_CANCEL_OPTION, PLAIN_MESSAGE, null, menu, menu[0]);
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
		List<Double> statusList = DefaultData.CORE_STATUS_LIST.get(selectNumber);
		String comment;
		comment ="【ステータス】\n"
				+ "HP倍率: " + statusList.get(1) + "倍\n"
				+ "攻撃倍率: " + Math.abs(statusList.get(2)) + "倍\n"
				+ "防御倍率: " + statusList.get(3) + "倍\n"
				+ "射程倍率: " + statusList.get(4) + "倍\n"
				+ "回復倍率: " + statusList.get(5) + "倍\n"
				+ "攻撃速度倍率: " + statusList.get(6) + "倍\n"
				+ "足止め数倍率: " + statusList.get(7) + "倍\n"
				+ "配置コスト倍率: " + statusList.get(8)+ "倍";
		showMessageDialog(null, comment);
	}
	
	private void weaponStatus() {
		List<Integer> statusList = DefaultData.WEAPON_STATUS_LIST.get(selectNumber);
		String comment;
		String type = DefaultData.DISTANCE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(0))
				+ " / " + DefaultData.HANDLE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(1));
		String atack;
		if(0 <= statusList.get(2)) {
			atack = "攻撃力: ";
		}else {
			atack = "回復力: ";
		}
		comment ="【ステータス】\n"
				+ "HP: " + statusList.get(1) + "\n"
				+ atack + Math.abs(statusList.get(2)) + "\n"
				+ "防御力: " + statusList.get(3) + "\n"
				+ "射程: " + statusList.get(4) + "\n"
				+ "回復: " + statusList.get(5) + "\n"
				+ "攻撃速度: " + statusList.get(6) + " ms\n"
				+ "足止め数: " + statusList.get(7) + "\n"
				+ "配置コスト: " + statusList.get(8) + "\n"
				+ "武器タイプ: " + type;
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