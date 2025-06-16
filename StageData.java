package defaultdata.stage;

import java.util.List;

public abstract class StageData {
	//ステージの名前
	public abstract String getName();
	
	//ステージ画像ファイル名
	public abstract List<String> getImageName();
	
	//戦功内容　実際の表示では"("で改行が入るため"("の前のスペース禁止
	public abstract List<String> getMerit();
}
