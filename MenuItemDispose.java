package menuitemdispose;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import defaultdata.DefaultData;
import displaysort.DisplaySort;
import displaystatus.DisplayStatus;
import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

//アイテムのリサイクル
public class MenuItemDispose extends JPanel{
	JLabel typeLabel = new JLabel();
	JButton switchButton = new JButton();
	JButton sortButton = new JButton();
	JButton disposeButton = new JButton();
	JButton returnButton = new JButton();
	JScrollPane itemScroll = new JScrollPane();
	ImagePanel CoreImagePanel = new ImagePanel();
	ImagePanel WeaponImagePanel = new ImagePanel();
	DisplaySort coreDisplaySort = new DisplaySort();
	DisplaySort weaponDisplaySort = new DisplaySort();
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<Integer> coreNumberList;
	List<Integer> weaponNumberList;
	List<List<List<Integer>>> allCompositionList;
	int[] usedCoreNumber;
	int[] usedWeaponNumber;
	List<BufferedImage> coreImageList = new DefaultData().getCoreImage(2);
	List<BufferedImage> weaponImageList = new DefaultData().getWeaponImage(2);
	
	public MenuItemDispose(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		load();
		itemCount();
		initializeDisplayList();
		add(typeLabel);
		addSwitchButton();
		addSortButton();
		addDisposeButton();
		addReturnButton(MainFrame);
		addScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setTypeLabel();
		setSwitchButton();
		setSortButton();
		setDisposeButton();
		setReturnButton();
		setItemScroll();
		requestFocus();
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
		coreNumberList = SaveHoldItem.getCoreNumberList();
		weaponNumberList = SaveHoldItem.getWeaponNumberList();
		allCompositionList = SaveComposition.getAllCompositionList();
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
	}
	
	private void initializeDisplayList() {
		coreDisplaySort.core(getCoreDisplayList());
		weaponDisplaySort.weapon(getWeaponDisplayList());
	}
	
	private List<Integer> getCoreDisplayList(){
		List<Integer> displayList = getDisplayList(coreNumberList);
		displayList.remove(0);//初期コアはリサイクル禁止
		return displayList;
	}
	
	private List<Integer> getWeaponDisplayList(){
		return getDisplayList(weaponNumberList);
	}
	
	private List<Integer> getDisplayList(List<Integer> list){
		return IntStream.range(0, list.size()).mapToObj(i -> (list.get(i) == 0)? -1: i).filter(i -> i != -1).collect(Collectors.toList());
	}
	
	private void setTypeLabel() {
		typeLabel.setText((itemScroll.getViewport().getView() == CoreImagePanel)? "コアリスト": "武器リスト");
		typeLabel.setBounds(20, 10, 400, 30);
		typeLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
	}
	
	private void addSwitchButton() {
		add(switchButton);
		switchButton.addActionListener(e->{
			itemScroll.getViewport().setView((itemScroll.getViewport().getView() == CoreImagePanel)? WeaponImagePanel: CoreImagePanel);
		});
	}
	
	private void setSwitchButton() {
		switchButton.setText("表示切替");
		switchButton.setBounds(20, 530, 150, 60);
		setButton(switchButton);
	}
	
	private void addSortButton() {
		add(sortButton);
		sortButton.addActionListener(e->{
			if(itemScroll.getViewport().getView() == CoreImagePanel) {
				CoreImagePanel.updateList(coreDisplaySort.getDisplayList());
			}else {
				WeaponImagePanel.updateList(weaponDisplaySort.getDisplayList());
			}
		});
	}
	
	private void setSortButton() {
		sortButton.setText("ソート");
		sortButton.setBounds(190, 530, 150, 60);
		setButton(sortButton);
	}
	
	private void addDisposeButton() {
		add(disposeButton);
		disposeButton.addActionListener(e->{
			if(itemScroll.getViewport().getView() == CoreImagePanel) {
				recycle(CoreImagePanel, coreNumberList, usedCoreNumber, coreImageList, DefaultData.CORE_RARITY_LIST);
			}else {
				recycle(WeaponImagePanel, weaponNumberList, usedWeaponNumber, weaponImageList, DefaultData.WEAPON_RARITY_LIST);
			}
		});
	}
	
	private void recycle(ImagePanel ImagePanel, List<Integer> numberList, int[] usedNumber, List<BufferedImage> imageList, List<Integer> rarityList) {
		Predicate<Integer> selectCheck = (select) -> {
			if(select < 0) {
				showMessageDialog(null, "リサイクルする対象が選択されていません");
				return false;
			}
			return true;
		};
		Predicate<Integer> numberCheck = (max) -> {
			if(max == 0) {
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
					
					//int medal = RecyclePanel.getMedal();
					//いずれガチャメダルの保存も記述する
					
					save();
				}
			}
		}
	}
	
	private void save() {
		try {
			ObjectOutputStream saveItemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			saveItemData.writeObject(new SaveHoldItem(coreNumberList, weaponNumberList));
			saveItemData.close();
			
			//いずれガチャメダルの保存も記述する
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDisposeButton() {
		disposeButton.setText("リサイクル");
		disposeButton.setBounds(360, 530, 150, 60);
		setButton(disposeButton);
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(530, 530, 150, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addScroll() {
		CoreImagePanel.setImagePanel(coreImageList, getCoreDisplayList(), coreNumberList, true);
		WeaponImagePanel.setImagePanel(weaponImageList, getWeaponDisplayList(), weaponNumberList, false);
		itemScroll.getViewport().setView(CoreImagePanel);
    	add(itemScroll);
	}
	
	private void setItemScroll() {
		itemScroll.setBounds(20, 50, 660, 470);
		itemScroll.setPreferredSize(itemScroll.getSize());
	}
}

//所持リスト表示
class ImagePanel extends JPanel implements MouseListener{
	List<BufferedImage> imageList;
	List<Integer> numberList;
	List<Integer> displayList;
	boolean existsWhich;
	int selectNumber;
	int unitSize = 60;
	int drawSize = 120;
	int columns = 5;
	
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
		drawImage(g);
		setComenntLabel();
		setResultLabel();
		setSpinner();
		setRecycleButton();
		setReturnButton();
		requestFocus();
	}
	
	private void drawImage(Graphics g) {
		g.drawImage(image, 10, 10, null);
	}
	
	private void setComenntLabel() {
		commentLabel.setText("ガチャメダルへリサイクルする数量を入力してください");
		commentLabel.setBounds(120, 10, 400, 30);
		setLabel(commentLabel);
	}
	
	private void setResultLabel() {
		resultLabel.setText("ガチャメダル: " + getMedal() +  "枚");
		resultLabel.setBounds(370, 50, 400, 30);
		setLabel(resultLabel);
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
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
		editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
		countSpinner.setEditor(editor);
	}
	
	private void setSpinner() {
		countSpinner.setBounds(250, 50, 100, 30);
		countSpinner.setPreferredSize(countSpinner.getSize());
		countSpinner.setFont(new Font("Arail", Font.BOLD, 15));
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
	
	private void setRecycleButton() {
		recycleButton.setText("リサイクル");
		recycleButton.setBounds(170, 100, 120, 40);
		setButton(recycleButton);
	}
	
	private void addReturnButton() {
		add(returnButton);
		returnButton.addActionListener(e->{
			RecycleDialog.disposeDialog();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(310, 100, 120, 40);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
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
		return quantity * rarity;
	}
}
