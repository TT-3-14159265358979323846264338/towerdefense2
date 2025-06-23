package defaultdata.facility;

import java.util.Arrays;
import java.util.List;

public class No0001Gate extends FacilityData{
	@Override
	public String getName() {
		return "城門";
	}

	@Override
	public List<String> getActionFrontImageName() {
		return Arrays.asList("image/field/front gate.png");
	}

	@Override
	public List<String> getActionSideImageName() {
		return Arrays.asList("image/field/side gate.png");
	}

	@Override
	public String getBreakImageName() {
		return "image/field/break gate.png";
	}

	@Override
	public List<Integer> getElement() {
		return Arrays.asList();
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return Arrays.asList();
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(2000, 2000, 0, 0, -1);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}