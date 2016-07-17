package kr.ac.hansung.criminallntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Owner on 2016-07-14.
 */
public abstract class SingleFragmentActivity extends FragmentActivity{

    protected abstract Fragment createFragment();
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

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
