package mainmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import mainframe.MainFrame;

//メニュー画面
public class MainMenu extends JPanel{
	MainFrame MainFrame;
	JButton itemGetButton = new JButton();
	JButton compositionButton = new JButton();
	JButton selectStageButton = new JButton();
	
	public MainMenu(MainFrame MainFrame) {
		this.MainFrame = MainFrame;
		setBackground(new Color(240, 170, 80));
		addItemGetButton();
		addCompositionButton();
		addBattleButton();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setItemGetButton();
		setCompositionButton();
		setBattleButton();
	}
	
	private void addItemGetButton() {
		add(itemGetButton);
		itemGetButton.addActionListener(e->{
			MainFrame.itemGetMenuDraw();
		});
	}
	
	private void setItemGetButton() {
		itemGetButton.setText("ガチャ");
		itemGetButton.setBounds(10, 30, 130, 60);
		setButton(itemGetButton);
	}
	
	private void addCompositionButton() {
		add(compositionButton);
		compositionButton.addActionListener(e->{
			MainFrame.compositionDraw();
		});
	}
	
	private void setCompositionButton() {
		compositionButton.setText("ユニット編成");
		compositionButton.setBounds(150, 30, 130, 60);
		setButton(compositionButton);
	}
	
	private void addBattleButton() {
		add(selectStageButton);
		selectStageButton.addActionListener(e->{
			MainFrame.selectStageDraw();
		});
	}
	
	private void setBattleButton() {
		selectStageButton.setText("ステージ選択");
		selectStageButton.setBounds(290, 30, 130, 60);
		setButton(selectStageButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
		button.setFocusable(false);
	}
}
