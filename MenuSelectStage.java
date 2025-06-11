package defendthecastle;

import static javax.swing.JOptionPane.*;
import static savedata.SaveGameProgress.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import defaultdata.DataStage;
import savedata.SaveGameProgress;

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
	ProgressData ProgressData = new ProgressData();
	StagePanel StagePanel = new StagePanel(ProgressData.getSelectStage());
	MeritPanel MeritPanel = new MeritPanel(StagePanel, ProgressData.getMeritStatus());
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
		informationLabel.setBounds(170, 10, 200, 30);
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
		returnButton.setBounds(10, 460, 150, 60);
		setButton(returnButton);
	}
	
	private void addNormalModeButton() {
		add(normalModeButton);
		normalModeButton.addActionListener(e->{
			ProgressData.save(StagePanel.getSelelct());
			showMessageDialog(null, "調整中\nnormal mode でゲーム開始");
		});
	}
	
	private void setNormalModeButton() {
		normalModeButton.setText("normal");
		normalModeButton.setBounds(580, 460, 155, 60);
		setButton(normalModeButton);
	}
	
	private void addHardModeButton() {
		add(hardModeButton);
		hardModeButton.addActionListener(e->{
			ProgressData.save(StagePanel.getSelelct());
			showMessageDialog(null, "調整中\nhard mode でゲーム開始");
		});
	}
	
	private void setHardModeButton() {
		hardModeButton.setText("hard");
		hardModeButton.setBounds(745, 460, 155, 60);
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
		stageScroll.setBounds(10, 40, 150, 410);
		stageScroll.setPreferredSize(stageScroll.getSize());
	}
	
	private void addMeritScroll() {
		meritScroll.getViewport().setView(MeritPanel);
    	add(meritScroll);
	}
	
	private void setMeritScroll() {
		meritScroll.setBounds(170, 275, 400, 245);
		meritScroll.setPreferredSize(meritScroll.getSize());
	}
	
	private void addEnemyScroll() {
		enemyScroll.getViewport().setView(EnemyPanel);
    	add(enemyScroll);
	}
	
	private void setEnemyScroll() {
		enemyScroll.setBounds(580, 40, 320, 410);
		enemyScroll.setPreferredSize(enemyScroll.getSize());
	}
	
	private void drawField(Graphics g){
		g.drawImage(stageImage.get(StagePanel.getSelelct()), 170, 40, this);
	}
}

//クリアデータ取込み
class ProgressData{
	SaveGameProgress SaveGameProgress;
	
	protected ProgressData() {
		try {
			ObjectInputStream loadProgressData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(PROGRESS_FILE)));
			SaveGameProgress = (SaveGameProgress) loadProgressData.readObject();
			loadProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void save(int select) {
		try {
			ObjectOutputStream saveProgressData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(PROGRESS_FILE)));
			saveProgressData.writeObject(new SaveGameProgress(SaveGameProgress.getClearStatus(), SaveGameProgress.getMeritStatus(), SaveGameProgress.getMedal(), select));
			saveProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected List<List<Boolean>> getMeritStatus(){
		return SaveGameProgress.getMeritStatus();
	}
	
	protected int getSelectStage() {
		return SaveGameProgress.getSelectStage();
	}
}

//ステージ切り替え
class StagePanel extends JPanel implements MouseListener{
	JLabel[] nameLabel = IntStream.range(0, DataStage.STAGE_NAME_LIST.size()).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
	List<BufferedImage> stageImage = new DataStage().getStageImage(18);
	int select = 0;
	
	protected StagePanel(int select) {
		addMouseListener(this);
		setPreferredSize(new Dimension(100, 85 * stageImage.size()));
		this.select = select;
		Stream.of(nameLabel).forEach(i -> addLabel(i));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		IntStream.range(0, nameLabel.length).forEach(i -> setLabel(i));
		IntStream.range(0, stageImage.size()).forEach(i -> drawField(i, g));
	}
	
	private void addLabel(JLabel label) {
		add(label);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.RED);
	}
	
	private void setLabel(int number) {
		nameLabel[number].setText(DataStage.STAGE_NAME_LIST.get(number));
		nameLabel[number].setBounds(0, 25 + 85 * number, 130, 30);
		nameLabel[number].setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	private void drawField(int number, Graphics g) {
		if(select == number) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 85 * number, 135, 85);
		}
		g.drawImage(stageImage.get(number), 10, 10 + 85 * number, this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i = 0; i < stageImage.size(); i++) {
			if(ValueRange.of(10, 125).isValidIntValue(e.getX())
					&& ValueRange.of(10 + 85 * i, -10 + 85 * (i + 1)).isValidIntValue(e.getY())) {
				select = i;
				break;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	protected int getSelelct() {
		return select;
	}
}

//戦功情報
class MeritPanel extends JPanel{
	JLabel[] meritLabel = IntStream.range(0, DataStage.MERIT_INFORMATION.stream().mapToInt(i -> i.size()).max().getAsInt()).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
	JLabel[] clearLabel = IntStream.range(0, meritLabel.length).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
	StagePanel StagePanel;
	List<List<Boolean>> meritStatus;
	List<List<String>> meritInformation;
	Font meritFont = new Font("ＭＳ ゴシック", Font.BOLD, 15);
	Font clearFont = new Font("Arail", Font.BOLD, 30);
	
	protected MeritPanel(StagePanel StagePanel, List<List<Boolean>> meritStatus) {
		this.StagePanel = StagePanel;
		this.meritStatus = meritStatus;
		meritInformation = DataStage.MERIT_INFORMATION.stream().map(i -> i.stream().map(j -> wrap(j)).toList()).toList();
		addLabel();
	}
	
	private String wrap(String comment) {
		int lastPosition = 0;
		List<Integer> wrapPosition = new ArrayList<>();
		for(int i = 0; i < comment.length(); i++) {
			if(comment.substring(i, i + 1).equals("(") || 280 < getFontMetrics(meritFont).stringWidth(comment.substring(lastPosition, i))) {
				wrapPosition.add(i);
				lastPosition = i;
			}
		}
		if(wrapPosition.isEmpty()) {
			return comment;
		}
		StringBuilder wrapComment = new StringBuilder(comment);
		wrapPosition.stream().sorted(Comparator.reverseOrder()).forEach(i -> wrapComment.insert(i, "<br>"));
		return wrapComment.insert(0, "<html>").toString();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension(200, 70 * meritInformation.get(StagePanel.getSelelct()).size()));
		IntStream.range(0, meritLabel.length).forEach(i -> setLabel(i));
		IntStream.range(0, meritLabel.length).forEach(i -> g.drawLine(0, 70 * i, 400, 70 * i));
	}
	
	private void addLabel() {
		Stream.of(meritLabel).forEach(i -> {
			add(i);
			i.setFont(meritFont);
		});
		Stream.of(clearLabel).forEach(i -> {
			add(i);
			i.setHorizontalAlignment(JLabel.CENTER);
			i.setForeground(Color.RED);
			i.setFont(clearFont);
		});
	}
	
	private void setLabel(int number) {
		try{
			meritLabel[number].setText(meritInformation.get(StagePanel.getSelelct()).get(number));
			meritLabel[number].setBounds(5, number * 70, 400, 70);
			clearLabel[number].setText(meritStatus.get(StagePanel.getSelelct()).get(number)? "clear": "");
			clearLabel[number].setBounds(290, number * 70, 100, 70);
		}catch (Exception e) {
			meritLabel[number].setText("");
			clearLabel[number].setText("");
		}
	}
}

//敵兵情報
class EnemyPanel extends JPanel{
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}