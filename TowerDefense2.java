package towerdefense2;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import mainframe.MainFrame;
import savecomposition.SaveComposition;
import saveholditem.SaveHoldItem;

public class TowerDefense2 {
	public static void main(String[] args) {
		new FileCheck();
		new MainFrame();
	}
}

//データ保存用ファイルの存在確認
class FileCheck{
	protected FileCheck() {
		if(Files.notExists(Paths.get(SaveHoldItem.HOLD_FILE)) || Files.notExists(Paths.get(SaveComposition.COMPOSITION_FILE))) {
			try {
				ObjectOutputStream itemData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SaveHoldItem.HOLD_FILE)));
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
}