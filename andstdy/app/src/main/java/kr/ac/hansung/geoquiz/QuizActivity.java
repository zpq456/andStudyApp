package kr.ac.hansung.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX2 = "Array";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mUndoButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question1, false),
            new TrueFalse(R.string.question2, true),
            new TrueFalse(R.string.question3, false),
            new TrueFalse(R.string.question4, false),
            new TrueFalse(R.string.question5, true),
            new TrueFalse(R.string.question6, true)
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater = false;
    private boolean[] cheatChecker = new boolean[mQuestionBank.length];

    private void updateQuestion() {
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        if(cheatChecker[mCurrentIndex] == true) mIsCheater = true;
    }

    private void checkAnswer(boolean userPressTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

        //if(cheatChecker[mCurrentIndex] == true) mIsCheater = true;

        if(mIsCheater) {
            messageResId = R.string.judgment_toast;
        }
        else {
            if (userPressTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        //각 문제마다 컨닝여부 확인
        for(int i=0;i<mQuestionBank.length;i++){
            cheatChecker[i] = false;
        }

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                //CheatActivity의 시작
                //startActivity(i);//엑티비티만 이동함
                startActivityForResult(i,0);//엑티비티 이동후 결과값 반환 i는 인텐트코드, 0은 사용자가 설정한 요청코드
            }

        });

        mUndoButton = (Button)findViewById(R.id.undo_button);
        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(mCurrentIndex > 0)
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                else
                    mCurrentIndex = mQuestionBank.length - 1;

                mIsCheater = false;
                updateQuestion();
            }
        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            cheatChecker = savedInstanceState.getBooleanArray(KEY_INDEX2);
        }

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_INDEX2, cheatChecker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        cheatChecker[mCurrentIndex] = mIsCheater;
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    public void onDestory(){
        super.onDestroy();
        Log.d(TAG, "onDestory() called");
    }
}
