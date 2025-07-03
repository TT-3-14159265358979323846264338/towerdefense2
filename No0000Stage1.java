package defaultdata.stage;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class No0000Stage1 extends StageData{
	@Override
	public String getName() {
		return "stage 1";
	}

	@Override
	public String getImageName() {
		return "image/field/stage 1.png";
	}

	@Override
	public List<Integer> getFacility() {
		return Arrays.asList(0, 1, 1, 1);
	}

	@Override
	public List<Boolean> getFacilityDirection() {
		return Arrays.asList(true, false, false, true);
	}

	@Override
	public List<Point> getFacilityPoint() {
		return Arrays.asList(new Point(530, 10), new Point(310, 320), new Point(660, 430), new Point(910, 170));
	}

	@Override
	public List<List<List<Double>>> getPlacementPoint() {
		double size = 29.5;
		double centerX = 483;
		double centerY = 265;
		return Arrays.asList(
				Arrays.asList(Arrays.asList(centerX - size * 3, centerY + size * 3),
						Arrays.asList(centerX + size, centerY + size * 3),
						Arrays.asList(centerX + size * 5, centerY + size * 3),
						Arrays.asList(centerX + size * 9, centerY + size * 3),
						
						Arrays.asList(centerX - size * 3, centerY + size * 7),
						Arrays.asList(centerX + size, centerY + size * 7),
						Arrays.asList(centerX + size * 5, centerY + size * 7),
						Arrays.asList(centerX + size * 9, centerY + size * 7),
						
						Arrays.asList(centerX + size * 11, centerY + size),
						Arrays.asList(centerX + size * 15, centerY + size)),
				Arrays.asList(
						Arrays.asList(centerX - size * 3, (double) centerY),
						Arrays.asList(centerX + size, (double) centerY),
						Arrays.asList(centerX + size * 5, (double) centerY),
						
						Arrays.asList(centerX + size * 7, centerY + size * 3),
						Arrays.asList(centerX + size * 11, centerY + size * 3),
						Arrays.asList(centerX + size * 15, centerY + size * 3),
						
						Arrays.asList(centerX - size * 5, centerY + size * 7),
						Arrays.asList(centerX + size * 11, centerY + size * 7),
						
						Arrays.asList(centerX + size * 7, centerY - size * 2),
						Arrays.asList(centerX + size * 11, centerY - size * 2)),
				Arrays.asList(
						Arrays.asList(centerX + size * 7, centerY - size * 7),
						Arrays.asList(centerX + size * 11, centerY - size * 7),
						Arrays.asList(centerX + size * 15, centerY - size * 7),
						
						Arrays.asList(centerX + size * 7, centerY - size * 5),
						Arrays.asList(centerX + size * 11, centerY - size * 5),
						Arrays.asList(centerX + size * 15, centerY - size * 5)));
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
				Arrays.asList(1, 1, 1000),
				Arrays.asList(0, 2, 2000),
				Arrays.asList(1, 3, 2000)
				);
	}

	@Override
	public List<Integer> getDisplayOrder() {
		return Arrays.asList(0, 1);
	}
	
	@Override
	public List<List<List<Integer>>> getRoute() {
		return Arrays.asList(
				Arrays.asList(
						Arrays.asList(30, 500, 0, 0),
						Arrays.asList(30, 310, 100, 0),
						Arrays.asList(30, 310, 4, 0),
						Arrays.asList(610, 310, 8, 0),
						Arrays.asList(610, 440, 4, 0),
						Arrays.asList(725, 440, 0, 0),
						Arrays.asList(725, 260, 4, 0),
						Arrays.asList(920, 260, 0, 0),
						Arrays.asList(920, 90, 12, 0)
						),
				Arrays.asList(
						Arrays.asList(30, 510, 0, 0),
						Arrays.asList(30, 320, 100, 0),
						Arrays.asList(30, 320, 4, 0),
						Arrays.asList(370, 320, 8, 0),
						Arrays.asList(370, 450, 4, 0),
						Arrays.asList(725, 450, 0, 0),
						Arrays.asList(725, 260, 4, 0),
						Arrays.asList(910, 260, 0, 0),
						Arrays.asList(910, 25, 12, 0)
						),
				Arrays.asList(
						Arrays.asList(0, 510, 0, 0),
						Arrays.asList(0, 290, 2, 100),
						Arrays.asList(100, 190, 100, 100),
						Arrays.asList(100, 190, 1, 0),
						Arrays.asList(125, 140, 2, 0),
						Arrays.asList(175, 90, 3, 0),
						Arrays.asList(225, 65, 4, 0),
						Arrays.asList(275, 65, 5, 0),
						Arrays.asList(325, 90, 6, 0),
						Arrays.asList(475, 240, 5, 0),
						Arrays.asList(595, 300, 4, 0),
						Arrays.asList(650, 300, 3, 0),
						Arrays.asList(700, 275, 2, 0),
						Arrays.asList(750, 225, 1, 0),
						Arrays.asList(775, 175, 0, 0)
						
						
						),
				Arrays.asList(
						Arrays.asList(900, 510, 0, 0),
						Arrays.asList(900, 440, 200, 0),
						Arrays.asList(900, 440, 14, 0),
						Arrays.asList(660, 200, 200, 0),
						Arrays.asList(660, 200, 14, 0)
						)
				);
	}
}

/*
テスト用enemy
		return Arrays.asList(
				Arrays.asList(0, 0, 1000),
				Arrays.asList(0, 1, 1000),
				Arrays.asList(1, 0, 2000),
				Arrays.asList(1, 1, 2000)
				);
テスト用route
		return Arrays.asList(
				Arrays.asList(
						Arrays.asList(0, 0, 4, 0),
						Arrays.asList(0, 100, 2, 0),
						Arrays.asList(100, 100, 100, 0),
						Arrays.asList(100, 100, 2, 0),
						Arrays.asList(200, 100, 200, 100),
						Arrays.asList(200, 100, 0, 0),
						Arrays.asList(200, 0, 6, 100),
						Arrays.asList(100, 0, 4, 0),
						Arrays.asList(100, 100, 5, 0)),
				Arrays.asList(
						Arrays.asList(0, 0, 2, 0),
						Arrays.asList(100, 0, 4, 0),
						Arrays.asList(100, 100, 2, 100),
						Arrays.asList(200, 100, 4, 100),
						Arrays.asList(200, 200, 4, 0),
						Arrays.asList(200, 200, 7, 0)
						)
				);
*/