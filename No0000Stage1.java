package defaultdata.stage;

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
	public List<String> getMerit() {
		return Arrays.asList("ステージをクリアする(normal)",
				"ユニットが一度も倒されずクリアする(normal)",
				"ステージをクリアする(hard)",
				"ユニットも城門も破壊されずクリアする(hard)");
	}
}