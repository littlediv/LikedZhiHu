package name.littlediv.likezhihu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.littlediv.likezhihu.R;
import name.littlediv.likezhihu.activity.LoginActivity;
import name.littlediv.likezhihu.bean.NewsThemes;
import name.littlediv.likezhihu.http.APIService;
import name.littlediv.likezhihu.http.OkhttpManager;
import name.littlediv.likezhihu.utils.Constant;
import name.littlediv.likezhihu.utils.NetWorkUtil;
import name.littlediv.likezhihu.utils.PrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by win7 on 2016/6/12.
 */
public class MenuFragment extends BaseFragment {


    List<NewsThemes> newsThemes;
    NewsTypeAdapter adapter;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_backup)
    TextView tvBackup;
    @Bind(R.id.tv_download)
    TextView tvDownload;
    @Bind(R.id.ll_menu)
    LinearLayout llMenu;
    @Bind(R.id.tv_main)
    TextView tvMain;
    @Bind(R.id.lv_item)
    ListView lvItem;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_menu, container, false);
        ButterKnife.bind(this, view);
        lvItem = (ListView) view.findViewById(R.id.lv_item);

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

    @OnClick(R.id.tv_main)
    public void click() {
        Toast.makeText(mActivity,"aa", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void initData() {
        super.initData();
        newsThemes = new ArrayList<>();
        if (NetWorkUtil.isNetWorkAvailable(mActivity)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(OkhttpManager.client)
                    .baseUrl(Constant.BASEURL)
                    .build();

            retrofit.create(APIService.class).getThemes().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String json = response.body();
                    PrefUtil.putStringPref(mActivity, Constant.THEMES, json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        parseJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        } else {
            String json = PrefUtil.getStringPref(mActivity, Constant.THEMES, "");
            try {
                JSONObject jsonObject = new JSONObject(json);
                parseJson(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseJson(JSONObject response) {
        try {
            JSONArray itemsArray = response.getJSONArray("others");
            for (int i = 0; i < itemsArray.length(); i++) {
                NewsThemes newsListItem = new NewsThemes();
                JSONObject itemObject = itemsArray.getJSONObject(i);
                newsListItem.setTitle(itemObject.getString("name"));
                newsListItem.setId(itemObject.getString("id"));
                newsThemes.add(newsListItem);
            }
            adapter = new NewsTypeAdapter();
            lvItem.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class NewsTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsThemes.size();
        }

        @Override
        public Object getItem(int position) {
            return newsThemes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.left_menu_list_item, parent, false);
            }
            TextView tv_item = (TextView) convertView
                    .findViewById(R.id.tv_item);
            tv_item.setText(newsThemes.get(position).getTitle());
            return convertView;
        }
    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_main:
//                ((MainActivity) mActivity).loadLatest();
//                ((MainActivity) mActivity).closeMenu();
//                break;
//        }
//    }
//

    @OnClick(R.id.tv_login)
    public void login() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivity(intent);
    }

}
