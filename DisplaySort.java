package displaysort;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import defaultdata.DefaultData;

//ソート画面振り分け
public class DisplaySort extends SortPanel{
	public void core() {
		rarityList = DefaultData.CORE_RARITY_LIST;
		weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST;
		unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST;
		cutList = DefaultData.CORE_CUT_STATUS_LIST;
		super.setSortPanel();
	}
	
	public void weapon() {
		rarityList = DefaultData.WEAPON_RARITY_LIST;
		weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.stream().map(i -> i.stream().map(j -> (double) j).toList()).toList();
		cutList = DefaultData.WEAPON_CUT_STATUS_LIST;
		typeList = DefaultData.WEAPON_TYPE;
		elementList = DefaultData.WEAPON_ELEMENT;
		super.setSortPanel();
	}
}

//ソート画面表示用ダイアログ
class SortDialog extends JDialog{
	protected SortDialog(SortPanel CoreSort) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("ステータス");
		setSize(600, 195);
		setLocationRelativeTo(null);
		add(CoreSort);
		setVisible(true);
	}
}

//ソート画面
class SortPanel extends JPanel {
	List<Integer> rarityList;
	List<List<Double>> weaponStatusList;
	List<List<Double>> unitStatusList;
	List<List<Integer>> cutList;
	List<List<Integer>> typeList;
	List<List<Integer>> elementList;
	JRadioButton order[];
	JRadioButton rarity[];
	JRadioButton weapon[];
	JRadioButton unit[];
	JRadioButton cut[];
	List<Integer> diaplayList;
	
	protected void setSortPanel() {
		Consumer<JRadioButton[]> initialize = (radio) -> {
			for(int i = 0; i < radio.length; i++) {
				radio[i] = new JRadioButton();
			}
		};
		order = new JRadioButton[2];{
			initialize.accept(order);
		}
		rarity = new JRadioButton[Collections.max(rarityList)];{
			initialize.accept(rarity);
		}
		weapon = new JRadioButton[weaponStatusList.get(0).size()];{
			initialize.accept(weapon);
		}
		unit = new JRadioButton[unitStatusList.get(0).size()];{
			initialize.accept(unit);
		}
		cut = new JRadioButton[cutList.get(0).size()];{
			initialize.accept(cut);
		}
		setBackground(new Color(240, 170, 80));
		
		
		
		new SortDialog(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
	}
	
	protected List<Integer> getDisplayList(){
		return diaplayList;
	}
	
	
	
	
	
	
	
}