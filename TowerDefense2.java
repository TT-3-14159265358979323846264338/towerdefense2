package towerdefense2;

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
import java.util.function.BiPredicate;

import defaultdata.DefaultData;
import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

public class TowerDefense2 {
	public static void main(String[] args) {
		new FileCheck();
		new MainFrame();
	}
}

//データ保存用ファイルの確認
class FileCheck{
	SaveHoldItem SaveHoldItem;
	
	protected FileCheck() {
		fileExistenceCheck();
		internalDataCheck();
	}
	
	private void fileExistenceCheck() {
		if(Files.notExists(Paths.get(saveholditem.SaveHoldItem.HOLD_FILE)) || Files.notExists(Paths.get(SaveComposition.COMPOSITION_FILE))) {
			try {
				ObjectOutputStream itemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
				itemData.writeObject(new SaveHoldItem());
				itemData.close();
				ObjectOutputStream compositionData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SaveComposition.COMPOSITION_FILE)));
				compositionData.writeObject(new SaveComposition());
				compositionData.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void internalDataCheck() {
		BiPredicate<Integer, Integer> check = (size1, size2) -> {
			return size1 != size2;
		};
		BiConsumer<Integer, List<Integer>> addList = (count, list) -> {
			for(int i = 0; i < count; i++) {
				list.add(0);
			}
		};
		try {
			ObjectInputStream loadItemData = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			SaveHoldItem = (SaveHoldItem) loadItemData.readObject();
			loadItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		List<Integer> coreNumberList = SaveHoldItem.getCoreNumberList();
		if(check.test(DefaultData.CORE_NAME_LIST.size(), coreNumberList.size())) {
			addList.accept(DefaultData.CORE_NAME_LIST.size() - coreNumberList.size(), coreNumberList);
		}
		List<Integer> weaponNumberList = SaveHoldItem.getWeaponNumberList();
		if(check.test(DefaultData.WEAPON_NAME_LIST.size(), weaponNumberList.size())) {
			addList.accept(DefaultData.WEAPON_NAME_LIST.size() - weaponNumberList.size(), weaponNumberList);
		}
		try {
			ObjectOutputStream saveItemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveholditem.SaveHoldItem.HOLD_FILE)));
			saveItemData.writeObject(new SaveHoldItem(coreNumberList, weaponNumberList));
			saveItemData.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}