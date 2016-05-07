package com.example.xwan.Info;

import android.widget.ImageView.ScaleType;

public class ImageInfo {
	private Integer id;
	private String img_url;
	private String img_locaPath;
	private int img_rouseId;
	private ScaleType img_ScaleType;

	public String getImg_locaPath() {
		return img_locaPath;
	}

	public void setImg_locaPath(String img_locaPath) {
		this.img_locaPath = img_locaPath;
	}

	public ScaleType getImg_ScaleType() {
		return img_ScaleType;
	}

	public void setImg_ScaleType(ScaleType img_ScaleType) {
		this.img_ScaleType = img_ScaleType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public int getImg_rouseId() {
		return img_rouseId;
	}

	public void setImg_rouseId(int img_rouseId) {
		this.img_rouseId = img_rouseId;
	}

}
