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
public class InsertVo {

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

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTrailName() {
		return trailName;
	}

	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getTrailType() {
		return trailType;
	}

	public void setTrailType(String trailType) {
		this.trailType = trailType;
	}

	public String getTrailLevel() {
		return trailLevel;
	}

	public void setTrailLevel(String trailLevel) {
		this.trailLevel = trailLevel;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getManagementUnit() {
		return managementUnit;
	}

	public void setManagementUnit(String managementUnit) {
		this.managementUnit = managementUnit;
	}

	public BigDecimal getLengthKm() {
		return lengthKm;
	}

	public void setLengthKm(BigDecimal lengthKm) {
		this.lengthKm = lengthKm;
	}

	public LocalDateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}

	public LocalDateTime getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * district 行政區域代碼
	 */
	private String district;

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
