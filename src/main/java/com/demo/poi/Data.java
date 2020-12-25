package com.demo.poi;
import java.util.Date;

public class Data {


	public Date dateTime = new Date();
	public String enter = "";
	public String leave = "";

	public String getEnter() {
		return enter;
	}

	public void setEnter(String enter) {
		this.enter = enter;
	}



	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}
