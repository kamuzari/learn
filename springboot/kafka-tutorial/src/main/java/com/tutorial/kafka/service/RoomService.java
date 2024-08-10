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
		Room save = roomRepository.save(new Room(roomId,sessionId));
	}

	@Transactional
	public void remove(String roomId, String sessionId) {
		Room room = roomRepository.deleteByRoomIdAndSessionId(roomId, sessionId)
			.orElseThrow(RuntimeException::new);
	}

	public List<String> read(String roomId) {
		List<String> list = roomRepository.findByRoomId(roomId).stream().map(Room::getSessionId).toList();
		return list;
	}
}
