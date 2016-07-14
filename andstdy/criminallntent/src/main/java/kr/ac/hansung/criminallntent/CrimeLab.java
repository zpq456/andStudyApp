package kr.ac.hansung.criminallntent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Owner on 2016-07-14.
 */
//싱글톤 = 딱 하나의 인스턴스만 생성할 수 있는 클래스
    //장시간 데이터 보존을 하는 솔루션은 아니지만 컨트롤러 클래스와의 데이터 전달이 쉽다.
public class CrimeLab {
    private static CrimeLab sCrimeLab; // s == static이란 뜻이다.

    private List<Crime> mCrimes; // Crime을 값으로 가지는 Arr

    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        for(int i=0;i<100;i++){
            Crime crime = new Crime();
            crime.setTitle("범죄 #"+i);
            crime.setSolved(i%2 == 0);//짝수번째 요소는 true로 임의 설정 test용
            mCrimes.add(crime); // 리스트에 crime을 삽입
        }
    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
