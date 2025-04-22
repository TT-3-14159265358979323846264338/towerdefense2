package menuitemdispose;

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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import defaultdata.DefaultData;
import drawstatus.DrawStatus;
import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

//アイテムのリサイクル
public class MenuItemDispose extends JPanel{
	JButton coreSortButton = new JButton();
	JButton weaponSortButton = new JButton();
	JButton disposeButton = new JButton();
	JButton returnButton = new JButton();
	JScrollPane coreScroll = new JScrollPane();
	JScrollPane weaponScroll = new JScrollPane();
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
	
	public MenuItemDispose(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		load();
		itemCount();
		initializeDrawList();
		addCoreSortButton();
		addWeaponSortButton();
		addDisposeButton();
		addReturnButton(MainFrame);
		addCoreScroll();
		addWeaponScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCoreSortButton();
		setWeaponSortButton();
		setDisposeButton();
		setReturnButton();
		setCoreScroll();
		setWeaponScroll();
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
	
	private void addCoreSortButton() {
		add(coreSortButton);
		coreSortButton.addActionListener(e->{
			
			
			
			
			
			
			
			
			
			
		});
	}
	
	private void setCoreSortButton() {
		coreSortButton.setText("ソート (コア)");
		coreSortButton.setBounds(150, 530, 200, 60);
		setButton(coreSortButton);
	}
	
	private void addWeaponSortButton() {
		add(weaponSortButton);
		weaponSortButton.addActionListener(e->{
			
			
			
			
			
			
			
			
			
			
		});
	}
	
	private void setWeaponSortButton() {
		weaponSortButton.setText("ソート (武器)");
		weaponSortButton.setBounds(710, 530, 200, 60);
		setButton(weaponSortButton);
	}
	
	private void addDisposeButton() {
		add(disposeButton);
		disposeButton.addActionListener(e->{
			
			
			
			
			
			
			
			
			
			
		});
	}
	
	private void setDisposeButton() {
		disposeButton.setText("リサイクル");
		disposeButton.setBounds(370, 530, 150, 60);
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
		returnButton.setBounds(540, 530, 150, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		button.setFocusable(false);
	}
	
	private void addCoreScroll() {
		CoreImagePanel.setImagePanel(new DefaultData().getCoreImage(2), coreDrawList, coreMaxNumber, coreNumberList, WeaponImagePanel, true);
		coreScroll.getViewport().setView(CoreImagePanel);
    	add(coreScroll);
	}
	
	private void setCoreScroll() {
		coreScroll.setBounds(20, 20, 500, 500);
		coreScroll.setPreferredSize(coreScroll.getSize());
	}
	
	private void addWeaponScroll() {
		WeaponImagePanel.setImagePanel(new DefaultData().getWeaponImage(2), weaponDrawList, weaponMaxNumber, weaponNumberList, CoreImagePanel, false);
		weaponScroll.getViewport().setView(WeaponImagePanel);
    	add(weaponScroll);
	}
	
	private void setWeaponScroll() {
		weaponScroll.setBounds(540, 20, 500, 500);
		weaponScroll.setPreferredSize(weaponScroll.getSize());
	}
}

class ImagePanel extends JPanel implements MouseListener{
	int[] maxNumber;
	List<BufferedImage> imageList;
	List<Integer> numberList;
	List<Integer> drawList;
	ImagePanel ImagePanel;
	boolean existsWhich;
	int selectNumber;
	final static int SIZE = 60;
	
	protected ImagePanel() {
		resetSelectNumber();
		addMouseListener(this);
	}
	
	protected void setImagePanel(List<BufferedImage> imageList, List<Integer> drawList, int[] maxNumber, List<Integer> numberList, ImagePanel ImagePanel, boolean existsWhich) {
		this.imageList = imageList;
		this.drawList = drawList;
		this.maxNumber = maxNumber;
		this.numberList = numberList;
		this.existsWhich = existsWhich;
		this.ImagePanel = ImagePanel;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension(400, (drawList.size() / 4 + 1) * 120));
		for(int i = 0; i < drawList.size(); i++) {
			if(selectNumber == i) {
				g.setColor(Color.WHITE);
				g.fillRect(i % 4 * 120, i / 4 * 120, 90, 90);
			}
			g.drawImage(imageList.get(drawList.get(i)), i % 4 * 120, i / 4 * 120, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("" + numberList.get(drawList.get(i)), 80 + i % 4 * 120, 80 + i / 4 * 120);
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
			if(ValueRange.of(10 + i % 4 * 120, 10 + i % 4 * 120 + SIZE).isValidIntValue(e.getX())
					&& ValueRange.of(10 + i / 4 * 120, 10 + i / 4 * 120 + SIZE).isValidIntValue(e.getY())){
				if(selectNumber == i) {
					if(existsWhich) {
						new DrawStatus().core(imageList.get(drawList.get(selectNumber)), drawList.get(selectNumber));
					}else {
						new DrawStatus().weapon(imageList.get(drawList.get(selectNumber)), drawList.get(selectNumber));
					}
				}else {
					selectNumber = i;
					ImagePanel.resetSelectNumber();
				}
				break;
	    	}
			if(i == imageList.size() - 1) {
				resetSelectNumber();
				ImagePanel.resetSelectNumber();
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
