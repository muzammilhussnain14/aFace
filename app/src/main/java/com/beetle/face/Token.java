package com.beetle.face;

import com.google.code.p.leveldb.LevelDB;
import com.google.gson.annotations.SerializedName;

/**
 * Created by houxh on 14-8-11.
 */
public class Token {
    private static Token instance;
    public static Token getInstance() {
        if (instance == null) {
            instance = new Token();
            instance.load();
        }
        return instance;
    }

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("expires_in")
    public int expireTimestamp;
    public long uid;

    public void save() {
        LevelDB db = LevelDB.getDefaultDB();
        try {
            db.set("access_token", accessToken);
            db.set("refresh_token", refreshToken);
            db.setLong("token_expire", expireTimestamp);
            db.setLong("token_uid", uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        LevelDB db = LevelDB.getDefaultDB();
        try {
            accessToken = db.get("access_token");
            refreshToken = db.get("refresh_token");
            expireTimestamp = (int)db.getLong("token_expire");
            uid = db.getLong("token_uid");
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }
}
