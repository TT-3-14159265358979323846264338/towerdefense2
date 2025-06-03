package menuselectstage;

import static javax.swing.JOptionPane.*;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mainframe.MainFrame;

//ステージ選択画面
public class MenuSelectStage extends JPanel{
	JLabel stageLabel = new JLabel();
	JLabel informationLabel = new JLabel();
	JButton returnButton = new JButton();
	JButton normalModeButton = new JButton();
	JButton hardModeButton = new JButton();
	JScrollPane stageScroll = new JScrollPane();
	JScrollPane enemyScroll = new JScrollPane();
	StagePanel StagePanel = new StagePanel();
	EnemyPanel EnemyPanel = new EnemyPanel();
	
	
	
	
	
	
	
	
	
	public MenuSelectStage(MainFrame MainFrame) {
		addStageLabel();
		addInformationLabel();
		addReturnButton(MainFrame);
		addNormalModeButton();
		addHardModeButton();
		addStageScroll();
		addEnemyScroll();
		
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setStageLabel();
		setInformationLabel();
		setReturnButton();
		setNormalModeButton();
		setHardModeButton();
		setStageScroll();
		setEnemyScroll();
		
		
		
		drawField(g);
		requestFocus();
	}
	
	private void addStageLabel() {
		add(stageLabel);
	}
	
	private void setStageLabel() {
		stageLabel.setText("ステージ選択");
		stageLabel.setBounds(10, 10, 200, 30);
		setLabel(stageLabel);
	}
	
	private void addInformationLabel() {
		add(informationLabel);
	}
	
	private void setInformationLabel() {
		informationLabel.setText("ステージ情報");
		informationLabel.setBounds(220, 10, 200, 30);
		setLabel(informationLabel);
	}
	
	private void setLabel(JLabel label) {
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addReturnButton(MainFrame MainFrame) {
		add(returnButton);
		returnButton.addActionListener(e->{
			MainFrame.mainMenuDraw();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(10, 450, 200, 60);
		setButton(returnButton);
	}
	
	private void addNormalModeButton() {
		add(normalModeButton);
		normalModeButton.addActionListener(e->{
			showMessageDialog(null, "調整中　narmal mode でゲーム開始");
		});
	}
	
	private void setNormalModeButton() {
		normalModeButton.setText("normal");
		normalModeButton.setBounds(320, 450, 100, 60);
		setButton(normalModeButton);
	}
	
	private void addHardModeButton() {
		add(hardModeButton);
		hardModeButton.addActionListener(e->{
			showMessageDialog(null, "調整中　hard mode でゲーム開始");
		});
	}
	
	private void setHardModeButton() {
		hardModeButton.setText("hard");
		hardModeButton.setBounds(430, 450, 100, 60);
		setButton(hardModeButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
	}
	
	private void addStageScroll() {
		stageScroll.getViewport().setView(StagePanel);
    	add(stageScroll);
	}
	
	private void setStageScroll() {
		stageScroll.setBounds(10, 40, 200, 400);
		stageScroll.setPreferredSize(stageScroll.getSize());
	}
	
	private void addEnemyScroll() {
		enemyScroll.getViewport().setView(EnemyPanel);
    	add(enemyScroll);
	}
	
	private void setEnemyScroll() {
		enemyScroll.setBounds(220, 40, 320, 400);
		enemyScroll.setPreferredSize(enemyScroll.getSize());
	}
	
	
	
	
	
	
	private void drawField(Graphics g){
		//フィールド画像を表示
	}
}

//ステージ切り替え
class StagePanel extends JPanel{
	
	
	
	
	
	
	
	
	
}

//敵兵情報
class EnemyPanel extends JPanel{
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}