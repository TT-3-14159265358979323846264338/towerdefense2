package menumain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import mainframe.MainFrame;

//トップメニュー画面
public class MenuMain extends JPanel{
	MainFrame MainFrame;
	JButton itemGetButton = new JButton();
	JButton itemDisposeButton = new JButton();
	JButton compositionButton = new JButton();
	JButton selectStageButton = new JButton();
	
	public MenuMain(MainFrame mainFrame2) {
		this.MainFrame = mainFrame2;
		setBackground(new Color(240, 170, 80));
		addItemGetButton();
		addItemDisposeButton();
		addCompositionButton();
		addBattleButton();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setItemGetButton();
		setItemDisposeButton();
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
	
	private void addItemDisposeButton() {
		add(itemDisposeButton);
		itemDisposeButton.addActionListener(e->{
			MainFrame.itemDisposeMenuDraw();
		});
	}
	
	private void setItemDisposeButton() {
		itemDisposeButton.setText("リサイクル");
		itemDisposeButton.setBounds(150, 30, 130, 60);
		setButton(itemDisposeButton);
	}
	
	private void addCompositionButton() {
		add(compositionButton);
		compositionButton.addActionListener(e->{
			MainFrame.compositionDraw();
		});
	}
	
	private void setCompositionButton() {
		compositionButton.setText("ユニット編成");
		compositionButton.setBounds(290, 30, 130, 60);
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
		selectStageButton.setBounds(430, 30, 130, 60);
		setButton(selectStageButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
		button.setFocusable(false);
	}
}
