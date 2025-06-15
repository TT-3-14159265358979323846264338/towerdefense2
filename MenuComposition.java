package defendthecastle;

import static javax.swing.JOptionPane.*;
import static savedata.SaveComposition.*;
import static savedata.SaveHoldItem.*;

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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import defaultdata.DefaultUnit;
import defaultdata.EditImage;
import defaultdata.core.CoreData;
import defaultdata.weapon.WeaponData;
import savedata.SaveComposition;
import savedata.SaveHoldItem;
import screendisplay.DisplaySort;
import screendisplay.DisplayStatus;

//編成
public class MenuComposition extends JPanel implements MouseListener{
	JLabel compositionNameLabel = new JLabel();
	JLabel compositionLabel = new JLabel();
	JLabel typeLabel = new JLabel();
	JButton newButton = new JButton();
	JButton removeButton = new JButton();
	JButton swapButton = new JButton();
	JButton nameChangeButton = new JButton();
	JButton saveButton = new JButton();
	JButton loadButton = new JButton();
	JButton resetButton = new JButton();
	JButton returnButton = new JButton();
	JButton switchButton = new JButton();
	JButton sortButton = new JButton();
	DefaultListModel<String> compositionListModel = new DefaultListModel<>();
	JList<String> compositionJList = new JList<>(compositionListModel);
	JScrollPane compositionScroll = new JScrollPane();
	JScrollPane itemScroll = new JScrollPane();
	ImagePanel CoreImagePanel = new ImagePanel();
	ImagePanel WeaponImagePanel = new ImagePanel();
	SaveData SaveData = new SaveData();
	DisplayListCreation DisplayListCreation = new DisplayListCreation(SaveData);
	List<BufferedImage> rightWeaponList = new ArrayList<>(new DefaultUnit().getRightWeaponImage(2));
	List<BufferedImage> ceterCoreList = new ArrayList<>(new DefaultUnit().getCenterCoreImage(2));
	List<BufferedImage> leftWeaponList = new ArrayList<>(new DefaultUnit().getLeftWeaponImage(2));
	static int unitSize = 60;
	
	protected MenuComposition(MainFrame MainFrame) {
		addMouseListener(this);
		setBackground(new Color(240, 170, 80));
		addCompositionNameLabel();
		addCompositionLabel();
		addTypeLabel();
		addNewButton();
		addRemoveButton();
		addSwapButton();
		addNameChangeButton();
		addSaveButton();
		addLoadButton();
		addResetButton();
		addReturnButton(MainFrame);
		addSwitchButton();
		addSortButton();
		addCompositionScroll();
		addItemScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCompositionNameLabel();
		setCompositionLabel();
		setTypeLabel();
		setNewButton();
		setRemoveButton();
		setSwapButton();
		setNameChangeButton();
		setSaveButton();
		setLoadButton();
		setResetButton();
		setReturnButton();
		setCompositionScroll();
		setSwitchButton();
		setSortButton();
		setItemScroll();
		drawComposition(g);
		SaveData.countNumber();
		requestFocus();
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
	
	private void addTypeLabel() {
		add(typeLabel);
	}
	
	private void setTypeLabel() {
		typeLabel.setText((itemScroll.getViewport().getView() == CoreImagePanel)? "コアリスト": "武器リスト");
		typeLabel.setBounds(570, 10, 130, 30);
		setLabel(typeLabel);
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addNewButton(){
		add(newButton);
		newButton.addActionListener(e->{
			SaveData.addNewComposition();
			modelUpdate();
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
			SaveData.removeComposition(compositionJList.getSelectedIndices());
			modelUpdate();
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
			SaveData.swapComposition(compositionJList.getMaxSelectionIndex(), compositionJList.getMinSelectionIndex());
			modelUpdate();
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
			String newName = SaveData.changeCompositionName();
			if(newName != null) {
				compositionListModel.set(SaveData.getSelectNumber(), newName);
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
			SaveData.saveProcessing();
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
			SaveData.loadProcessing();
			modelUpdate();
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
			SaveData.resetComposition();
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
			if(SaveData.returnProcessing()) {
				MainFrame.mainMenuDraw();
			}
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(120, 460, 100, 60);
		setButton(returnButton);
	}
	
	private void addSwitchButton() {
		add(switchButton);
		switchButton.addActionListener(e->{
			itemScroll.getViewport().setView((itemScroll.getViewport().getView() == CoreImagePanel)? WeaponImagePanel: CoreImagePanel);
		});
	}
	
	private void setSwitchButton() {
		switchButton.setText("表示切替");
		switchButton.setBounds(570, 460, 185, 60);
		setButton(switchButton);
	}
	
	private void addSortButton() {
		add(sortButton);
		sortButton.addActionListener(e->{
			if(itemScroll.getViewport().getView() == CoreImagePanel) {
				CoreImagePanel.updateList(DisplayListCreation.getCoreDisplayList());
			}else {
				WeaponImagePanel.updateList(DisplayListCreation.getWeaponDisplayList());
			}
		});
	}
	
	private void setSortButton() {
		sortButton.setText("ソート");
		sortButton.setBounds(765, 460, 185, 60);
		setButton(sortButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
	}
	
	private void addCompositionScroll() {
		modelUpdate();
		compositionScroll.getViewport().setView(compositionJList);
    	add(compositionScroll);
    	new DelaySelect(compositionJList, SaveData.getSelectNumber()).start();
	}
	
	private void modelUpdate() {
		compositionListModel.clear();
		SaveData.getCompositionNameList().stream().forEach(i -> compositionListModel.addElement(i));
		compositionJList.setSelectedIndex(SaveData.getSelectNumber());
	}
	
	private void setCompositionScroll() {
		compositionJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		compositionScroll.setPreferredSize(new Dimension(210, 200));
    	compositionScroll.setBounds(10, 40, 210, 200);
    	SaveData.selectNumberUpdate(compositionJList.getSelectedIndex());
	}
	
	private void addItemScroll() {
		CoreImagePanel.setImagePanel(new DefaultUnit().getCoreImage(2), DisplayListCreation.getDisplayList(SaveData.getCoreNumberList()), SaveData.getNowCoreNumberList(), true);
		WeaponImagePanel.setImagePanel(new DefaultUnit().getWeaponImage(2), DisplayListCreation.getDisplayList(SaveData.getWeaponNumberList()), SaveData.getNowWeaponNumberList(), false);
		itemScroll.getViewport().setView(CoreImagePanel);
    	add(itemScroll);
	}
	
	private void setItemScroll() {
		itemScroll.setBounds(570, 40, 380, 410);
		itemScroll.setPreferredSize(itemScroll.getSize());
	}
	
	private void drawComposition(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 40, 330, 480);
		IntStream.range(0, SaveData.getActiveCompositionList().size()).forEach(i -> {
			try {
				g.drawImage(rightWeaponList.get(SaveData.getActiveUnit(i).get(0)), getPositionX(i), getPositionY(i), this);
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			g.drawImage(ceterCoreList.get(SaveData.getActiveUnit(i).get(1)), getPositionX(i), getPositionY(i), this);
			try {
				g.drawImage(leftWeaponList.get(SaveData.getActiveUnit(i).get(2)), getPositionX(i), getPositionY(i), this);
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		});
	}
	
	private int getPositionX(int i) {
		return 230 + i % 2 * 150;
	}
	
	private int getPositionY(int i) {
		return 40 + i / 2 * 100;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		IntStream.range(0, SaveData.getActiveCompositionList().size()).forEach(i -> {
			int x = getPositionX(i) + 60;
			int y = getPositionY(i) + 60;
			if(ValueRange.of(x, x + unitSize).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + unitSize).isValidIntValue(e.getY())){
				try {
					if(itemScroll.getViewport().getView() == CoreImagePanel) {
						int selectCore = CoreImagePanel.getSelectNumber();
						if(0 < SaveData.getCoreNumberList().get(selectCore)) {
							SaveData.changeCore(i, selectCore);
							CoreImagePanel.resetSelectNumber();
							SaveData.countNumber();
						}
					}else {
						int selectWeapon = WeaponImagePanel.getSelectNumber();
						if(0 < SaveData.getWeaponNumberList().get(selectWeapon)) {
							SaveData.changeWeapon(i, selectWeapon);
							WeaponImagePanel.resetSelectNumber();
							SaveData.countNumber();
						}
					}
				}catch(Exception notSelect) {
					unitStstus(i);
				}
			}
		});
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
	
	private void unitStstus(int number) {
		List<Integer> unitData = SaveData.getActiveUnit(number);
		StatusCalculation StatusCalculation = new StatusCalculation(unitData);
		new DisplayStatus().unit(new EditImage().compositeImage(getImageList(unitData)), unitData, StatusCalculation.getWeaponStatus(), StatusCalculation.getUnitStatus());
	}
	
	private List<BufferedImage> getImageList(List<Integer> unitData){
		List<BufferedImage> originalImage = new ArrayList<>();
		try {
			originalImage.add(rightWeaponList.get(unitData.get(0)));
		}catch(Exception e) {
			originalImage.add(null);
		}
		originalImage.add(ceterCoreList.get(unitData.get(1)));
		try {
			originalImage.add(leftWeaponList.get(unitData.get(2)));
		}catch(Exception e) {
			originalImage.add(null);
		}
		return originalImage;
	}
}

//セーブデータ処理
class SaveData{
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<Integer> coreNumberList = new ArrayList<>();
	List<Integer> weaponNumberList = new ArrayList<>();
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	int selectNumber;
	List<Integer> nowCoreNumberList = new ArrayList<>();
	List<Integer> nowWeaponNumberList = new ArrayList<>();
	
	protected SaveData() {
		load();
	}
	
	private void load() {
		try {
			ObjectInputStream itemData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(HOLD_FILE)));
			SaveHoldItem = (SaveHoldItem) itemData.readObject();
			itemData.close();
			ObjectInputStream compositionData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(COMPOSITION_FILE)));
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
		selectNumber = SaveComposition.getSelectNumber();
	}
	
	private void save() {
		List<List<List<Integer>>> weaponStatusList = new ArrayList<>();
		List<List<List<Integer>>> unitStatusList = new ArrayList<>();
		List<Integer> typeList = new ArrayList<>();
		allCompositionList.get(selectNumber).stream().forEach(i -> {
			StatusCalculation StatusCalculation = new StatusCalculation(i);
			weaponStatusList.add(StatusCalculation.getWeaponStatus());
			unitStatusList.add(StatusCalculation.getUnitStatus());
			typeList.add(StatusCalculation.getType());
		});
		try {
			ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(COMPOSITION_FILE)));
			compositionData.writeObject(new SaveComposition(allCompositionList, compositionNameList, selectNumber, getActiveCompositionList(), weaponStatusList, unitStatusList, typeList));
			compositionData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void countNumber() {
		int[] core = new int[coreNumberList.size()];
    	int[] weapon = new int[weaponNumberList.size()];
    	getActiveCompositionList().stream().forEach(i -> {
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
    	});
    	BiFunction<List<Integer>, int[], List<Integer>> getNowNumber = (list, count) -> {
    		return IntStream.range(0, list.size()).mapToObj(i -> list.get(i) - count[i]).toList();
    	};
    	nowCoreNumberList.clear();
    	nowCoreNumberList.addAll(getNowNumber.apply(coreNumberList, core));
    	nowWeaponNumberList.clear();
    	nowWeaponNumberList.addAll(getNowNumber.apply(weaponNumberList, weapon));
	}
	
	protected void addNewComposition() {
		SaveComposition.newComposition();
		input();
	}
	
	protected void removeComposition(int[] number) {
		if(1 < allCompositionList.size()) {
			int select = showConfirmDialog(null, "選択中の編成を全て削除しますか", "編成削除確認", YES_NO_OPTION, QUESTION_MESSAGE);
			switch(select) {
			case 0:
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
	}
	
	protected void swapComposition(int max, int min) {
		if(max == min) {
			showMessageDialog(null, "入れ替える2つの編成を選択してください");
		}
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
			break;
		default:
			break;
		}
	}
	
	protected String changeCompositionName() {
		String newName = showInputDialog(null, "変更後の編成名を入力してください", "名称変更", INFORMATION_MESSAGE);
		if(newName != null && !newName.isEmpty()) {
			if(!newName.startsWith(" ") && !newName.startsWith("　")) {
				compositionNameList.set(selectNumber, newName);
				return newName;
			}
			showMessageDialog(null, "スペースで始まる名称は使用できません");
		}
		return null;
	}
	
	protected void saveProcessing() {
		int select = showConfirmDialog(null, "現在の編成を保存しますか?", "保存確認", YES_NO_OPTION, QUESTION_MESSAGE);
		switch(select) {
		case 0:
			save();
		default:
			break;
		}
	}
	
	protected void loadProcessing() {
		int select = showConfirmDialog(null, "保存せずに元のデータをロードしますか?", "ロード確認", YES_NO_OPTION, QUESTION_MESSAGE);
		switch(select) {
		case 0:
			load();
		default:
			break;
		}
	}
	
	protected void resetComposition() {
		int select = showConfirmDialog(null, "現在の編成をリセットしますか", "リセット確認", YES_NO_OPTION, QUESTION_MESSAGE);
		switch(select) {
		case 0:
			allCompositionList.set(selectNumber, new ArrayList<>(IntStream.range(0, 8).mapToObj(i -> new ArrayList<>(DEFAULT)).toList()));
		default:
			break;
		}
	}
	
	protected boolean returnProcessing() {
		int select = showConfirmDialog(null, "保存して戻りますか?", "実行確認", YES_NO_CANCEL_OPTION, QUESTION_MESSAGE);
		switch(select) {
		case 0:
			save();
			return true;
		case 1:
			return true;
		default:
			break;
		}
		return false;
	}
	
	protected void selectNumberUpdate(int indexNumber) {
		selectNumber = indexNumber;
	}
	
	protected void changeCore(int number, int selectCore) {
		getActiveUnit(number).set(1, selectCore);
	}
	
	protected void changeWeapon(int number, int selectWeapon) {
		DefaultUnit DefaultUnit = new DefaultUnit();
		if(DefaultUnit.getWeaponData(selectWeapon).getType().get(1) == 1) {
			getActiveUnit(number).set(2, selectWeapon);
			getActiveUnit(number).set(0, -1);
		}else if(getActiveUnit(number).get(2) == -1) {
			change(number, selectWeapon);
		}else {
			switch(DefaultUnit.getWeaponData(getActiveUnit(number).get(2)).getType().get(1)) {
			case 0:
				change(number, selectWeapon);
				break;
			case 1:
				if(change(number, selectWeapon) == 1) {
					getActiveUnit(number).set(2, -1);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private int change(int number, int selectWeapon) {
		String[] menu = {"左", "右", "戻る"};
		int select = showOptionDialog(null, "左右どちらの武器を変更しますか", "武器変更", OK_CANCEL_OPTION, PLAIN_MESSAGE, null, menu, menu[0]);
		switch(select) {
		case 0:
			getActiveUnit(number).set(2, selectWeapon);
			break;
		case 1:
			getActiveUnit(number).set(0, selectWeapon);
			break;
		default:
			break;
		}
		return select;
	}
	
	protected List<Integer> getCoreNumberList(){
		return coreNumberList;
	}
	
	protected List<Integer> getWeaponNumberList(){
		return weaponNumberList;
	}
	
	protected List<String> getCompositionNameList(){
		return compositionNameList;
	}
	
	protected int getSelectNumber() {
		return selectNumber;
	}
	
	protected List<List<Integer>> getActiveCompositionList(){
		return allCompositionList.get(selectNumber);
	}
	
	protected List<Integer> getActiveUnit(int number){
		return allCompositionList.get(selectNumber).get(number);
	}
	
	protected List<Integer> getNowCoreNumberList(){
		return nowCoreNumberList;
	}
	
	protected List<Integer> getNowWeaponNumberList(){
		return nowWeaponNumberList;
	}
}

//表示リスト作成
class DisplayListCreation{
	DisplaySort coreDisplaySort = new DisplaySort();
	DisplaySort weaponDisplaySort = new DisplaySort();
	
	protected DisplayListCreation(SaveData SaveData) {
		coreDisplaySort.core(getDisplayList(SaveData.getCoreNumberList()));
		weaponDisplaySort.weapon(getDisplayList(SaveData.getWeaponNumberList()));
	}
	
	protected List<Integer> getDisplayList(List<Integer> list){
		return IntStream.range(0, list.size()).mapToObj(i -> (list.get(i) == 0)? -1: i).filter(i -> i != -1).collect(Collectors.toList());
	}
	
	protected List<Integer> getCoreDisplayList() {
		return coreDisplaySort.getDisplayList();
	}
	
	protected List<Integer> getWeaponDisplayList() {
		return weaponDisplaySort.getDisplayList();
	}
}

//ユニット表示
class ImagePanel extends JPanel implements MouseListener{
	List<BufferedImage> imageList;
	List<Integer> displayList;
	List<Integer> numberList;
	boolean existsWhich;
	int selectNumber;
	int drawSize = 120;
	int column = 3;
	
	protected ImagePanel() {
		resetSelectNumber();
		addMouseListener(this);
	}
	
	protected void setImagePanel(List<BufferedImage> imageList, List<Integer> displayList, List<Integer> numberList, boolean existsWhich) {
		this.imageList = imageList;
		this.displayList = displayList;
		this.numberList = numberList;
		this.existsWhich = existsWhich;
	}
	
	protected void updateList(List<Integer> displayList) {
		this.displayList = displayList;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension(100, (displayList.size() / column + 1) * drawSize));
		IntStream.range(0, displayList.size()).forEach(i -> {
			int x = i % column * drawSize;
			int y = i / column * drawSize;
			if(selectNumber == displayList.get(i)) {
				g.setColor(Color.WHITE);
				g.fillRect(x, y, 90, 90);
			}
			g.drawImage(imageList.get(displayList.get(i)), x, y, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("" + numberList.get(displayList.get(i)), 80 + x, 80 + y);
		});
	}
	
	protected int getSelectNumber() {
		return selectNumber;
	}
	
	protected void resetSelectNumber() {
		selectNumber = -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < displayList.size(); i++) {
			int x = i % column * drawSize + 10;
			int y = i / column * drawSize + 10;
			if(ValueRange.of(x, x + MenuComposition.unitSize).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + MenuComposition.unitSize).isValidIntValue(e.getY())){
				if(selectNumber == displayList.get(i)) {
					if(existsWhich) {
						new DisplayStatus().core(imageList.get(selectNumber), selectNumber);
					}else {
						new DisplayStatus().weapon(imageList.get(selectNumber), selectNumber);
					}
				}else {
					selectNumber = displayList.get(i);
				}
				break;
	    	}
			if(i == displayList.size() - 1) {
				resetSelectNumber();
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
	List<Integer> rightElement;
	List<Integer> rightWeaponStatus;
	List<Integer> rightUnitStatus;
	int leftType;
	List<Integer> leftElement;
	List<Integer> leftWeaponStatus;
	List<Integer> leftUnitStatus;
	List<Double> coreWeaponStatus;
	List<Double> coreUnitStatus;
	
	List<Integer> rightWeaponCutList;
	List<Integer> leftWeaponCutList;
	List<Integer> coreCutList;
	
	protected StatusCalculation(List<Integer> unitData) {
		DefaultUnit DefaultUnit = new DefaultUnit();
		try {
			WeaponData WeaponData = DefaultUnit.getWeaponData(unitData.get(0));
			rightType = WeaponData.getType().get(0);
			rightElement = WeaponData.getElement();
			rightWeaponStatus = WeaponData.getWeaponStatus();
			rightUnitStatus = WeaponData.getUnitStatus();
			rightWeaponCutList = WeaponData.getCutStatus();
		}catch(Exception noWeapon) {
			rightType = defaultType();
			rightElement = defaultElement();
			rightWeaponStatus = defaultWeaponStatus();
			rightUnitStatus = defaultUnitStatus();
			rightWeaponCutList = defaultCutList();
		}
		try {
			WeaponData WeaponData = DefaultUnit.getWeaponData(unitData.get(2));
			leftType = WeaponData.getType().get(0);
			leftElement = WeaponData.getElement();
			leftWeaponStatus = WeaponData.getWeaponStatus();
			leftUnitStatus = WeaponData.getUnitStatus();
			leftWeaponCutList = WeaponData.getCutStatus();
		}catch(Exception noWeapon) {
			leftType = defaultType();
			leftElement = defaultElement();
			leftWeaponStatus = defaultWeaponStatus();
			leftUnitStatus = defaultUnitStatus();
			leftWeaponCutList = defaultCutList();
		}
		CoreData CoreData = DefaultUnit.getCoreData(unitData.get(1));
		coreWeaponStatus = CoreData.getWeaponStatus();
		coreUnitStatus = CoreData.getUnitStatus();
		coreCutList = CoreData.getCutStatus();
	}
	
	private int defaultType() {
		return -1;
	}
	
	private List<Integer> defaultElement(){
		return Arrays.asList(-1);
	}
	
	private List<Integer> defaultWeaponStatus(){
		return Arrays.asList(0, 0, 0, 0);
	}
	
	private List<Integer> defaultUnitStatus(){
		return Arrays.asList(1000, 1000, 0, 0, 0, 0);
	}
	
	private List<Integer> defaultCutList(){
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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
		Function<List<Integer>, List<Integer>> getStatus = (list) -> {
			return IntStream.range(0, list.size()).mapToObj(i -> (int) (list.get(i) * coreWeaponStatus.get(i))).toList();
		};
		List<List<Integer>> weaponStatus = new ArrayList<>();
		weaponStatus.add(rightElement);
		weaponStatus.add(getStatus.apply(rightWeaponStatus));
		weaponStatus.add(leftElement);
		weaponStatus.add(getStatus.apply(leftWeaponStatus));
		return weaponStatus;
	}
	
	protected List<List<Integer>> getUnitStatus(){
		List<List<Integer>> statusList = new ArrayList<>();
		statusList.add(IntStream.range(0, coreCutList.size()).mapToObj(i -> leftWeaponCutList.get(i) + coreCutList.get(i) + rightWeaponCutList.get(i)).toList());
		statusList.add(IntStream.range(0, coreUnitStatus.size()).mapToObj(i -> (int) ((rightUnitStatus.get(i) + leftUnitStatus.get(i)) * coreUnitStatus.get(i))).toList());
		return statusList;
	}
}