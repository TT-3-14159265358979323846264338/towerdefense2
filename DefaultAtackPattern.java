package defaultdata;

import defaultdata.atackpattern.*;

public class DefaultAtackPattern {
	//パターンの種類
	public final static int PATTERN_SPECIES = 2;
	
	public AtackPattern getAtackPattern(int number) {
		switch(number) {
		case 0:
			return new No00Near();
		case 1:
			return new No01Far();
		default:
			return null;
		}
		/*
		新たなデータを追加したらPATTERN_SPECIESにも加算すること
		 */
	}
}