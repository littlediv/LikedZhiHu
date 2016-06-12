package name.littlediv.likezhihu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import name.littlediv.likezhihu.MyApplication;
import name.littlediv.likezhihu.R;
import name.littlediv.likezhihu.bean.Start;
import name.littlediv.likezhihu.http.OkhttpManager;
import name.littlediv.likezhihu.http.RetrofitManager;
import name.littlediv.likezhihu.utils.NetWorkUtil;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by win7 on 2016/6/12.
 */
public class SplashActivity extends Activity {
    private ImageView iv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        initImage();


    }

    private void initImage() {
        File dir = getFilesDir();
        final File imgFile = new File(dir, "/start.jpg");

        if (imgFile.exists()) {
            iv_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            iv_start.setImageResource(R.mipmap.start);
        }


        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat (Animation animation){

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
                    RetrofitManager.getApiService().getStart().enqueue(new Callback<Start>() {
                        @Override
                        public void onResponse(Call<Start> call, Response<Start> response) {
                            String imageUrl = response.body().getImg();

                            Request request = new Request.Builder()
                                    .url(imageUrl)
                                    .build();
                            OkhttpManager.client.newCall(request).enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(okhttp3.Call call, IOException e) {
                                    startActivity();
                                }

                                @Override
                                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                    byte[] bytes = response.body().bytes();
                                    saveImage(imgFile, bytes);
                                    startActivity();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Start> call, Throwable t) {
                            startActivity();
                        }
                    });
                }
                else {
                    Toast.makeText(SplashActivity.this, "没有网络连接!", Toast.LENGTH_LONG).show();
                    startActivity();
                }

            }
        });
        iv_start.startAnimation(scaleAnim);

    }

    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void saveImage(File file, byte[] bytes) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
