package com.who.read.reading.who.manager;

/**
 * @Classname Limit
 * @date 2020/10/21 9:09 PM
 * @Created by fengjiadong
 */
public class Limit {
	/**
	 * 分页开始位置
	 */
	private Integer start;
	/**
	 * 分页结束位置(包含)
	 */
	private Integer end;

	public Limit(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
}
