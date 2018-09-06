/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.dashboard;

import lombok.Data;

/**
 * @author: jfz
 * @version: 1.0 2018/8/30
 */
@Data
public class TotalStatusInfoDetail {
	/**
	 * 分布式总容量
	 */
	private String distcapacity;
	/**
	 * 分布式可用容量
	 */
	private String distfree;
	/**
	 * 磁带库总磁带数
	 */
	private Integer tapecapacity;
	/**
	 * 磁带库可用磁带数
	 */
	private Integer tapefree;
	/**
	 * 光盘库总光盘数
	 */
	private Integer cdcapacity;
	/**
	 * 光盘库可用光盘数
	 */
	private Integer cdfree;
}
