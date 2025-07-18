package defendthecastle;

import static javax.swing.JOptionPane.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import defaultdata.DefaultStage;
import defaultdata.DefaultUnit;
import defaultdata.stage.StageData;
import savedata.SaveGameProgress;
import savedata.SaveHoldItem;

//セーブデータ編集ダイアログ
public class TestDataEdit extends JDialog{
	protected TestDataEdit() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("テスト用セーブデータ編集");
		setSize(785, 640);
		setLocationRelativeTo(null);
		add(new TestPanel(this));
		setVisible(true);
	}
	
	protected void disposeDialog() {
		dispose();
	}
}

//セーブデータ編集用メインパネル
class TestPanel extends JPanel{
	JLabel typeLabel = new JLabel();
	JButton switchButton = new JButton();
	JButton saveButton = new JButton();
	JButton returnButton = new JButton();
	JScrollPane itemScroll = new JScrollPane();
	JScrollPane progressScroll = new JScrollPane();
	EditItem EditItem = new EditItem();
	EditProgress EditProgress = new EditProgress();
	TestDataEdit TestDataEdit;
	
	protected TestPanel(TestDataEdit TestDataEdit) {
		this.TestDataEdit = TestDataEdit;
		setBackground(new Color(240, 170, 80));
		add(typeLabel);
		addSwitchButton();
		addSaveButton();
		addReturnButton();
		addScroll();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setTypeLabel();
		setSwitchButton();
		setSaveButton();
		setReturnButton();
		setScroll();
		requestFocus();
	}
	
	private void setTypeLabel() {
		typeLabel.setText((itemScroll.getViewport().getView() == EditItem)? "保有アイテム": "クリア状況");
		typeLabel.setBounds(20, 10, 400, 30);
		typeLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
	}
	
	private void addSwitchButton() {
		add(switchButton);
		switchButton.addActionListener(e->{
			itemScroll.getViewport().setView((itemScroll.getViewport().getView() == EditItem)? EditProgress: EditItem);
		});
	}
	
	private void setSwitchButton() {
		switchButton.setText("表示切替");
		switchButton.setBounds(145, 530, 150, 60);
		setButton(switchButton);
	}
	
	private void addSaveButton() {
		add(saveButton);
		saveButton.addActionListener(e->{
			if(itemScroll.getViewport().getView() == EditItem) {
				EditItem.save();
			}else {
				EditProgress.save();
			}
			showMessageDialog(null, "セーブしました");
		});
	}
	
	private void setSaveButton() {
		saveButton.setText("セーブ");
		saveButton.setBounds(315, 530, 150, 60);
		setButton(saveButton);
	}
	
	private void addReturnButton() {
		add(returnButton);
		returnButton.addActionListener(e->{
			TestDataEdit.disposeDialog();
		});
	}
	
	private void setReturnButton() {
		returnButton.setText("戻る");
		returnButton.setBounds(485, 530, 150, 60);
		setButton(returnButton);
	}
	
	private void setButton(JButton button) {
		button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
	}
	
	private void addScroll() {
		itemScroll.getViewport().setView(EditItem);
    	add(itemScroll);
	}
	
	private void setScroll() {
		itemScroll.setBounds(20, 50, 730, 470);
		itemScroll.setPreferredSize(itemScroll.getSize());
	}
}

//保有アイテム編集
class EditItem extends JPanel{
	JLabel[] coreLabel;
	JLabel[] weaponLabel;
	JSpinner[] coreSpinner;
	JSpinner[] weaponSpinner;
	List<BufferedImage> coreImage = IntStream.range(0, DefaultUnit.CORE_DATA_MAP.size()).mapToObj(i -> DefaultUnit.CORE_DATA_MAP.get(i).getImage(4)).toList();
	List<BufferedImage> weaponImage = IntStream.range(0, DefaultUnit.WEAPON_DATA_MAP.size()).mapToObj(i -> DefaultUnit.WEAPON_DATA_MAP.get(i).getImage(4)).toList();
	SaveHoldItem SaveHoldItem = new SaveHoldItem();
	List<Integer> coreNumberList;
	List<Integer> weaponNumberList;
	int size = 50;
	
	protected EditItem() {
		load();
		addLabel();
		addSpinner();
		setPreferredSize(new Dimension(100, size * (weaponImage.size() < coreImage.size()? coreImage.size(): weaponImage.size())));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawImage(g);
		setLabel();
		setSpinner();
	}
	
	private void load() {
		SaveHoldItem.load();
		coreNumberList = SaveHoldItem.getCoreNumberList();
		weaponNumberList = SaveHoldItem.getWeaponNumberList();
	}
	
	protected void save() {
		Function<JSpinner[], List<Integer>> input = (spinner) -> {
			return Stream.of(spinner).map(i ->  (int) i.getValue()).collect(Collectors.toList());
		};
		coreNumberList = input.apply(coreSpinner);
		weaponNumberList = input.apply(weaponSpinner);
		SaveHoldItem.save(coreNumberList, weaponNumberList);
	}
	
	private void addLabel() {
		Function<Integer, JLabel[]> initialize = count -> {
			return IntStream.range(0, count).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
		};
		BiConsumer<JLabel[], List<String>> set = (label, name) -> {
			IntStream.range(0, label.length).forEach(i -> {
				add(label[i]);
				label[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
				label[i].setText(name.get(i));
			});
		};
		coreLabel = initialize.apply(coreImage.size());
		weaponLabel = initialize.apply(weaponImage.size());
		set.accept(coreLabel, IntStream.range(0, coreLabel.length).mapToObj(i -> DefaultUnit.CORE_DATA_MAP.get(i).getName()).toList());
		set.accept(weaponLabel, IntStream.range(0, weaponLabel.length).mapToObj(i -> DefaultUnit.WEAPON_DATA_MAP.get(i).getName()).toList());
	}
	
	private void setLabel() {
		BiConsumer<JLabel[], Integer> draw = (label, position) -> {
			IntStream.range(0, label.length).forEach(i -> label[i].setBounds(size + position, i * size, 200, size));
		};
		draw.accept(coreLabel, 0);
		draw.accept(weaponLabel, 360);
	}
	
	private void addSpinner() {
		Function<Integer, JSpinner[]> initialize = count -> {
			return IntStream.range(0, count).mapToObj(i -> new JSpinner()).toArray(JSpinner[]::new);
		};
		BiConsumer<JSpinner[], List<Integer>> set = (spinner, number) -> {
			IntStream.range(0, spinner.length).forEach(i -> {
				add(spinner[i]);
				spinner[i].setModel(new SpinnerNumberModel((int) number.get(i), 0, 100, 1));
				JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner[i]);
				editor.getTextField().setEditable(false);
				editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
				spinner[i].setEditor(editor);
			});
		};
		coreSpinner = initialize.apply(coreImage.size());
		weaponSpinner = initialize.apply(weaponImage.size());
		set.accept(coreSpinner, coreNumberList);
		set.accept(weaponSpinner, weaponNumberList);
	}
	
	private void setSpinner() {
		BiConsumer<JSpinner[], Integer> draw = (spinner, position) -> {
			IntStream.range(0, spinner.length).forEach(i -> {
				spinner[i].setBounds(size + position + 200, i * size, 100, size);
				spinner[i].setPreferredSize(spinner[i].getSize());
				spinner[i].setFont(new Font("Arail", Font.BOLD, 15));
			});
		};
		draw.accept(coreSpinner, 0);
		draw.accept(weaponSpinner, 360);
	}
	
	private void drawImage(Graphics g) {
		BiConsumer<List<BufferedImage>, Integer> draw = (image, position) -> {
			IntStream.range(0, image.size()).forEach(i -> g.drawImage(image.get(i), position, i * size, this));
		};
		draw.accept(coreImage, 0);
		draw.accept(weaponImage, 360);
	}
}

//クリア状況編集
class EditProgress extends JPanel{
	JLabel medalLabel;
	JSpinner medalSpinner;
	JLabel[] nameLabel;
	JRadioButton[] stage;
	List<JRadioButton[]> merit;
	StageData[] StageData = IntStream.range(0, DefaultStage.STAGE_DATA_MAP.size()).mapToObj(i -> DefaultStage.STAGE_DATA_MAP.get(i)).toArray(StageData[]::new);
	List<BufferedImage> stageImage = Stream.of(StageData).map(i -> i.getImage(20)).toList();
	SaveGameProgress SaveGameProgress = new SaveGameProgress();
	List<Boolean> clearStatus;
	List<List<Boolean>> meritStatus;
	int medal;
	int sizeX = 110;
	int sizeY = 70;
	
	protected EditProgress() {
		load();
		addLabel();
		addSpinner();
		addRadio();
		setPreferredSize(new Dimension(500, sizeY * stageImage.size()));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawImage(g);
		setLabel();
		setSpinner();
		setRadio();
	}
	
	private void load() {
		SaveGameProgress.load();
		clearStatus = SaveGameProgress.getClearStatus();
		meritStatus = SaveGameProgress.getMeritStatus();
		medal = SaveGameProgress.getMedal();
	}
	
	protected void save() {
		clearStatus = Stream.of(stage).map(i -> i.isSelected()).collect(Collectors.toList());
		meritStatus = merit.stream().map(i -> Stream.of(i).map(j -> j.isSelected()).collect(Collectors.toList())).collect(Collectors.toList());
		medal = (int) medalSpinner.getValue();
		SaveGameProgress.save(clearStatus, meritStatus, medal, SaveGameProgress.getSelectStage());
	}
	
	private void addLabel() {
		Function<Integer, JLabel[]> initialize = count -> {
			return IntStream.range(0, count).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
		};
		BiConsumer<JLabel, String> set = (label, name) -> {
			add(label);
			label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
			label.setText(name);
		};
		nameLabel = initialize.apply(stageImage.size());
		IntStream.range(0, StageData.length).forEach(i -> set.accept(nameLabel[i], StageData[i].getName()));
		medalLabel = new JLabel();
		set.accept(medalLabel, "保有メダル");
	}
	
	private void setLabel() {
		IntStream.range(0, nameLabel.length).forEach(i -> nameLabel[i].setBounds(sizeX, (i + 1) * sizeY, sizeX, sizeY));
		medalLabel.setBounds(sizeX, 0, sizeX, sizeY);
	}
	
	private void addSpinner() {
		medalSpinner = new JSpinner();
		add(medalSpinner);
		medalSpinner.setModel(new SpinnerNumberModel(medal, 0, 100000, 100));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(medalSpinner);
		editor.getTextField().setEditable(false);
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		medalSpinner.setEditor(editor);
	}
	
	private void setSpinner() {
		medalSpinner.setBounds(sizeX * 2, 0, sizeX, sizeY);
		medalSpinner.setPreferredSize(medalSpinner.getSize());
		medalSpinner.setFont(new Font("Arail", Font.BOLD, 15));
	}
	
	private void addRadio() {
		Function<Integer, JRadioButton[]> initialize = count -> {
			return IntStream.range(0, count).mapToObj(i -> new JRadioButton()).toArray(JRadioButton[]::new);
		};
		BiConsumer<JRadioButton[], List<Boolean>> set = (radio, clear) -> {
			IntStream.range(0, radio.length).forEach(i -> {
				add(radio[i]);
				radio[i].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 10));
				radio[i].setOpaque(false);
				if(clear.get(i)) {
					radio[i].setSelected(true);
				}
			});
		};
		stage = initialize.apply(stageImage.size());
		set.accept(stage, clearStatus);
		Stream.of(stage).forEach(i -> i.setText("ステージクリア"));
		merit = Stream.of(StageData).map(i -> initialize.apply(i.getMerit().size())).toList();
		IntStream.range(0, merit.size()).forEach(i -> {
			set.accept(merit.get(i), meritStatus.get(i));
			IntStream.range(0, merit.get(i).length).forEach(j -> merit.get(i)[j].setText("戦功" + (j + 1) + "クリア"));
		});
	}
	
	private void setRadio() {
		IntStream.range(0, stage.length).forEach(i -> stage[i].setBounds(sizeX + 100, (i + 1) * sizeY, sizeX, sizeY));
		IntStream.range(0, merit.size()).forEach(i -> IntStream.range(0, merit.get(i).length).forEach(j -> merit.get(i)[j].setBounds(100 + sizeX * (2 + j / 2), (i + 1) * sizeY + j % 2 * sizeY / 2, sizeX, sizeY / 2)));
	}
	
	private void drawImage(Graphics g) {
		IntStream.range(0, stageImage.size()).forEach(i -> g.drawImage(stageImage.get(i), 0, (i + 1) * sizeY, this));
	}
}