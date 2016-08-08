package kr.ac.hansung.beatbox;

/**
 * Created by Owner on 2016-08-06.
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");//파일 이름 뽑아내기
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");//뽑아낸 파일이름에서 확장자명 지우기
    }

    public String getAssetPath(){
        return mAssetPath;
    }
    public String getName(){
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer SoundId) {
        this.mSoundId = SoundId;
    }
}
