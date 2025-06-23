package battle;

import static javax.swing.JOptionPane.*;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JPanel;

import defaultdata.DefaultStage;
import defaultdata.EditImage;
import defaultdata.stage.StageData;
import defendthecastle.MainFrame;

public class Battle extends JPanel{
	JButton rangeDrawButton = new JButton();
	JButton targetButton = new JButton();
	JButton pauseButton = new JButton();
	JButton returnButton = new JButton();
	StageData StageData;
	BufferedImage stageImage;
	BattleData[] unitData;
	BattleData[] facilityData;
	BattleData[] enemyData;
	int targetCode = 0;
	Map<Integer, String> targetMap = new HashMap<>();{
		targetMap.put(0, "HP割合 低");
		targetMap.put(1, "HP割合 高");
		targetMap.put(2, "距離 近");
		targetMap.put(3, "距離 遠");
	}
	
	public Battle(MainFrame MainFrame, StageData StageData, int difficultyCode) {
		this.StageData = StageData;
		install(StageData);
		addRangeDrawButton();
		addTargetButton();
		addPauseButton();
		addReturnButton(MainFrame);
		
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setButton(rangeDrawButton, "射程表示", 1010, 465, 95, 40);
		setButton(targetButton, targetMap.get(targetCode), 1110, 465, 95, 40);
		setButton(pauseButton, "一時停止", 1010, 515, 95, 40);
		setButton(returnButton, "降参", 1110, 515, 95, 40);
		drawField(g);
		
		
		
		requestFocus();
	}
	
	private void install(StageData StageData) {
		stageImage = new EditImage().input(StageData.getImageName(), 2);
		facilityData = IntStream.range(0, StageData.getFacility().size()).mapToObj(i -> new BattleData(new DefaultStage().getFacilityData(StageData.getFacility().get(i)), StageData.getFacilityDirection().get(i))).toArray(BattleData[]::new);
		
		
		
		
	}
	
	private void addRangeDrawButton() {
		add(rangeDrawButton);
		rangeDrawButton.addActionListener(e->{
			showMessageDialog(null, "現在調整中");
		});
	}
	
	private void addTargetButton() {
		add(targetButton);
		targetButton.addActionListener(e->{
			targetCode = (targetCode < targetMap.size() - 1)? targetCode + 1: 0;
		});
	}
	
	private void addPauseButton() {
		add(pauseButton);
		pauseButton.addActionListener(e->{
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
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 12));
	}
	
	private void drawField(Graphics g) {
		g.drawImage(stageImage, 0, 0, this);
		IntStream.range(0, facilityData.length).forEach(i -> g.drawImage(facilityData[i].getActionImage().get(0), StageData.getFacilityPoint().get(i).x, StageData.getFacilityPoint().get(i).y, this));
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
