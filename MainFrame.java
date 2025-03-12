package mainframe;

import javax.swing.JFrame;

import compositionmenu.CompositionMenu;
import itemgetmenu.ItemGetMenu;
import mainmenu.MainMenu;
import selectstagemenu.SelectStageMenu;

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
		add(new MainMenu(this));
		setLocationRelativeTo(null);
	}
	
	public void itemGetMenuDraw() {
		getContentPane().removeAll();
		setTitle("ガチャ");
		setSize(1000, 160);
		add(new ItemGetMenu(this));
		setLocationRelativeTo(null);
	}
	
	public void compositionDraw() {
		getContentPane().removeAll();
		setTitle("ユニット編成");
		setSize(700, 160);
		add(new CompositionMenu(this));
		setLocationRelativeTo(null);
	}
	
	public void selectStageDraw() {
		getContentPane().removeAll();
		setTitle("ステージ選択");
		setSize(700, 300);
		add(new SelectStageMenu(this));
		setLocationRelativeTo(null);
	}
}