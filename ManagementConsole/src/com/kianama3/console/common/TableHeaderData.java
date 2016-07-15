package com.kianama3.console.common;

public class TableHeaderData {
	public String title;
	public int width;
	public int alignment;

	public TableHeaderData (String title, int width, int alignment) {
		this.title = title;
		this.width = width;
		this.alignment = alignment;
	}
}