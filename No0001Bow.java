package defaultdata.weapon;

import java.util.Arrays;
import java.util.List;

public class No0001Bow extends WeaponData{
	@Override
	public String getName() {
		return "弓";
	}

	@Override
	public String getImageName() {
		return "image/soldier/bow.png";
	}

	@Override
	public List<String> getRightActionImageName() {
		return Arrays.asList();
	}

	@Override
	public List<String> getLeftActionImageName() {
		return Arrays.asList("image/soldier/bow left 0.png",
				"image/soldier/bow left 1.png",
				"image/soldier/bow left 2.png",
				"image/soldier/bow left 3.png",
				"image/soldier/bow left 4.png",
				"image/soldier/bow left 5.png");
	}

	@Override
	public int getRarity() {
		return 1;
	}

	@Override
	public int getDistance() {
		return 1;
	}

	@Override
	public int getHandle() {
		return 1;
	}

	@Override
	public List<Integer> getElement() {
		return Arrays.asList(1);
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return Arrays.asList(200, 150, 1000, 1);
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(1000, 1000, 100, 10, 0, 10);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}
