package battle;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.Timer;

//全キャラクターの共通システム
public class BattleData extends atackMotion implements ActionListener{
	String name;
	Point position;
	List<Integer> element;
	List<Integer> defaultWeaponStatus;
	List<Integer> defaultUnitStatus;
	List<Integer> defaultCutStatus;
	List<Integer> collectionWeaponStatus;
	List<Integer> collectionUnitStatus;
	List<Integer> collectionCutStatus;
	List<Double> ratioWeaponStatus;
	List<Double> ratioUnitStatus;
	List<Double> ratioCutStatus;
	int HP;
	boolean canActivate;
	Timer atackTimer;
	
	protected BattleData() {
		//特になし
	}
	
	protected void initialize() {
		collectionWeaponStatus = defaultWeaponStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionUnitStatus = defaultUnitStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionCutStatus = defaultCutStatus.stream().map(i -> 0).collect(Collectors.toList());
		ratioWeaponStatus = defaultWeaponStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioUnitStatus = defaultUnitStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioCutStatus = defaultCutStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		HP = unitCalculate(1);
		atackTimer = new Timer(getAtackSpeed(), this);
	}
	
	public String getName() {
		return name;
	}
	
	protected Point getPosition() {
		return position;
	}
	
	public List<Integer> getElement(){
		return element;
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
	
	protected int getAtackNumber() {
		return weaponCalculate(3);
	}
	
	protected int getMaxHP() {
		return unitCalculate(0);
	}
	
	protected int getNowHP() {
		return HP;
	}
	
	protected int getDefense() {
		return unitCalculate(2);
	}
	
	protected int getRecover() {
		return unitCalculate(3);
	}
	
	protected int getMove() {
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
	
	protected void deactivate() {
		if(HP <= 0 ) {
			canActivate = false;
		}
	}
	
	protected boolean getActivate() {
		return canActivate;
	}
	
	protected void atackTimerStart() {
		if(!atackTimer.isRunning()) {
			atackTimer.start();
		}
	}
	
	protected void atackTimerStop() {
		if(atackTimer.isRunning()) {
			atackTimer.stop();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}

//攻撃動作
class atackMotion implements ActionListener{
	List<BufferedImage> actionImage;
	Timer motionTimer = new Timer(10, this);
	int motionNumber = 0;
	
	protected BufferedImage getActionImage(){
		try {
			return actionImage.get(motionNumber);
		}catch(Exception e) {
			return null;
		}
	}
	
	public BufferedImage getDefaultImage() {
		try {
			return actionImage.get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	protected void motionTimerStart() {
		if(!motionTimer.isRunning()) {
			motionTimer.start();
		}
	}
	
	protected void motionTimerStop() {
		if(motionTimer.isRunning()) {
			motionTimer.stop();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		motionNumber = (motionNumber == 5)? 0: motionNumber + 1;
	}
}