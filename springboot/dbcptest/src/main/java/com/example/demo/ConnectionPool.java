package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectionPool {
	private static final String URL = "jdbc:mysql://localhost:3306/dpcptest";
	private static final String ID = "dpcptest";
	private static final String PASSWORD = "password";

	private final List<Connection> pool;
	private int pointer = 0;

	public ConnectionPool() {
		this.pool = initializeConnectionPool();
	}

	private List<Connection> initializeConnectionPool() {
		return IntStream.range(0, 10)
			.mapToObj(ignored -> generateConnection())
			.collect(Collectors.toList());
	}

	private Connection generateConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

	public Connection getConnection() {
		Connection connection = pool.get(pointer % 10);
		pointer++;
		return connection;
	}
}
