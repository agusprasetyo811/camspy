package org.omaps.data;

public class SpyData {

	private String fileNameVideo;
	private String listVideo;
	private String baseServer;
	private String cameraPath;
	private String videoPath;
	private String cameraType;

	private static SpyData data = new SpyData();

	private SpyData() {
	}

	public static SpyData getData() {
		return data;
	}

	public void setFileNameVideo(String fileNameVideo) {
		this.fileNameVideo = fileNameVideo;
	}

	public String getFileNameVideo() {
		return fileNameVideo;
	}

	public void setListVideo(String listVideo) {
		this.listVideo = listVideo;
	}

	public String getListVideo() {
		return listVideo;
	}

	public void setBaseServer(String baseServer) {
		this.baseServer = baseServer;
	}

	public String getBaseServer() {
		return baseServer;
	}

	public void setCameraPath(String cameraPath) {
		this.cameraPath = cameraPath;
	}

	public String getCameraPath() {
		return cameraPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}

	public String getCameraType() {
		return cameraType;
	}
}
