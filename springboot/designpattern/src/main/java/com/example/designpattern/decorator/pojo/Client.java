package com.example.designpattern.decorator.pojo;

import com.example.designpattern.decorator.pojo.datasource.DataSource;
import com.example.designpattern.decorator.pojo.datasource.FileDataSource;
import com.example.designpattern.decorator.pojo.datasource.decorator.CompressionDecorator;
import com.example.designpattern.decorator.pojo.datasource.decorator.DataSourceDecorator;
import com.example.designpattern.decorator.pojo.datasource.decorator.EncryptionDecorator;

public class Client {

	public static void main(String[] args) {
		String fileName = "test.txt";
		String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
		DataSourceDecorator encoded = new CompressionDecorator(
			new EncryptionDecorator(
				new FileDataSource(fileName)
			)
		);
		encoded.writeData(salaryRecords);
		DataSource plain = new FileDataSource(fileName);

		System.out.println("- Input ----------------");
		System.out.println(salaryRecords);
		System.out.println("- Encoded --------------");
		System.out.println(plain.readData());
		System.out.println("- Decoded --------------");
		System.out.println(encoded.readData());
	}

}
