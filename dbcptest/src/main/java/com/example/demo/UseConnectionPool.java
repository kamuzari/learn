package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class UseConnectionPool {
	public static void main(String[] args) {
		ConnectionPool connectionPool = new ConnectionPool();

		Instant start = Instant.now();

		for (int i = 0; i < 100; i++) {
			Connection connection = connectionPool.getConnection();
			doInsert(connection);
		}

		Instant end = Instant.now();
		System.out.println("수행시간: " + Duration.between(start, end).toMillis() + " 밀리초");
	}

	private static void doInsert(final Connection connection) {
		try {
			String sql = "INSERT INTO member(username, password) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "test");
			preparedStatement.setString(2, "1234");

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
