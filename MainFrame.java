package defendthecastle;

import java.util.List;

import javax.swing.JFrame;

import battle.Battle;
import defaultdata.stage.StageData;

//メイン画面切り替え
public class MainFrame extends JFrame{
	protected MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		mainMenuDraw();
	}
	
	protected void mainMenuDraw() {
		getContentPane().removeAll();
		setTitle("メインメニュー");
		setSize(585, 510);
		add(new MenuMain(this));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void itemGetMenuDraw() {
		getContentPane().removeAll();
		setTitle("ガチャ");
		setSize(585, 510);
		add(new MenuItemGet(this));
		setLocationRelativeTo(null);
	}
	
	protected void itemDisposeMenuDraw() {
		getContentPane().removeAll();
		setTitle("リサイクル");
		setSize(715, 640);
		add(new MenuItemDispose(this));
		setLocationRelativeTo(null);
	}
	
	protected void compositionDraw() {
		getContentPane().removeAll();
		setTitle("ユニット編成");
		setSize(975, 570);
		add(new MenuComposition(this));
		setLocationRelativeTo(null);
	}
	
	public void selectStageDraw() {
		getContentPane().removeAll();
		setTitle("ステージ選択");
		setSize(925, 570);
		add(new MenuSelectStage(this));
		setLocationRelativeTo(null);
	}
	
	protected void battleDraw(StageData StageData, List<Boolean> clearMerit, int difficultyCode) {
		getContentPane().removeAll();
		setTitle(StageData.getName());
		setSize(1235, 600);
		add(new Battle(this, StageData, clearMerit, difficultyCode));
		setLocationRelativeTo(null);
	}
}