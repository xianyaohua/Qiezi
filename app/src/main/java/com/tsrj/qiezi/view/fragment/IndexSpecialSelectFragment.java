package com.tsrj.qiezi.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.SimpleBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;
import com.tsrj.qiezi.R;
import com.tsrj.qiezi.glide.GlideApp;
import com.tsrj.qiezi.utils.ToastUtil;
import com.tsrj.qiezi.view.fragment.base.BaseFragment;
import com.tsrj.qiezi.view.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页精选
 */
public class IndexSpecialSelectFragment extends BaseFragment {

    @BindView(R.id.iv_history)
    ImageView ivHistory;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.banner)
    XBanner banner;
    //测试数据
    private String[] urls=new String[]{"https://bysq.blob.core.windows.net/dev/avatar/5427515/20190803181114_2729_400X400.jpg",
            "http://youngmonster.oss-cn-shanghai.aliyuncs.com/Placeholder/lALPBbCc1Vpo6y7NAsbNAsY_710_710.png",
            "https://bysq.blob.core.windows.net/dev/post/5427515/20190813222638_47_960X1280.jpg"
    };
    private ArrayList<BannerEntity> bannerList=new ArrayList<>();
    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_index_special_select;
    }

    @Override
    public void onContentViewSeted() {
        banner.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(this.getActivity()) / 2));
        for(int i=0;i<urls.length;i++){
            bannerList.add(new BannerEntity(urls[i]));
        }
        banner.setPageTransformer(Transformer.Accordion);
        banner.setAutoPlayAble(urls.length > 1);
        banner.setBannerData(bannerList);
        setListener();
    }

    @OnClick({R.id.iv_history, R.id.iv_download, R.id.iv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_history:
                break;
            case R.id.iv_download:
                break;
            case R.id.iv_scan:
                break;
        }
    }


    /**
     * 初始化XBanner
     */
    private void setListener() {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                ToastUtil.show(IndexSpecialSelectFragment.this.getActivity(), "点击了第" + (position + 1) + "图片");
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //在此处使用图片加载框架加载图片，demo中使用glide加载，可替换成自己项目中的图片加载框架
                //TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                //String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                GlideApp.with(IndexSpecialSelectFragment.this.getActivity()).load(((BannerEntity)model).getXBannerUrl()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });
    }
    /**
     * 为了更好的体验效果建议在下面两个生命周期中调用下面的方法
     **/
    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    public class BannerEntity extends SimpleBannerInfo {
        private String url;
        public BannerEntity(String url){
            this.url=url;
        }
        @Override
        public Object getXBannerUrl() {
            return url;
        }
    }
}
