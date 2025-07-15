package battle;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import defaultdata.atackpattern.AtackPattern;

//全キャラクターの共通システム
public class BattleData{
	Battle Battle;
	List<BattleData> allyData;
	List<BattleData> enemyData;
	List<BufferedImage> actionImage;
	int motionNumber = 0;
	boolean canAtack;
	String name;
	double positionX;
	double positionY;
	List<Integer> element;
	AtackPattern AtackPattern;
	List<Integer> defaultWeaponStatus;
	List<Integer> defaultUnitStatus;
	List<Integer> defaultCutStatus;
	List<Integer> collectionWeaponStatus;
	List<Integer> collectionUnitStatus;
	List<Integer> collectionCutStatus;
	List<Double> ratioWeaponStatus;
	List<Double> ratioUnitStatus;
	List<Double> ratioCutStatus;
	int nowHP;
	boolean canActivate;
	
	protected void initialize() {
		collectionWeaponStatus = defaultWeaponStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionUnitStatus = defaultUnitStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionCutStatus = defaultCutStatus.stream().map(i -> 0).collect(Collectors.toList());
		ratioWeaponStatus = defaultWeaponStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioUnitStatus = defaultUnitStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioCutStatus = defaultCutStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		nowHP = unitCalculate(1);
	}
	
	protected BufferedImage getActionImage(){
		return actionImage.get(motionNumber);
	}
	
	public BufferedImage getDefaultImage() {
		return actionImage.get(0);
	}
	
	protected void atackTimer() {
		int nowSpeed = getAtackSpeed();
		if(nowSpeed <= 0) {
			return;
		}
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(() -> {
			if(!canActivate) {
				scheduler.shutdown();
				return;
			}
			if(nowSpeed != getAtackSpeed()) {
				atackTimer();
				scheduler.shutdown();
				return;
			}
			motionTimer(targetCheck());
			timerWait();
		}, 0, getAtackSpeed(), TimeUnit.MILLISECONDS);
	}
	
	private List<BattleData> targetCheck() {
		List<BattleData> targetList = AtackPattern.getTarget();
		do {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Battle.timerWait();
			targetList = AtackPattern.getTarget();
		}while(targetList.isEmpty());
		canAtack = true;
		return targetList;
	}
	
	private void motionTimer(List<BattleData> targetList) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(() -> {
			Battle.timerWait();
			if(5 <= motionNumber) {
				targetList.stream().forEach(i -> motionResult(i));
				timerRestart();
				scheduler.shutdown();
				return;
			}
			motionNumber++;
		}, 0, 1000 * getAtackSpeed() / 50, TimeUnit.MICROSECONDS);
	}
	
	protected synchronized void timerWait() {
		if(canAtack) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void timerRestart() {
		notifyAll();
		canAtack = false;
		motionNumber = 0;
	}
	
	private void motionResult(BattleData target) {
		if(getAtack() == 0) {
			buff(target);
			return;
		}
		if(element.stream().anyMatch(i -> i == 11)) {
			heal(target);
			return;
		}
		atack(target);
	}
	
	private void buff(BattleData target) {
		
	}
	
	private void heal(BattleData target) {
		
	}
	
	private void atack(BattleData target) {
		
	}
	
	protected void healTimer() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(() -> {
			Battle.timerWait();
			if(!canActivate) {
				scheduler.shutdown();
				return;
			}
			int futureHP = nowHP + getRecover();
			nowHP = (futureHP < getMaxHP())? futureHP: getMaxHP();
		}, 0, 3, TimeUnit.SECONDS);
	}
	
	public String getName() {
		return name;
	}
	
	public double getPositionX() {
		return positionX;
	}
	
	public double getPositionY() {
		return positionY;
	}
	
	public List<Integer> getElement(){
		return element;
	}
	
	public AtackPattern getAtackPattern() {
		return AtackPattern;
	}
	
	private int getAtack() {
		return weaponCalculate(0);
	}
	
	public int getRange() {
		return weaponCalculate(1);
	}
	
	private int getAtackSpeed() {
		return weaponCalculate(2);
	}
	
	public int getAtackNumber() {
		return weaponCalculate(3);
	}
	
	protected int getMaxHP() {
		return unitCalculate(0);
	}
	
	protected int getNowHP() {
		return nowHP;
	}
	
	private int getDefense() {
		return unitCalculate(2);
	}
	
	private int getRecover() {
		return unitCalculate(3);
	}
	
	protected int getMoveSpeedOrBlock() {
		return unitCalculate(4);
	}
	
	private int getcost() {
		return unitCalculate(5);
	}
	
	public List<Integer> getWeapon(){
		return IntStream.range(0, defaultWeaponStatus.size()).mapToObj(i -> weaponCalculate(i)).toList();
	}
	
	private int weaponCalculate(int number) {
		return calculate(defaultWeaponStatus.get(number), collectionWeaponStatus.get(number), ratioWeaponStatus.get(number));
	}
	
	public List<Integer> getUnit(){
		return IntStream.range(0, defaultUnitStatus.size()).mapToObj(i -> unitCalculate(i)).toList();
	}
	
	private int unitCalculate(int number) {
		return calculate(defaultUnitStatus.get(number), collectionUnitStatus.get(number), ratioUnitStatus.get(number));
	}
	
	public List<Integer> getCut(){
		return IntStream.range(0, defaultCutStatus.size()).mapToObj(i -> getCut(i)).toList();
	}
	
	private int getCut(int number) {
		return calculate(defaultCutStatus.get(number), collectionCutStatus.get(number), ratioCutStatus.get(number));
	}
	
	private int calculate(int fixedValue, int flexValue, double ratio) {
		return (int) ((fixedValue + flexValue) * ratio);
	}
	
	public boolean getActivate() {
		return canActivate;
	}
}