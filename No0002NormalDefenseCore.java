package defaultdata.core;

import java.util.Arrays;
import java.util.List;

public class No0002NormalDefenseCore extends CoreData{
	@Override
	public String getCoreName() {
		return "ノーマルブラックコア";
	}
	
	@Override
	public String getCoreImageName() {
		return "image/soldier/normal defense core.png";
	}
	
	@Override
	public String getCoreActionImageName() {
		return "image/soldier/normal defense core center.png";
	}
	
	@Override
	public int getRarity() {
		return 1;
	}
	
	@Override
	public List<Double> getWeaponStatus(){
		return Arrays.asList(1.0, 1.0, 1.0, 1.0);
	}
	
	@Override
	public List<Double> getUnitStatus(){
		return Arrays.asList(1.0, 1.0, 1.1, 1.0, 1.0, 1.0);
	}
	
	@Override
	public List<Integer> getCutStatus(){
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}