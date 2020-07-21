package com.rapandroid.kamov5.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShow implements Parcelable {
    private String photo, title, release, language,overview;
    private Double vote_avg;
    private Integer id;

    public TvShow(){

    }

    protected TvShow(Parcel in) {
        id = (Integer) in.readValue(Integer.class.getClassLoader());
        photo = in.readString();
        title = in.readString();
        release = in.readString();
        language = in.readString();
        overview = in.readString();
        vote_avg = (Double) in.readValue(Double.class.getClassLoader());
    }


    public TvShow(JSONObject object){
        try{
            Integer id = object.getInt("id");
            String titlejs = object.getString("name");
            String photojs = object.getString("poster_path");
            String overviewjs = object.getString("overview");
            String releasejs = object.getString("first_air_date");
            String languagejs = object.getString("original_language");
            Double vote_avgjs = object.getDouble("vote_average");

            this.id = id;
            this.title = titlejs;
            this.photo = photojs;
            this.overview = overviewjs;
            this.release = releasejs;
            this.language = languagejs;
            this.vote_avg = vote_avgjs;
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeString(photo);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeValue(vote_avg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(Double vote_avg) {
        this.vote_avg = vote_avg;
    }

}
