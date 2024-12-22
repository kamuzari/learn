package com.happyfree.eventtutorial.step2.domain;

import java.util.StringJoiner;

import com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent;
import com.happyfree.eventtutorial.step2.event.producer.EventsProducer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "orders")
@Entity
public class Order {
	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private ShipStatus shipStatus;

	protected Order() {
	}

	public Order(String id, ShipStatus shipStatus) {
		this.id = id;
		this.shipStatus = shipStatus;
	}

	public String getId() {
		return id;
	}

	public ShipStatus getShipStatus() {
		return shipStatus;
	}

	public void cancel() {
		if (ShipStatus.DELIVERED.equals(this.shipStatus) ||
			ShipStatus.COMPLETED.equals(this.shipStatus) ||
			ShipStatus.CANCELED.equals(this.shipStatus)
		) {
			throw new IllegalStateException("취소가 불가합니다.");
		}

		this.shipStatus = ShipStatus.CANCELED;
		EventsProducer.raise(new OrderCanceledEvent(this.id));
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
			.add("id='" + id + "'")
			.add("shipStatus=" + shipStatus)
			.toString();
	}
}
