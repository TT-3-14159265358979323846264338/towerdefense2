package battle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import defaultdata.atackpattern.AtackPattern;

//全キャラクターの共通システム
public class BattleData{
	Battle Battle;
	List<BattleData[]> allyData = new ArrayList<>();
	List<BattleData[]> enemyData = new ArrayList<>();
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
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(() -> {
			if(!canActivate) {
				scheduler.shutdown();
				return;
			}
			CompletableFuture.runAsync(() -> Battle.timerWait()).join();
			atackSpeedMonitoring(scheduler, nowSpeed);
			attackHandling();
		}, 0, getAtackSpeed(), TimeUnit.MILLISECONDS);
	}
	
	private void atackSpeedMonitoring(ScheduledExecutorService scheduler, int nowSpeed) {
		if(nowSpeed != getAtackSpeed()) {
			atackTimer();
			scheduler.shutdown();
		}
	}
	
	private void attackHandling() {
		List<BattleData> target = AtackPattern.getTarget();
		if(target.isEmpty()) {
			return;
		}
		
		
		
		
		
		
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
	}
	
	private void motionTimer() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(() -> {
			CompletableFuture.runAsync(() -> Battle.timerWait()).join();
			if(5 < motionNumber) {
				motionNumber = 0;
				timerRestart();
				scheduler.shutdown();
			}
			motionNumber++;
		}, 0, 1000 * getAtackSpeed() / 5, TimeUnit.MICROSECONDS);
	}
	
	public String getName() {
		return name;
	}
	
	protected double getPositionX() {
		return positionX;
	}
	
	protected double getPositionY() {
		return positionY;
	}
	
	public List<Integer> getElement(){
		return element;
	}
	
	public AtackPattern getAtackPattern() {
		return AtackPattern;
	}
	
	protected int getAtack() {
		return weaponCalculate(0);
	}
	
	protected int getRange() {
		return weaponCalculate(1);
	}
	
	protected int getAtackSpeed() {
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
	
	protected int getDefense() {
		return unitCalculate(2);
	}
	
	protected int getRecover() {
		return unitCalculate(3);
	}
	
	protected int getMoveSpeedOrBlock() {
		return unitCalculate(4);
	}
	
	protected int getcost() {
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
	
	protected int getCut(int number) {
		return calculate(defaultCutStatus.get(number), collectionCutStatus.get(number), ratioCutStatus.get(number));
	}
	
	private int calculate(int fixedValue, int flexValue, double ratio) {
		return (int) ((fixedValue + flexValue) * ratio);
	}
	
	protected boolean getActivate() {
		return canActivate;
	}
}