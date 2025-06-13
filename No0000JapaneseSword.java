package defaultdata.weapon;

import java.util.Arrays;
import java.util.List;

public class No0000JapaneseSword extends WeaponData{
	@Override
	public String getWeaponName() {
		return "日本刀";
	}

	@Override
	public String getWeaponImageName() {
		return "image/soldier/Japanese sword.png";
	}

	@Override
	public List<String> getRightWeaponActionImageName() {
		return Arrays.asList("image/soldier/Japanese sword right 0.png",
				"image/soldier/Japanese sword right 1.png",
				"image/soldier/Japanese sword right 2.png",
				"image/soldier/Japanese sword right 3.png",
				"image/soldier/Japanese sword right 4.png",
				"image/soldier/Japanese sword right 5.png");
	}

	@Override
	public List<String> getLeftWeaponActionImageName() {
		return Arrays.asList("image/soldier/Japanese sword left 0.png",
				"image/soldier/Japanese sword left 1.png",
				"image/soldier/Japanese sword left 2.png",
				"image/soldier/Japanese sword left 3.png",
				"image/soldier/Japanese sword left 4.png",
				"image/soldier/Japanese sword left 5.png");
	}

	@Override
	public int getRarity() {
		return 1;
	}

	@Override
	public List<Integer> getType() {
		return Arrays.asList(0, 0);
	}

	@Override
	public List<Integer> getElement() {
		return Arrays.asList(0);
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return Arrays.asList(100, 30, 1000, 1);
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(1000, 1000, 100, 10, 1, 5);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}
