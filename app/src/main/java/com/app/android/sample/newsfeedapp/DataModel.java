package com.app.android.sample.newsfeedapp;

public class DataModel {
    String _locationName,_imageName,_postId;

    public DataModel(String _locationName, String _imageName, String _postId) {
        this._locationName = _locationName;
        this._imageName = _imageName;
        this._postId = _postId;
    }

    public String get_locationName() {
        return _locationName;
    }

    public void set_locationName(String _locationName) {
        this._locationName = _locationName;
    }

    public String get_imageName() {
        return _imageName;
    }

    public void set_imageName(String _imageName) {
        this._imageName = _imageName;
    }

    public String get_postId() {
        return _postId;
    }

    public void set_postId(String _postId) {
        this._postId = _postId;
    }
}
