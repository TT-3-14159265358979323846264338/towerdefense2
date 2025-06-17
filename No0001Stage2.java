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
	public List<String> getImageName() {
		return Arrays.asList("image/field/stage 2-1.png",
				"image/field/stage 2-2.png",
				"image/field/stage 2-3.png",
				"image/field/stage 2-4.png",
				"image/field/stage 2-5.png",
				"image/field/stage 2-6.png",
				"image/field/stage 2-7.png",
				"image/field/stage 2-8.png");
	}

	@Override
	public List<List<Boolean>> getGateMode() {
		return Arrays.asList(
				Arrays.asList(true, true, true),
				Arrays.asList(false, true, true),
				Arrays.asList(true, false, true),
				Arrays.asList(true, true, false),
				Arrays.asList(false, false, true),
				Arrays.asList(false, true, false),
				Arrays.asList(true, false, false),
				Arrays.asList(false, false, false));
	}

	@Override
	public List<Integer> getFacility() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Point> getFacilityPoint() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
				Arrays.asList(0, 4, 400)
				);
	}

	@Override
	public List<Integer> getDisplayOrder() {
		return Arrays.asList(1, 0);
	}

	@Override
	public List<List<List<Integer>>> getMove() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}