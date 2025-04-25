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
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import defaultdata.DefaultData;
import drawstatus.DrawStatus;
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
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<Integer> coreNumberList;
	List<Integer> weaponNumberList;
	List<List<List<Integer>>> allCompositionList;
	int[] usedCoreNumber;
	int[] usedWeaponNumber;
	List<Integer> coreDrawList = new ArrayList<>();
	List<Integer> weaponDrawList = new ArrayList<>();
	List<BufferedImage> coreImageList = new DefaultData().getCoreImage(2);
	List<BufferedImage> weaponImageList = new DefaultData().getWeaponImage(2);
	
	public MenuItemDispose(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		load();
		itemCount();
		initializeDrawList();
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
			for(int i = 0; i < max.length; i++) {
				if(max[i] < count[i]) {
					max[i] = count[i];
				}
			}
		};
		int[] coreMax = new int[coreNumberList.size()];
		int[] weaponMax = new int[weaponNumberList.size()];
		for(int i = 0; i < allCompositionList.size(); i++) {
			int[] coreCount = new int[coreNumberList.size()];
			int[] weaponCount = new int[weaponNumberList.size()];
			for(int j = 0; j < allCompositionList.get(i).size(); j++) {
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
			}
			maxNumberUpdate.accept(coreMax, coreCount);
			maxNumberUpdate.accept(weaponMax, weaponCount);
		}
		usedCoreNumber = coreMax;
		usedWeaponNumber = weaponMax;
	}
	
	private void initializeDrawList() {
		BiConsumer<List<Integer>, List<Integer>> initialize = (drawList, numberList) -> {
			for(int i = 0; i < numberList.size(); i++) {
				if(numberList.get(i) != 0) {
					drawList.add(i);
				}
			}
		};
		initialize.accept(coreDrawList, coreNumberList);
		coreDrawList.remove(0);//初期コアはリサイクル禁止
		initialize.accept(weaponDrawList, weaponNumberList);
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
			
			//JCheckBox, JRadioButton
			
			
			
			
			
			
			
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
				recycle(CoreImagePanel, coreNumberList, usedCoreNumber, coreDrawList, coreImageList, DefaultData.CORE_RARITY_LIST);
			}else {
				recycle(WeaponImagePanel, weaponNumberList, usedWeaponNumber, weaponDrawList, weaponImageList, DefaultData.WEAPON_RARITY_LIST);
			}
		});
	}
	
	private void recycle(ImagePanel ImagePanel, List<Integer> numberList, int[] usedNumber, List<Integer> drawList, List<BufferedImage> imageList, List<Integer> rarityList) {
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
			int max = numberList.get(drawList.get(select)) - usedNumber[drawList.get(select)];
			if(numberCheck.test(max)) {
				RecyclePanel RecyclePanel = new RecyclePanel(imageList.get(drawList.get(select)), max, rarityList.get(drawList.get(select)));
				int quantity = RecyclePanel.getQuantity();
				int medal = RecyclePanel.getMedal();
				if(medal !=0) {
					numberList.set(drawList.get(select), numberList.get(drawList.get(select)) - quantity);
					
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
		CoreImagePanel.setImagePanel(coreImageList, coreDrawList, coreNumberList, true);
		WeaponImagePanel.setImagePanel(weaponImageList, weaponDrawList, weaponNumberList, false);
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
	List<Integer> drawList;
	boolean existsWhich;
	int selectNumber;
	final static int SIZE = 60;
	
	protected ImagePanel() {
		resetSelectNumber();
		addMouseListener(this);
	}
	
	protected void setImagePanel(List<BufferedImage> imageList, List<Integer> drawList, List<Integer> numberList, boolean existsWhich) {
		this.imageList = imageList;
		this.drawList = drawList;
		this.numberList = numberList;
		this.existsWhich = existsWhich;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension(400, (drawList.size() / 5 + 1) * 120));
		for(int i = 0; i < drawList.size(); i++) {
			if(selectNumber == i) {
				g.setColor(Color.WHITE);
				g.fillRect(i % 5 * 120, i / 5 * 120, 90, 90);
			}
			g.drawImage(imageList.get(drawList.get(i)), i % 5 * 120, i / 5 * 120, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("" + numberList.get(drawList.get(i)), 80 + i % 5 * 120, 80 + i / 5 * 120);
		}
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
		for(int i = 0; i < drawList.size(); i++) {
			if(ValueRange.of(10 + i % 5 * 120, 10 + i % 5 * 120 + SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(10 + i / 5 * 120, 10 + i / 5 * 120 + SIZE).isValidIntValue(e.getY())){
				if(selectNumber == i) {
					if(existsWhich) {
						new DrawStatus().core(imageList.get(drawList.get(selectNumber)), drawList.get(selectNumber));
					}else {
						new DrawStatus().weapon(imageList.get(drawList.get(selectNumber)), drawList.get(selectNumber));
					}
				}else {
					selectNumber = i;
				}
				break;
	    	}
			if(i == imageList.size() - 1) {
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
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
			if(YES_OPTION == showConfirmDialog(null, (int) countSpinner.getValue() + "個をリサイクルしますか","リサイクル確認",YES_NO_OPTION , QUESTION_MESSAGE)) {
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
			quantity = 0;
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
	
	protected int getQuantity() {
		return quantity;
	}
	
	protected int getMedal() {
		return quantity * rarity;
	}
}
