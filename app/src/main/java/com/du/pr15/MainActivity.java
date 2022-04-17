package com.du.pr15;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.du.pr15.data.MyDbHandler;
import com.du.pr15.model.Word;

import java.util.Optional;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDbHandler myDbHandler;
    private Button add;
    private Button update;
    private Button delete;
    private TextView textView;
    private EditText editText;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHandler = new MyDbHandler(this);

        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);

        updateContents();
        handleVisibility();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        String data;
        Word word;
        switch (view.getId()){
            case R.id.add:
                data = editText.getText().toString();
                word = new Word();
                word.setWord(data);
                myDbHandler.addWord(word);
                updateContents();
                handleVisibility();
                break;

            case R.id.update:
                data = editText.getText().toString();
                word = new Word();
                word.setWord(data);
                myDbHandler.updateWord(word);
                updateContents();
                handleVisibility();
                break;

            case R.id.delete:
                word = new Word();
                word.setWord(textView.getText().toString());
                myDbHandler.deleteWord(word);
                updateContents();
                handleVisibility();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateContents() {
        Optional<Word> optionalWord = myDbHandler.readWord();
        if (optionalWord.isPresent()) {
            String word = optionalWord.get().getWord();
            textView.setText(word);
        }else{
            textView.setText("");
        }
    }

    public void handleVisibility(){
        if(!textView.getText().toString().isEmpty()){
            add.setEnabled(false);
            update.setEnabled(true);
            delete.setEnabled(true);
        }else{
            update.setEnabled(false);
            delete.setEnabled(false);
            add.setEnabled(true);
        }

        editText.setText("");
    }
}