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
	public List<String> getImageName() {
		return Arrays.asList("image/field/stage 1-1.png",
				"image/field/stage 1-2.png",
				"image/field/stage 1-3.png",
				"image/field/stage 1-4.png");
	}

	@Override
	public List<List<Boolean>> getGateMode(){
		return Arrays.asList(
				Arrays.asList(true, true, true),
				Arrays.asList(false, true, true),
				Arrays.asList(false, false, true),
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
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Integer> getDisplayOrder() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<List<List<Integer>>> getMove() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}