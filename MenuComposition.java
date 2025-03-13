package menucomposition;

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
	JLabel compositionLabel = new JLabel();
	JButton returnButton = new JButton();
	JButton saveButton = new JButton();
	JButton inputButton = new JButton();
	SaveHoldItem SaveHoldItem;
	SaveComposition SaveComposition;
	List<List<List<Integer>>> allCompositionList = new ArrayList<>();
	List<String> compositionNameList = new ArrayList<>();
	List<List<Integer>> selectCompositionList = new ArrayList<>();
	DefaultListModel<String> compositionListModel = new DefaultListModel<String>();
	JList<String> compositionJList = new JList<String>(compositionListModel);
	JScrollPane compositionScroll = new JScrollPane();
	JScrollPane coreScroll = new JScrollPane();
	JScrollPane weaponScroll = new JScrollPane();
	
	public MenuComposition(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		addReturnButton(MainFrame);
		addSaveButton();
		addInputButton();
		addCompositionLabel();
		addCompositionScroll();
		addCoreScroll();
		addWeaponScroll();
		load();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setReturnButton();
		setSaveButton();
		setInputButton();
		setCompositionLabel();
		setCompositionScroll();
		setCoreScroll();
		setWeaponScroll();
		//test(g);
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
		returnButton.setBounds(190, 480, 130, 60);
		setButton(returnButton);
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
		saveButton.setBounds(330, 480, 130, 60);
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
		inputButton.setBounds(470, 480, 130, 60);
		setButton(inputButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
		button.setFocusable(false);
	}
	
	private void save() {
		try {
			ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(savecomposition.SaveComposition.COMPOSITION_FILE)));
			compositionData.writeObject(new SaveComposition(allCompositionList, compositionNameList, selectCompositionList));
			compositionData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
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
		allCompositionList = SaveComposition.getAllCompositionList();
		compositionNameList = SaveComposition.getCompositionModel();
		selectCompositionList = SaveComposition.getSelectCompositionList();
		compositionListModel.clear();
		for(String i: compositionNameList) {
			compositionListModel.addElement(i);
		}
	}
	
	private void addCompositionLabel() {
		add(compositionLabel);
	}
	
	private void setCompositionLabel() {
		compositionLabel.setText("編成名");
		compositionLabel.setBounds(10, 10, 130, 30);
		compositionLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addCompositionScroll() {
		compositionScroll.getViewport().setView(compositionJList);
    	add(compositionScroll);
	}
	
	private void setCompositionScroll() {
		compositionJList.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		compositionScroll.setPreferredSize(new Dimension(170, 500));
    	compositionScroll.setBounds(10,40,170,500);
	}
	
	private void addCoreScroll() {
		coreScroll.getViewport().setView(new ImagePanel(new DefaultData().coreImage(2)));
    	add(coreScroll);
	}
	
	private void setCoreScroll() {
		coreScroll.setPreferredSize(new Dimension(170, 500));
		coreScroll.setBounds(610,40,170,500);
	}
	
	private void addWeaponScroll() {
		weaponScroll.getViewport().setView(new ImagePanel(new DefaultData().weaponImage(2)));
    	add(weaponScroll);
	}
	
	private void setWeaponScroll() {
		weaponScroll.setPreferredSize(new Dimension(120, 500));
		weaponScroll.setBounds(790,40,120,500);
	}
	
	private void test(Graphics g) {
		DefaultData DefaultData = new DefaultData();
		List<List<BufferedImage>> right = DefaultData.rightWeaponImage(2);
		for(int i = 0; i < right.get(0).size(); i ++) {
			g.drawImage(right.get(0).get(i), 10 + i * 100 , 10, this);
		}
		List<BufferedImage> core = DefaultData.coreImage(2);
		for(int i = 0; i < core.size(); i ++) {
			g.drawImage(core.get(i), 10 + i * 100 , 100, this);
		}
	}
}

class ImagePanel extends JPanel{
	List<BufferedImage> imageList;
	
	protected ImagePanel(List<BufferedImage> imageList) {
		this.imageList = imageList;
		setPreferredSize(new Dimension(100, imageList.size() * 100 + 50));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < imageList.size(); i++) {
			g.drawImage(imageList.get(i), 0, i * 100, this);
		}
	}
}