package com.zyh.wanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 88421876
 * @date 2019/1/21
 */
public class RegisterLogin implements Parcelable {
    private String username;
    private String password;
    private String icon;
    private int type;
    private List<Integer> collectIds;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.icon);
        dest.writeInt(this.type);
        dest.writeList(this.collectIds);
    }

    public RegisterLogin() {
    }

    protected RegisterLogin(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.icon = in.readString();
        this.type = in.readInt();
        this.collectIds = new ArrayList<>();
        in.readList(this.collectIds, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<RegisterLogin> CREATOR = new Parcelable.Creator<RegisterLogin>() {
        @Override
        public RegisterLogin createFromParcel(Parcel source) {
            return new RegisterLogin(source);
        }

        @Override
        public RegisterLogin[] newArray(int size) {
            return new RegisterLogin[size];
        }
    };
}

