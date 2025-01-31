package com.tutorial.cdc;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventDto (
	@JsonProperty("event_type")
	String eventType,
	@JsonProperty("aggregate_id")
	String aggregateId,
	@JsonProperty("payload")
	String payload){

}
