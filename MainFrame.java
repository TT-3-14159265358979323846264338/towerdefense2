package mainframe;

import javax.swing.JFrame;

import menucomposition.MenuComposition;
import menuitemget.MenuItemGet;
import menumain.MenuMain;
import menuselectstage.MenuSelectStage;

//メイン画面切り替え
public class MainFrame extends JFrame{
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		mainMenuDraw();
	}
	
	public void mainMenuDraw() {
		getContentPane().removeAll();
		setTitle("メインメニュー");
		setSize(445, 160);
		add(new MenuMain(this));
		setLocationRelativeTo(null);
	}
	
	public void itemGetMenuDraw() {
		getContentPane().removeAll();
		setTitle("ガチャ");
		setSize(1000, 160);
		add(new MenuItemGet(this));
		setLocationRelativeTo(null);
	}
	
	public void compositionDraw() {
		getContentPane().removeAll();
		setTitle("ユニット編成");
		setSize(885, 600);
		add(new MenuComposition(this));
		setLocationRelativeTo(null);
	}
	
	public void selectStageDraw() {
		getContentPane().removeAll();
		setTitle("ステージ選択");
		setSize(700, 300);
		add(new MenuSelectStage(this));
		setLocationRelativeTo(null);
	}
}