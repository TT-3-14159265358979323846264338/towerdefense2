package displaysort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
		setTitle("ソート/絞り込み");
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
	JLabel[] commentLabel = IntStream.range(0, 8).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
	JButton sortButton = new JButton();
	JButton resetButton = new JButton();
	JButton returnButton = new JButton();
	JRadioButton[] mode;
	JRadioButton[] raritySort;
	JRadioButton[] weapon;
	JRadioButton[] unit;
	JRadioButton[] cut;
	JRadioButton[] rarity;
	JRadioButton[] distance;
	JRadioButton[] handle;
	JRadioButton[] element;
	ButtonGroup sortGroup = new ButtonGroup();
	ButtonGroup itemGroup = new ButtonGroup();
	SortDialog SortDialog = new SortDialog();
	List<Integer> rarityList;
	List<List<Double>> weaponStatusList;
	List<List<Double>> unitStatusList;
	List<List<Integer>> cutList;
	List<List<Integer>> typeList;
	List<List<Integer>> elementList;
	List<Integer> defaultDisplayList;
	boolean canSort;
	
	protected void setSortPanel(List<Integer> defaultList) {
		setBackground(new Color(240, 170, 80));
		this.defaultDisplayList = defaultList;
		speedInversion();
		addLabel();
		addSortButton();
		addResetButton();
		addReturnButton();
		initializeRadioButton();
		setRadioButtonAction();
		setRadioButtonName();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawField(g);
		setLabel();
		setButton();
		setRadioButton();
	}
	
	private void speedInversion(){//攻撃速度のみは昇降順が逆になっている
		weaponStatusList = weaponStatusList.stream().map(i -> IntStream.range(0, i.size()).mapToObj(j -> (j == 2)? 1 / i.get(j): i.get(j)).toList()).toList();
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
		Stream.of(commentLabel).forEach(i -> {
				i.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
				add(i);
		});
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
				Stream.of(radio).forEach(i -> i.setSelected(false));
			};
			mode[0].setSelected(true);
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
			JRadioButton[] radio = IntStream.range(0, size).mapToObj(i -> new JRadioButton()).toArray(JRadioButton[]::new);
			Stream.of(radio).forEach(i -> {
				i.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
				i.setOpaque(false);
				add(i);
			});
			return radio;
		};
		BiConsumer<ButtonGroup, JRadioButton[]> grouping = (group, radio) -> {
			Stream.of(radio).forEach(i -> group.add(i));
		};
		
		mode = initialize.apply(2);
		mode[0].setSelected(true);
		grouping.accept(sortGroup, mode);
		
		raritySort = initialize.apply(1);
		raritySort[0].setSelected(true);
		weapon = initialize.apply(DefaultData.WEAPON_WEAPON_MAP.size());
		unit = initialize.apply(DefaultData.WEAPON_UNIT_MAP.size());
		cut = initialize.apply(DefaultData.ELEMENT_MAP.size());
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
	
	private void setRadioButtonAction() {
		BiConsumer<JRadioButton[], Map<Integer, String>> setAction = (radio, map) ->{
			IntStream.range(0, radio.length).forEach(i -> radio[i].setActionCommand(map.get(i)));
		};		
		mode[0].setActionCommand("true");
		mode[1].setActionCommand("false");
		
		raritySort[0].setActionCommand("true");
		setAction.accept(weapon, DefaultData.WEAPON_WEAPON_MAP);
		setAction.accept(unit, DefaultData.WEAPON_UNIT_MAP);
		setAction.accept(cut, DefaultData.ELEMENT_MAP);
		
		IntStream.range(0, rarity.length).forEach(i -> rarity[i].setActionCommand("" + (i + 1)));
		if(Objects.nonNull(typeList)) {
			setAction.accept(distance, DefaultData.DISTANCE_MAP);
			setAction.accept(handle, DefaultData.HANDLE_MAP);
			setAction.accept(element, DefaultData.ELEMENT_MAP);
		}
	}
	
	private void setRadioButtonName() {
		BiConsumer<JRadioButton[], Map<Integer, String>> getName = (radio, map) -> {
			IntStream.range(0, radio.length).forEach(i -> radio[i].setText(map.get(i)));
		};
		mode[0].setText("降順");
		mode[1].setText("昇順");
		raritySort[0].setText("レアリティ");
		getName.accept(weapon, DefaultData.WEAPON_WEAPON_MAP);
		getName.accept(unit, DefaultData.WEAPON_UNIT_MAP);
		getName.accept(cut, DefaultData.ELEMENT_MAP);
		IntStream.range(0, rarity.length).forEach(i -> rarity[i].setText("★" + (i + 1)));
		if(Objects.nonNull(typeList)) {
			getName.accept(distance, DefaultData.DISTANCE_MAP);
			getName.accept(handle, DefaultData.HANDLE_MAP);
			getName.accept(element, DefaultData.ELEMENT_MAP);
		}
	}
	
	private void setLabel(){
		sortLabel.setBounds(10, 10, 300, 40);
		filterLabel.setBounds(10, 240, 300, 40);
		IntStream.range(0, commentLabel.length / 2).forEach(i -> {
			commentLabel[i].setBounds(10, 90 + 30 * (i % 4), 300, 30);
			commentLabel[i + 4].setBounds(10, 280 + 30 * (i % 4), 300, 30);
		});
	}
	
	private void setButton() {
		sortButton.setBounds(220, 440, 120, 40);
		resetButton.setBounds(350, 440, 120, 40);
		returnButton.setBounds(480, 440, 120, 40);
	}
	
	private void setRadioButton() {
		int sizeX = 110;
		int sizeY = 30;
		IntStream.range(0, mode.length).forEach(i -> mode[i].setBounds(150 + i * sizeX, 50, sizeX, sizeY));
		
		raritySort[0].setBounds(150, 90, sizeX, sizeY);
		IntStream.range(0, weapon.length).forEach(i -> weapon[i].setBounds(150 + i * sizeX, 90 + sizeY, sizeX, sizeY));
		IntStream.range(0, unit.length).forEach(i -> unit[i].setBounds(150 + i * sizeX, 90 + sizeY * 2, sizeX, sizeY));
		IntStream.range(0, cut.length).forEach(i -> cut[i].setBounds(150 + i % 6 * sizeX, 90 + sizeY * (3 + i / 6), sizeX, sizeY));
		
		IntStream.range(0, rarity.length).forEach(i -> rarity[i].setBounds(150 + i * sizeX, 280, sizeX, sizeY));
		if(Objects.nonNull(typeList)) {
			IntStream.range(0, distance.length).forEach(i -> distance[i].setBounds(150 + i * sizeX, 280 + sizeY, sizeX, sizeY));
			IntStream.range(0, handle.length).forEach(i -> handle[i].setBounds(150 + i * sizeX, 280 + sizeY * 2, sizeX, sizeY));
			IntStream.range(0, element.length).forEach(i -> element[i].setBounds(150 + i % 6 * sizeX, 280 + sizeY * (3 + i / 6), sizeX, sizeY));
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
		IntStream.range(0, 3).forEach(i -> g.drawRect(5, 90 + i * 30, 800, 30));
		g.drawRect(5, 180, 800, 60);
		IntStream.range(0, 3).forEach(i -> g.drawRect(5, 280 + i * 30, 800, 30));
		g.drawRect(5, 370, 800, 60);
		g.drawLine(150, 90, 150, 240);
		g.drawLine(150, 280, 150, 430);
	}
	
	public List<Integer> getDisplayList(){
		canSort = false;
		SortDialog.setSortDialog(this);
		if(!canSort) {
			return defaultDisplayList;
		}
		return getFilterList(getSortList(defaultDisplayList));
	}
	
	private List<Integer> getSortList(List<Integer> displayList){
		String radioCommand = itemGroup.getSelection().getActionCommand();
		Function<Map<Integer, String>, List<String>> getMapList = (map) -> {
			return IntStream.range(0, map.size()).mapToObj(i -> map.get(i)).toList();
		};
		Predicate<List<String>> check = (mapList) -> {
			return mapList.contains(radioCommand);
		};
		BiFunction<List<List<Double>>, List<String>, List<Integer>> getDisplayListDouble = (statusList, mapList) -> {
			return displayList.stream().sorted(Comparator.comparing(i -> statusList.get(i).get(mapList.indexOf(radioCommand)), getOrderDouble())).collect(Collectors.toList());
		};
		BiFunction<List<List<Integer>>, List<String>, List<Integer>> getDisplayListInteger = (statusList, mapList) -> {
			return displayList.stream().sorted(Comparator.comparing(i -> statusList.get(i).get(mapList.indexOf(radioCommand)), getOrderInteger())).collect(Collectors.toList());
		};
		
		if(Boolean.valueOf(radioCommand)) {
			return displayList.stream().sorted(Comparator.comparing(i -> rarityList.get(i), getOrderInteger())).collect(Collectors.toList());
		}
		List<String> mapList = getMapList.apply(DefaultData.WEAPON_WEAPON_MAP);
		if(check.test(mapList)) {
			return getDisplayListDouble.apply(weaponStatusList, mapList);
		}
		mapList = getMapList.apply(DefaultData.WEAPON_UNIT_MAP);
		if(check.test(mapList)) {
			return getDisplayListDouble.apply(unitStatusList, mapList);
		}
		mapList = getMapList.apply(DefaultData.ELEMENT_MAP);
		if(check.test(mapList)) {
			return getDisplayListInteger.apply(cutList, mapList);
		}
		return displayList;
	}
	
	private Comparator<Integer> getOrderInteger() {
		return Boolean.valueOf(sortGroup.getSelection().getActionCommand())? Comparator.reverseOrder(): Comparator.naturalOrder();
	}
	
	private Comparator<Double> getOrderDouble() {
		return Boolean.valueOf(sortGroup.getSelection().getActionCommand())? Comparator.reverseOrder(): Comparator.naturalOrder();
	}
	
	private List<Integer> getFilterList(List<Integer> displayList){
		if(Objects.isNull(typeList)) {
			return getRarityList(displayList);
		}
		displayList = getRarityList(displayList);
		displayList = getTypeList(displayList, distance, DefaultData.DISTANCE_MAP, 0);
		displayList = getTypeList(displayList, handle, DefaultData.HANDLE_MAP, 1);
		return getElementList(displayList);
	}
	
	private List<Integer> getRarityList(List<Integer> displayList){
		Function<JRadioButton[], Stream<Integer>> getActiveButtonStream = (radio) -> {
			return Stream.of(radio).filter(i -> i.isSelected()).map(i -> Integer.parseInt(i.getActionCommand()));
		};
		if(selectCheck(rarity)) {
			return displayList;
		}
		return displayList.stream().filter(i -> getActiveButtonStream.apply(rarity).anyMatch(j -> rarityList.get(i) == j)).collect(Collectors.toList());
	}
	
	private List<Integer> getTypeList(List<Integer> displayList, JRadioButton[] radio, Map<Integer, String> map, int index){
		if(selectCheck(radio)) {
			return displayList;
		}
		return displayList.stream().filter(i -> getActiveButtonStream(radio).anyMatch(j -> map.get(typeList.get(i).get(index)).equals(j))).collect(Collectors.toList());
	}
	
	private List<Integer> getElementList(List<Integer> displayList){
		if(selectCheck(element)) {
			return displayList;
		}
		return displayList.stream().filter(i -> getActiveButtonStream(element).anyMatch(j -> elementList.get(i).stream().anyMatch(k -> DefaultData.ELEMENT_MAP.get(k).equals(j)))).collect(Collectors.toList());
	}
	
	private boolean selectCheck(JRadioButton[] radio) {
		return !Stream.of(radio).map(i -> i.isSelected()).anyMatch(i -> i);
	}
	
	private Stream<String> getActiveButtonStream(JRadioButton[] radio){
		return Stream.of(radio).filter(i -> i.isSelected()).map(i -> i.getActionCommand());
	}
}