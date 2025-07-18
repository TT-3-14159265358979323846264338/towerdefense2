package defaultdata;

import defaultdata.atackpattern.*;

public class DefaultAtackPattern {
	//パターンの種類
	public final static int PATTERN_SPECIES = 2;
	
	//コード名
	public final static int NEAR = 0;
	public final static int FAR = 1;
	
	//コードの振り分け(戦闘時、各キャラに独自のAtackPatternクラスを搭載するため、毎回新インスタンスを生成する必要がある)
	public AtackPattern getAtackPattern(int code) {
		switch(code) {
		case 0:
			return new No00Near();
		case 1:
			return new No01Far();
		default:
			return null;
		}
	}
	
	/*
	新たなデータを追加したらPATTERN_SPECIESにも加算すること
	 */
}