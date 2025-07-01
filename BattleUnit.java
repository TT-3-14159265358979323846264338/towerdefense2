package battle;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
	protected BattleUnit(Battle Battle, List<Integer> composition, Point position) {
		this.Battle = Battle;
		waitObject = Battle.getWaitObject();
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		name = new DisplayStatus().getUnitName(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(0)).getRightActionImageName(), 4);
		}catch(Exception e) {
			actionImage = Arrays.asList(getBlankImage());
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
	protected BattleUnit(Battle Battle, List<Integer> composition) {
		this.Battle = Battle;
		StatusCalculation StatusCalculation = new StatusCalculation(composition);
		try {
			actionImage = new EditImage().input(new DefaultUnit().getWeaponData(composition.get(2)).getLeftActionImageName(), 4);
		}catch(Exception e) {
			actionImage = Arrays.asList(getBlankImage());
		}
		element = StatusCalculation.getLeftElement();
		defaultWeaponStatus = StatusCalculation.getLeftWeaponStatus();
		defaultUnitStatus = StatusCalculation.getUnitStatus();
		defaultCutStatus = StatusCalculation.getCutStatus();
		super.initialize();
	}
	
	private BufferedImage getBlankImage() {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, 0);
		return image;
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