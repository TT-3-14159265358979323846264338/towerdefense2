package savedata;

import static savedata.SaveComposition.*;
import static savedata.SaveGameProgress.*;
import static savedata.SaveHoldItem.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import defaultdata.DefaultStage;
import defaultdata.DefaultUnit;
import defaultdata.stage.StageData;

//データ保存用ファイルの確認
public class FileCheck{
	SaveHoldItem SaveHoldItem;
	SaveGameProgress SaveGameProgress;
	
	public FileCheck() {
		fileExistenceCheck();
		itemDataCheck();
		progressDataCheck();
	}
	
	//ファイルの存在確認
	private void fileExistenceCheck() {
		if(Files.notExists(Paths.get(HOLD_FILE)) || Files.notExists(Paths.get(COMPOSITION_FILE)) || Files.notExists(Paths.get(PROGRESS_FILE))) {
			try {
				ObjectOutputStream itemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(HOLD_FILE)));
				itemData.writeObject(new SaveHoldItem());
				itemData.close();
				ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(COMPOSITION_FILE)));
				compositionData.writeObject(new SaveComposition());
				compositionData.close();
				ObjectOutputStream gameProgress = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(PROGRESS_FILE)));
				gameProgress.writeObject(new SaveGameProgress());
				gameProgress.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//item dataのデータ数確認
	private void itemDataCheck() {
		BiConsumer<Integer, List<Integer>> addList = (count, list) -> {
			IntStream.range(0, count).forEach(i -> list.add(0));
		};
		//データのロード
		try {
			ObjectInputStream loadItemData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(HOLD_FILE)));
			SaveHoldItem = (SaveHoldItem) loadItemData.readObject();
			loadItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		//データ数を確認し、足りなければ追加
		List<Integer> coreNumberList = SaveHoldItem.getCoreNumberList();
		if(checkSize(DefaultUnit.CORE_SPECIES, coreNumberList.size())) {
			addList.accept(DefaultUnit.CORE_SPECIES - coreNumberList.size(), coreNumberList);
		}
		List<Integer> weaponNumberList = SaveHoldItem.getWeaponNumberList();
		if(checkSize(DefaultUnit.WEAPON_SPECIES, weaponNumberList.size())) {
			addList.accept(DefaultUnit.WEAPON_SPECIES - weaponNumberList.size(), weaponNumberList);
		}
		//データの保存
		try {
			ObjectOutputStream saveItemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(HOLD_FILE)));
			saveItemData.writeObject(new SaveHoldItem(coreNumberList, weaponNumberList));
			saveItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//progress dataのデータ数確認
	private void progressDataCheck() {
		//データのロード
		try {
			ObjectInputStream loadProgressData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(PROGRESS_FILE)));
			SaveGameProgress = (SaveGameProgress) loadProgressData.readObject();
			loadProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		//データ数を確認し、足りなければ追加
		List<Boolean> clearStatus = SaveGameProgress.getClearStatus();
		if(checkSize(DefaultStage.STAGE_SPECIES, clearStatus.size())) {
			IntStream.range(0, DefaultStage.STAGE_SPECIES - clearStatus.size()).forEach(i -> clearStatus.add(false));
		}
		List<List<Boolean>> meritStatus = SaveGameProgress.getMeritStatus();
		IntStream.range(0, DefaultStage.STAGE_SPECIES).forEach(i -> {
			StageData StageData = new DefaultStage().getStageData(i);
			try {
				//meritStatusの内側のListのデータ数を確認し、足りなければ追加
				if(checkSize(StageData.getMerit().size(), meritStatus.get(i).size())) {
					IntStream.range(0, StageData.getMerit().size() - meritStatus.get(i).size()).forEach(j -> meritStatus.get(i).add(false));
				}
			}catch(Exception e) {
				//エラーが起こる時はList<Boolean>の数が足りない時である
				//その時はList<Boolean>を追加
				meritStatus.add(StageData.getMerit().stream().map(j -> false).collect(Collectors.toList()));
			}
			});
		//データの保存
		try {
			ObjectOutputStream saveProgressData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(PROGRESS_FILE)));
			saveProgressData.writeObject(new SaveGameProgress(clearStatus, meritStatus, SaveGameProgress.getMedal(), SaveGameProgress.getSelectStage()));
			saveProgressData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkSize(int size1, int size2) {
		return size2 < size1;
	}
}