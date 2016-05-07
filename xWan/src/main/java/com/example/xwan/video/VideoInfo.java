package com.example.xwan.video;

import java.io.Serializable;

/**
 * 播放器播放事情的VIDEO信息对象
 * 
 * @author Administrator
 * 
 */
public class VideoInfo implements Serializable{
	private int Video_id;
	private int Video_No;
	private String Video_title;
	private String Video_img;
	private String Video_url;
	private String Video_time = "";

	public VideoInfo() {

	}

	public VideoInfo(int no, String title, String img, String url, String time) {
		this.Video_No = no;
		this.Video_title = title;
		this.Video_img = img;
		this.Video_url = url;
		this.Video_time = time;
	}

	public VideoInfo(int id, int no, String title, String img, String url, String time) {
		this.Video_id = id;
		this.Video_No = no;
		this.Video_title = title;
		this.Video_img = img;
		this.Video_url = url;
		this.Video_time = time;
	}

	public String getVideo_time() {
		return Video_time;
	}

	public void setVideo_time(String video_time) {
		Video_time = video_time;
	}

	public int getVideo_id() {
		return Video_id;
	}

	public void setVideo_id(int video_id) {
		Video_id = video_id;
	}

	public int getVideo_No() {
		return Video_No;
	}

	public void setVideo_No(int video_No) {
		Video_No = video_No;
	}

	public String getVideo_title() {
		return Video_title;
	}

	public void setVideo_title(String video_title) {
		Video_title = video_title;
	}

	public String getVideo_img() {
		return Video_img;
	}

	public void setVideo_img(String video_img) {
		Video_img = video_img;
	}

	public String getVideo_url() {
		return Video_url;
	}

	public void setVideo_url(String video_url) {
		Video_url = video_url;
	}

	@Override
	public String toString() {
		return "VideoInfo [Video_id=" + Video_id + ", Video_No=" + Video_No + ", Video_title="
				+ Video_title + ", Video_img=" + Video_img + ", Video_url=" + Video_url + "]";
	}

}
