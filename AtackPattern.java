package defaultdata.atackpattern;

import java.util.List;

import battle.BattleData;

public abstract class AtackPattern {
	BattleData myself;
	List<BattleData[]> candidate;
	
	public void install(BattleData myself, List<BattleData[]> candidate) {
		this.myself = myself;
		this.candidate = candidate;
	}
	
	//攻撃パターン説明
	public abstract String getExplanation();
	
	//targetになる相手のBattleDataを返す
	public abstract List<BattleData> getTarget();
}