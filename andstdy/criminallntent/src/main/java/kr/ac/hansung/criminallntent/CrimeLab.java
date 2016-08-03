package kr.ac.hansung.criminallntent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kr.ac.hansung.criminallntent.database.CrimeBaseHelper;
import kr.ac.hansung.criminallntent.database.CrimeCursorWrapper;
import kr.ac.hansung.criminallntent.database.CrimeDbSchema.CrimeTable;
/**
 * Created by Owner on 2016-07-14.
 */
//싱글톤 = 딱 하나의 인스턴스만 생성할 수 있는 클래스
    //장시간 데이터 보존을 하는 솔루션은 아니지만 컨트롤러 클래스와의 데이터 전달이 쉽다.
public class CrimeLab {
    private static CrimeLab sCrimeLab; // s == static이란 뜻이다.

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }
    
    public void deleteCrime(Crime crime){
        String uuidString = crime.getId().toString();//삭제할 항목을 가져옴

        mDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null,null);

        try{
            cursor.moveToFirst();//커서 이동 방식
            while (!cursor.isAfterLast()){//추가하고 다음으로 넘어감
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
        }
        }
        finally {
            cursor.close();
        }
        return crimes;
    }
    public Crime getCrime(UUID id){
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally {
            cursor.close();
        }
    }


    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?", //?을 사용하는것이 옳고 안전하다
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.TIME, crime.getTime().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,// null인 경우 테이블의 모든 열을 의미
                whereClause,
                whereArgs,
                null,//SQL Select 명령문의 groupBy
                null,//SQL Select 명령문의 having
                null//SQL Select 명령문의 orderBy
        );
        return new CrimeCursorWrapper(cursor); // cursor는 쿼리된 결과 데이터를 가져오는 데 사용된다.
    }

    public File getPhotoFile(Crime crime){//올바른 위치를 가리키는 File객체를 반환
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null){
            return null;
        }
        return new File(externalFilesDir, crime.getPhotoFilename());
    }
    
}
