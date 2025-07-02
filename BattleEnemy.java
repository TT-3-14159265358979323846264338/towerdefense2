package battle;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import defaultdata.DefaultEnemy;
import defaultdata.EditImage;
import defaultdata.enemy.EnemyData;
import defaultdata.stage.StageData;

//敵のバトル情報
public class BattleEnemy extends BattleData{
	int move;
	int type;
	List<List<Integer>> route;
	int routeNumber;
	int actitateTime;
	
	protected BattleEnemy(Battle Battle, StageData StageData, int number) {
		this.Battle = Battle;
		EnemyData EnemyData = new DefaultEnemy().getEnemyData(StageData.getEnemy().get(number).get(0));
		name = EnemyData.getName();
		actionImage = new EditImage().input(EnemyData.getActionImageName(), 4);
		move = EnemyData.getMove();
		type = EnemyData.getType();
		route = StageData.getRoute().get(StageData.getEnemy().get(number).get(1));
		actitateTime = StageData.getEnemy().get(number).get(2);
		position = new Point(route.get(0).get(0), route.get(0).get(1));
		element = EnemyData.getElement();
		defaultWeaponStatus = EnemyData.getWeaponStatus().stream().toList();
		defaultUnitStatus = EnemyData.getUnitStatus().stream().toList();
		defaultCutStatus = EnemyData.getCutStatus().stream().toList();
		canActivate = false;
		super.initialize();
		routeTimer();
	}
	
	public int getMove() {
		return move;
	}
	
	public int getType() {
		return type;
	}
	
	protected void routeTimer() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		int nowSpeed = getMoveSpeedOrBlock();
		if(nowSpeed <= 0) {
			scheduler.scheduleWithFixedDelay(() -> {
				if(actitateTime <= Battle.getMainTime()) {
					canActivate = true;
					scheduler.shutdown();
				}
			}, 0, 10, TimeUnit.MILLISECONDS);
			return;
		}
		scheduler.scheduleWithFixedDelay(() -> {
			if(canActivate) {
				CompletableFuture.runAsync(() -> Battle.timerWait()).join();
				move();
				if(nowSpeed != getMoveSpeedOrBlock()) {
					routeTimer();
					scheduler.shutdown();
				}
			}else {
				if(actitateTime <= Battle.getMainTime()) {
					canActivate = true;
				}
			}
		}, 0, 2000000 / nowSpeed, TimeUnit.MICROSECONDS);
	}
	
	private void move() {
		try {
			moveDistance();
			if(Math.abs(route.get(routeNumber + 1).get(0) - position.x) <= 5
					&& Math.abs(route.get(routeNumber + 1).get(1) - position.y) <= 5) {
				routeNumber++;
			}
		}catch (Exception ignore) {
			//最後のrouteに入ったので、これ以上routeNumberは増えない
		}
	}
	
	private void moveDistance() {
		int x;
		int y;
		switch(route.get(routeNumber).get(2)) {
		case 0:
			x = 0;
			y = -2;
			break;
		case 1:
			x = 2;
			y = -2;
			break;
		case 2:
			x = 2;
			y = 0;
			break;
		case 3:
			x = 2;
			y = 2;
			break;
		case 4:
			x = 0;
			y = 2;
			break;
		case 5:
			x = -2;
			y = 2;
			break;
		case 6:
			x = -2;
			y = 0;
			break;
		case 7:
			x = -2;
			y = -2;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		position.x += x;
		position.y += y;
	}
	
	
	
	
}