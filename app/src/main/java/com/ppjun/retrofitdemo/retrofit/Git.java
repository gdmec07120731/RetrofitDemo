package com.ppjun.retrofitdemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Package :com.ppjun.retrofitdemo.retrofit
 * Description :
 * Author :Rc3
 * Created at :2016/10/21 14:07.
 */

public class Git {
    private static volatile Git mInstance = new Git();
    private GitService mGitService;

    public Git() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GitApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGitService = retrofit.create(GitService.class);
    }

    public static Git getInstance() {

        if (mInstance == null) {
            Git mIns = mInstance;
            synchronized (Git.class) {

                if (mIns == null) {

                    mIns = new Git();
                    mInstance = mIns;
                }
            }
        }
        return mInstance;
    }

    public GitService getGitService() {
        return mGitService;
    }

}
