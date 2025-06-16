package defaultdata.stage;

import java.util.Arrays;
import java.util.List;

public class No0001Stage2 extends StageData{
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
	public List<String> getMerit() {
		return Arrays.asList("ステージをクリアする(normal)",
				"ユニットが一度も倒されずクリアする(normal)",
				"ステージをクリアする(hard)",
				"ユニットも城門も破壊されずクリアする(hard)");
	}
}