package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.Instant;

public class NotUseConnectionPool {
	private static final String URL = "jdbc:mysql://localhost:3306/dpcptest";
	private static final String ID = "dpcptest";
	private static final String PASSWORD = "password";

	public static void main(String[] args) {
		Instant start = Instant.now();

		for (int i = 0; i < 100; i++) {
			Connection connection = generateConnection();
			doInsert(connection);
		}

		Instant end = Instant.now();
		System.out.println("수행시간: " + Duration.between(start, end).toMillis() + " 밀리초");

	}

	private static void doInsert(final Connection connection) {
		try (connection) {
			String sql = "INSERT INTO member(username, password) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "test");
			preparedStatement.setString(2, "1234");
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Connection generateConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}
}
