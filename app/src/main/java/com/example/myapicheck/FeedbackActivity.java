package com.example.myapicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    EditText name, feedback;
    Button sendbutton, clearbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.feedback_name);
        feedback = findViewById(R.id.feedback_text);
        sendbutton = findViewById(R.id.sendButton_id);
        clearbutton = findViewById(R.id.clearButton_id);

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                feedback.setText("");
            }
        });

        if (name != null && feedback != null) {
            sendbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uname = name.getText().toString();
                    String ufeed = feedback.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/email");
                    String sub[] = {"nj.non.00f@gmail.com"};
                    String head = "Feedback from Arg News App";
                    intent.putExtra(Intent.EXTRA_EMAIL, sub);
                    intent.putExtra(Intent.EXTRA_SUBJECT, head);
                    intent.putExtra(Intent.EXTRA_TEXT, "Name :" + uname + "\n Feedback :" + ufeed);
                    startActivity(Intent.createChooser(intent, "select mailbox"));
                }
            });

        } else {
            Toast.makeText(this, "put any text please! ", Toast.LENGTH_SHORT).show();
        }

    }

}