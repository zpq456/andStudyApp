package kr.ac.hansung.photogallery;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Owner on 2016-07-14.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();//프래그먼트 매니져 받기
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);//프레그먼트 매니져를 통해서 컨테이너를 가리킴

        if(fragment==null){//fragment가 비어있으면 새걸로 만들어줌
            fragment = createFragment();
            fm.beginTransaction() // fragment의 인스턴스를 만들고 반환
                    .add(R.id.fragment_container, fragment) // 인스턴스에 프래그먼트 객체를 추가
                    .commit();//커밋함
        }
    }
}
