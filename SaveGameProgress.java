package savedata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveGameProgress implements Serializable{
	public transient final static String PROGRESS_FILE = "progress data.dat";
	List<Boolean> clearStatus = new ArrayList<>();
	List<List<Boolean>> meritStatus = new ArrayList<>();
	int medal;
	int selectStage;
	
	/*
	非セーブデータの構造
	PROGRESS_FILE: セーブデータ保存ファイルの名称
	
	セーブデータの構造
	clearStatus: 各ステージのクリア状況
	meritStatus: 各ステージの戦功取得状況
	これらのListのsize()は、DataStageに新規追加されると、TowerDefense2で自動的に追加される
	medal: 現在保有しているガチャメダル数
	selectStage: 最後に出撃したステージ番号
	*/
	
	public SaveGameProgress() {
		medal = 1000;
		selectStage = 0;
	}
	
	public SaveGameProgress(List<Boolean> clearStatus, List<List<Boolean>> meritStatus, int medal, int selectStage) {
		this.clearStatus = clearStatus;
		this.meritStatus = meritStatus;
		this.medal = medal;
		this.selectStage = selectStage;
	}
	
	public List<Boolean> getClearStatus(){
		return clearStatus;
	}
	
	public List<List<Boolean>> getMeritStatus(){
		return meritStatus;
	}
	
	public int getMedal() {
		return medal;
	}
	
	public int getSelectStage() {
		return selectStage;
	}
}