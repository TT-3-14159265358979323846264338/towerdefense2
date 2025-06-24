package battle;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import defaultdata.DefaultEnemy;
import defaultdata.EditImage;
import defaultdata.enemy.EnemyData;
import defaultdata.stage.StageData;

//敵のバトル情報
public class BattleEnemy extends BattleData implements ActionListener{
	List<List<Integer>> move;
	
	protected BattleEnemy(StageData StageData, int number) {
		EnemyData EnemyData = new DefaultEnemy().getEnemyData(StageData.getEnemy().get(number).get(0));
		name = EnemyData.getName();
		actionImage = new EditImage().input(EnemyData.getActionImageName(), 4);
		move = StageData.getMove().get(StageData.getEnemy().get(number).get(1));
		position = new Point(move.get(0).get(0), move.get(0).get(1));
		element = EnemyData.getElement();
		defaultWeaponStatus = EnemyData.getWeaponStatus().stream().toList();
		defaultUnitStatus = EnemyData.getUnitStatus().stream().toList();
		defaultCutStatus = EnemyData.getCutStatus().stream().toList();
		canActivate = false;
		super.initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}