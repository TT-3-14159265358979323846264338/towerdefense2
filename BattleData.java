package battle;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import defaultdata.DefaultStage;
import defaultdata.EditImage;
import defaultdata.facility.FacilityData;

public class BattleData {
	String name;
	List<BufferedImage> actionImage;
	BufferedImage breakImage;
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
	
	protected BattleData(FacilityData FacilityData, boolean existsWhich) {
		name = FacilityData.getName();
		actionImage = new EditImage().input(existsWhich? FacilityData.getActionFrontImageName(): FacilityData.getActionSideImageName(), 4);
		breakImage = new EditImage().input(FacilityData.getBreakImageName(), 4);
		element = FacilityData.getElement().stream().toList();
		if(FacilityData.getWeaponStatus() == null || FacilityData.getWeaponStatus().isEmpty()) {
			defaultWeaponStatus = IntStream.range(0, DefaultStage.WEAPON_MAP.size()).mapToObj(i -> 0).toList();
		}else {
			defaultWeaponStatus = FacilityData.getWeaponStatus().stream().toList();
		}
		defaultUnitStatus = FacilityData.getUnitStatus().stream().toList();
		defaultCutStatus = FacilityData.getCutStatus().stream().toList();
		collectionWeaponStatus = defaultWeaponStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionUnitStatus = defaultUnitStatus.stream().map(i -> 0).collect(Collectors.toList());
		collectionCutStatus = defaultCutStatus.stream().map(i -> 0).collect(Collectors.toList());
		ratioWeaponStatus = defaultWeaponStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioUnitStatus = defaultUnitStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		ratioCutStatus = defaultCutStatus.stream().map(i -> 1.0).collect(Collectors.toList());
		HP = unitCalculate(1);
	}
	
	protected String getName() {
		return name;
	}
	
	protected List<BufferedImage> getActionImage(){
		return actionImage;
	}
	
	protected void breakFacility() {
		actionImage.clear();
		actionImage.add(breakImage);
	}
	
	protected List<Integer> getElement(){
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
	
	protected int getCut(int number) {
		return calculate(defaultCutStatus.get(number), collectionCutStatus.get(number), ratioCutStatus.get(number));
	}
	
	private int weaponCalculate(int number) {
		return calculate(defaultWeaponStatus.get(number), collectionWeaponStatus.get(number), ratioWeaponStatus.get(number));
	}
	
	private int unitCalculate(int number) {
		return calculate(defaultUnitStatus.get(number), collectionUnitStatus.get(number), ratioUnitStatus.get(number));
	}
	
	private int calculate(int fixedValue, int flexValue, double ratio) {
		return (int) ((fixedValue + flexValue) * ratio);
	}
}
