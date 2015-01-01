package com.beetle.face;

import android.app.Application;
import android.util.Log;

import com.beetle.NativeWebRtcContextRegistry;
import com.beetle.face.model.ContactDB;
import com.beetle.im.IMService;

import com.google.code.p.leveldb.LevelDB;

import java.io.File;

/**
 * Created by houxh on 14-12-31.
 */
public class FaceApplication  extends Application {
    private final static String TAG = "face";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "app application create");

        LevelDB ldb = LevelDB.getDefaultDB();
        String dir = getFilesDir().getAbsoluteFile() + File.separator + "db";
        Log.i(TAG, "dir:" + dir);
        ldb.open(dir);

        ContactDB cdb = ContactDB.getInstance();
        cdb.setContentResolver(getApplicationContext().getContentResolver());
        cdb.monitorConctat(getApplicationContext());

        new NativeWebRtcContextRegistry().register(this);

        IMService im =  IMService.getInstance();
        im.setHost(Config.HOST);
        im.setPort(Config.PORT);

        //already login
        if (Token.getInstance().uid > 0) {
            im.setUid(Token.getInstance().uid);
            im.start();
        }
    }
}
