package displaystatus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import defaultdata.DefaultData;

//ユニットデータ取込み
public class DisplayStatus extends StatusPanel{
	public void core(BufferedImage image, int number) {
		setLabelName(getRarity(DefaultData.CORE_RARITY_LIST.get(number)) + DefaultData.CORE_NAME_LIST.get(number));
		setWeapon(DefaultData.CORE_WEAPON_STATUS_LIST.get(number));
		setUnit(DefaultData.CORE_UNIT_STATUS_LIST.get(number), "倍");
		setCut(DefaultData.CORE_CUT_STATUS_LIST.get(number));
		super.setStatusPanel(image);
	}
	
	public void weapon(BufferedImage image, int number) {
		setLabelName(getRarity(DefaultData.WEAPON_RARITY_LIST.get(number)) + DefaultData.WEAPON_NAME_LIST.get(number));
		setWeapon(DefaultData.WEAPON_WEAPON_STATUS_LIST.get(number), number);
		setUnit(DefaultData.WEAPON_UNIT_STATUS_LIST.get(number));
		setCut(DefaultData.WEAPON_CUT_STATUS_LIST.get(number));
		super.setStatusPanel(image);
	}
	
	public void unit(BufferedImage image, List<Integer> compositionList, List<List<Integer>> weaponStatusList, List<List<Integer>> unitStatusList) {
		setLabelName(getUnitName(compositionList));
		setWeapon(compositionList, weaponStatusList);
		setUnit(unitStatusList.get(1));
		setCut(unitStatusList.get(0));
		super.setStatusPanel(image);
	}
	
	private void setLabelName(String unitName) {
		name[0].setText("【名称】");
		name[1].setText(unitName);
		name[2].setText("【武器ステータス】");
		name[3].setText("【ユニットステータス】");
	}
	
	private String getRarity(int count) {
		return "★" + count + " ";
	}
	
	private String getUnitName(List<Integer> compositionList) {
		String name = "";
		try {
			name += getRarity(DefaultData.WEAPON_RARITY_LIST.get(compositionList.get(2))) + DefaultData.WEAPON_NAME_LIST.get(compositionList.get(2)) + " - ";
		}catch(Exception ignore) {
			//左武器を装備していないので、無視する
		}
		name += getRarity(DefaultData.CORE_RARITY_LIST.get(compositionList.get(1))) + DefaultData.CORE_NAME_LIST.get(compositionList.get(1)) + " - ";
		try {
			name += getRarity(DefaultData.WEAPON_RARITY_LIST.get(compositionList.get(0))) + DefaultData.WEAPON_NAME_LIST.get(compositionList.get(0)) + " - ";
		}catch(Exception ignore) {
			//右武器を装備していないので、無視する
		}
		return name.substring(0, name.length() - 3);
	}
	
	private void setWeapon(List<Double> statusList) {
		for(int i = 0; i < statusList.size(); i++) {
			weapon[i + 1].setText(DefaultData.CORE_WEAPON_MAP.get(i));
			weapon[i + 9].setText(statusList.get(i) + "倍");
		}
		weapon[8].setText("武器性能");
	}
	
	private void setWeapon(List<Integer> statusList, int number) {
		for(int i = 0; i < statusList.size(); i++) {
			weapon[i + 1].setText(DefaultData.WEAPON_WEAPON_MAP.get(i));
			weapon[i + 9].setText("" + statusList.get(i));
		}
		weapon[5].setText("距離タイプ");
		weapon[6].setText("装備タイプ");
		weapon[7].setText("属性");
		weapon[8].setText("武器性能");
		weapon[13].setText("" + DefaultData.DISTANCE_MAP.get(number));
		weapon[14].setText("" + DefaultData.HANDLE_MAP.get(number));
		weapon[15].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(number)));
	}
	
	private void setWeapon(List<Integer> compositionList, List<List<Integer>> weaponStatusList) {
		for(int i = 0; i < DefaultData.WEAPON_WEAPON_STATUS_LIST.get(0).size(); i++) {
			weapon[i + 1].setText(DefaultData.WEAPON_WEAPON_MAP.get(i));
		}
		weapon[5].setText("距離タイプ");
		weapon[6].setText("装備タイプ");
		weapon[7].setText("属性");
		weapon[8].setText("左武器");
		if(0 <= compositionList.get(2)) {
			for(int i = 0; i < weaponStatusList.get(3).size(); i++) {
				weapon[i + 9].setText("" + weaponStatusList.get(3).get(i));;
			}
			weapon[13].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(2)));
			weapon[14].setText("" + DefaultData.HANDLE_MAP.get(compositionList.get(2)));
			weapon[15].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(2))));
		}
		weapon[16].setText("右武器");
		if(0 <= compositionList.get(0)) {
			for(int i = 0; i < weaponStatusList.get(1).size(); i++) {
				weapon[i + 17].setText("" + weaponStatusList.get(1).get(i));;
			}
			weapon[21].setText("" + DefaultData.DISTANCE_MAP.get(compositionList.get(0)));
			weapon[22].setText("" + DefaultData.HANDLE_MAP.get(compositionList.get(0)));
			weapon[23].setText("" + getElement(DefaultData.WEAPON_ELEMENT.get(compositionList.get(0))));
		}
	}
	
	private String getElement(List<Integer> elementList) {
		String element = "";
		for(int i: elementList) {
			element += DefaultData.ELEMENT_MAP.get(i) + ", ";
		}
		return element.substring(0, element.length() - 2);
	}
	
	private void setUnit(List<Double> statusList, String comment) {
		for(int i = 0; i < statusList.size(); i++) {
			unit[i].setText(DefaultData.CORE_UNIT_MAP.get(i));
			unit[i + 6].setText(statusList.get(i) + comment);
		}
	}
	
	private void setUnit(List<Integer> statusList) {
		for(int i = 0; i < statusList.size(); i++) {
			unit[i].setText(DefaultData.WEAPON_UNIT_MAP.get(i));
			unit[i + 6].setText(statusList.get(i) + "");
		}
	}
	
	private void setCut(List<Integer> cutList) {
		for(int i = 0; i < cutList.size(); i++) {
			cut[i].setText(DefaultData.ELEMENT_MAP.get(i) + "耐性");
		}
		for(int i = 0; i < cutList.size(); i++) {
			cut[i + 11].setText(cutList.get(i) + "%");
		}
	}
}

//ステータス表示用ダイアログ
class StstusDialog extends JDialog{
	protected StstusDialog(StatusPanel StatusPanel) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ステータス");
		setSize(720, 680);
		setLocationRelativeTo(null);
		add(StatusPanel);
		setVisible(true);
	}
}

//ステータス表示
class StatusPanel extends JPanel{
	JLabel image;
	Consumer<JLabel[]> initialize = (label) -> {
		for(int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
		}
	};
	JLabel[] name = new JLabel[4]; {
		initialize.accept(name);
	}
	JLabel[] weapon = new JLabel[24]; {
		initialize.accept(weapon);
	}
	JLabel[] unit = new JLabel[12]; {
		initialize.accept(unit);
	}
	JLabel[] cut = new JLabel[22]; {
		initialize.accept(cut);
	}
	int startX = 20;
	int startY = 20;
	int sizeX = 110;
	int sizeY = 30;
	
	protected void setStatusPanel(BufferedImage image) {
		setBackground(new Color(240, 170, 80));
		this.image = new JLabel(new ImageIcon(image));
		addLabel();
		setLabelFont();
		setLabelHorizontal();
		new StstusDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLabelPosition();
		drawBackground(g);
	}
	
	private void addLabel() {
		Consumer<JLabel[]> addLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				add(label[i]);
			}
		};
		add(image);
		addLabel.accept(name);
		addLabel.accept(weapon);
		addLabel.accept(unit);
		addLabel.accept(cut);
	}
	
	private void setLabelFont() {
		BiConsumer<JLabel[], Integer> setLabel = (label, size) -> {
			String fontName = "ＭＳ ゴシック";
			int bold = Font.BOLD;
			for(int i = 0; i < label.length; i++) {
				int fontSize = 15;
				int width = getFontMetrics(new Font(fontName, bold, fontSize)).stringWidth(label[i].getText());
				while(size < width) {
					fontSize--;
					width = getFontMetrics(new Font(fontName, bold, fontSize)).stringWidth(label[i].getText());
				}
				label[i].setFont(new Font(fontName, bold, fontSize));
			}
		};
		setLabel.accept(name, sizeX * 5);
		setLabel.accept(weapon, sizeX);
		setLabel.accept(unit, sizeX);
		setLabel.accept(cut, sizeX);
	}
	
	private void setLabelHorizontal() {
		Consumer<JLabel[]> setLabel = (label) -> {
			for(int i = 0; i < label.length; i++) {
				label[i].setHorizontalAlignment(JLabel.CENTER);
			}
		};
		image.setHorizontalAlignment(JLabel.CENTER);
		setLabel.accept(weapon);
		setLabel.accept(unit);
		setLabel.accept(cut);
	}
	
	private void setLabelPosition() {
		name[0].setBounds(startX, startY, sizeX, sizeY);
		name[1].setBounds(startX + 20, startY + sizeY, sizeX * 5, sizeY);
		name[2].setBounds(startX + sizeX * 3, startY + sizeY * 3, sizeX * 3, sizeY);
		name[3].setBounds(startX, startY + sizeY * 13, sizeX * 3, sizeY);
		image.setBounds(startX, startY + sizeY * 3, sizeX * 3, sizeY * 9);
		for(int i = 0; i < weapon.length; i++) {
			weapon[i].setBounds(startX + (i / 8 + 3) * sizeX, startY + (i % 8 + 4) * sizeY, sizeX, sizeY);
		}
		for(int i = 0; i < unit.length; i++) {
			unit[i].setBounds(startX + (i / 6) * sizeX, startY + (i % 6 + 14) * sizeY, sizeX, sizeY);
		}
		for(int i = 0; i < cut.length / 2; i++) {
			cut[i].setBounds(startX + (i / 6 * 2 + 2) * sizeX, startY + (i % 6 + 14) * sizeY, sizeX, sizeY);
			cut[i + cut.length / 2].setBounds(startX + (i / 6 * 2 + 3) * sizeX, startY + (i % 6 + 14) * sizeY, sizeX, sizeY);
		}
	}
	
	private void drawBackground(Graphics g) {
		g.setColor(Color.WHITE);	
		g.fillRect(startX, startY, sizeX * 6, sizeY * 2);
		g.fillRect(startX, startY + sizeY * 3, sizeX * 6, sizeY * 9);
		g.fillRect(startX, startY + sizeY * 13, sizeX * 6, sizeY * 7);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(startX + sizeX * 3, startY + sizeY * 4, sizeX * 3, sizeY);
		g.fillRect(startX + sizeX * 3, startY + sizeY * 5, sizeX, sizeY * 7);
		for(int i = 0; i < 3; i++) {
			g.fillRect(startX + sizeX * i * 2, startY + sizeY * 14, sizeX, sizeY * 6);
		}
		g.setColor(Color.YELLOW);
		g.fillRect(startX + sizeX * 4, startY + sizeY * 5, sizeX * 2, sizeY * 7);
		for(int i = 0; i < 3; i++) {
			g.fillRect(startX + sizeX * (i * 2 + 1), startY + sizeY * 14, sizeX, sizeY * 6);
		}
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2));
		g.drawRect(startX, startY, sizeX * 6, sizeY * 2);
		g.drawRect(startX, startY + sizeY * 3, sizeX * 3, sizeY * 9);
		g.drawRect(startX + sizeX * 3, startY + sizeY * 3, sizeX * 3, sizeY * 9);
		g.drawRect(startX, startY + sizeY * 13, sizeX * 6, sizeY * 7);
		g2.setStroke(new BasicStroke(1));
		g.drawLine(startX + sizeX * 3, startY + sizeY * 4, startX + sizeX * 4, startY + sizeY * 5);
		for(int i = 0; i < 8; i++) {
			g.drawLine(startX + sizeX * 3, startY + sizeY * (4 + i), startX + sizeX * 6, startY + sizeY * (4 + i));
		}
		for(int i = 0; i < 2; i++) {
			g.drawLine(startX + sizeX * (4 + i), startY + sizeY * 4, startX + sizeX * (4 + i), startY + sizeY * 12);
		}
		for(int i = 0; i < 7; i++) {
			g.drawLine(startX, startY + sizeY * (14 + i), startX + sizeX * 6, startY + sizeY * (14 + i));
		}
		for(int i = 0; i < 5; i++) {
			g.drawLine(startX + sizeX * (1 + i), startY + sizeY * 14, startX + sizeX * (1 + i), startY + sizeY * 20);
		}
	}
}