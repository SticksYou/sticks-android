package com.halloweenlabs.sticks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.halloweenlabs.sticks.models.Game;
import com.halloweenlabs.sticks.models.Play;
import com.halloweenlabs.sticks.models.Stick;
import com.halloweenlabs.sticks.viewmodels.PlayViewModel;
import com.halloweenlabs.sticks.viewmodels.StickViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View {
    private Game game;

    private Bitmap bitmap;
    private Paint canvasPaint;
    private List<StickViewModel> stickViews = new ArrayList<StickViewModel>();
    private List<PlayViewModel> playViews = new ArrayList<PlayViewModel>();

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setGame(Game game) {
        this.game = game;
        stickViews = createStickViews(game);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);

        for(StickViewModel stickView : stickViews) {
            canvas.drawPath(stickView.getPath(), stickView.getPaint());
        }

        for(PlayViewModel playView : playViews) {
            canvas.drawPath(playView.getPath(), playView.getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        PlayViewModel playView;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                playView = new PlayViewModel(new Play(this.game));
                playView.addPoint(x, y);
                playViews.add(playView);
                break;
            case MotionEvent.ACTION_MOVE:
                playView = playViews.get(playViews.size()-1);
                playView.addPoint(x, y);
                break;
            case MotionEvent.ACTION_UP:
                playView = playViews.get(playViews.size()-1);
                playView.addPoint(x, y);
                updateSticksAvailability(playView, stickViews);
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    private void updateSticksAvailability(PlayViewModel playView, List<StickViewModel> stickViews) {
        for (StickViewModel stickView : stickViews) {
            if(playView.intersect(stickView)) {
                stickView.getStick().setAvailable(false);
            }
        }
    }

    private List<StickViewModel> createStickViews(Game game) {
        ArrayList<StickViewModel> views = new ArrayList<StickViewModel>();

        for(Stick stick : game.getSticks()) {
            views.add(new StickViewModel(stick));
        }

        return views;
    }
}
