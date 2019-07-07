package com.example.piyush.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startAgain;
    private TextView display,highscore;

    private String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        highscore=findViewById(R.id.highScore);

        score=getIntent().getExtras().get("score").toString();

        display=findViewById(R.id.displayScore);
        startAgain=findViewById(R.id.play_again);
        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        display.setText("Score: "+score);

        SharedPreferences sharedPreferences=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int high=sharedPreferences.getInt("HIGH_SCORE",0);

        int p=Integer.parseInt(score);
        if(p>high)
        {
            highscore.setText("High Score: "+p);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("HIGH_SCORE",p);
            editor.commit();
        }
        else
        {
            highscore.setText("High Score: "+high);
        }
    }
}
