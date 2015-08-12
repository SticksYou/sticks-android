package com.halloweenlabs.sticks.viewmodels;

import android.graphics.Paint;
import android.graphics.Path;

import com.halloweenlabs.sticks.models.Stick;
import com.halloweenlabs.sticks.models.utils.Point;

public class StickViewModel {

    private final Stick stick;
    private Path path;
    private Point start;
    private Point end;

    private static Paint availablePaint;
    private static Paint unavailablePaint;

    public StickViewModel(Stick stick) {
        this.stick = stick;
        createPoints(stick);
        createPath(stick);
    }

    public Stick getStick() {
        return stick;
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        if(stick.isAvailable()) {
            return getAvailablePaint();
        }
        else {
            return getUnavailablePaint();
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    private void createPoints(Stick stick) {
        int paddingTop = 100;
        int paddingLeft = 100;
        int stickHeight = 120;
        int paddingStickTop = 40;
        int paddingStickLeft = 70;

        int row = stick.getRow();
        int column = stick.getColumn();

        int x1 = paddingStickLeft * column + paddingTop;
        int y1 = (paddingStickTop + stickHeight) * row + paddingLeft;
        int x2 = x1;
        int y2 = y1 + stickHeight;

        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    private void createPath(Stick stick) {
        path = new Path();
        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);
    }

    private static Paint getAvailablePaint() {
        if(availablePaint == null)
            availablePaint = createAvailablePaint();
        return availablePaint;
    }

    private static Paint getUnavailablePaint() {
        if(unavailablePaint == null)
            unavailablePaint = createUnavailablePaint();
        return unavailablePaint;
    }

    private static Paint createAvailablePaint() {
        Paint paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private static Paint createUnavailablePaint() {
        Paint paint = new Paint();
        paint.setColor(0xFF000099);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }
}
