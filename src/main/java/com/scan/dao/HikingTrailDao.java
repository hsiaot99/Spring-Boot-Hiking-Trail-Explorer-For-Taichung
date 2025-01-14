package com.scan.dao;

import java.util.List;

import com.scan.model.po.HikingTrail;
import com.scan.model.vo.InsertVo;

public interface HikingTrailDao {

	HikingTrail insert(InsertVo request);

	int delete(Long seq);

	HikingTrail update(InsertVo request);

	List<HikingTrail> queryAll();

	HikingTrail queryBySeq(Long seq);
}
