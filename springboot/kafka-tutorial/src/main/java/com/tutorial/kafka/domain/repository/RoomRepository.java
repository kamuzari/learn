package com.tutorial.kafka.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutorial.kafka.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	void deleteByRoomIdAndSessionId(String id, String sessionId);

	List<Room> findByRoomId(String roomId);

	@Query("SELECT r FROM Room r WHERE r.roomId = :roomId AND r.sessionId != :sessionId")
	List<Room> findByRoomIdAndSessionIdNotContaining(
		@Param("roomId") String roomId,
		@Param("sessionId") String sessionId);
}
