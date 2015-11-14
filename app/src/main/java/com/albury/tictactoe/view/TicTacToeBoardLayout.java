package com.albury.tictactoe.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.albury.tictactoe.BuildConfig;
import com.albury.tictactoe.R;
import com.albury.tictactoe.activity.game.GameState;
import com.albury.tictactoe.gamerules.TTTRules;

/**
 * Created by salbury on 5/4/15.
 */
public class TicTacToeBoardLayout extends View {
    private static final String TAG = TicTacToeBoardLayout.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static final long FPS_MS = 1000 / 4; //How fast the selection bl
    private static final int MARGIN = 4;
    private static final int MSG_BLINK = 1;

    private final Handler mHandler = new Handler(new MyHandler());

    private final Rect mSrcRect = new Rect();
    private final Rect mDstRect = new Rect();

    private int mSxy;
    private int mOffetX;
    private int mOffetY;

    private Paint mWinPaint;
    private Paint mLinePaint;
    private Paint mBmpPaint;

    private Bitmap mBmpPlayer1;
    private Bitmap mBmpPlayer2;
    private Drawable mDrawableBg;

    private ICellListener mCellListener;

    private final GameState[][] mData = new GameState[3][3];
    int mLastXCoord;
    int mLastYCoord;
    private int mSelectedCell = -1;

    private GameState mSelectedValue = GameState.EMPTY;
    private GameState mCurrentPlayer = GameState.PLAYER1;

    private boolean mBlinkDisplayOff;
    private final Rect mBlinkRect = new Rect();

    public interface ICellListener {
        abstract void onCellSelected(int xCoord, int yCoord);
    }

    public TicTacToeBoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();

        mDrawableBg = getResources().getDrawable(R.drawable.lib_bg);
        setBackgroundDrawable(mDrawableBg);

        mBmpPlayer1 = getResBitmap(R.drawable.lib_cross);
        mBmpPlayer2 = getResBitmap(R.drawable.lib_circle);

        if (mBmpPlayer1 != null) {
            mSrcRect.set(0, 0, mBmpPlayer1.getWidth() - 1, mBmpPlayer1.getHeight() - 1);
        }

        mBmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mLinePaint = new Paint();
        mLinePaint.setColor(0xFFFFFFFF);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Style.STROKE);

        mWinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWinPaint.setColor(0xFFFF0000);
        mWinPaint.setStrokeWidth(10);
        mWinPaint.setStyle(Style.STROKE);
    }

    // Set/Reset the board back to all empty values.
    public void initializeBoard() {
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData.length; j++) {
                mData[i][j] = GameState.EMPTY;
            }

        }

        invalidate();
    }

    /**
     * @param cellXIndex
     * @param cellYIndex
     * @param value      the mark from the player
     */
    public void setCell(int cellXIndex, int cellYIndex, GameState value) {
        if (DEBUG) {
            Log.i(TAG, "setCell");
        }
        mData[cellXIndex][cellYIndex] = value;

        invalidate();
    }

    public void setCellListener(ICellListener cellListener) {
        mCellListener = cellListener;
    }

    public int[] getSelection() {
        if (DEBUG) {
            Log.i(TAG, "getSelection");
        }
        if (mSelectedValue == mCurrentPlayer) {
            return TTTRules.getCoordFromLinearCell(mSelectedCell);
        }

        if (DEBUG) {
            Log.i(TAG, "getSelection RETURNING RESULT");
        }

        int[] result = new int[3];
        result[0] = mLastXCoord;
        result[1] = mLastYCoord;
        result[2] = mSelectedCell;

        return result;
    }

    public GameState getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(GameState player) {
        mCurrentPlayer = player;
        mSelectedCell = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sxy = mSxy;
        int s3 = sxy * 3;
        int x7 = mOffetX;
        int y7 = mOffetY;
        for (int i = 0, k = sxy; i < 2; i++, k += sxy) {
            canvas.drawLine(x7, y7 + k, x7 + s3 - 1, y7 + k, mLinePaint);
            canvas.drawLine(x7 + k, y7, x7 + k, y7 + s3 - 1, mLinePaint);
        }
        for (int j = 0, k = 0, y = y7; j < 3; j++, y += sxy) {
            for (int i = 0, x = x7; i < 3; i++, k++, x += sxy) {
                mDstRect.offsetTo(MARGIN + x, MARGIN + y);
                GameState v;
                if (mSelectedCell == k) {
                    if (mBlinkDisplayOff) {
                        continue;
                    }
                    v = mSelectedValue;
                } else {
                    int[] c = TTTRules.getCoordFromLinearCell(k);
                    v = mData[c[0]][c[1]];
                }
                switch (v) {
                    case PLAYER1:
                        if (mBmpPlayer1 != null) {
                            canvas.drawBitmap(mBmpPlayer1, mSrcRect, mDstRect, mBmpPaint);
                        }
                        break;
                    case PLAYER2:
                        if (mBmpPlayer2 != null) {
                            canvas.drawBitmap(mBmpPlayer2, mSrcRect, mDstRect, mBmpPaint);
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Keep the view squared
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int sx = (w - 2 * MARGIN) / 3;
        int sy = (h - 2 * MARGIN) / 3;
        int size = sx < sy ? sx : sy;
        mSxy = size;
        mOffetX = (w - 3 * size) / 2;
        mOffetY = (h - 3 * size) / 2;
        mDstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int sxy = mSxy;
            x = (x - MARGIN) / sxy;
            y = (y - MARGIN) / sxy;
            if (isEnabled() && x >= 0 && x < 3 && y >= 0 & y < 3) {
                if (mData[x][y] == GameState.EMPTY) {
                    mLastXCoord = x;
                    mLastYCoord = y;

                    if (!TTTRules.isMoveValid(mData, x, y)) {
                        return false;
                    }

                    int cell = x + 3 * y;
                    GameState state = cell == mSelectedCell ? mSelectedValue : mData[x][y];
                    state = state == GameState.EMPTY ? mCurrentPlayer : GameState.EMPTY;

                    stopBlink();

                    mSelectedCell = cell;
                    mSelectedValue = state;
                    mBlinkDisplayOff = false;
                    mBlinkRect.set(MARGIN + x * sxy, MARGIN + y * sxy,
                            MARGIN + (x + 1) * sxy, MARGIN + (y + 1) * sxy);
                    if (state != GameState.EMPTY) {
                        // Start the blinker
                        mHandler.sendEmptyMessageDelayed(MSG_BLINK, FPS_MS);
                    }
                    if (mCellListener != null) {
                        mCellListener.onCellSelected(mLastXCoord, mLastYCoord);
                    }
                }

            }
            return true;
        }
        return false;
    }

    public void stopBlink() {
        boolean hadSelection = mSelectedCell != -1;
        mSelectedCell = -1;
        //mSelectedValue = GameState.EMPTY;
        if (!mBlinkRect.isEmpty()) {
            invalidate(mBlinkRect);
        }
        mBlinkDisplayOff = false;
        mBlinkRect.setEmpty();
        mHandler.removeMessages(MSG_BLINK);
        if (hadSelection && mCellListener != null) {
            mCellListener.onCellSelected(mLastXCoord, mLastYCoord);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if (DEBUG) {
            Log.i(TAG, "onSaveInstanceState");
        }

        Bundle b = new Bundle();
        Parcelable s = super.onSaveInstanceState();
        b.putParcelable("gv_super_state", s);
        b.putBoolean("gv_en", isEnabled());
        int[][] data = new int[mData.length][mData.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                data[i][j] = mData[i][j].getValue();
            }
        }
//        Bundle bun = new Bundle();
        b.putSerializable("gv_data", data);
//        b..putIntArray("gv_data", data);
        b.putInt("gv_sel_cell", mSelectedCell);
        b.putInt("gv_sel_val", mSelectedValue.getValue());
        b.putInt("gv_curr_play", mCurrentPlayer.getValue());
        b.putBoolean("gv_blink_off", mBlinkDisplayOff);
        b.putParcelable("gv_blink_rect", mBlinkRect);
        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (DEBUG) {
            Log.i(TAG, "onRestoreInstanceState");
        }

        if (!(state instanceof Bundle)) {
            // Not supposed to happen.
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle b = (Bundle) state;
        Parcelable superState = b.getParcelable("gv_super_state");
        setEnabled(b.getBoolean("gv_en", true));
        int[][] data = (int[][]) b.getSerializable("gv_data");
        if (data != null && data.length == mData.length) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++)
                    mData[i][j] = GameState.fromInt(data[i][j]);
            }
        }
        mSelectedCell = b.getInt("gv_sel_cell", -1);
        mSelectedValue = GameState.fromInt(b.getInt("gv_sel_val", GameState.EMPTY.getValue()));
        mCurrentPlayer = GameState.fromInt(b.getInt("gv_curr_play", GameState.EMPTY.getValue()));
        mBlinkDisplayOff = b.getBoolean("gv_blink_off", false);
        Rect r = b.getParcelable("gv_blink_rect");
        if (r != null) {
            mBlinkRect.set(r);
        }
        // let the blink handler decide if it should blink or not
        mHandler.sendEmptyMessage(MSG_BLINK);
        super.onRestoreInstanceState(superState);
    }

    //-----
    private class MyHandler implements Callback {
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_BLINK) {
                if (mSelectedCell >= 0 && mSelectedValue != GameState.EMPTY && mBlinkRect.top != 0) {
                    mBlinkDisplayOff = !mBlinkDisplayOff;
                    invalidate(mBlinkRect);
                    if (!mHandler.hasMessages(MSG_BLINK)) {
                        mHandler.sendEmptyMessageDelayed(MSG_BLINK, FPS_MS);
                    }
                }
                return true;
            }
            return false;
        }
    }

    private Bitmap getResBitmap(int bmpResId) {
        Options opts = new Options();
        opts.inDither = false;
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);
        if (bmp == null && isInEditMode()) {
            Drawable d = res.getDrawable(bmpResId);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            d.setBounds(0, 0, w - 1, h - 1);
            d.draw(c);
        }
        return bmp;
    }
}