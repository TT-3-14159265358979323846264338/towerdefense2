package statuscomment;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import defaultdata.DefaultData;

public class StatusComment {
	public void coreStatus(int selectNumber, List<BufferedImage> imageList) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (35 - comment.getBytes().length) + "s", comment);
		};
		List<Double> weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST.get(selectNumber);
		List<Double> unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【" + DefaultData.CORE_NAME_LIST.get(selectNumber) + "】" + "\n"
					+ "\n"
					+ "【武器ステータス倍率】\n"
					+ editComment.apply("攻撃倍率:") + weaponStatusList.get(0) + "倍\n"
					+ editComment.apply("射程倍率:") + weaponStatusList.get(1) + "倍\n"
					+ editComment.apply("攻撃速度倍率:") + weaponStatusList.get(2) + "倍\n"
					+ "\n"
					+ "【ユニットステータス倍率】\n"
					+ editComment.apply("体力倍率:") + unitStatusList.get(0) + "倍\n"
					+ editComment.apply("防御倍率:") + unitStatusList.get(1) + "倍\n"
					+ editComment.apply("回復倍率:") + unitStatusList.get(2) + "倍\n"
					+ editComment.apply("足止め数倍率:") + unitStatusList.get(3) + "倍\n"
					+ editComment.apply("配置コスト倍率:") + unitStatusList.get(4)+ "倍";
		showMessageDialog(null, comment, "コア情報", INFORMATION_MESSAGE, new ImageIcon(imageList.get(selectNumber)));
	}
	
	public void weaponStatus(int selectNumber, List<BufferedImage> imageList) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (25 - comment.getBytes().length) + "s", comment);
		};
		List<Integer> weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(selectNumber);
		List<Integer> unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【" + DefaultData.WEAPON_NAME_LIST.get(selectNumber) + "】" + "\n"
					+ "\n"
					+ "【武器ステータス】\n"
					+ editComment.apply(((0 <= weaponStatusList.get(0))? "攻撃力:": "回復力:")) + Math.abs(weaponStatusList.get(0)) + "\n"
					+ editComment.apply("射程:") + weaponStatusList.get(1) + "\n"
					+ editComment.apply("攻撃速度:") + weaponStatusList.get(2) + " ms\n"
					+ "\n"
					+ "【ユニットステータス】\n"
					+ editComment.apply("体力:") + unitStatusList.get(0) + "\n"
					+ editComment.apply("防御力:") + unitStatusList.get(1) + "\n"
					+ editComment.apply("回復:") + unitStatusList.get(2) + "\n"
					+ editComment.apply("足止め数:") + unitStatusList.get(3) + "\n"
					+ editComment.apply("配置コスト:") + unitStatusList.get(4) + "\n"
					+ "\n"
					+ "【武器タイプ】\n"
					+ editComment.apply("距離タイプ:") + DefaultData.DISTANCE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(0)) + "\n"
					+ editComment.apply("装備タイプ:") + DefaultData.HANDLE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(1)) + "\n"
					+ editComment.apply("属性:") + getElement(DefaultData.WEAPON_ELEMENT.get(selectNumber));
		showMessageDialog(null, comment, "武器情報", INFORMATION_MESSAGE, new ImageIcon(imageList.get(selectNumber)));
	}
	
	public String getComment(List<List<Integer>> weaponStatusList, List<Integer> unitStatusList,int type) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (25 - comment.getBytes().length) + "s", comment);
		};
		Function<Integer, String> editWeaponComment = (i) -> {
			return (editComment.apply((0 <= weaponStatusList.get(i).get(0))? "攻撃力:": "回復力:")) + Math.abs(weaponStatusList.get(i).get(0)) + "\n"
					+ editComment.apply("射程:") + weaponStatusList.get(i).get(1) + "\n"
					+ editComment.apply("攻撃速度:") + weaponStatusList.get(i).get(2) + " ms\n"
					+ editComment.apply("属性:") + getElement(weaponStatusList.get(i - 1)) + "\n"
					+ "\n";
		};
		String comment = "【武器ステータス】\n";
		boolean existsRight = (weaponStatusList.get(0).get(0) != -1);
		boolean existsLeft = (weaponStatusList.get(2).get(0) != -1);
		if(existsLeft && existsRight) {
			comment += "右武器\n" + editWeaponComment.apply(1);
			comment += "左武器\n" + editWeaponComment.apply(3);
		}else if(!existsLeft && !existsRight){
			comment += "武器無し\n\n";
		}else {
			comment += existsRight? editWeaponComment.apply(1): editWeaponComment.apply(3);
		}
		comment += "【ユニットステータス】\n"
				+ editComment.apply("体力:") + unitStatusList.get(0) + "\n"
				+ editComment.apply("防御力:") + unitStatusList.get(1) + "\n"
				+ editComment.apply("回復:") + unitStatusList.get(2) + "\n"
				+ editComment.apply("足止め数:") + unitStatusList.get(3) + "\n"
				+ editComment.apply("配置コスト:") + unitStatusList.get(4) + "\n"
				+ "\n"
				+ "【武器タイプ】\n"
				+ DefaultData.DISTANCE_MAP.get(type) + "\n"
				+ "\n"
				+ "↓武器解除する位置を選択してください↓"
				+ "\n";
		return comment;
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
	protected StstusDialog(BufferedImage image, List<Integer> compositionList, int type, List<List<Integer>> weaponStatusList, List<List<Integer>> unitStatusList, List<Double> cutList) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Brush");
		setSize(500, 500);
		add(new DrawStatus(image, compositionList, type, weaponStatusList, unitStatusList, cutList));
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

class DrawStatus extends JPanel{
	Consumer<JLabel[]> initialize = (label) -> {
		for(int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
		}
	};
	JLabel[] name = new JLabel[2]; {
		initialize.accept(name);
	}
	JLabel[] weapon = new JLabel[27]; {
		initialize.accept(weapon);
	}
	JLabel[] unit = new JLabel[33]; {
		initialize.accept(unit);
	}
	BufferedImage image;
	List<Integer> compositionList;
	int type;
	List<List<Integer>> weaponStatusList;
	List<List<Integer>> unitStatusList;
	List<Double> cutList;
	
	protected DrawStatus(BufferedImage image, List<Integer> compositionList, int type, List<List<Integer>> weaponStatusList, List<List<Integer>> unitStatusList, List<Double> cutList) {
		setBackground(new Color(240, 170, 80));
		this.image = image;
		this.compositionList = compositionList;
		this.type = type;
		this.weaponStatusList = weaponStatusList;
		this.unitStatusList= unitStatusList;
		this.cutList = cutList;
		addLabel();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLabelFont();
		setLabelText();
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
	}
	
	private void setLabelFont() {
		Consumer<JLabel[]> setLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				label[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
			}
		};
		setLabel.accept(name);
		setLabel.accept(weapon);
		setLabel.accept(unit);
	}
	
	private void setLabelText() {
		name[0].setText("名称");
		name[1].setText(getUnitName());
		
		weapon[0].setText("武器ステータス");
		weapon[1].setText("右武器");
		weapon[2].setText(((0 <= weaponStatusList.get(0).get(0))? "攻撃力:": "回復力:"));
		weapon[3].setText("射程");
		weapon[4].setText("攻撃速度");
		weapon[5].setText("距離タイプ");
		weapon[6].setText("装備タイプ");
		weapon[7].setText("属性");
		for(int i = 0; i < 3; i++) {
			weapon[i + 8].setText("" + weaponStatusList.get(0).get(i));;
		}
		weapon[11].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(0)));
		weapon[12].setText("" + DefaultData.WEAPON_UNIT_STATUS_LIST.get(compositionList.get(0)));
		weapon[13].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(0))));
		
		weapon[14].setText("左武器");
		weapon[15].setText(((0 <= weaponStatusList.get(2).get(0))? "攻撃力:": "回復力:"));
		weapon[16].setText("射程");
		weapon[17].setText("攻撃速度");
		weapon[18].setText("距離タイプ");
		weapon[19].setText("装備タイプ");
		weapon[20].setText("属性");
		for(int i = 0; i < 3; i++) {
			weapon[i + 21].setText("" + weaponStatusList.get(2).get(i));;
		}
		weapon[24].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(2)));
		weapon[25].setText("" + DefaultData.WEAPON_UNIT_STATUS_LIST.get(compositionList.get(2)));
		weapon[26].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(2))));
		
		
		
		
		
		
		
		
		
		
	}
	
	private String getUnitName() {
		String name = "";
		try {
			name += DefaultData.WEAPON_NAME_LIST.get(compositionList.get(0)) + " - ";
		}catch(Exception ignore) {
			//右武器を装備していないので、無視する
		}
		name += DefaultData.CORE_NAME_LIST.get(compositionList.get(1)) + " - ";
		try {
			name += DefaultData.WEAPON_NAME_LIST.get(compositionList.get(2)) + " - ";
		}catch(Exception ignore) {
			//左武器を装備していないので、無視する
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
	
	private void setLabelPosition() {
		
		
		
		
		
		
		
		
		
	}
	
	private void drawImage(Graphics g) {
		g.drawImage(image, 20, 20, this);
	}
}