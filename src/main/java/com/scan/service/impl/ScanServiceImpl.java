package com.scan.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scan.dao.HikingTrailDao;
import com.scan.model.po.HikingTrail;
import com.scan.model.vo.InsertVo;
import com.scan.service.ScanService;

@Service
public class ScanServiceImpl implements ScanService {

	@Autowired
	private HikingTrailDao hikingTrailDao;

	public void setDao(HikingTrailDao hikingTrailDao) {
		this.hikingTrailDao = hikingTrailDao;
	}

	@Override
	public HikingTrail insert(InsertVo request) {

		return hikingTrailDao.insert(request);
	}

	@Override
	public int delete(Long seq) {

		return hikingTrailDao.delete(seq);
	}

	@Override
	public HikingTrail update(InsertVo request) {

		return hikingTrailDao.update(request);
	}

	@Override
	public List<HikingTrail> queryAll() {

		return hikingTrailDao.queryAll();
	}

	@Override
	public HikingTrail queryBySeq(Long seq) {

		return hikingTrailDao.queryBySeq(seq);
	}

	public List<HikingTrail> readHikingTrailsFromCsv(String filePath) throws IOException {
		try {
			byte[] bytes = Files.readAllBytes(new File(filePath).toPath());
			String jsonContent = new String(bytes, StandardCharsets.UTF_8);

			ObjectMapper mapper = new ObjectMapper();
			// 設定允許處理中文字元
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// 設定使用 UTF-8 編碼
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			// 解析 JSON
			List<Map<String, String>> jsonList = mapper.readValue(jsonContent,
					new TypeReference<List<Map<String, String>>>() {
					});
			List<HikingTrail> hikingTrailList = new ArrayList<HikingTrail>();
			// 轉換並存入資料庫
			for (Map<String, String> record : jsonList) {
				InsertVo insertData = new InsertVo();
				insertData.setSeq(Long.valueOf(record.get("編號")));
				insertData.setNumber(record.get("縣市別代碼"));
				insertData.setCityCode(record.get("區域"));
				insertData.setDistrict(record.get("行政區域代碼"));
				insertData.setTrailName(record.get("登山步道名稱"));

				// 經緯度格式處理
				String lonStr = record.get("經度(步道大約位置)").trim();
				String latStr = record.get("緯度(步道大約位置)").trim();

				try {
					// 檢查數值是否過大，如果是就進行轉換
					double lon = Double.parseDouble(lonStr);
					double lat = Double.parseDouble(latStr);

					if (lon > 180 || lon < -180) {
						lon = lon / 1000000.0; // 調整為正確的經度格式
					}
					if (lat > 90 || lat < -90) {
						lat = lat / 1000000.0; // 調整為正確的緯度格式
					}

					insertData.setLongitude(new BigDecimal(lon).setScale(6, RoundingMode.HALF_UP));
					insertData.setLatitude(new BigDecimal(lat).setScale(6, RoundingMode.HALF_UP));
				} catch (NumberFormatException e) {
					System.err.println("經緯度格式錯誤: " + lonStr + ", " + latStr);
					continue;
				}

				insertData.setTrailType(record.get("步道分類"));
				insertData.setTrailLevel(record.get("步道等級"));
				insertData.setFacilities(record.get("附屬設施"));
				insertData.setManagementUnit(record.get("管理維護單位"));
				insertData.setLengthKm(new BigDecimal(record.get("長度公里")));
				insertData.setCreatetime(LocalDateTime.now());
				insertData.setUpdatetime(LocalDateTime.now());

				hikingTrailList.add(hikingTrailDao.insert(insertData));
			}

			return hikingTrailList;

		} catch (Exception e) {
			throw new RuntimeException("處理文件失敗: " + e.getMessage());
		}

	}

	public void exportToFile(List<HikingTrail> trails, String filePath) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> jsonData = new ArrayList<>();

		for (HikingTrail trail : trails) {
			Map<String, String> trailMap = new HashMap<>();
			trailMap.put("編號", String.valueOf(trail.getSeq()));
			trailMap.put("縣市別代碼", trail.getCityCode());
			trailMap.put("區域", trail.getDistrict());
			trailMap.put("行政區域代碼", trail.getNumber());
			trailMap.put("登山步道名稱", trail.getTrailName());
			trailMap.put("經度(步道大約位置)", trail.getLongitude().toString());
			trailMap.put("緯度(步道大約位置)", trail.getLatitude().toString());
			trailMap.put("步道分類", trail.getTrailType());
			trailMap.put("步道等級", trail.getTrailLevel());
			trailMap.put("附屬設施", trail.getFacilities());
			trailMap.put("管理維護單位", trail.getManagementUnit());
			trailMap.put("長度公里", trail.getLengthKm().toString());

			jsonData.add(trailMap);
		}

		try (FileWriter writer = new FileWriter(filePath)) {
			// 設定中文輸出格式
			mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
			// 設定縮排格式
			mapper.writerWithDefaultPrettyPrinter().writeValue(writer, jsonData);
		} catch (IOException e) {
			throw new RuntimeException("匯出 JSON 檔案失敗: " + e.getMessage());
		}
	}

	// ScanServiceImpl.java

	public void exportToCSV(List<HikingTrail> trails, String filePath) throws IOException {
		try (FileWriter writer = new FileWriter(filePath)) {
			// 寫入 CSV header
			writer.write("編號,縣市別代碼,區域,行政區域代碼,登山步道名稱,經度,緯度,步道分類,步道等級,附屬設施,管理維護單位,長度公里\n");

			// 寫入資料
			for (HikingTrail trail : trails) {
				writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", trail.getSeq(),
						escapeCsvField(trail.getNumber()), escapeCsvField(trail.getCityCode()),
						escapeCsvField(trail.getDistrict()), escapeCsvField(trail.getTrailName()), trail.getLongitude(),
						trail.getLatitude(), escapeCsvField(trail.getTrailType()),
						escapeCsvField(trail.getTrailLevel()), escapeCsvField(trail.getFacilities()),
						escapeCsvField(trail.getManagementUnit()), trail.getLengthKm()));
			}
		}
	}

	private String escapeCsvField(String field) {
		if (field == null)
			return "";
		// 如果字段包含逗號或換行，用引號包圍
		if (field.contains(",") || field.contains("\n") || field.contains("\"")) {
			return "\"" + field.replace("\"", "\"\"") + "\"";
		}
		return field;
	}

	public void exportToXML(List<HikingTrail> trails, String filePath) throws IOException {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			writer.write("<HikingTrails>\n");

			for (HikingTrail trail : trails) {
				writer.write("  <Trail>\n");
				writer.write(String.format("    <Seq>%d</Seq>\n", trail.getSeq()));
				writer.write(String.format("    <Number>%s</Number>\n", escapeXml(trail.getNumber())));
				writer.write(String.format("    <CityCode>%s</CityCode>\n", escapeXml(trail.getCityCode())));
				writer.write(String.format("    <District>%s</District>\n", escapeXml(trail.getDistrict())));
				writer.write(String.format("    <TrailName>%s</TrailName>\n", escapeXml(trail.getTrailName())));
				writer.write(String.format("    <Longitude>%s</Longitude>\n", trail.getLongitude()));
				writer.write(String.format("    <Latitude>%s</Latitude>\n", trail.getLatitude()));
				writer.write(String.format("    <TrailType>%s</TrailType>\n", escapeXml(trail.getTrailType())));
				writer.write(String.format("    <TrailLevel>%s</TrailLevel>\n", escapeXml(trail.getTrailLevel())));
				writer.write(String.format("    <Facilities>%s</Facilities>\n", escapeXml(trail.getFacilities())));
				writer.write(String.format("    <ManagementUnit>%s</ManagementUnit>\n",
						escapeXml(trail.getManagementUnit())));
				writer.write(String.format("    <LengthKm>%s</LengthKm>\n", trail.getLengthKm()));
				writer.write("  </Trail>\n");
			}

			writer.write("</HikingTrails>");
		}
	}

	private String escapeXml(String data) {
		if (data == null)
			return "";
		return data.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'",
				"&apos;");
	}

}
