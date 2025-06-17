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
		return Arrays.asList(new Point(530, 10), new Point(310, 320), new Point(660, 430), new Point(910, 160));
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
				Arrays.asList(1, 1, 200)
				);
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