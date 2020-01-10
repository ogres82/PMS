package com.jdry.pms.report.service;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface OwnerChartService {

	List<Object> ownerInByRoomType();

	List<Object> ownerInByRoomState();

	List<Object> communityAndRoom();

	List<Object> communityAndIns();

	List<Object> ownerInByMonth();

	List<Object> roomAndInRate();

	List<Object> ownerAgeChart();

}
