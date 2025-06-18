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
	public List<List<Point>> getPlacementPoint() {
		double size = 29.5;
		Point center = new Point(483, 265);
		return Arrays.asList(
				Arrays.asList(new Point((int) (center.x - size * 3), (int) (center.y + size * 3)),
						new Point((int) (center.x + size), (int) (center.y + size * 3)),
						new Point((int) (center.x + size * 5), (int) (center.y + size * 3)),
						new Point((int) (center.x + size * 9), (int) (center.y + size * 3)),
						
						new Point((int) (center.x - size * 3), (int) (center.y + size * 7)),
						new Point((int) (center.x + size), (int) (center.y + size * 7)),
						new Point((int) (center.x + size * 5), (int) (center.y + size * 7)),
						new Point((int) (center.x + size * 9), (int) (center.y + size * 7)),
						
						new Point((int) (center.x + size * 11), (int) (center.y + size)),
						new Point((int) (center.x + size * 15), (int) (center.y + size))),
				Arrays.asList(
						new Point((int) (center.x - size * 3), (int) (center.y)),
						new Point((int) (center.x + size), (int) (center.y)),
						new Point((int) (center.x + size * 5), (int) (center.y)),
						
						new Point((int) (center.x + size * 7), (int) (center.y + size * 3)),
						new Point((int) (center.x + size * 11), (int) (center.y + size * 3)),
						new Point((int) (center.x + size * 15), (int) (center.y + size * 3)),
						
						new Point((int) (center.x - size * 5), (int) (center.y + size * 7)),
						new Point((int) (center.x + size * 11), (int) (center.y + size * 7)),
						
						new Point((int) (center.x + size * 7), (int) (center.y - size * 2)),
						new Point((int) (center.x + size * 11), (int) (center.y - size * 2))),
				Arrays.asList(
						new Point((int) (center.x + size * 7), (int) (center.y - size * 7)),
						new Point((int) (center.x + size * 11), (int) (center.y - size * 7)),
						new Point((int) (center.x + size * 15), (int) (center.y - size * 7)),
						
						new Point((int) (center.x + size * 7), (int) (center.y - size * 5)),
						new Point((int) (center.x + size * 11), (int) (center.y - size * 5)),
						new Point((int) (center.x + size * 15), (int) (center.y - size * 5))));
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