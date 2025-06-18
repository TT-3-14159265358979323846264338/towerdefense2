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
	public List<List<Point>> getPlacementPoint() {
		double size = 29.5;
		Point center = new Point(483, 265);
		return Arrays.asList(
				Arrays.asList(new Point((int) (center.x - size * 7), (int) (center.y - size * 5)),
						new Point((int) (center.x - size * 5), (int) (center.y - size * 5)),
						new Point((int) (center.x + size * 5), (int) (center.y - size * 5)),
						new Point((int) (center.x + size * 7), (int) (center.y - size * 5)),
						
						new Point((int) (center.x - size), (int) (center.y - size)),
						new Point((int) (center.x + size), (int) (center.y - size))),
				Arrays.asList(new Point((int) (center.x - size * 9), (int) (center.y - size * 8)),
						new Point((int) (center.x + size * 9), (int) (center.y - size * 8)),
						
						new Point((int) (center.x - size * 6), (int) (center.y - size * 7)),
						new Point((int) (center.x - size * 4), (int) (center.y - size * 7)),
						new Point((int) (center.x + size * 6), (int) (center.y - size * 7)),
						new Point((int) (center.x + size * 4), (int) (center.y - size * 7)),
						
						new Point((int) (center.x - size * 9), (int) (center.y - size * 3)),
						new Point((int) (center.x - size * 5), (int) (center.y - size * 3)),
						new Point((int) (center.x - size * 3), (int) (center.y - size * 3)),
						new Point((int) (center.x + size * 9), (int) (center.y - size * 3)),
						new Point((int) (center.x + size * 5), (int) (center.y - size * 3)),
						new Point((int) (center.x + size * 3), (int) (center.y - size * 3)),
						
						new Point((int) (center.x - size * 9), (int) (center.y - size)),
						new Point((int) (center.x + size * 9), (int) (center.y - size)),
						
						new Point((int) (center.x - size * 7), (int) (center.y + size)),
						new Point((int) (center.x - size * 5), (int) (center.y + size)),
						new Point((int) (center.x - size * 3), (int) (center.y + size)),
						new Point((int) (center.x + size * 7), (int) (center.y + size)),
						new Point((int) (center.x + size * 5), (int) (center.y + size)),
						new Point((int) (center.x + size * 3), (int) (center.y + size))),
				Arrays.asList(new Point((int) (center.x - size * 3), (int) (center.y - size * 5)),
						new Point((int) (center.x + size * 3), (int) (center.y - size * 5)),
						
						new Point((int) (center.x - size), (int) (center.y - size * 3)),
						new Point((int) (center.x + size), (int) (center.y - size * 3))
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