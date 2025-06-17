package defaultdata.stage;

import java.awt.Point;
import java.util.List;

public abstract class StageData {
	//ステージの名前
	public abstract String getName();
	
	//ステージ画像ファイル名
	public abstract String getImageName();
	
	//設備の種類番号
	public abstract List<Integer> getFacility();
	
	//設備の向き　前向きをtrue, 横向きをfalse
	public abstract List<Boolean> getFacilityDirection();
	
	//設備の位置
	public abstract List<Point> getFacilityPoint();
	
	//配置位置
	public abstract List<Point> getNearPoint();
	public abstract List<Point> getFarPoint();
	public abstract List<Point> getAllPoint();
	
	//戦功内容　実際の表示では"("で改行が入るため"("の前のスペース禁止
	public abstract List<String> getMerit();
	
	//敵情報　敵番号, 移動番号, 出撃タイミング の順にリスト化
	public abstract List<List<Integer>> getEnemy();
	
	//敵情報表示順
	public abstract List<Integer> getDisplayOrder();
	
	//移動情報　①List: 移動番号, ②List: 移動経路(位置x, 位置y, 移動方向番号, 描写可否番号, 停止時間) の順にリスト化
	public abstract List<List<List<Integer>>> getMove();
}
