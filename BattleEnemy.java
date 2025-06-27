package battle;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import defaultdata.DefaultEnemy;
import defaultdata.EditImage;
import defaultdata.enemy.EnemyData;
import defaultdata.stage.StageData;

//敵のバトル情報
public class BattleEnemy extends BattleData implements ActionListener{
	int move;
	int type;
	List<List<Integer>> route;
	Timer moveTimer;
	
	protected BattleEnemy(StageData StageData, int number) {
		EnemyData EnemyData = new DefaultEnemy().getEnemyData(StageData.getEnemy().get(number).get(0));
		name = EnemyData.getName();
		actionImage = new EditImage().input(EnemyData.getActionImageName(), 4);
		move = EnemyData.getMove();
		type = EnemyData.getType();
		route = StageData.getMove().get(StageData.getEnemy().get(number).get(1));
		position = new Point(route.get(0).get(0), route.get(0).get(1));
		element = EnemyData.getElement();
		defaultWeaponStatus = EnemyData.getWeaponStatus().stream().toList();
		defaultUnitStatus = EnemyData.getUnitStatus().stream().toList();
		defaultCutStatus = EnemyData.getCutStatus().stream().toList();
		canActivate = false;
		super.initialize();
		moveTimer = new Timer(getMove(), this);
	}
	
	public int getMove() {
		return move;
	}
	
	public int getType() {
		return type;
	}
	
	protected void moveTimerStart() {
		if(!moveTimer.isRunning()) {
			moveTimer.start();
		}
	}
	
	protected void moveTimerStop() {
		if(moveTimer.isRunning()) {
			moveTimer.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
	
	
	
	
	
	
	
	
}