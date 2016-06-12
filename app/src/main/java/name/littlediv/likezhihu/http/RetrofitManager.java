package name.littlediv.likezhihu.http;

import name.littlediv.likezhihu.utils.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by win7 on 2016/6/12.
 */
public class RetrofitManager {
    private static APIService apiService= null;

    private RetrofitManager(){}

    public static APIService getApiService() {
        if (apiService == null) {
            synchronized (RetrofitManager.class) {
                apiService= new Retrofit.Builder()
                        .baseUrl(Constant.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(OkhttpManager.client)
                        .build().create(APIService.class);
            }

        }

        return apiService;
    }

}
