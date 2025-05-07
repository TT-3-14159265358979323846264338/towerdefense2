package displaysort;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import defaultdata.DefaultData;

//ソート画面振り分け
public class DisplaySort extends SortPanel{
	public List<Integer> core(List<Integer> defaultList) {
		rarityList = DefaultData.CORE_RARITY_LIST;
		weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST;
		unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST;
		cutList = DefaultData.CORE_CUT_STATUS_LIST;
		super.setSortPanel(defaultList);
		return getDisplayList();
	}
	
	public List<Integer> weapon(List<Integer> defaultList) {
		rarityList = DefaultData.WEAPON_RARITY_LIST;
		weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		cutList = DefaultData.WEAPON_CUT_STATUS_LIST;
		typeList = DefaultData.WEAPON_TYPE;
		elementList = DefaultData.WEAPON_ELEMENT;
		super.setSortPanel(defaultList);
		return getDisplayList();
	}
}

//ソート画面表示用ダイアログ
class SortDialog extends JDialog{
	protected void setSortDialog(SortPanel CoreSort) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ソート/並び替え");
		setSize(600, 195);
		setLocationRelativeTo(null);
		add(CoreSort);
		setVisible(true);
	}
	
	protected void disposeDialog() {
		dispose();
	}
}

//ソート画面
class SortPanel extends JPanel {
	JButton sortButton = new JButton();
	JButton resetButton = new JButton();
	JButton returnButton = new JButton();
	JRadioButton order[];
	JRadioButton rarity[];
	JRadioButton weapon[];
	JRadioButton unit[];
	JRadioButton cut[];
	JRadioButton distance[];
	JRadioButton handle[];
	JRadioButton element[];
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
		addSortButton();
		addResetButton();
		addReturnButton();
		initializeRadioButton();
		setRadioButtonName();
		
		
		
		
		SortDialog.setSortDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setButton();
		setRadioButton();
		
		
		
		
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
			initialize.accept(rarity);
			initialize.accept(weapon);
			initialize.accept(unit);
			initialize.accept(cut);
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
	
	private void setButton() {
		sortButton.setBounds(310, 100, 120, 40);
		resetButton.setBounds(310, 100, 120, 40);
		returnButton.setBounds(310, 100, 120, 40);
	}
	
	private void initializeRadioButton() {
		Function<Integer, JRadioButton[]> initialize = (size) -> {
			JRadioButton[] radio = new JRadioButton[size];
			for(int i = 0; i < size; i++) {
				radio[i] = new JRadioButton();
				radio[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
				add(radio[i]);
			}
			return radio;
		};
		order = initialize.apply(2);
		order[0].setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(order[0]);
		group.add(order[1]);
		rarity = initialize.apply(Collections.max(rarityList));
		weapon = initialize.apply(DefaultData.WEAPON_WEAPON_MAP.size());
		unit = initialize.apply(DefaultData.WEAPON_UNIT_MAP.size());
		cut = initialize.apply(DefaultData.ELEMENT_MAP.size());
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
		for(int i = 0; i < rarity.length; i++) {
			rarity[i].setText("★" + (i + 1));
		}
		getName.accept(weapon, DefaultData.WEAPON_WEAPON_MAP);
		getName.accept(unit, DefaultData.WEAPON_UNIT_MAP);
		getName.accept(cut, DefaultData.ELEMENT_MAP);
		if(Objects.nonNull(typeList)) {
			getName.accept(distance, DefaultData.DISTANCE_MAP);
			getName.accept(handle, DefaultData.HANDLE_MAP);
			getName.accept(element, DefaultData.ELEMENT_MAP);
		}
	}
	
	private void setRadioButton() {
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	protected List<Integer> getDisplayList(){
		if(!canSort) {
			return defaultDisplayList;
		}
		
		
		
		
		
		
		
		
		
		
		return diaplayList;
	}
}