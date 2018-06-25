package com.star.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MakeupActivity extends AppCompatActivity {

    private TextView mWordsLeftTextView;
    private EditText mEditText;
    private Button mOKButton;
    private TextView mIntroductionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_makeup);

        mWordsLeftTextView = (TextView) findViewById(R.id.words_left);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mOKButton = (Button) findViewById(R.id.ok);
        mIntroductionTextView = (TextView) findViewById(R.id.introduction);

        final Story story = new Story(this);

        final int[] wordsLeft = {story.getCount()};

        mWordsLeftTextView.setText(wordsLeft[0] + " word(s) left");

        String wordToReplace = story.findNextWordToReplace();

        mEditText.setHint(wordToReplace);

        mIntroductionTextView.setText("Please type a/an " + wordToReplace);

        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String word = mEditText.getText().toString();

                if (story.isInDictionary(word)) {

                    story.replaceWord(word);

                    wordsLeft[0]--;

                    if (wordsLeft[0] == 0) {
                        Intent intent = new Intent(MakeupActivity.this, StoryActivity.class);
                        intent.putExtra("story", story.getStory());

                        startActivity(intent);
                    } else {
                        mWordsLeftTextView.setText(wordsLeft[0] + " word(s) left");

                        String wordToReplace = story.findNextWordToReplace();

                        mEditText.setText("");
                        mEditText.setHint(wordToReplace);

                        mIntroductionTextView.setText("Please type a/an " + wordToReplace);

                        Toast.makeText(MakeupActivity.this, "Great! Keep going!",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MakeupActivity.this, "Not in dictionary.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
