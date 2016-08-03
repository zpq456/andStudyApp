package kr.ac.hansung.criminallntent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Owner on 2016-07-12.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    Date mDate;//범죄가 발생한 날자
    Date mTime;//범죄 발생 시간
    private boolean mSolved;//범죄가 해결되었는지 확인
    private String mSuspect;

    public Crime(){
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();
        mTime = new Date();
    }
    //getter & setter
    public UUID getId() {
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
    public Date getTime() {
        return mTime;
    }
    public void setTime(Date mTime) {
        this.mTime = mTime;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }

    public String getPhotoFilename(){//파일 이름 얻기
        return "IMG_" + getId().toString() + ".jpg";
    }
}
