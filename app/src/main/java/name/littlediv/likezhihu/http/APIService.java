package name.littlediv.likezhihu.http;

import name.littlediv.likezhihu.bean.Start;
import name.littlediv.likezhihu.utils.Constant;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by win7 on 2016/6/12.
 */
public interface APIService {

    @GET(Constant.START)
    Call<Start> getStart();

}

