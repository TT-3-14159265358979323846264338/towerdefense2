package defaultdata.weapon;

import java.util.Arrays;
import java.util.List;

import defaultdata.DefaultAtackPattern;

public class No0000JapaneseSword extends WeaponData{
	@Override
	public String getName() {
		return "日本刀";
	}

	@Override
	public String getImageName() {
		return "image/soldier/Japanese sword.png";
	}

	@Override
	public List<String> getRightActionImageName() {
		return Arrays.asList("image/soldier/Japanese sword right 0.png",
				"image/soldier/Japanese sword right 1.png",
				"image/soldier/Japanese sword right 2.png",
				"image/soldier/Japanese sword right 3.png",
				"image/soldier/Japanese sword right 4.png",
				"image/soldier/Japanese sword right 5.png");
	}

	@Override
	public List<String> getLeftActionImageName() {
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
	public int getDistance() {
		return 0;
	}

	@Override
	public int getHandle() {
		return 0;
	}

	@Override
	public List<Integer> getElement() {
		return Arrays.asList(0);
	}

	@Override
	public int getAtackPattern() {
		return DefaultAtackPattern.NEAR;
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return Arrays.asList(100, 30, 1000, 3);
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(1000, 1000, 100, 10, 1, 5);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}
