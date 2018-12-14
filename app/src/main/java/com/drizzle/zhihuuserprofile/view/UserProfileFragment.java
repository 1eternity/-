package com.drizzle.zhihuuserprofile.view;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drizzle.zhihuuserprofile.DensityUtil;
import com.drizzle.zhihuuserprofile.R;
import com.drizzle.zhihuuserprofile.widget.ZhihuUserProfileLayout;

import org.w3c.dom.Text;

public class UserProfileFragment extends Fragment {

    private static final String TAG = "UserProfileFragment";

    private LinearLayout mTabLayout;
    private RelativeLayout relativeLayout;
    private ViewPager mViewPager;
    private View mAvatar;
//    mCover
    private UserInfoFragment mHomepageFragment, mDetailFragment;
    private InfoPagerAdapter mPagerAdapter;
    private ZhihuUserProfileLayout mZhihuUserProfileLayout;
    private WaveView waveView;
    private LinearLayout radioGroup;
    private  boolean isDown=false;
    private LinearLayout line1;
    private LinearLayout linearLayout;
    private TextView pm2_5;

    public UserProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mTabLayout = (LinearLayout) parentView.findViewById(R.id.user_profile_tablayout);
        mViewPager = (ViewPager) parentView.findViewById(R.id.user_profile_viewpager);
        mAvatar = parentView.findViewById(R.id.user_profile_avatar);
        relativeLayout = (RelativeLayout) parentView.findViewById(R.id.relativeLayout);
        pm2_5 = (TextView) parentView.findViewById(R.id.home_pm2_5_value2);
        waveView = (WaveView) parentView.findViewById(R.id.wave_button);
        linearLayout = (LinearLayout) parentView.findViewById(R.id.linearLayout);
        radioGroup = (LinearLayout) parentView.findViewById(R.id.radioGroup);
        line1 = (LinearLayout) parentView.findViewById(R.id.text1);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.i(TAG, "屏幕高度_measuredHeight"+dm.heightPixels);
        // 应用区域
        Rect outRect1 = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        Log.i(TAG, "应用区域_measuredHeight"+outRect1.height());


        radioGroup.measure(width,height);
        int measuredHeight = radioGroup.getMeasuredHeight();
        Log.i(TAG, " radioGroup_measuredHeight"+DensityUtil.px2dip(getActivity(),measuredHeight));

        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "状态栏高度_measuredHeight"+statusBarHeight1);

        initViewAndData(parentView);
        return parentView;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initViewAndData(View parent) {

        waveView.setColor(0xffffff);
        waveView.setStyle(Paint.Style.STROKE);
        waveView.setWidth(5);
        waveView.setInitialRadius(230f);
        waveView.setMaxRadius(420f);
        waveView.setDuration(3000);
        waveView.setSpeed(800);
        waveView.setInterpolator(new LinearInterpolator());
        waveView.start();
//        mCover.setAlpha(0.5f);
        mHomepageFragment = UserInfoFragment.newInstance(UserInfoFragment.TYPE_USER_HOMEPAGE);
        mDetailFragment = UserInfoFragment.newInstance(UserInfoFragment.TYPE_USER_DETAIL);
        mPagerAdapter = new InfoPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
        mZhihuUserProfileLayout = (ZhihuUserProfileLayout) parent.findViewById(R.id.user_profile_md_layout);
        mZhihuUserProfileLayout.setNestedScrollViewProvider(mHomepageFragment);

        mZhihuUserProfileLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.i(TAG, "scrollX: "+i1+"----old_scrollX:"+i3);
                relativeLayout.setPadding(0,i1-5,0,0);
                waveView.setPadding(0,i1,0,0);
                if(i1==0){
//                    AlphaAnimation anim = new AlphaAnimation(0, 1);
//                    anim.setDuration(100);
//                    anim.setInterpolator(getActivity(), android.R.interpolator.linear);
//                    radioGroup.setAnimation(anim);
                    radioGroup.setVisibility(View.VISIBLE);
                }else{
//                    AlphaAnimation anim = new AlphaAnimation(1, 0);
//                    anim.setDuration(100);
//                    anim.setInterpolator(getActivity(), android.R.interpolator.linear);
//                    radioGroup.setAnimation(anim);
                    radioGroup.setVisibility(View.GONE);
                }




            }
        });
        mZhihuUserProfileLayout.setOnCollapsingListener(new ZhihuUserProfileLayout.OnCollapsingListener() {
            @Override
            public void onCollapsing(float progress) {

                Log.i(TAG, "dispatchTouchEvent:isDown  "+isDown);
                Log.i(TAG, "onCollapsing: "+progress);
//                mCover.setAlpha(1.0f - progress / 2);
                if (progress < 0.7f) {
                    waveView.setAlpha(0f);
//                    imageLick.setVisibility(View.GONE);
//                    if(!isDown) {
//                        Log.i(TAG, "dispatchTouchEvent: 松开");
//                        linearLayout.setAnimation(moveToViewLocation());
//                    }
//                    HideAnimation(radioGroup);
                } else if (progress > 0.9f&&progress==1.0f) {
                    mZhihuUserProfileLayout.stopNestedScroll();
                    waveView.setAlpha(1f);
//                    imageLick.setVisibility(View.VISIBLE);
//                    if(!isDown) {
//                        Log.i(TAG, "dispatchTouchEvent: 按下");
//                        linearLayout.setAnimation(moveToViewLocation());
//                    }
                } else {
                    Log.i(TAG, "onCollapsing: 这 ");
                    waveView.setAlpha((progress - 0.8f) * 10);
//                    ShowAnimation(radioGroup);
                    AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.ra);
//                    radioGroup.startAnimation(alphaAnimation);
                }
            }

            @Override
            public void dispatchTouchEvent(MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isDown=true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isDown=false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isDown=true;
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mZhihuUserProfileLayout.setNestedScrollViewProvider(mHomepageFragment);
                        mDetailFragment.getNestedScrollView().scrollToPosition(0);
                        break;
                    case 1:
                        mZhihuUserProfileLayout.setNestedScrollViewProvider(mDetailFragment);
                        mHomepageFragment.getNestedScrollView().scrollToPosition(0);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private static  LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR=new LinearOutSlowInInterpolator();



    public static void ShowAnimation(View view){
        view.setVisibility(View.VISIBLE);
//        ViewCompat.animate(view)
//                .scaleX(1.0f)
//                .scaleY(1.0f)
//                .alpha(1.0f)
//                .setDuration(400)
//                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
//                .start();
        ViewCompat.animate(view)
                .translationY(0)
                .setDuration(150)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .start();
    }

    public static void HideAnimation(View view){
        view.setVisibility(View.VISIBLE);
//        ViewCompat.animate(view)
//                .scaleX(0.0f)
//                .scaleY(0.0f)
//                .alpha(0.0f)
//                .setDuration(400)
//                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
//                .start();
        ViewCompat.animate(view)
                .translationY(300)
                .setDuration(150)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .start();
    }

    public static Animation VisibleAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);//第一个参数开始的透明度，第二个参数结束的透明度
        alphaAnimation.setDuration(500);//多长时间完成这个动作
        return alphaAnimation;
    }
    public static Animation GsonAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);//第一个参数开始的透明度，第二个参数结束的透明度
        alphaAnimation.setDuration(500);//多长时间完成这个动作
        return alphaAnimation;
    }

    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    private class InfoPagerAdapter extends FragmentPagerAdapter {

        InfoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mHomepageFragment;
                case 1:
                    return mDetailFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.homepage);
                case 1:
                    return getString(R.string.info_detail);
            }
            return super.getPageTitle(position);
        }
    }

}
