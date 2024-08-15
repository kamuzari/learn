package com.tutorial.kafka.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.kafka.domain.Room;
import com.tutorial.kafka.domain.repository.RoomRepository;

@Transactional(readOnly = true)
@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Transactional
	public void create(String sessionId, String roomId) {
		roomRepository.save(new Room(roomId, sessionId));
	}

	@Transactional
	public void remove(String roomId, String sessionId) {
		roomRepository.deleteByRoomIdAndSessionId(roomId, sessionId);
	}

	public List<String> read(String roomId) {
		return roomRepository.findByRoomId(roomId).stream().map(Room::getSessionId).toList();
	}
}
