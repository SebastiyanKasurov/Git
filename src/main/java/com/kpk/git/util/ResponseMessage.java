package com.kpk.git.util;

import java.time.LocalDateTime;

public class ResponseMessage {
	private String msg;
	private int status;
	private LocalDateTime timeCause;
	
	public ResponseMessage(String msg, int status, LocalDateTime timeCause) {
		this.msg = msg;
		this.status = status;
		this.timeCause = timeCause;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public LocalDateTime getTimeCause() {
		return timeCause;
	}
	
	public void setTimeCause(LocalDateTime timeCause) {
		this.timeCause = timeCause;
	}
}