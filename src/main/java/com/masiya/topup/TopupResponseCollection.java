package com.masiya.topup;

import java.util.List;

public class TopupResponseCollection<T> {

	private List<T> rows;
	private Integer total;
	
	public TopupResponseCollection(List<T> data) {
		super();
		this.rows = data;
		this.total = data.size();
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
		this.total = rows.size();
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
