package com.happyfree.eventtutorial.step2.domain;

import com.happyfree.eventtutorial.step2.event.producer.EventsProducer;
import com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "orders")
@Entity
public class Order {
	@Id
	private String id;

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

}
