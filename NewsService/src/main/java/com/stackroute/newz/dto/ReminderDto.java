package com.stackroute.newz.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class ReminderDto {
	
    private Long reminderId;
	
	@JsonSerialize(using = ToStringSerializer.class)
    @NotBlank(message="Schedule time of the Reminder cannot be blank")
	private LocalDateTime schedule;

	public Long getReminderId() {
		return reminderId;
	}

	public void setReminderId(Long reminderId) {
		this.reminderId = reminderId;
	}

	public LocalDateTime getSchedule() {
		return schedule;
	}

	public void setSchedule(LocalDateTime schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "ReminderDto [reminderId=" + reminderId + ", schedule=" + schedule + "]";
	}
	
	

}
