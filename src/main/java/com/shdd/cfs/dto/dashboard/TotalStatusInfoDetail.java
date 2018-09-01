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
	 * 今日总任务
	 */
	private Integer task;
	/**
	 * 运行中任务
	 */
	private Integer running;
	/**
	 * 已完成任务个数
	 */
	private Integer completed;
	/**
	 * 新增条目
	 */
	private Integer added;
	/**
	 * 存储池总使用容量 磁带库+分布式+光盘库
	 */
	private Double capacity;
	/**
	 * 警告个数
	 */
	private Integer warning;
}
