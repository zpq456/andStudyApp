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
    private boolean mSolved;//범죄가 해결되었는지 확인

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
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
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}
