package com.scan.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.scan.dao.HikingTrailDao;
import com.scan.model.po.HikingTrail;
import com.scan.model.vo.InsertVo;

@Repository
public class HikingTrailDaoImpl implements HikingTrailDao {

	private Connection conn;

	public HikingTrailDaoImpl() throws ClassNotFoundException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String url = "jdbc:sqlserver://localhost:1433;"
					+ "databaseName=HikingTrail;"
					+ "encrypt=false;"
					+ "user=hsiao;password=hsiao;"
					+ "characterEncoding=UTF-8;" 
				    + "useUnicode=true;";
			conn = DriverManager.getConnection(url);
			createDatabaseIfNotExists();
			createTableIfNotExists();

		} catch (Exception e) {
			throw new RuntimeException("數據庫連接失敗: " + e.getMessage());
		}
	}

	private void createDatabaseIfNotExists() {
		try {
			Statement stmt = conn.createStatement();

			// 創建數據庫（如果不存在）
			stmt.execute("IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'hiking_trail') "
					+ "CREATE DATABASE hiking_trail");

			// 切換到新創建的數據庫
			stmt.execute("USE hiking_trail");

		} catch (SQLException e) {
			throw new RuntimeException("創建數據庫失敗: " + e.getMessage());
		}
	}

	private void createTableIfNotExists() {
		String sql = "IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[hiking_trail_t]') AND type in (N'U'))\n"
				+ "BEGIN\n" + "CREATE TABLE hiking_trail_t (\n" + "    seq BIGINT PRIMARY KEY,\n"
				+ "    number NVARCHAR(50) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    city_code NVARCHAR(50) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    district NVARCHAR(100) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    trail_name NVARCHAR(200) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    longitude DECIMAL(10,6),\n" + "    latitude DECIMAL(10,6),\n"
				+ "    trail_type NVARCHAR(50) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    trail_level NVARCHAR(50) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    facilities NVARCHAR(500) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    management_unit NVARCHAR(200) COLLATE Chinese_Taiwan_Stroke_CI_AS,\n"
				+ "    length_km DECIMAL(10,2),\n" + "    createtime DATETIME2 DEFAULT GETDATE(),\n"
				+ "    updatetime DATETIME2 DEFAULT GETDATE()\n" + ")\n" + "END";

		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("創建表失敗: " + e.getMessage());
		}
	}

	@Override
	public HikingTrail insert(InsertVo request) {
		String sql = "INSERT INTO hiking_trail_t (number, city_code, district, "
				+ "trail_name, longitude, latitude, trail_type, "
				+ "trail_level, facilities, management_unit, length_km, createtime, updatetime, seq) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, request.getNumber());
			pstmt.setString(2, request.getCityCode());
			pstmt.setString(3, request.getDistrict());
			pstmt.setString(4, request.getTrailName());
			pstmt.setBigDecimal(5, request.getLongitude());
			pstmt.setBigDecimal(6, request.getLatitude());
			pstmt.setString(7, request.getTrailType());
			pstmt.setString(8, request.getTrailLevel());
			pstmt.setString(9, request.getFacilities());
			pstmt.setString(10, request.getManagementUnit());
			pstmt.setBigDecimal(11, request.getLengthKm());
			pstmt.setLong(12, request.getSeq());

			pstmt.executeUpdate();

			return queryBySeq(request.getSeq());
		} catch (SQLException e) {
			throw new RuntimeException("插入失敗: " + e.getMessage());
		}
	}

	@Override
	public int delete(Long seq) {
		String sql = "DELETE FROM hiking_trail_t WHERE seq = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, seq);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("刪除失敗: " + e.getMessage());
		}
	}

	@Override
	public HikingTrail update(InsertVo request) {
		String sql = "UPDATE hiking_trail_t SET " + "city_code = ?, district = ?, "
				+ "trail_name = ?, longitude = ?, latitude = ?, " + "trail_type = ?, trail_level = ?, facilities = ?, "
				+ "management_unit = ?, length_km = ?, " + "updatetime = GETDATE(), number = ? " + "WHERE seq = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, request.getCityCode());
			pstmt.setString(2, request.getDistrict());
			pstmt.setString(3, request.getTrailName());
			pstmt.setBigDecimal(4, request.getLongitude());
			pstmt.setBigDecimal(5, request.getLatitude());
			pstmt.setString(6, request.getTrailType());
			pstmt.setString(7, request.getTrailLevel());
			pstmt.setString(8, request.getFacilities());
			pstmt.setString(9, request.getManagementUnit());
			pstmt.setBigDecimal(10, request.getLengthKm());
			pstmt.setString(11, request.getNumber());
			pstmt.setLong(12, request.getSeq());

			int updatedRows = pstmt.executeUpdate();
			if (updatedRows > 0) {
				return queryBySeq(request.getSeq());
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException("更新失敗: " + e.getMessage());
		}
	}

	@Override
	public List<HikingTrail> queryAll() {
		String sql = "SELECT * FROM hiking_trail_t";
		List<HikingTrail> trails = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				trails.add(mapResultSetToTrail(rs));
			}
			return trails;
		} catch (SQLException e) {
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		}
	}

	@Override
	public HikingTrail queryBySeq(Long seq) {
		String sql = "SELECT * FROM hiking_trail_t WHERE seq = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, seq);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToTrail(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		}
	}

	private HikingTrail queryBySeq(String number) {
		String sql = "SELECT TOP 1 * FROM hiking_trail_t WHERE seq = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, number);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToTrail(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		}
	}

	private HikingTrail mapResultSetToTrail(ResultSet rs) throws SQLException {
		HikingTrail trail = new HikingTrail();
		trail.setSeq(rs.getLong("seq"));
		trail.setNumber(rs.getString("number"));
		trail.setCityCode(rs.getString("city_code"));
		trail.setDistrict(rs.getString("district"));
		trail.setTrailName(rs.getString("trail_name"));
		trail.setLongitude(rs.getBigDecimal("longitude"));
		trail.setLatitude(rs.getBigDecimal("latitude"));
		trail.setTrailType(rs.getString("trail_type"));
		trail.setTrailLevel(rs.getString("trail_level"));
		trail.setFacilities(rs.getString("facilities"));
		trail.setManagementUnit(rs.getString("management_unit"));
		trail.setLengthKm(rs.getBigDecimal("length_km"));
		trail.setCreatetime(rs.getTimestamp("createtime").toLocalDateTime());
		trail.setUpdatetime(rs.getTimestamp("updatetime").toLocalDateTime());
		return trail;
	}

	// 記得在應用程式結束時關閉連接
	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
