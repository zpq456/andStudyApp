package kr.ac.hansung.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2016-08-06.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context){
        mAssets = context.getAssets();
        //이 생성자는 deprecated 되었지만 호환성 유지를 위해 필요하다.
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        //재생할 음원의 최대개수, 재생할 오디오 스트림의 종류, 샘플 레이트 컨버터의 퀄리티
        loadSounds();//에셋의 파일 내역을 찾음
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){ return; }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release(){
        mSoundPool.release();
    }

    private void loadSounds() {
        String[] soundNames;
        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");//찾은 음원 개수 알려줌
        }catch (IOException ioe){//에셋에 파일이 없을때 오류문구
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        for(String filename : soundNames){
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);//리스트에 음원 추가
            }catch (IOException ioe){
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds(){
        return mSounds;
    }

}
