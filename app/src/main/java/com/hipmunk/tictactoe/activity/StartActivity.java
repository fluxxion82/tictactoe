package com.hipmunk.tictactoe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hipmunk.tictactoe.R;
import com.hipmunk.tictactoe.activity.game.GameState;
import com.hipmunk.tictactoe.activity.game.TicTacToeActivity;

/**
 * Created by salbury on 5/4/15.
 *
 * Some things I wanted to show with this demo is that I tried to think ahead and be flexible with adding other games, particularly board games.
 * I need to make the models better, and the Strategy stuff didn't turn out how I initially planned. Wanted to use a 'Strategy' Pattern, but it
 * turned into a decorator pattern basically. I could refactor it to fit the strategy pattern, but that's too much work right now. Tried to use
 * basically MVP framework...tried to keep the view stuff(TicTacToeActivity) pretty light and keep most of the business logic in the presenter.
 * If the other activities did more, they would probably get presenter's too.
 *
 */
public class StartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView text = (TextView) findViewById(R.id.welcome_text);
        text.setText(R.string.welcome);

        Button play = (Button) findViewById(R.id.play_ttt);
        play.setText(R.string.play_ttt);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(StartActivity.this, TicTacToeActivity.class);
                final Bundle bundle = new Bundle();
                new AlertDialog.Builder(StartActivity.this)
                        .setTitle(R.string.you_decide)
                        .setMessage(R.string.decide_who_starts_first)
                        .setNegativeButton(R.string.you_start_first, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        bundle.putInt("first", GameState.PLAYER1.getValue());//don't like this...it's confusing
                                        bundle.putInt("second", GameState.PLAYER2.getValue());//don't like this...it's confusing
                                        i.putExtras(bundle);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setPositiveButton(R.string.you_start_second, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bundle.putInt("second", GameState.PLAYER2.getValue());//don't like this...it's confusing
                                bundle.putInt("first", -2); //don't like this...it's confusing. but sets first player to computer
                                i.putExtras(bundle);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            }
        });
    }
}
