package defaultdata.stage;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class No0001Stage2 extends StageData {
	@Override
	public String getName() {
		return "stage 2";
	}

	@Override
	public String getImageName() {
		return "image/field/stage 2.png";
	}

	@Override
	public List<Integer> getFacility() {
		return Arrays.asList(0, 1, 1, 1);
	}

	@Override
	public List<Boolean> getFacilityDirection() {
		return Arrays.asList(true, true, false, false);
	}

	@Override
	public List<Point> getFacilityPoint() {
		return Arrays.asList(new Point(430, 20), new Point(450, 260), new Point(190, 80), new Point(720, 80));
	}

	@Override
	public List<List<List<Double>>> getPlacementPoint() {
		double size = 29.5;
		double centerX = 483;
		double centerY = 265;
		return Arrays.asList(
				Arrays.asList(Arrays.asList(centerX - size * 7, centerY - size * 5),
						Arrays.asList(centerX - size * 5, centerY - size * 5),
						Arrays.asList(centerX + size * 5, centerY - size * 5),
						Arrays.asList(centerX + size * 7, centerY - size * 5),
						
						Arrays.asList(centerX - size, centerY - size),
						Arrays.asList(centerX + size, centerY - size)),
				Arrays.asList(Arrays.asList(centerX - size * 9, centerY - size * 8),
						Arrays.asList(centerX + size * 9, centerY - size * 8),
						
						Arrays.asList(centerX - size * 6, centerY - size * 7),
						Arrays.asList(centerX - size * 4, centerY - size * 7),
						Arrays.asList(centerX + size * 6, centerY - size * 7),
						Arrays.asList(centerX + size * 4, centerY - size * 7),
						
						Arrays.asList(centerX - size * 9, centerY - size * 3),
						Arrays.asList(centerX - size * 5, centerY - size * 3),
						Arrays.asList(centerX - size * 3, centerY - size * 3),
						Arrays.asList(centerX + size * 9, centerY - size * 3),
						Arrays.asList(centerX + size * 5, centerY - size * 3),
						Arrays.asList(centerX + size * 3, centerY - size * 3),
						
						Arrays.asList(centerX - size * 9, centerY - size),
						Arrays.asList(centerX + size * 9, centerY - size),
						
						Arrays.asList(centerX - size * 7, centerY + size),
						Arrays.asList(centerX - size * 5, centerY + size),
						Arrays.asList(centerX - size * 3, centerY + size),
						Arrays.asList(centerX + size * 7, centerY + size),
						Arrays.asList(centerX + size * 5, centerY + size),
						Arrays.asList(centerX + size * 3, centerY + size)),
				Arrays.asList(Arrays.asList(centerX - size * 3, centerY - size * 5),
						Arrays.asList(centerX + size * 3, centerY - size * 5),
						
						Arrays.asList(centerX - size, centerY - size * 3),
						Arrays.asList(centerX + size, centerY - size * 3)
						));
	}

	@Override
	public List<String> getMerit() {
		return Arrays.asList("ステージをクリアする(normal)",
				"ユニットが一度も倒されずクリアする(normal)",
				"ステージをクリアする(hard)",
				"ユニットも城門も破壊されずクリアする(hard)");
	}

	@Override
	public List<List<Integer>> getEnemy() {
		return Arrays.asList(
				Arrays.asList(0, 0, 1000),
				Arrays.asList(0, 1, 1000),
				Arrays.asList(1, 0, 2000),
				Arrays.asList(1, 1, 2000),
				Arrays.asList(0, 0, 3000),
				Arrays.asList(0, 1, 3000));
	}

	@Override
	public List<Integer> getDisplayOrder() {
		return Arrays.asList(0, 1);
	}

	@Override
	public List<List<List<Integer>>> getRoute() {
		return Arrays.asList(
				Arrays.asList(
						Arrays.asList(0, 0, 4, 0),
						Arrays.asList(0, 100, 2, 0),
						Arrays.asList(100, 100, 4, 0),
						Arrays.asList(100, 200, 2, 0)
						),
				Arrays.asList(
						Arrays.asList(0, 0, 2, 0),
						Arrays.asList(100, 0, 4, 0),
						Arrays.asList(100, 100, 4, 0),
						Arrays.asList(100, 200, 1, 0)
						)
				);
	}
}