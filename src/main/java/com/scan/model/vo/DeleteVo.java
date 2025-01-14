package com.scan.model.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteVo {

	/**
	 * seq 編號
	 */
	private Long seq;

	/**
	 * number 縣市別代碼
	 */
	private String number;

	/**
	 * cityCode 區域
	 */
	private String cityCode;

	/**
	 * district 行政區域代碼
	 */
	private String district;

	/**
	 * districtCode 登山步道名稱
	 */
	private String districtCode;

	/**
	 * trailName 登山步道名稱
	 */
	private String trailName;

	/**
	 * longitude trailName 經度(步道大約位置)
	 */
	private BigDecimal longitude;

	/**
	 * latitude longitude 緯度(步道大約位置)
	 */
	private BigDecimal latitude;

	/**
	 * trailType 步道分類
	 */
	private String trailType;

	/**
	 * trailLevel 步道等級
	 */
	private String trailLevel;

	/**
	 * facilities 附屬設施
	 */
	private String facilities;

	/**
	 * managementUnit 管理維護單位
	 */
	private String managementUnit;

	/**
	 * lengthKm 長度公里
	 */
	private BigDecimal lengthKm;

	/**
	 * createtime 創建時間
	 */
	private LocalDateTime createtime;

	/**
	 * updatetime 更新時間
	 */
	private LocalDateTime updatetime;

}
