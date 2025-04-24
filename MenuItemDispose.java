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
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
	int[] coreMaxNumber;
	int[] weaponMaxNumber;
	List<Integer> coreDrawList = new ArrayList<>();
	List<Integer> weaponDrawList = new ArrayList<>();
	List<BufferedImage> coreImageList = new DefaultData().getCoreImage(2);
	List<BufferedImage> weaponImageList = new DefaultData().getWeaponImage(2);
	
	public MenuItemDispose(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		load();
		itemCount();
		initializeDrawList();
		addSwitchButton();
		addSortButton();
		addDisposeButton();
		addReturnButton(MainFrame);
		addScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
		BiConsumer<int[], int[]> check = (max, count) -> {
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
			check.accept(coreMax, coreCount);
			check.accept(weaponMax, weaponCount);
		}
		coreMaxNumber = coreMax;
		weaponMaxNumber = weaponMax;
	}
	
	private void initializeDrawList() {
		BiConsumer<List<Integer>, Integer> initialize = (list, count) -> {
			int number = 0;
			for(int i = 0; i < count; i++) {
				list.add(number);
				number++;
			}
		};
		initialize.accept(coreDrawList, coreNumberList.size());
		initialize.accept(weaponDrawList, weaponNumberList.size());
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
				recycle(CoreImagePanel, coreNumberList, coreMaxNumber, coreDrawList, coreImageList);
			}else {
				recycle(WeaponImagePanel, weaponNumberList, weaponMaxNumber, weaponDrawList, weaponImageList);
			}
		});
	}
	
	private void recycle(ImagePanel ImagePanel, List<Integer> numberList, int[] maxNumber, List<Integer> drawList, List<BufferedImage> imageList) {
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
			int max = numberList.get(drawList.get(select)) - maxNumber[drawList.get(select)];
			if(numberCheck.test(max)) {
				new RecyclePanel(imageList.get(drawList.get(select)), max);
			}
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
		itemScroll.setBounds(20, 20, 660, 500);
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
	protected RecycleDialog(RecyclePanel RecyclePanel) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ステータス");
		setSize(600, 195);
		setLocationRelativeTo(null);
		add(RecyclePanel);
		setVisible(true);
	}
}

//リサイクル数確定画面
class RecyclePanel extends JPanel{
	JLabel commentLabel = new JLabel();
	JSpinner countSpinner = new JSpinner();
	JButton recycleButton = new JButton();
	JButton returnButton = new JButton();
	BufferedImage image;
	int count;
	
	protected RecyclePanel(BufferedImage image, int max) {
		this.image = image;
		add(commentLabel);
		addSpinner(max);
		addRecycleButton();
		addReturnButton();
		new RecycleDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawImage(g);
		setLabel();
		setSpinner();
		setRecycleButton();
		setReturnButton();
		requestFocus();
	}
	
	private void drawImage(Graphics g) {
		g.drawImage(image, 10, 10, null);
	}
	
	private void setLabel() {
		commentLabel.setText("ガチャメダルへリサイクルする数量を入力してください");
		commentLabel.setBounds(120, 10, 400, 30);
		commentLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
	}
	
	private void addSpinner(int max) {
		add(countSpinner);
		countSpinner.setModel(new SpinnerNumberModel(1, 1, max, 1));
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
			count = (int) countSpinner.getValue();
			
			
			
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
			count = 0;
			
			
			
			
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
}
