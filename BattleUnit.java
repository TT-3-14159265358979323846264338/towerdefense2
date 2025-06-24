package battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

//ユニットのバトル情報
public class BattleUnit extends BattleData implements ActionListener{
	BufferedImage centerActionImage;
	
	protected BattleUnit(List<Integer> composition) {
		
		super.initialize();
	}
	
	protected BufferedImage getCenterImage(){
		return centerActionImage;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
	
	
}