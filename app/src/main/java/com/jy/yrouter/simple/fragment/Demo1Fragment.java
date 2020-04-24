package com.jy.yrouter.simple.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jy.yrouter.annotation.RouterPage;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.common.PageAnnotationHandler;
import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.fragment.FragmentBuildPostcard;
import com.jy.yrouter.fragment.FragmentTransactionPostcard;
import com.jy.yrouter.simple.R;


/**
 * @description fragment to fragment跳转与创建示例
 * @date: 2020/4/23 16:04
 * @author: jy
 */
@RouterPage(path = DemoConstant.TEST_DEMO_FRAGMENT_1)
public class Demo1Fragment extends Fragment {

    public static Demo1Fragment newInstance() {
        return new Demo1Fragment();
    }

    public Demo1Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo_1, container, false);


        //跳转fragment
        v.findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentTransactionPostcard(Demo1Fragment.this.getActivity(), PageAnnotationHandler.SCHEME_HOST + DemoConstant.TEST_DEMO_FRAGMENT_2)
                        .replace(R.id.fragment_container)
                        .onComplete(new OnCompleteListener() {
                            @Override
                            public void onSuccess(@NonNull Postcard postcard) {
                            }

                            @Override
                            public void onError(@NonNull Postcard postcard, int resultCode) {

                            }
                        })
                        .start();
            }
        });

        //创建fragment
        v.findViewById(R.id.btn_cus_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentBuildPostcard(Demo1Fragment.this.getContext(), PageAnnotationHandler.SCHEME_HOST + DemoConstant.TEST_DEMO_FRAGMENT_2)
                        .onComplete(new OnCompleteListener() {
                            @Override
                            public void onSuccess(@NonNull Postcard postcard) {
                                Fragment fragment = postcard.getField(Fragment.class, FragmentBuildPostcard.FRAGMENT);
                                Demo1Fragment.this.getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, fragment)
                                        .commitAllowingStateLoss();
                            }

                            @Override
                            public void onError(@NonNull Postcard postcard, int resultCode) {

                            }
                        })
                        .start();
            }
        });
        return v;
    }
}
