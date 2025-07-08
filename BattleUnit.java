package battle;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import defaultdata.DefaultAtackPattern;
import defaultdata.DefaultUnit;
import defaultdata.EditImage;
import screendisplay.DisplayStatus;
import screendisplay.StatusCalculation;

//ユニットのバトル情報
public class BattleUnit extends BattleData{
	BufferedImage coreImage;
	Point initialPosition;
	int type;
	
	//右武器/コア用　攻撃・被弾などの判定はこちらで行う
	protected BattleUnit(Battle Battle, List<Integer> composition, int positionX, int positionY) {
		this.Battle = Battle;
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		name = new DisplayStatus().getUnitName(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(0)).getRightActionImageName(), 4);
		}catch(Exception e) {
			actionImage = Arrays.asList(getBlankImage());
		}
		coreImage = new EditImage().input(new DefaultUnit().getCoreData(composition.get(1)).getActionImageName(), 4);
		this.positionX = positionX;
		this.positionY = positionY;
		initialPosition = new Point(positionX, positionY);
		type = StatusCalculation.getType();
		element = StatusCalculation.getRightElement().stream().toList();
		AtackPattern = new DefaultAtackPattern().getAtackPattern(StatusCalculation.getRightAtackPattern());
		defaultWeaponStatus = StatusCalculation.getRightWeaponStatus().stream().toList();
		defaultUnitStatus = StatusCalculation.getUnitStatus().stream().toList();
		defaultCutStatus = StatusCalculation.getCutStatus().stream().toList();
		canActivate = false;
		super.initialize();
	}
	
	//左武器用
	protected BattleUnit(Battle Battle, List<Integer> composition) {
		this.Battle = Battle;
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(2)).getLeftActionImageName(), 4);
		}catch(Exception e) {
			actionImage = Arrays.asList(getBlankImage());
		}
		element = StatusCalculation.getLeftElement();
		AtackPattern = new DefaultAtackPattern().getAtackPattern(StatusCalculation.getLeftAtackPattern());
		defaultWeaponStatus = StatusCalculation.getLeftWeaponStatus();
		defaultUnitStatus = StatusCalculation.getUnitStatus();
		defaultCutStatus = StatusCalculation.getCutStatus();
		super.initialize();
	}
	
	protected void install(BattleUnit[] unitMainData, BattleFacility[] facilityData, BattleEnemy[] enemyData) {
		allyData.add(unitMainData);
		allyData.add(facilityData);
		this.enemyData.add(enemyData);
		if(0 < getAtack()) {
			AtackPattern.install(this, this.enemyData);
			return;
		}
		if(getAtack() < 0) {
			AtackPattern.install(this, allyData);
			return;
		}
	}
	
	private BufferedImage getBlankImage() {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, 0);
		return image;
	}
	
	public BufferedImage getCoreImage(){
		return coreImage;
	}
	
	public int getType() {
		return type;
	}
	
	protected void activate(int x, int y) {
		canActivate = true;
		positionX = x;
		positionY = y;
		atackTimer();
	}
	
	protected void deactivate() {
		canActivate = false;
		positionX = initialPosition.x;
		positionY = initialPosition.y;
		super.initialize();
	}
	
	
	
	
}