package com.scan.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.scan.dao.impl.HikingTrailDaoImpl;
import com.scan.model.po.HikingTrail;
import com.scan.model.vo.InsertVo;
import com.scan.service.impl.ScanServiceImpl;

public class MainApplication {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// 設定 Console 編碼
	    System.setProperty("file.encoding", "UTF-8");
	    System.setProperty("sun.jnu.encoding", "UTF-8");
	    // 設定控制台輸出編碼
	    System.setProperty("console.encoding", "UTF-8");
		try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
			ScanServiceImpl scanService = connectDao();
			boolean continueRunning = true;

			while (continueRunning) {
				displayMenu();
				String funcNum = scanner.nextLine();

				if ("0".equals(funcNum)) {
					System.out.println("程式結束，謝謝使用！");
					continueRunning = false;
					continue;
				}

				switch (funcNum) {
				case "1": {
					System.out.print("請輸入要查詢的步道編號: ");
					try {
						Long seqNumber = Long.parseLong(scanner.nextLine());
						HikingTrail trail = scanService.queryBySeq(seqNumber);
						if (trail != null) {
							printHeader();
							printTrail(trail);
						} else {
							System.out.println("找不到編號 " + seqNumber + " 的步道資料");
						}
					} catch (NumberFormatException e) {
						System.out.println("請輸入有效的數字編號！");
					}
					break;
				}
				case "2": {
					printHeader();
					try {
						List<HikingTrail> hikingTrailList = scanService
								.readHikingTrailsFromCsv("C:\\Users\\hsiao\\Desktop\\ScanData\\臺中市健行步道資料.json");
						hikingTrailList.forEach(MainApplication::printTrail);
					} catch (Exception e) {
						System.out.println("新增資料錯誤，請確認資料是否重複!");
					}

					break;
				}
				case "3": {
					System.out.print("請輸入要新增的步道編號: ");
					Long seqNumber = Long.parseLong(scanner.nextLine());
					HikingTrail trail = scanService.queryBySeq(seqNumber);
					if (trail != null) {
						System.out.println("步道編號重複無法新增");
					} else {
						try {
							InsertVo insertVo = new InsertVo();
							insertVo.setSeq(seqNumber);

							System.out.print("請輸入要新增的縣市別代碼: ");
							String number = scanner.nextLine();
							insertVo.setNumber(number);

							System.out.print("請輸入要新增的區域: ");
							String cityCode = scanner.nextLine();
							insertVo.setCityCode(cityCode);

							System.out.print("請輸入要新增的行政區域代碼: ");
							String district = scanner.nextLine();
							insertVo.setDistrict(district);

							System.out.print("請輸入要新增的登山步道名稱: ");
							String trailName = scanner.nextLine();
							insertVo.setTrailName(trailName);

							System.out.print("請輸入要新增的經度(步道大約位置): ");
							BigDecimal longitude = new BigDecimal(scanner.nextLine());
							insertVo.setLongitude(longitude);

							System.out.print("請輸入要新增的緯度(步道大約位置): ");
							BigDecimal latitude = new BigDecimal(scanner.nextLine());
							insertVo.setLatitude(latitude);

							System.out.print("請輸入要新增的步道分類: ");
							String trailType = scanner.nextLine();
							insertVo.setTrailType(trailType);

							System.out.print("請輸入要新增的步道等級: ");
							String trailLevel = scanner.nextLine();
							insertVo.setTrailLevel(trailLevel);

							System.out.print("請輸入要新增的附屬設施: ");
							String facilities = scanner.nextLine();
							insertVo.setFacilities(facilities);

							System.out.print("請輸入要新增的管理維護單位: ");
							String managementUnit = scanner.nextLine();
							insertVo.setManagementUnit(managementUnit);

							System.out.print("請輸入要新增的長度公里: ");
							BigDecimal lengthKm = new BigDecimal(scanner.nextLine());
							insertVo.setLengthKm(lengthKm);

							insertVo.setCreatetime(LocalDateTime.now());
							insertVo.setUpdatetime(LocalDateTime.now());

							HikingTrail hikingTrail = scanService.insert(insertVo);

							printHeader();
							printTrail(hikingTrail);
						} catch (Exception e) {
							System.out.print("請輸入錯誤: " + e);
						}

					}

					break;
				}
				case "4": {
					System.out.print("請輸入要更新的步道編號: ");
					Long seqNumber = Long.parseLong(scanner.nextLine());
					HikingTrail trail = scanService.queryBySeq(seqNumber);
					if (trail == null) {
						System.out.println("找不到編號 " + seqNumber + " 的步道資料");
					} else {
						try {
							InsertVo insertVo = new InsertVo();
							insertVo.setSeq(seqNumber);

							System.out.print("請輸入要更新的縣市別代碼: ");
							String number = scanner.nextLine();
							insertVo.setNumber(number);

							System.out.print("請輸入要更新的區域: ");
							String cityCode = scanner.nextLine();
							insertVo.setCityCode(cityCode);

							System.out.print("請輸入要更新的行政區域代碼: ");
							String district = scanner.nextLine();
							insertVo.setDistrict(district);

							System.out.print("請輸入要更新的登山步道名稱: ");
							String trailName = scanner.nextLine();
							insertVo.setTrailName(trailName);

							System.out.print("請輸入要更新的經度(步道大約位置): ");
							BigDecimal longitude = new BigDecimal(scanner.nextLine());
							insertVo.setLongitude(longitude);

							System.out.print("請輸入要更新的緯度(步道大約位置): ");
							BigDecimal latitude = new BigDecimal(scanner.nextLine());
							insertVo.setLatitude(latitude);

							System.out.print("請輸入要更新的步道分類: ");
							String trailType = scanner.nextLine();
							insertVo.setTrailType(trailType);

							System.out.print("請輸入要更新的步道等級: ");
							String trailLevel = scanner.nextLine();
							insertVo.setTrailLevel(trailLevel);

							System.out.print("請輸入要更新的附屬設施: ");
							String facilities = scanner.nextLine();
							insertVo.setFacilities(facilities);

							System.out.print("請輸入要更新的管理維護單位: ");
							String managementUnit = scanner.nextLine();
							insertVo.setManagementUnit(managementUnit);

							System.out.print("請輸入要更新的長度公里: ");
							BigDecimal lengthKm = new BigDecimal(scanner.nextLine());
							insertVo.setLengthKm(lengthKm);

							insertVo.setCreatetime(LocalDateTime.now());
							insertVo.setUpdatetime(LocalDateTime.now());

							HikingTrail hikingTrail = scanService.update(insertVo);

							printHeader();
							printTrail(hikingTrail);
						} catch (Exception e) {
							System.out.print("請輸入錯誤: " + e);
						}

					}

					break;
				}
				case "5": {
					System.out.print("請輸入要刪除的步道編號: ");
					Long seqNumber = Long.parseLong(scanner.nextLine());
					scanService.delete(seqNumber);
					System.out.print("刪除的步道編號" + seqNumber);
					break;
				}
				case "6": {
					printHeader();
					List<HikingTrail> allTrails = scanService.queryAll();
					allTrails.forEach(MainApplication::printTrail);
					break;
				}
				case "7": {
					List<HikingTrail> allTrails = scanService.queryAll();
					String filePath = "C:\\Users\\hsiao\\臺中市健行步道資料.json";
					scanService.exportToFile(allTrails, filePath);
					System.out.println("資料已更新並匯出到檔案:" + filePath);
					break;
				}
				case "8": {
					try {
						String filePath = "C:\\Users\\hsiao\\臺中市健行步道資料.csv";
						List<HikingTrail> allTrails = scanService.queryAll();

						scanService.exportToCSV(allTrails, filePath);
						System.out.println("CSV 檔案已匯出至: " + filePath);

					} catch (IOException e) {
						System.err.println("匯出失敗: " + e.getMessage());
					}
					break;
				}
				case "9": {
					try {
						String filePath = "C:\\Users\\hsiao\\臺中市健行步道資料.xml";
						List<HikingTrail> allTrails = scanService.queryAll();

						scanService.exportToXML(allTrails, filePath);
						System.out.println("XML 檔案已匯出至: " + filePath);

					} catch (IOException e) {
						System.err.println("匯出失敗: " + e.getMessage());
					}
					break;
				}
				case "10": {
					try {
						List<HikingTrail> trails = scanService.queryAll();

						trails.forEach(trail -> {
							scanService.delete(trail.getSeq());
						});

					} catch (NumberFormatException e) {
						System.out.println("無法刪除!");
					}
					break;
				}
				default: {
					System.out.println("無效的選擇");
					break;
				}
				}

				System.out.println("\n是否繼續？(Y/N)");
				String answer = scanner.nextLine();
				if (answer.toUpperCase().equals("N")) {
					System.out.println("程式結束，謝謝使用！");
					continueRunning = false;
				}
			}
		} catch (Exception e) {
			System.err.println("發生錯誤: " + e.getMessage());
		}
	}

	private static void printHeader() {
		// 確保與 printTrail 使用相同的寬度設定
		System.out.printf("%-4s|%-10s|%-8s|%-12s|%-25s|%-15s|%-15s|%-10s|%-8s|%-30s|%-15s|%-8s%n", "編號", "縣市別代碼", "區域",
				"行政區域代碼", "登山步道名稱", "經度", "緯度", "步道分類", "步道等級", "附屬設施", "管理維護單位", "長度公里");
	}

	private static void printTrail(HikingTrail trail) {
		// 使用與 printHeader 完全相同的格式化字串
		System.out.printf("%-4d|%-12s|%-8s|%-13s|%-25s|%-15s|%-15s|%-10s|%-8s|%-30s|%-15s|%-8s%n", trail.getSeq(),
				padRight(trail.getNumber(), 10), padRight(trail.getCityCode(), 8), padRight(trail.getDistrict(), 12),
				padRight(trail.getTrailName(), 25), trail.getLongitude(), trail.getLatitude(),
				padRight(nullToEmpty(trail.getTrailType()), 10), padRight(trail.getTrailLevel(), 8),
				padRight(nullToEmpty(trail.getFacilities()), 30), padRight(trail.getManagementUnit(), 15),
				trail.getLengthKm());
	}

	// 處理中文字串的右側填充
	private static String padRight(String str, int length) {
		if (str == null) {
			str = "-";
		}
		int strLength = getDisplayLength(str);
		int paddingLength = length - strLength;
		if (paddingLength <= 0) {
			return str;
		}
		return str + " ".repeat(paddingLength);
	}

	// 計算字串實際顯示長度（中文字元算2個長度）
	private static int getDisplayLength(String str) {
		if (str == null)
			return 0;
		int length = 0;
		for (char c : str.toCharArray()) {
			if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) {
				length += 2; // 中文字元算2個長度
			} else {
				length += 1; // 其他字元算1個長度
			}
		}
		return length;
	}

	private static String nullToEmpty(String value) {
		return value == null || value.trim().isEmpty() ? "-" : value;
	}

	private static void displayMenu() {
		System.out.println("\n<-------------Menu------------->");
		System.out.println("0.離開程式");
		System.out.println("1.Get HiKing Trail By 編號");
		System.out.println("2.Add Json HiKing Trail");
		System.out.println("3.Add HiKing Trail");
		System.out.println("4.Update HiKing Trail");
		System.out.println("5.Delete HiKing Trail");
		System.out.println("6.Get All HiKing Trails");
		System.out.println("7.Export Json");
		System.out.println("8.Export Csv");
		System.out.println("9.Export Xml");
		System.out.println("10.Delete All HiKing Trail");
		System.out.println("請輸入要使用的功能：");
	}

	private static ScanServiceImpl connectDao() throws ClassNotFoundException {
		ScanServiceImpl scanService = new ScanServiceImpl();
		HikingTrailDaoImpl dao = new HikingTrailDaoImpl();
		System.out.println("連接Dao");
		scanService.setDao(dao);
		System.out.println("設定jdbc連線...");
		return scanService;
	}
}