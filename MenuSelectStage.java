package menuselectstage;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import datastage.DataStage;
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
	JScrollPane meritScroll = new JScrollPane();
	StagePanel StagePanel = new StagePanel();
	MeritPanel MeritPanel = new MeritPanel();
	EnemyPanel EnemyPanel = new EnemyPanel();
	List<BufferedImage> stageImage = new DataStage().getStageImage(5);
	
	public MenuSelectStage(MainFrame MainFrame) {
		setBackground(new Color(240, 170, 80));
		addStageLabel();
		addInformationLabel();
		addReturnButton(MainFrame);
		addNormalModeButton();
		addHardModeButton();
		addStageScroll();
		addMeritScroll();
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
		setMeritScroll();
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
		returnButton.setBounds(10, 460, 200, 60);
		setButton(returnButton);
	}
	
	private void addNormalModeButton() {
		add(normalModeButton);
		normalModeButton.addActionListener(e->{
			showMessageDialog(null, "調整中\nnormal mode でゲーム開始");
		});
	}
	
	private void setNormalModeButton() {
		normalModeButton.setText("normal");
		normalModeButton.setBounds(630, 460, 155, 60);
		setButton(normalModeButton);
	}
	
	private void addHardModeButton() {
		add(hardModeButton);
		hardModeButton.addActionListener(e->{
			showMessageDialog(null, "調整中\nhard mode でゲーム開始");
		});
	}
	
	private void setHardModeButton() {
		hardModeButton.setText("hard");
		hardModeButton.setBounds(795, 460, 155, 60);
		setButton(hardModeButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addStageScroll() {
		stageScroll.getViewport().setView(StagePanel);
    	add(stageScroll);
	}
	
	private void setStageScroll() {
		stageScroll.setBounds(10, 40, 200, 410);
		stageScroll.setPreferredSize(stageScroll.getSize());
	}
	
	private void addMeritScroll() {
		meritScroll.getViewport().setView(MeritPanel);
    	add(meritScroll);
	}
	
	private void setMeritScroll() {
		meritScroll.setBounds(220, 275, 400, 245);
		meritScroll.setPreferredSize(meritScroll.getSize());
	}
	
	private void addEnemyScroll() {
		enemyScroll.getViewport().setView(EnemyPanel);
    	add(enemyScroll);
	}
	
	private void setEnemyScroll() {
		enemyScroll.setBounds(630, 40, 320, 410);
		enemyScroll.setPreferredSize(enemyScroll.getSize());
	}
	
	private void drawField(Graphics g){
		//フィールド画像を表示
		//現在の画像はテスト用
		g.drawImage(stageImage.get(0), 220, 40, this);
	}
}

//ステージ切り替え
class StagePanel extends JPanel{
	
	
	
	
	
	
	
	
	
}

//戦功情報
class MeritPanel extends JPanel{
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

//敵兵情報
class EnemyPanel extends JPanel{
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}