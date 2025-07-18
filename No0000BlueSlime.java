package defaultdata.enemy;

import java.util.Arrays;
import java.util.List;

import defaultdata.DefaultAtackPattern;

public class No0000BlueSlime extends EnemyData{
	@Override
	public String getName() {
		return "ブルースライム";
	}

	@Override
	public String getImageName() {
		return "image/enemy/blue slime.png";
	}

	@Override
	public List<String> getActionImageName() {
		return Arrays.asList("image/enemy/blue slime 0.png",
				"image/enemy/blue slime 1.png",
				"image/enemy/blue slime 2.png",
				"image/enemy/blue slime 3.png",
				"image/enemy/blue slime 4.png",
				"image/enemy/blue slime 5.png");
	}

	@Override
	public int getMove() {
		return 0;
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public List<Integer> getElement() {
		return Arrays.asList(2);
	}

	@Override
	public int getAtackPattern() {
		return DefaultAtackPattern.NEAR;
	}

	@Override
	public List<Integer> getWeaponStatus() {
		return Arrays.asList(20, 30, 1000, 1);
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(1000, 1000, 10, 0, 100, 1);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}
