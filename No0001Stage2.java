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
		return Arrays.asList(new Point(430, 20), new Point(450, 260), new Point(190, 80), new Point(715, 80));
	}

	@Override
	public List<Point> getNearPoint() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Point> getFarPoint() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Point> getAllPoint() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
				Arrays.asList(0, 0, 100),
				Arrays.asList(0, 1, 100),
				Arrays.asList(1, 0, 200),
				Arrays.asList(1, 1, 200),
				Arrays.asList(0, 3, 400),
				Arrays.asList(0, 4, 400));
	}

	@Override
	public List<Integer> getDisplayOrder() {
		return Arrays.asList(0, 1);
	}

	@Override
	public List<List<List<Integer>>> getMove() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}