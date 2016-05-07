package com.example.xwan.Info;

public class MmainFoodInfo {
	private String id;
	private String Name;
	private String FromName;
	private String url;
	private String FromNameHeadUrl;
	private String Address;
	private String Time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getFromName() {
		return FromName;
	}

	public void setFromName(String fromName) {
		FromName = fromName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFromNameHeadUrl() {
		return FromNameHeadUrl;
	}

	public void setFromNameHeadUrl(String fromNameHeadUrl) {
		FromNameHeadUrl = fromNameHeadUrl;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	@Override
	public String toString() {
		return "MmainFoodInfo [id=" + id + ", Name=" + Name + ", FromName="
				+ FromName + ", url=" + url + ", FromNameHeadUrl="
				+ FromNameHeadUrl + ", Address=" + Address + ", Time=" + Time
				+ "]";
	}
}
