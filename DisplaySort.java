package displaysort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import defaultdata.DefaultData;

//ソート画面振り分け
public class DisplaySort extends SortPanel{
	public void core(List<Integer> defaultList) {
		rarityList = DefaultData.CORE_RARITY_LIST;
		weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST;
		unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST;
		cutList = DefaultData.CORE_CUT_STATUS_LIST;
		super.setSortPanel(defaultList);
	}
	
	public void weapon(List<Integer> defaultList) {
		rarityList = DefaultData.WEAPON_RARITY_LIST;
		weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		cutList = DefaultData.WEAPON_CUT_STATUS_LIST;
		typeList = DefaultData.WEAPON_TYPE;
		elementList = DefaultData.WEAPON_ELEMENT;
		super.setSortPanel(defaultList);
	}
}

//ソート画面表示用ダイアログ
class SortDialog extends JDialog{
	protected void setSortDialog(SortPanel SortPanel) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ソート/並び替え");
		setSize(835, 535);
		setLocationRelativeTo(null);
		add(SortPanel);
		setVisible(true);
	}
	
	protected void disposeDialog() {
		dispose();
	}
}

//ソート画面
class SortPanel extends JPanel {
	JLabel sortLabel = new JLabel();
	JLabel filterLabel = new JLabel();
	JLabel[] commentLabel = new JLabel[8]; {
		for(int i = 0; i < commentLabel.length; i++) {
			commentLabel[i] = new JLabel();
		}
	};
	JButton sortButton = new JButton();
	JButton resetButton = new JButton();
	JButton returnButton = new JButton();
	JRadioButton[] order;
	JRadioButton[] raritySort;
	JRadioButton[] weapon;
	JRadioButton[] unit;
	JRadioButton[] cut;
	JRadioButton[] rarity;
	JRadioButton[] distance;
	JRadioButton[] handle;
	JRadioButton[] element;
	SortDialog SortDialog = new SortDialog();
	List<Integer> rarityList;
	List<List<Double>> weaponStatusList;
	List<List<Double>> unitStatusList;
	List<List<Integer>> cutList;
	List<List<Integer>> typeList;
	List<List<Integer>> elementList;
	List<Integer> defaultDisplayList;
	List<Integer> diaplayList;
	boolean canSort;
	
	protected void setSortPanel(List<Integer> defaultList) {
		setBackground(new Color(240, 170, 80));
		this.defaultDisplayList = defaultList;
		addLabel();
		addSortButton();
		addResetButton();
		addReturnButton();
		initializeRadioButton();
		setRadioButtonName();
	}
	
	protected void display() {
		SortDialog.setSortDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawField(g);
		setLabel();
		setButton();
		setRadioButton();
	}
	
	private void addLabel(){
		sortLabel.setText("並び替え");
		setLabel(sortLabel);
		add(sortLabel);
		filterLabel.setText("絞り込み");
		setLabel(filterLabel);
		add(filterLabel);
		commentLabel[0].setText("レアリティ");
		commentLabel[1].setText("武器ステータス");
		commentLabel[2].setText("ユニットステータス");
		commentLabel[3].setText("属性耐性");
		commentLabel[4].setText("レアリティ");
		if(Objects.nonNull(typeList)) {
			commentLabel[5].setText("距離タイプ");
			commentLabel[6].setText("装備タイプ");
			commentLabel[7].setText("属性");
		}
		for(int i = 0; i < commentLabel.length; i++) {
			commentLabel[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
			add(commentLabel[i]);
		}
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addSortButton() {
		sortButton.setText("ソート");
		setButton(sortButton);
		add(sortButton);
		sortButton.addActionListener(e->{
			canSort = true;
			SortDialog.dispose();
		});
	}
	
	private void addResetButton() {
		resetButton.setText("リセット");
		setButton(resetButton);
		add(resetButton);
		resetButton.addActionListener(e->{
			Consumer<JRadioButton[]> initialize = (radio) -> {
				for(int i = 0; i < radio.length; i++) {
					radio[i].setSelected(false);
				}
			};
			order[0].setSelected(true);
			raritySort[0].setSelected(true);
			initialize.accept(rarity);
			if(Objects.nonNull(typeList)) {
				initialize.accept(distance);
				initialize.accept(handle);
				initialize.accept(element);
			}
		});
	}
	
	private void addReturnButton() {
		returnButton.setText("戻る");
		setButton(returnButton);
		add(returnButton);
		returnButton.addActionListener(e->{
			SortDialog.dispose();
		});
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void initializeRadioButton() {
		Function<Integer, JRadioButton[]> initialize = (size) -> {
			JRadioButton[] radio = new JRadioButton[size];
			for(int i = 0; i < size; i++) {
				radio[i] = new JRadioButton();
				radio[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
				radio[i].setOpaque(false);
				add(radio[i]);
			}
			return radio;
		};
		BiConsumer<ButtonGroup, JRadioButton[]> grouping = (group, radio) -> {
			for(int i = 0; i < radio.length; i++) {
				group.add(radio[i]);
			}
		};
		
		order = initialize.apply(2);
		order[0].setSelected(true);
		ButtonGroup sortGroup = new ButtonGroup();
		grouping.accept(sortGroup, order);
		
		raritySort = initialize.apply(1);
		raritySort[0].setSelected(true);
		weapon = initialize.apply(DefaultData.WEAPON_WEAPON_MAP.size());
		unit = initialize.apply(DefaultData.WEAPON_UNIT_MAP.size());
		cut = initialize.apply(DefaultData.ELEMENT_MAP.size());
		ButtonGroup itemGroup = new ButtonGroup();
		grouping.accept(itemGroup, raritySort);
		grouping.accept(itemGroup, weapon);
		grouping.accept(itemGroup, unit);
		grouping.accept(itemGroup, cut);
		
		rarity = initialize.apply(Collections.max(rarityList));
		if(Objects.nonNull(typeList)) {
			distance = initialize.apply(DefaultData.DISTANCE_MAP.size());
			handle = initialize.apply(DefaultData.HANDLE_MAP.size());
			element = initialize.apply(DefaultData.ELEMENT_MAP.size());
		}
	}
	
	private void setRadioButtonName() {
		BiConsumer<JRadioButton[], Map<Integer, String>> getName = (radio, map) -> {
			for(int i = 0; i < radio.length; i++) {
				radio[i].setText(map.get(i));
			}
		};
		order[0].setText("降順");
		order[1].setText("昇順");
		raritySort[0].setText("レアリティ");
		getName.accept(weapon, DefaultData.WEAPON_WEAPON_MAP);
		getName.accept(unit, DefaultData.WEAPON_UNIT_MAP);
		getName.accept(cut, DefaultData.ELEMENT_MAP);
		for(int i = 0; i < rarity.length; i++) {
			rarity[i].setText("★" + (i + 1));
		}
		if(Objects.nonNull(typeList)) {
			getName.accept(distance, DefaultData.DISTANCE_MAP);
			getName.accept(handle, DefaultData.HANDLE_MAP);
			getName.accept(element, DefaultData.ELEMENT_MAP);
		}
	}
	
	private void setLabel(){
		sortLabel.setBounds(10, 10, 300, 40);
		filterLabel.setBounds(10, 240, 300, 40);
		for(int i = 0; i < commentLabel.length / 2; i++) {
			commentLabel[i].setBounds(10, 90 + 30 * (i % 4), 300, 30);
			commentLabel[i + 4].setBounds(10, 280 + 30 * (i % 4), 300, 30);
		}
	}
	
	private void setButton() {
		sortButton.setBounds(220, 440, 120, 40);
		resetButton.setBounds(350, 440, 120, 40);
		returnButton.setBounds(480, 440, 120, 40);
	}
	
	private void setRadioButton() {
		int sizeX = 110;
		int sizeY = 30;
		for(int i = 0; i < order.length; i++) {
			order[i].setBounds(150 + i * sizeX, 50, sizeX, sizeY);
		}
		
		raritySort[0].setBounds(150, 90, sizeX, sizeY);
		for(int i = 0; i < weapon.length; i++) {
			weapon[i].setBounds(150 + i * sizeX, 90 + sizeY, sizeX, sizeY);
		}
		for(int i = 0; i < unit.length; i++) {
			unit[i].setBounds(150 + i * sizeX, 90 + sizeY * 2, sizeX, sizeY);
		}
		for(int i = 0; i < cut.length; i++) {
			cut[i].setBounds(150 + i % 6 * sizeX, 90 + sizeY * (3 + i / 6), sizeX, sizeY);
		}
		
		for(int i = 0; i < rarity.length; i++) {
			rarity[i].setBounds(150 + i * sizeX, 280, sizeX, sizeY);
		}
		if(Objects.nonNull(typeList)) {
			for(int i = 0; i < distance.length; i++) {
				distance[i].setBounds(150 + i * sizeX, 280 + sizeY, sizeX, sizeY);
			}
			for(int i = 0; i < handle.length; i++) {
				handle[i].setBounds(150 + i * sizeX, 280 + sizeY * 2, sizeX, sizeY);
			}
			for(int i = 0; i < element.length; i++) {
				element[i].setBounds(150 + i % 6 * sizeX, 280 + sizeY * (3 + i / 6), sizeX, sizeY);
			}
		}
	}
	
	private void drawField(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(5, 90, 150, 150);
		g.fillRect(5, 280, 150, 150);
		g.setColor(Color.YELLOW);
		g.fillRect(150, 50, 220, 30);
		g.fillRect(150, 90, 655, 150);
		g.fillRect(150, 280, 655, 150);
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2));
		g.drawRect(150, 50, 220, 30);
		for(int i = 0; i < 3; i++) {
			g.drawRect(5, 90 + i * 30, 800, 30);
		}
		g.drawRect(5, 180, 800, 60);
		for(int i = 0; i < 3; i++) {
			g.drawRect(5, 280 + i * 30, 800, 30);
		}
		g.drawRect(5, 370, 800, 60);
		g.drawLine(150, 90, 150, 240);
		g.drawLine(150, 280, 150, 430);
	}
	
	public List<Integer> getDisplayList(){
		canSort = false;
		diaplayList = new ArrayList<>(defaultDisplayList);
		SortDialog.setSortDialog(this);
		if(!canSort) {
			return defaultDisplayList;
		}
		
		//RadioButtonの状態を見てdiaplayListを作成
		
		
		
		
		return diaplayList;
	}
}