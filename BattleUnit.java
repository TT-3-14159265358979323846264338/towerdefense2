package battle;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

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
	protected BattleUnit(List<Integer> composition, Point position) {
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		name = new DisplayStatus().getUnitName(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(0)).getRightActionImageName(), 4);
		}catch(Exception ignore) {
			//右武器を装備していないので、無視する
		}
		coreImage = new EditImage().input(new DefaultUnit().getCoreData(composition.get(1)).getActionImageName(), 4);
		this.position = position;
		initialPosition = position;
		type = StatusCalculation.getType();
		element = StatusCalculation.getRightElement();
		defaultWeaponStatus = StatusCalculation.getRightWeaponStatus();
		defaultUnitStatus = StatusCalculation.getUnitStatus();
		defaultCutStatus = StatusCalculation.getCutStatus();
		canActivate = false;
		super.initialize();
	}
	
	//左武器用
	protected BattleUnit(List<Integer> composition) {
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(2)).getLeftActionImageName(), 4);
		}catch(Exception ignore) {
			//左武器を装備していないので、無視する
		}
		element = StatusCalculation.getLeftElement();
		defaultWeaponStatus = StatusCalculation.getLeftWeaponStatus();
		defaultUnitStatus = StatusCalculation.getUnitStatus();
		defaultCutStatus = StatusCalculation.getCutStatus();
		super.initialize();
	}
	
	public BufferedImage getCoreImage(){
		return coreImage;
	}
	
	protected void activate(Point point) {
		canActivate = true;
		position = point;
	}
	
	protected void deactivate() {
		canActivate = false;
		position = initialPosition;
		super.initialize();
	}
	
	public int getType() {
		return type;
	}
	
	
	
	
}