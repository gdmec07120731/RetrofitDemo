package com.ppjun.retrofitdemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ppjun.retrofitdemo.bean.GitReturnCode;
import com.ppjun.retrofitdemo.bean.Repos;
import com.ppjun.retrofitdemo.retrofit.Git;
import com.ppjun.retrofitdemo.retrofit.GitApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_PICTURES;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                Log.i("TAG", "1");
                Git.getInstance().getGitService().listRepos("gdmec07120731").enqueue(new Callback<List<Repos>>() {
                    @Override
                    public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {

                        Log.i("TAG", response.body().get(0).getName());
                    }

                    @Override
                    public void onFailure(Call<List<Repos>> call, Throwable t) {
                        Log.i("TAG", t.toString());
                    }
                });
                break;
            case R.id.post:
                Git.getInstance().getGitService().init(GitApi.INIT_URL, "g00000001", "1").enqueue(new Callback<GitReturnCode>() {
                    @Override
                    public void onResponse(Call<GitReturnCode> call, Response<GitReturnCode> response) {
                        Log.i("", response.body().getDesc());
                    }

                    @Override
                    public void onFailure(Call<GitReturnCode> call, Throwable t) {

                    }
                });
                break;
            case R.id.upload:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0x002);

                break;
            case R.id.download:

                Git.getInstance().getGitService().download(GitApi.IMAGE_URL).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                saveFile(response);
                            }
                        }).start();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x002 && resultCode == RESULT_OK && data != null) {
            String imagePath = getImagePath(data);

            post(imagePath);

        }
    }

    private String getImagePath(Intent data) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imagePath = cursor.getString(columnIndex);
        return imagePath;

    }


    private void post(String imagepath) {
        RequestBody uuid = RequestBody.create(MediaType.parse("multipart/form-data"), "C9E0B11C1B264A8991F4864C600B3D87");
        RequestBody grand = RequestBody.create(MediaType.parse("multipart/form-data"), "C9E0B11C1B264A8991F4864C600B3D87");
        RequestBody plate_number = RequestBody.create(MediaType.parse("multipart/form-data"), "C9E0B11C1B264A8991F4864C600B3D87");
        RequestBody frame_number = RequestBody.create(MediaType.parse("multipart/form-data"), "C9E0B11C1B264A8991F4864C600B3D87");
        RequestBody file1 = RequestBody.create(MediaType.parse("image/png"), new File(imagepath));

        String image[] = imagepath.split("/");

        MultipartBody.Part photo = MultipartBody.Part.createFormData("file1", image[image.length - 1], file1);
        MultipartBody.Part photo2 = MultipartBody.Part.createFormData("file2", image[image.length - 1], file1);
        Git.getInstance().getGitService().addCarInfo(GitApi.ADD_CAR_INFO_URL, uuid,
                grand, plate_number, frame_number, photo, photo2).enqueue(new Callback<GitReturnCode>() {
            @Override
            public void onResponse(Call<GitReturnCode> call, Response<GitReturnCode> response) {
                Log.i("TAG", response.body().getMsg());
            }

            @Override
            public void onFailure(Call<GitReturnCode> call, Throwable t) {
                Log.i("TAG", t.toString());
            }
        });
    }
    private void saveFile(Response<ResponseBody> response){
        File file;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            file=new File(Environment.getExternalStorageDirectory()+File.separator+DIRECTORY_PICTURES+File.separator+ UUID.randomUUID().toString()+".png");

        }else{
            file=new File(getCacheDir()+File.separator+  UUID.randomUUID().toString()+".png");
        }

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream is = response.body().byteStream();
        byte[] bytes = new byte[1024];
        try {
            FileOutputStream os = new FileOutputStream(file);
            int len = 0;

            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
