package battle;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import defaultdata.DefaultStage;
import defaultdata.EditImage;
import defaultdata.facility.FacilityData;
import defaultdata.stage.StageData;

//設備のバトル情報
public class BattleFacility extends BattleData{
	BufferedImage breakImage;
	
	protected BattleFacility(Battle Battle, StageData StageData, int number) {
		this.Battle = Battle;
		waitObject = Battle.getWaitObject();
		FacilityData FacilityData = new DefaultStage().getFacilityData(StageData.getFacility().get(number));
		name = FacilityData.getName();
		actionImage = new EditImage().input(StageData.getFacilityDirection().get(number)? FacilityData.getActionFrontImageName(): FacilityData.getActionSideImageName(), 4);
		breakImage = new EditImage().input(FacilityData.getBreakImageName(), 4);
		position = StageData.getFacilityPoint().get(number);
		element = FacilityData.getElement().stream().toList();
		if(FacilityData.getWeaponStatus() == null || FacilityData.getWeaponStatus().isEmpty()) {
			defaultWeaponStatus = IntStream.range(0, DefaultStage.WEAPON_MAP.size()).mapToObj(i -> 0).toList();
		}else {
			defaultWeaponStatus = FacilityData.getWeaponStatus().stream().toList();
		}
		defaultUnitStatus = FacilityData.getUnitStatus().stream().toList();
		defaultCutStatus = FacilityData.getCutStatus().stream().toList();
		canActivate = true;
		super.initialize();
	}
	
	protected BufferedImage getBreakImage() {
		return breakImage;
	}
	
	
	
	
	
	
	
	
	
	
	
}