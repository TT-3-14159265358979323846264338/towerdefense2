package statuscomment;

import static javax.swing.JOptionPane.*;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;

import javax.swing.ImageIcon;

import defaultdata.DefaultData;

public class StatusComment {
	public void coreStatus(int selectNumber, List<BufferedImage> imageList) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (35 - comment.getBytes().length) + "s", comment);
		};
		List<Double> weaponStatusList = DefaultData.CORE_WEAPON_STATUS_LIST.get(selectNumber);
		List<Double> unitStatusList = DefaultData.CORE_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【" + DefaultData.CORE_NAME_LIST.get(selectNumber) + "】" + "\n"
					+ "\n"
					+ "【武器ステータス倍率】\n"
					+ editComment.apply("攻撃倍率:") + weaponStatusList.get(0) + "倍\n"
					+ editComment.apply("射程倍率:") + weaponStatusList.get(1) + "倍\n"
					+ editComment.apply("攻撃速度倍率:") + weaponStatusList.get(2) + "倍\n"
					+ "\n"
					+ "【ユニットステータス倍率】\n"
					+ editComment.apply("体力倍率:") + unitStatusList.get(0) + "倍\n"
					+ editComment.apply("防御倍率:") + unitStatusList.get(1) + "倍\n"
					+ editComment.apply("回復倍率:") + unitStatusList.get(2) + "倍\n"
					+ editComment.apply("足止め数倍率:") + unitStatusList.get(3) + "倍\n"
					+ editComment.apply("配置コスト倍率:") + unitStatusList.get(4)+ "倍";
		showMessageDialog(null, comment, "コア情報", INFORMATION_MESSAGE, new ImageIcon(imageList.get(selectNumber)));
	}
	
	public void weaponStatus(int selectNumber, List<BufferedImage> imageList) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (25 - comment.getBytes().length) + "s", comment);
		};
		List<Integer> weaponStatusList = DefaultData.WEAPON_WEAPON_STATUS_LIST.get(selectNumber);
		List<Integer> unitStatusList = DefaultData.WEAPON_UNIT_STATUS_LIST.get(selectNumber);
		String comment = "【" + DefaultData.WEAPON_NAME_LIST.get(selectNumber) + "】" + "\n"
					+ "\n"
					+ "【武器ステータス】\n"
					+ editComment.apply(((0 <= weaponStatusList.get(0))? "攻撃力:": "回復力:")) + Math.abs(weaponStatusList.get(0)) + "\n"
					+ editComment.apply("射程:") + weaponStatusList.get(1) + "\n"
					+ editComment.apply("攻撃速度:") + weaponStatusList.get(2) + " ms\n"
					+ "\n"
					+ "【ユニットステータス】\n"
					+ editComment.apply("体力:") + unitStatusList.get(0) + "\n"
					+ editComment.apply("防御力:") + unitStatusList.get(1) + "\n"
					+ editComment.apply("回復:") + unitStatusList.get(2) + "\n"
					+ editComment.apply("足止め数:") + unitStatusList.get(3) + "\n"
					+ editComment.apply("配置コスト:") + unitStatusList.get(4) + "\n"
					+ "\n"
					+ "【武器タイプ】\n"
					+ "距離タイプ: " + DefaultData.DISTANCE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(0)) + "\n"
					+ "装備タイプ: " + DefaultData.HANDLE_MAP.get(DefaultData.WEAPON_TYPE.get(selectNumber).get(1));
		showMessageDialog(null, comment, "武器情報", INFORMATION_MESSAGE, new ImageIcon(imageList.get(selectNumber)));
	}
	
	public String getComment(List<List<Integer>> weaponStatusList, List<Integer> unitStatusList,int type, boolean existsRight, boolean existsLeft) {
		Function<String, String> editComment = (comment) -> {
			return String.format("%-" + (25 - comment.getBytes().length) + "s", comment);
		};
		Function<List<Integer>, String> editWeaponComment = (statusList) -> {
			return (editComment.apply((0 <= statusList.get(0))? "攻撃力:": "回復力:")) + Math.abs(statusList.get(0)) + "\n"
					+ editComment.apply("射程:") + statusList.get(1) + "\n"
					+ editComment.apply("攻撃速度:") + statusList.get(2) + " ms\n"
					+ "\n";
		};
		String comment = "【武器ステータス】\n";
		if(existsLeft && existsRight) {
			comment += "右武器\n" + editWeaponComment.apply(weaponStatusList.get(0));
			comment += "左武器\n" + editWeaponComment.apply(weaponStatusList.get(1));
		}else if(!existsLeft && !existsRight){
			comment += "武器無し\n\n";
		}else {
			comment += existsRight? editWeaponComment.apply(weaponStatusList.get(0)): editWeaponComment.apply(weaponStatusList.get(1));
		}
		comment += "【ユニットステータス】\n"
				+ editComment.apply("体力:") + unitStatusList.get(0) + "\n"
				+ editComment.apply("防御力:") + unitStatusList.get(1) + "\n"
				+ editComment.apply("回復:") + unitStatusList.get(2) + "\n"
				+ editComment.apply("足止め数:") + unitStatusList.get(3) + "\n"
				+ editComment.apply("配置コスト:") + unitStatusList.get(4) + "\n"
				+ "\n"
				+ "【武器タイプ】\n"
				+ DefaultData.DISTANCE_MAP.get(type) + "\n"
				+ "\n"
				+ "↓武器解除する位置を選択してください↓"
				+ "\n";
		return comment;
	}
}
