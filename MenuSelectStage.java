package menuselectstage;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import mainframe.MainFrame;

//ステージ選択
public class MenuSelectStage extends JPanel{
	MainFrame MainFrame;
	JButton test = new JButton();
	
	public MenuSelectStage(MainFrame MainFrame) {
		this.MainFrame = MainFrame;
		add(test);
		test.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		test.setText("調整中");
		test.setBounds(10, 30, 130, 60);
		test.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
		test.setFocusable(false);
	}
}