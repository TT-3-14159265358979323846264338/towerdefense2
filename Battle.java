package battle;

import static javax.swing.JOptionPane.*;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import defaultdata.EditImage;
import defaultdata.stage.StageData;
import defendthecastle.MainFrame;

public class Battle extends JPanel{
	JButton rangeDrawButton = new JButton();
	JButton setTargetButton = new JButton();
	JButton pauseButton = new JButton();
	JButton returnButton = new JButton();
	BufferedImage stageImage;
	
	public Battle(MainFrame MainFrame, StageData StageData, int difficultyCode) {
		addRangeDrawButton();
		
		
		addReturnButton(MainFrame);
		stageImage = new EditImage().stageImage(StageData, 2);
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setButton(rangeDrawButton, "射程表示", 1010, 465, 95, 40);
		
		
		
		setButton(returnButton, "降参", 1050, 515, 120, 40);
		
		g.drawImage(stageImage, 0, 0, this);
		
		
		
		requestFocus();
	}
	
	private void addRangeDrawButton() {
		add(rangeDrawButton);
		rangeDrawButton.addActionListener(e->{
			showMessageDialog(null, "現在調整中");
		});
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.selectStageDraw();
		});
	}
	
	private void setButton(JButton button, String name, int x, int y, int width, int height) {
		button.setText(name);
		button.setBounds(x, y, width, height);
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 14));
	}
	
	
	
}
