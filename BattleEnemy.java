package battle;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
		waitObject = Battle.getWaitObject();
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
	
	private void routeTimer() {
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
			if(canActivate) {
				IntStream.range(0, getMoveSpeed() / 10).forEach(i -> CompletableFuture.allOf(mainTimerMonitoring(), routeTimerMonitoring()).join());
				move();
			}else {
				if(actitateTime <= Battle.getMainTime()) {
					canActivate = true;
				}
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
	}
	
	private synchronized CompletableFuture<Void> routeTimerMonitoring(){
		return CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(getMoveSpeed() / 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void move() {
		try {
			moveDistance();
			if(Math.abs(route.get(routeNumber + 1).get(0) - position.x) <= 5
					&& Math.abs(route.get(routeNumber + 1).get(1) - position.y) <= 5) {
				routeNumber++;
			}
		}catch (Exception ignore) {
		}
	}
	
	private void moveDistance() {
		int x;
		int y;
		try {
			switch(route.get(routeNumber).get(2)) {
			case 0:
				x = 0;
				y = -5;
				break;
			case 1:
				x = 5;
				y = -5;
				break;
			case 2:
				x = 5;
				y = 0;
				break;
			case 3:
				x = 5;
				y = 5;
				break;
			case 4:
				x = 0;
				y = 5;
				break;
			case 5:
				x = -5;
				y = 5;
				break;
			case 6:
				x = -5;
				y = 0;
				break;
			case 7:
				x = -5;
				y = -5;
				break;
			default:
				x = 0;
				y = 0;
				break;
			}
		}catch (Exception e) {
			x = 0;
			y = 0;
		}
		position.x += x;
		position.y += y;
	}
	
	
	
	
}