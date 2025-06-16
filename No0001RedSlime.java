package defaultdata.enemy;

import java.util.Arrays;
import java.util.List;

public class No0001RedSlime extends EnemyData{
	@Override
	public String getName() {
		return "レッドスライム";
	}

	@Override
	public String getImageName() {
		return "image/enemy/red slime.png";
	}

	@Override
	public List<String> getActionImageName() {
		return Arrays.asList("image/enemy/red slime 0.png",
				"image/enemy/red slime 1.png",
				"image/enemy/red slime 2.png",
				"image/enemy/red slime 3.png",
				"image/enemy/red slime 4.png",
				"image/enemy/red slime 5.png");
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
	public List<Integer> getWeaponStatus() {
		return Arrays.asList(30, 30, 1000, 1);
	}

	@Override
	public List<Integer> getUnitStatus() {
		return Arrays.asList(1000, 1000, 10, 0, 100, 1);
	}

	@Override
	public List<Integer> getCutStatus() {
		return Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
}
