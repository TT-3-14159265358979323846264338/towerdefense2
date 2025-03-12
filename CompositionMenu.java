package compositionmenu;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import mainframe.MainFrame;

//編成
public class CompositionMenu extends JPanel{
	MainFrame MainFrame;
	JButton test = new JButton();
	
	public CompositionMenu(MainFrame MainFrame) {
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