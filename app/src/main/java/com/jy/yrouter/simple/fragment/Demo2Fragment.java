package com.jy.yrouter.simple.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jy.yrouter.annotation.RouterPage;
import com.jy.yrouter.common.FragmentPostcard;
import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.activity.TestUriRequestActivity;
import com.jy.yrouter.simple.constant.DemoConstant;


@RouterPage(path = DemoConstant.TEST_DEMO_FRAGMENT_2)
public class Demo2Fragment extends Fragment {

    public static Demo2Fragment newInstance() {
        return new Demo2Fragment();
    }

    public Demo2Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo_2, container, false);


        v.findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentPostcard(Demo2Fragment.this, DemoConstant.JUMP_WITH_REQUEST)
                        .activityRequestCode(100)
                        .putExtra(TestUriRequestActivity.INTENT_TEST_INT, 1)
                        .putExtra(TestUriRequestActivity.INTENT_TEST_STR, "str")
                        .overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
                        .onComplete(new OnCompleteListener() {
                            @Override
                            public void onSuccess(@NonNull Postcard postcard) {
                                Toast.makeText(postcard.getContext(), "跳转成功", Toast.LENGTH_LONG).show();
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

