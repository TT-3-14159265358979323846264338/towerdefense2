package menucomposition;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import defaultdata.DefaultData;
import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

//編成
public class MenuComposition extends JPanel{
	JLabel compositionNameLabel = new JLabel();
	JLabel compositionLabel = new JLabel();
	JLabel coreLabel = new JLabel();
	JLabel weaponLabel = new JLabel();
	JButton newButton = new JButton();
	JButton removeButton = new JButton();
	JButton nameChangeButton = new JButton();
	JButton saveButton = new JButton();
	JButton inputButton = new JButton();
	JButton returnButton = new JButton();
	DefaultListModel<String> compositionListModel = new DefaultListModel<String>();
	JList<String> compositionJList = new JList<String>(compositionListModel);
	JScrollPane compositionScroll = new JScrollPane();
	JScrollPane coreScroll = new JScrollPane();
	JScrollPane weaponScroll = new JScrollPane();
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<List<BufferedImage>> rightWeaponList = new ArrayList<>(new DefaultData().rightWeaponImage(2));
	List<BufferedImage> ceterCoreList = new ArrayList<>(new DefaultData().centerCoreImage(2));
	List<List<BufferedImage>> leftWeaponList = new ArrayList<>(new DefaultData().leftWeaponImage(2));
	List<Integer> coreNumberList = new ArrayList<>();
	List<Integer> weaponNumberList = new ArrayList<>();
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	int selectNumber;
	
	public MenuComposition(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		addCompositionNameLabel();
		addCompositionLabel();
		addCoreLabel();
		addWeaponLabel();
		addNewButton();
		addRemoveButton();
		addNameChangeButton();
		addSaveButton();
		addInputButton();
		addReturnButton(MainFrame);
		addCompositionScroll();
		addCoreScroll();
		addWeaponScroll();
		load();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCompositionNameLabel();
		setCompositionLabel();
		setCoreLabel();
		setWeaponLabel();
		setNewButton();
		setRemoveButton();
		setNameChangeButton();
		setSaveButton();
		setInputButton();
		setReturnButton();
		setCompositionScroll();
		setCoreScroll();
		setWeaponScroll();
		setComposition(g);
	}
	
	private void addCompositionNameLabel() {
		add(compositionNameLabel);
	}
	
	private void setCompositionNameLabel() {
		compositionNameLabel.setText("編成名");
		compositionNameLabel.setBounds(10, 10, 130, 30);
		setLabel(compositionNameLabel);
	}
	
	private void addCompositionLabel() {
		add(compositionLabel);
	}
	
	private void setCompositionLabel() {
		compositionLabel.setText("ユニット編成");
		compositionLabel.setBounds(230, 10, 130, 30);
		setLabel(compositionLabel);
	}
	
	private void addCoreLabel() {
		add(coreLabel);
	}
	
	private void setCoreLabel() {
		coreLabel.setText("コア");
		coreLabel.setBounds(570, 10, 130, 30);
		setLabel(coreLabel);
	}
	
	private void addWeaponLabel() {
		add(weaponLabel);
	}
	
	private void setWeaponLabel() {
		weaponLabel.setText("武器");
		weaponLabel.setBounds(700, 10, 130, 30);
		setLabel(weaponLabel);
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addNewButton(){
		add(newButton);
		newButton.addActionListener(e->{
			SaveComposition.newComposition();
			input();
			compositionJList.ensureIndexIsVisible(allCompositionList.size() - 1);
		});
	}
	
	private void setNewButton(){
		newButton.setText("編成追加");
		newButton.setBounds(10, 320, 100, 60);
		setButton(newButton);
	}
	
	private void addRemoveButton() {
		add(removeButton);
		removeButton.addActionListener(e->{
			if(1 < allCompositionList.size()) {
				SaveComposition.removeComposition(selectNumber);
				input();
			}else {
				showMessageDialog(null, "全ての編成を削除できません");
			}
		});
	}
	
	private void setRemoveButton() {
		removeButton.setText("編成削除");
		removeButton.setBounds(120, 320, 100, 60);
		setButton(removeButton);
	}
	
	private void addNameChangeButton() {
		add(nameChangeButton);
		nameChangeButton.addActionListener(e->{
			String newName = showInputDialog(null, "変更後の編成名を入力してください", "名称変更", INFORMATION_MESSAGE);
			if(newName != null && !newName.isEmpty()) {
				if(!newName.startsWith(" ") && !newName.startsWith("　")) {
					compositionListModel.set(selectNumber, newName);
					compositionNameList.set(selectNumber, newName);
				}else {
					showMessageDialog(null, "スペースで始まる名称は使用できません");
				}
			}
		});
	}
	
	private void setNameChangeButton() {
		nameChangeButton.setText("名称変更");
		nameChangeButton.setBounds(10, 390, 100, 60);
		setButton(nameChangeButton);
	}
	
	private void addSaveButton() {
		add(saveButton);
		saveButton.addActionListener(e->{
			int select = JOptionPane.showConfirmDialog(null, "現在の編成を保存しますか?", "実行確認", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch(select) {
			case 0:
				save();
			default:
				break;
			}
		});
	}
	
	private void setSaveButton() {
		saveButton.setText("セーブ");
		saveButton.setBounds(120, 390, 100, 60);
		setButton(saveButton);
	}
	
	private void addInputButton() {
		add(inputButton);
		inputButton.addActionListener(e->{
			int select = JOptionPane.showConfirmDialog(null, "保存せずに元のデータをロードしますか?", "実行確認", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch(select) {
			case 0:
				load();
			default:
				break;
			}
		});
	}
	
	private void setInputButton() {
		inputButton.setText("ロード");
		inputButton.setBounds(10, 460, 100, 60);
		setButton(inputButton);
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			int select = JOptionPane.showConfirmDialog(null, "保存して戻りますか?", "実行確認", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch(select) {
			case 0:
				save();
				MainFrame.mainMenuDraw();
				break;
			case 1:
				MainFrame.mainMenuDraw();
				break;
			default:
				break;
			}
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(120, 460, 100, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
		button.setFocusable(false);
	}
	
	private void addCompositionScroll() {
		compositionScroll.getViewport().setView(compositionJList);
    	add(compositionScroll);
	}
	
	private void setCompositionScroll() {
		compositionJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		compositionScroll.setPreferredSize(new Dimension(210, 270));
    	compositionScroll.setBounds(10, 40, 210, 270);
    	selectNumber = compositionJList.getSelectedIndex();
	}
	
	private void addCoreScroll() {
		coreScroll.getViewport().setView(new ImagePanel(new DefaultData().coreImage(2)));
    	add(coreScroll);
	}
	
	private void setCoreScroll() {
		coreScroll.setPreferredSize(new Dimension(120, 480));
		coreScroll.setBounds(570, 40, 120, 480);
	}
	
	private void addWeaponScroll() {
		weaponScroll.getViewport().setView(new ImagePanel(new DefaultData().weaponImage(2)));
    	add(weaponScroll);
	}
	
	private void setWeaponScroll() {
		weaponScroll.setPreferredSize(new Dimension(120, 480));
		weaponScroll.setBounds(700, 40, 120, 480);
	}
	
	private void setComposition(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 40, 330, 480);
		for(int i = 0; i < allCompositionList.get(selectNumber).size(); i++) {
			try {
				drawImage(g, i, rightWeaponList.get(allCompositionList.get(selectNumber).get(i).get(0)).get(0));
			}catch(Exception ignore) {
				//右武器を装備していないので、無視する
			}
			drawImage(g, i, ceterCoreList.get(allCompositionList.get(selectNumber).get(i).get(1)));
			try {
				drawImage(g, i, leftWeaponList.get(allCompositionList.get(selectNumber).get(i).get(2)).get(0));
			}catch(Exception ignore) {
				//左武器を装備していないので、無視する
			}
		}
	}
	
	private void drawImage(Graphics g, int i, BufferedImage image) {
		g.drawImage(image, 230 + i % 2 * 150, 40 + i / 2 * 100, this);
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
		input();
	}
	
	private void input() {
		coreNumberList = SaveHoldItem.getCoreNumberList();
		weaponNumberList = SaveHoldItem.getWeaponNumberList();
		allCompositionList = SaveComposition.getAllCompositionList();
		compositionNameList = SaveComposition.getCompositionNameList();
		compositionListModel.clear();
		for(String i: compositionNameList) {
			compositionListModel.addElement(i);
		}
		compositionJList.setSelectedIndex(SaveComposition.getSelectNumber());
	}
	
	private void save() {
		try {
			ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(savecomposition.SaveComposition.COMPOSITION_FILE)));
			compositionData.writeObject(new SaveComposition(allCompositionList, compositionNameList, selectNumber, allCompositionList.get(selectNumber)));
			compositionData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ImagePanel extends JPanel{
	List<BufferedImage> imageList;
	
	protected ImagePanel(List<BufferedImage> imageList) {
		this.imageList = imageList;
		setPreferredSize(new Dimension(100, imageList.size() * 100));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < imageList.size(); i++) {
			g.drawImage(imageList.get(i), 0, i * 100, this);
		}
	}
}