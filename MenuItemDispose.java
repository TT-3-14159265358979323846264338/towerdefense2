package defendthecastle;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import defaultdata.DefaultUnit;
import savedata.SaveComposition;
import savedata.SaveGameProgress;
import savedata.SaveHoldItem;
import screendisplay.DisplaySort;
import screendisplay.DisplayStatus;

//アイテムのリサイクル
public class MenuItemDispose extends JPanel{
	JLabel typeLabel = new JLabel();
	JButton switchButton = new JButton();
	JButton sortButton = new JButton();
	JButton disposeButton = new JButton();
	JButton returnButton = new JButton();
	JScrollPane itemScroll = new JScrollPane();
	ItemImagePanel CoreImagePanel = new ItemImagePanel();
	ItemImagePanel WeaponImagePanel = new ItemImagePanel();
	OperateData OperateData = new OperateData();
	CreateDisplayList DisplayListCreation = new CreateDisplayList(OperateData);
	List<BufferedImage> coreImageList = IntStream.range(0, DefaultUnit.CORE_DATA_MAP.size()).mapToObj(i -> DefaultUnit.CORE_DATA_MAP.get(i).getImage(2)).toList();
	List<BufferedImage> weaponImageList = IntStream.range(0, DefaultUnit.WEAPON_DATA_MAP.size()).mapToObj(i -> DefaultUnit.WEAPON_DATA_MAP.get(i).getImage(2)).toList();
	
	protected MenuItemDispose(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		add(typeLabel);
		addSwitchButton();
		addSortButton();
		addDisposeButton();
		addReturnButton(MainFrame);
		addScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLabel(typeLabel, (itemScroll.getViewport().getView() == CoreImagePanel)? "コアリスト": "武器リスト", 20, 10, 400, 30);
		setButton(switchButton, "表示切替", 20, 530, 150, 60);
		setButton(sortButton, "ソート", 190, 530, 150, 60);
		setButton(disposeButton, "リサイクル", 360, 530, 150, 60);
		setButton(returnButton, "戻る", 530, 530, 150, 60);
		setScroll(itemScroll, 20, 50, 660, 470);
		requestFocus();
	}
	
	private void addSwitchButton() {
		add(switchButton);
		switchButton.addActionListener(e->{
			itemScroll.getViewport().setView((itemScroll.getViewport().getView() == CoreImagePanel)? WeaponImagePanel: CoreImagePanel);
		});
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
	
	private void addDisposeButton() {
		add(disposeButton);
		disposeButton.addActionListener(e->{
			if(itemScroll.getViewport().getView() == CoreImagePanel) {
				OperateData.recycle(CoreImagePanel, OperateData.getCoreNumberList(), OperateData.getUsedCoreNumber(), coreImageList, IntStream.range(0, defaultdata.DefaultUnit.CORE_DATA_MAP.size()).mapToObj(i -> DefaultUnit.CORE_DATA_MAP.get(i).getRarity()).toList());
			}else {
				OperateData.recycle(WeaponImagePanel, OperateData.getWeaponNumberList(), OperateData.getUsedWeaponNumber(), weaponImageList, IntStream.range(0, defaultdata.DefaultUnit.WEAPON_DATA_MAP.size()).mapToObj(i -> DefaultUnit.WEAPON_DATA_MAP.get(i).getRarity()).toList());
			}
		});
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	private void addScroll() {
		CoreImagePanel.setImagePanel(coreImageList, DisplayListCreation.getInitialCoreDisplayList(), OperateData.getCoreNumberList(), true);
		WeaponImagePanel.setImagePanel(weaponImageList, DisplayListCreation.getInitialWeaponDisplayList(), OperateData.getWeaponNumberList(), false);
		itemScroll.getViewport().setView(CoreImagePanel);
    	add(itemScroll);
	}
	
	private void setLabel(JLabel label, String name, int x, int y, int width, int height) {
		label.setText(name);
		label.setBounds(x, y, width, height);
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
	}
	
	private void setButton(JButton button, String name, int x, int y, int width, int height) {
		button.setText(name);
		button.setBounds(x, y, width, height);
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void setScroll(JScrollPane scroll, int x, int y, int width, int height) {
		scroll.setBounds(x, y, width, height);
		scroll.setPreferredSize(scroll.getSize());
	}
}

//セーブデータの確認
class OperateData{
	SaveHoldItem SaveHoldItem = new SaveHoldItem();
	SaveComposition SaveComposition = new SaveComposition();
	SaveGameProgress SaveGameProgress = new SaveGameProgress();
	List<Integer> coreNumberList;
	List<Integer> weaponNumberList;
	List<List<List<Integer>>> allCompositionList;
	int medal;
	int[] usedCoreNumber;
	int[] usedWeaponNumber;
	
	protected OperateData() {
		load();
		itemCount();
	}
	
	private void load() {
		SaveHoldItem.load();
		SaveComposition.load();
		SaveGameProgress.load();
		coreNumberList = SaveHoldItem.getCoreNumberList();
		weaponNumberList = SaveHoldItem.getWeaponNumberList();
		allCompositionList = SaveComposition.getAllCompositionList();
		medal = SaveGameProgress.getMedal();
	}
	
	private void save() {
		SaveHoldItem.save(coreNumberList, weaponNumberList);
		SaveGameProgress.save(SaveGameProgress.getClearStatus(), SaveGameProgress.getMeritStatus(), medal, SaveGameProgress.getSelectStage());
	}
	
	private void itemCount() {
		BiConsumer<int[], int[]> maxNumberUpdate = (max, count) -> {
			IntStream.range(0, max.length).forEach(i -> {
				if(max[i] < count[i]) {
					max[i] = count[i];
				}
			});
		};
		int[] coreMax = new int[coreNumberList.size()];
		int[] weaponMax = new int[weaponNumberList.size()];
		IntStream.range(0, allCompositionList.size()).forEach(i -> {
			int[] coreCount = new int[coreNumberList.size()];
			int[] weaponCount = new int[weaponNumberList.size()];
			IntStream.range(0, allCompositionList.get(i).size()).forEach(j -> {
				try {
					weaponCount[allCompositionList.get(i).get(j).get(0)]++;
	    		}catch(Exception ignore) {
					//右武器を装備していないので、無視する
				}
				coreCount[allCompositionList.get(i).get(j).get(1)]++;
	    		try {
	    			weaponCount[allCompositionList.get(i).get(j).get(2)]++;
	    		}catch(Exception ignore) {
					//左武器を装備していないので、無視する
				}
			});
			maxNumberUpdate.accept(coreMax, coreCount);
			maxNumberUpdate.accept(weaponMax, weaponCount);
		});
		usedCoreNumber = coreMax;
		usedWeaponNumber = weaponMax;
		//初期武器は2本以外リサイクル禁止
		usedWeaponNumber[0] += 2;
		usedWeaponNumber[1] += 2;
	}
	
	protected void recycle(ItemImagePanel ImagePanel, List<Integer> numberList, int[] usedNumber, List<BufferedImage> imageList, List<Integer> rarityList) {
		Predicate<Integer> selectCheck = (select) -> {
			if(select < 0) {
				showMessageDialog(null, "リサイクルする対象が選択されていません");
				return false;
			}
			return true;
		};
		Predicate<Integer> numberCheck = (max) -> {
			if(max <= 0) {
				showMessageDialog(null, "最大所持数まで編成しているため、リサイクルできません");
				return false;
			}
			return true;
		};
		int select = ImagePanel.getSelectNumber();
		if(selectCheck.test(select)) {
			int max = numberList.get(select) - usedNumber[select];
			if(numberCheck.test(max)) {
				RecyclePanel RecyclePanel = new RecyclePanel(imageList.get(select), max, rarityList.get(select));
				if(RecyclePanel.getCanDispose()) {
					numberList.set(select, numberList.get(select) - RecyclePanel.getQuantity());
					medal += RecyclePanel.getMedal();
					save();
				}
			}
		}
	}
	
	protected List<Integer> getCoreNumberList(){
		return coreNumberList;
	}
	
	protected List<Integer> getWeaponNumberList(){
		return weaponNumberList;
	}
	
	protected int[] getUsedCoreNumber() {
		return usedCoreNumber;
	}
	
	protected int[] getUsedWeaponNumber() {
		return usedWeaponNumber;
	}
}

//表示リスト作成
class CreateDisplayList{
	DisplaySort coreDisplaySort = new DisplaySort();
	DisplaySort weaponDisplaySort = new DisplaySort();
	OperateData OperateData;
	
	protected CreateDisplayList(OperateData OperateData) {
		this.OperateData = OperateData;
		coreDisplaySort.core(getInitialCoreDisplayList());
		weaponDisplaySort.weapon(getInitialWeaponDisplayList());
	}
	
	protected List<Integer> getInitialCoreDisplayList(){
		List<Integer> displayList = getDisplayList(OperateData.getCoreNumberList());
		//初期コアはリサイクル禁止
		displayList.remove(0);
		return displayList;
	}
	
	protected List<Integer> getInitialWeaponDisplayList(){
		return getDisplayList(OperateData.getWeaponNumberList());
	}
	
	private List<Integer> getDisplayList(List<Integer> list){
		return IntStream.range(0, list.size()).mapToObj(i -> (list.get(i) == 0)? -1: i).filter(i -> i != -1).collect(Collectors.toList());
	}
	
	protected List<Integer> getCoreDisplayList() {
		return coreDisplaySort.getDisplayList();
	}
	
	protected List<Integer> getWeaponDisplayList() {
		return weaponDisplaySort.getDisplayList();
	}
}

//所持リスト表示
class ItemImagePanel extends JPanel implements MouseListener{
	List<BufferedImage> imageList;
	List<Integer> numberList;
	List<Integer> displayList;
	boolean existsWhich;
	int selectNumber;
	int unitSize = 60;
	int drawSize = 120;
	int columns = 5;
	
	protected ItemImagePanel() {
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
		setPreferredSize(new Dimension(400, (displayList.size() / columns + 1) * drawSize));
		IntStream.range(0, displayList.size()).forEach(i -> {
			int x = i % columns * drawSize;
			int y = i / columns * drawSize;
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
			int x = i % columns * drawSize + 10;
			int y = i / columns * drawSize + 10;
			if(ValueRange.of(x, x + unitSize).isValidIntValue(e.getX())
					&& ValueRange.of(y, y + unitSize).isValidIntValue(e.getY())){
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

//リサイクル画面表示用ダイアログ
class RecycleDialog extends JDialog{
	protected void setDialog(RecyclePanel RecyclePanel) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ステータス");
		setSize(600, 195);
		setLocationRelativeTo(null);
		add(RecyclePanel);
		setVisible(true);
	}
	
	protected void disposeDialog() {
		dispose();
	}
}

//リサイクル数確定画面
class RecyclePanel extends JPanel{
	JLabel commentLabel = new JLabel();
	JLabel resultLabel = new JLabel();
	JSpinner countSpinner = new JSpinner();
	JButton recycleButton = new JButton();
	JButton returnButton = new JButton();
	RecycleDialog RecycleDialog = new RecycleDialog();
	BufferedImage image;
	int rarity;
	int quantity;
	boolean canDispose;
	
	protected RecyclePanel(BufferedImage image, int max, int rarity) {
		this.image = image;
		this.rarity = rarity;
		add(commentLabel);
		add(resultLabel);
		addSpinner(max);
		addRecycleButton();
		addReturnButton();
		RecycleDialog.setDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 10, 10, null);
		setLabel(commentLabel, "ガチャメダルへリサイクルする数量を入力してください", 120, 10, 400, 30);
		setLabel(resultLabel, "ガチャメダル: " + getMedal() +  "枚", 370, 50, 400, 30);
		setSpinner(countSpinner, 250, 50, 100, 30);
		setButton(recycleButton, "リサイクル", 170, 100, 120, 40);
		setButton(returnButton, "戻る", 310, 100, 120, 40);
		requestFocus();
	}
	
	private void addSpinner(int max) {
		add(countSpinner);
		countSpinner.addChangeListener(e->{
			importQuantity();
		});
		countSpinner.setModel(new SpinnerNumberModel(1, 1, max, 1));
		importQuantity();
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(countSpinner);
		editor.getTextField().setEditable(false);
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		countSpinner.setEditor(editor);
	}
	
	private void addRecycleButton() {
		add(recycleButton);
		recycleButton.addActionListener(e->{
			if(YES_OPTION == showConfirmDialog(null, quantity + "個をリサイクルしますか","リサイクル確認",YES_NO_OPTION , QUESTION_MESSAGE)) {
				canDispose = true;
				RecycleDialog.disposeDialog();
			}
		});
	}
	
	private void addReturnButton() {
		add(returnButton);
		returnButton.addActionListener(e->{
			RecycleDialog.disposeDialog();
		});
	}
	
	private void setLabel(JLabel label, String name, int x, int y, int width, int height) {
		label.setText(name);
		label.setBounds(x, y, width, height);
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
	}
	
	private void setSpinner(JSpinner spinner, int x, int y, int width, int height) {
		spinner.setBounds(x, y, width, height);
		spinner.setPreferredSize(spinner.getSize());
		spinner.setFont(new Font("Arail", Font.BOLD, 15));
	}
	
	private void setButton(JButton button, String name, int x, int y, int width, int height) {
		button.setText(name);
		button.setBounds(x, y, width, height);
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
	}
	
	private void importQuantity() {
		quantity = (int) countSpinner.getValue();
	}
	
	protected boolean getCanDispose() {
		return canDispose;
	}
	
	protected int getQuantity() {
		return quantity;
	}
	
	protected int getMedal() {
		return quantity * rarity * rarity * 3;
	}
}