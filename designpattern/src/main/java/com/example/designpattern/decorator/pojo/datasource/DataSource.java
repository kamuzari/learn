package com.example.designpattern.decorator.pojo.datasource;

public interface DataSource {
	void writeData(String data);

	String readData();
}
