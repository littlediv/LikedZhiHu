package name.littlediv.likezhihu.http;

import com.squareup.okhttp.ResponseBody;

import name.littlediv.likezhihu.bean.Start;
import name.littlediv.likezhihu.bean.Themes;
import name.littlediv.likezhihu.utils.Constant;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by win7 on 2016/6/12.
 */
public interface APIService {

    @GET(Constant.START)
    Call<Start> getStart();

    @GET(Constant.THEMES)
    Call<String> getThemes();

}

