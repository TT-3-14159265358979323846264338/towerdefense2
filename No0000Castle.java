package defaultdata.facility;

import java.util.Arrays;
import java.util.List;

public class No0000Castle extends FacilityData{
	@Override
	public String getName() {
		return "本丸";
	}

	@Override
	public String getFrontImageName() {
		return "image/field/castle.png";
	}

	@Override
	public String getSideImageName() {
		return "image/field/castle.png";
	}

	@Override
	public String getBreakImagename() {
		return "image/field/castle.png";
	}

	@Override
	public List<String> getActionImageName() {
		return null;
	}

	@Override
	public List<Integer> getElement() {
		return null;
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return null;
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(10000, 10000, 0, 0, -1);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}