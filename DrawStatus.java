package drawstatus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import defaultdata.DefaultData;

public class DrawStatus {
	Consumer<JLabel[]> initialize = (label) -> {
		for(int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
		}
	};
	JLabel[] name = new JLabel[4]; {
		initialize.accept(name);
	}
	JLabel[] weapon = new JLabel[21]; {
		initialize.accept(weapon);
	}
	JLabel[] unit = new JLabel[10]; {
		initialize.accept(unit);
	}
	JLabel[] cut = new JLabel[22]; {
		initialize.accept(cut);
	}
	
	public void core(BufferedImage image, int number) {
		List<Double> weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST.get(number);
		List<Double> unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST.get(number);
		List<Integer> cutList = DefaultData.CORE_CUT_STATUS_LIST.get(number);
		name[0].setText("【名称】");
		name[1].setText(DefaultData.CORE_NAME_LIST.get(number));
		name[2].setText("【武器ステータス】");
		name[3].setText("【ユニットステータス】");
		weapon[1].setText("攻撃倍率");
		weapon[2].setText("射程倍率");
		weapon[3].setText("攻撃速度倍率");
		weapon[7].setText("武器性能");
		for(int i = 0; i < 3; i++) {
			weapon[i + 8].setText(weaponStatusList.get(i) + "倍");
		}
		unit[0].setText("体力倍率");
		unit[1].setText("防御力倍率");
		unit[2].setText("回復倍率");
		unit[3].setText("足止め数倍率");
		unit[4].setText("配置コスト倍率");
		for(int i = 0; i < 5; i++) {
			unit[i + 5].setText("" + unitStatusList.get(i) + "倍");
		}
		for(int i = 0; i < 11; i++) {
			cut[i].setText(DefaultData.ELEMENT_MAP.get(i) + "耐性");
		}
		for(int i = 0; i < 11; i++) {
			cut[i + 11].setText(cutList.get(i) + "%");
		}
		new StstusDialog(image, name, weapon, unit, cut);
	}
	
	public void weapon(BufferedImage image, int number) {
		List<Integer> weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(number);
		List<Integer> unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.get(number);
		List<Integer> cutList = DefaultData.WEAPON_CUT_STATUS_LIST.get(number);
		name[0].setText("【名称】");
		name[1].setText(DefaultData.WEAPON_NAME_LIST.get(number));
		name[2].setText("【武器ステータス】");
		name[3].setText("【ユニットステータス】");
		weapon[1].setText("攻撃力");
		weapon[2].setText("射程");
		weapon[3].setText("攻撃速度");
		weapon[4].setText("距離タイプ");
		weapon[5].setText("装備タイプ");
		weapon[6].setText("属性");
		weapon[7].setText("武器性能");
		for(int i = 0; i < 3; i++) {
			weapon[i + 8].setText("" + weaponStatusList.get(i));
		}
		weapon[11].setText("" + DefaultData.DISTANCE_MAP.get(number));
		weapon[12].setText("" + DefaultData.HANDLE_MAP.get(number));
		weapon[13].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(number)));
		unit[0].setText("体力");
		unit[1].setText("防御力");
		unit[2].setText("回復");
		unit[3].setText("足止め数");
		unit[4].setText("配置コスト");
		for(int i = 0; i < 5; i++) {
			unit[i + 5].setText("" + unitStatusList.get(i));
		}
		for(int i = 0; i < 11; i++) {
			cut[i].setText(DefaultData.ELEMENT_MAP.get(i) + "耐性");
		}
		for(int i = 0; i < 11; i++) {
			cut[i + 11].setText(cutList.get(i) + "%");
		}
		new StstusDialog(image, name, weapon, unit, cut);
	}
	
	public void unit(BufferedImage image, List<Integer> compositionList, List<List<Integer>> weaponStatusList, List<List<Integer>> unitStatusList) {
		name[0].setText("【名称】");
		name[1].setText(getUnitName(compositionList));
		name[2].setText("【武器ステータス】");
		name[3].setText("【ユニットステータス】");
		weapon[1].setText("攻撃力");
		weapon[2].setText("射程");
		weapon[3].setText("攻撃速度");
		weapon[4].setText("距離タイプ");
		weapon[5].setText("装備タイプ");
		weapon[6].setText("属性");
		weapon[7].setText("左武器");
		if(0 <= compositionList.get(2)) {
			for(int i = 0; i < 3; i++) {
				weapon[i + 8].setText("" + weaponStatusList.get(3).get(i));;
			}
			weapon[11].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(2)));
			weapon[12].setText("" + DefaultData.HANDLE_MAP.get(compositionList.get(2)));
			weapon[13].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(2))));
		}
		weapon[14].setText("右武器");
		if(0 <= compositionList.get(0)) {
			for(int i = 0; i < 3; i++) {
				weapon[i + 15].setText("" + weaponStatusList.get(1).get(i));;
			}
			weapon[18].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(0)));
			weapon[19].setText("" + DefaultData.HANDLE_MAP.get(compositionList.get(0)));
			weapon[20].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(0))));
		}
		unit[0].setText("体力");
		unit[1].setText("防御力");
		unit[2].setText("回復");
		unit[3].setText("足止め数");
		unit[4].setText("配置コスト");
		for(int i = 0; i < 5; i++) {
			unit[i + 5].setText("" + unitStatusList.get(1).get(i));
		}
		for(int i = 0; i < 11; i++) {
			cut[i].setText(DefaultData.ELEMENT_MAP.get(i) + "耐性");
		}
		for(int i = 0; i < 11; i++) {
			cut[i + 11].setText(unitStatusList.get(0).get(i) + "%");
		}
		new StstusDialog(image, name, weapon, unit, cut);
	}
	
	private String getUnitName(List<Integer> compositionList) {
		String name = "";
		try {
			name += DefaultData.WEAPON_NAME_LIST.get(compositionList.get(2)) + " - ";
		}catch(Exception ignore) {
			//左武器を装備していないので、無視する
		}
		name += DefaultData.CORE_NAME_LIST.get(compositionList.get(1)) + " - ";
		try {
			name += DefaultData.WEAPON_NAME_LIST.get(compositionList.get(0)) + " - ";
		}catch(Exception ignore) {
			//右武器を装備していないので、無視する
		}
		return name.substring(0, name.length() - 3);
	}
	
	private String getElement(List<Integer> elementList) {
		String element = "";
		for(int i: elementList) {
			element += DefaultData.ELEMENT_MAP.get(i) + ", ";
		}
		return element.substring(0, element.length() - 2);
	}
}

class StstusDialog extends JDialog{
	protected StstusDialog(BufferedImage image, JLabel[] name, JLabel[] weapon, JLabel[] unit, JLabel[] cut) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ステータス");
		setSize(860, 650);
		setLocationRelativeTo(null);
		add(new DrawPanel(image, name, weapon, unit, cut));
		setVisible(true);
	}
}

class DrawPanel extends JPanel{
	BufferedImage image;
	JLabel[] name;
	JLabel[] weapon;
	JLabel[] unit;
	JLabel[] cut;
	
	protected DrawPanel(BufferedImage image, JLabel[] name, JLabel[] weapon, JLabel[] unit, JLabel[] cut) {
		setBackground(new Color(240, 170, 80));
		this.image = image;
		this.name = name;
		this.weapon = weapon;
		this.unit = unit;
		this.cut = cut;
		addLabel();
		setLabelFont();
		setLabelHorizontal();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLabelPosition();
		drawImage(g);
	}
	
	private void addLabel() {
		Consumer<JLabel[]> addLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				add(label[i]);
			}
		};
		addLabel.accept(name);
		addLabel.accept(weapon);
		addLabel.accept(unit);
		addLabel.accept(cut);
	}
	
	private void setLabelFont() {
		Consumer<JLabel[]> setLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				label[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
			}
		};
		setLabel.accept(name);
		setLabel.accept(weapon);
		setLabel.accept(unit);
		setLabel.accept(cut);
	}
	
	private void setLabelHorizontal() {
		Consumer<JLabel[]> setLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				label[i].setHorizontalAlignment(JLabel.CENTER);
			}
		};
		setLabel.accept(weapon);
		setLabel.accept(unit);
		setLabel.accept(cut);
	}
	
	private void setLabelPosition() {
		int startX = 200;
		int startY = 20;
		int sizeX = 110;
		int sizeY = 30;
		name[0].setBounds(startX, startY, sizeX, sizeY);
		name[1].setBounds(startX, startY + sizeY, sizeX * 10, sizeY);
		name[2].setBounds(startX, startY + sizeY * 3, sizeX * 10, sizeY);
		name[3].setBounds(startX, startY + sizeY * 12, sizeX * 10, sizeY);
		for(int i = 0; i < weapon.length; i++) {
			weapon[i].setBounds(startX + (i / 7) * sizeX, startY + (i % 7 + 4) * sizeY, sizeX, sizeY);
		}
		for(int i = 0; i < unit.length; i++) {
			unit[i].setBounds(startX + (i / 5) * sizeX, startY + (i % 5 + 13) * sizeY, sizeX, sizeY);
		}
		for(int i = 0; i < cut.length / 2; i++) {
			cut[i].setBounds(startX + (i / 6 * 2 + 2) * sizeX, startY + (i % 6 + 13) * sizeY, sizeX, sizeY);
			cut[i + cut.length / 2].setBounds(startX + (i / 6 * 2 + 3) * sizeX, startY + (i % 6 + 13) * sizeY, sizeX, sizeY);
		}
	}
	
	private void drawImage(Graphics g) {
		g.drawImage(image, 20, 20, this);
	}
}