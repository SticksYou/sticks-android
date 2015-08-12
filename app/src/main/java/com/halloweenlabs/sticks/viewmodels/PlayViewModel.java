package com.halloweenlabs.sticks.viewmodels;

import android.graphics.Paint;
import android.graphics.Path;

import com.halloweenlabs.sticks.models.Play;
import com.halloweenlabs.sticks.models.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class PlayViewModel {
    private final Play play;
    private Path path;
    private Paint paint;
    private List<Point> points = new ArrayList<Point>();

    public PlayViewModel(Play play) {
        this.play = play;
        this.path = new Path();
    }

    public void addPoint(float x, float y) {
        Point point = new Point(x, y);
        if (points.isEmpty()) {
            path.moveTo(x, y);
        } else {
            path.lineTo(x, y);
        }
        points.add(point);
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        if (paint == null)
            paint = createPaint();
        return paint;
    }

    public boolean intersect(StickViewModel stickView) {

        float xs1 = stickView.getStart().x;
        float ys1 = stickView.getStart().y;
        float xs2 = stickView.getEnd().x;
        float ys2 = stickView.getEnd().y;

        for (int i = 0; i < points.size() - 1; i++) {
            float xp1 = points.get(i).x;
            float yp1 = points.get(i).y;
            float xp2 = points.get(i + 1).x;
            float yp2 = points.get(i + 1).y;

            if ((Math.abs(xs2 - xs1) < 0.00001) && (Math.abs(xp2 - xp1) < 0.00001)) {
                if (Math.abs(xs2 - xp2) < 0.00001) {
                    boolean y1s = Math.min(yp1, yp2) <= ys1 && ys1 <= Math.max(yp1, yp2);
                    boolean y2s = Math.min(yp1, yp2) <= ys2 && ys2 <= Math.max(yp1, yp2);
                    if (y1s || y2s) {
                        return true;
                    }
                }
                continue;
            }

            if ((Math.abs(ys2 - ys1) < 0.00001) && (Math.abs(yp2 - yp1) < 0.00001)) {
                if (Math.abs(ys2 - yp2) < 0.00001) {
                    boolean x1s = Math.min(xp1, xp2) <= xs1 && xs1 <= Math.max(xp1, xp2);
                    boolean x2s = Math.min(xp1, xp2) <= xs2 && xs2 <= Math.max(xp1, xp2);
                    if (x1s || x2s) {
                        return true;
                    }
                }
                continue;
            }

            float x = 0;
            float y = 0;

            if (Math.abs(xs2 - xs1) < 0.00001) {
                x = xs1;
                y = ((yp2 - yp1) * (x - xp1)) / (xp2 - xp1) + yp1;
            } else if (Math.abs(xp2 - xp1) < 0.00001) {
                x = xp1;
                y = ((ys2 - ys1) * (x - xs1)) / (xs2 - xs1) + ys1;
            } else if (Math.abs(ys2 - ys1) < 0.00001) {
                y = ys1;
                x = ((y - yp1) * (xp2 - xp1)) / (yp2 - yp1) + xp1;
            } else if (Math.abs(yp2 - yp1) < 0.00001) {
                y = yp1;
                x = ((y - ys1) * (xs2 - xs1)) / (ys2 - ys1) + xs1;
            } else {
                float a = (ys2 - ys1) / (xs2 - xs1);
                float b = (yp2 - yp1) / (xp2 - xp1);
                if (Math.abs(a - b) < 0.00001)
                    continue;

                x = (yp1 - ys1 + a * xs1 - b * xp1) / (a - b);
                y = b * (x - xp1) + yp1;
            }

            boolean xp = Math.min(xp1, xp2) <= x && x <= Math.max(xp1, xp2);
            boolean yp = Math.min(yp1, yp2) <= y && y <= Math.max(yp1, yp2);
            boolean xs = Math.min(xs1, xs2) <= x && x <= Math.max(xs1, xs2);
            boolean ys = Math.min(ys1, ys2) <= y && y <= Math.max(ys1, ys2);
            if ((xp && yp) && (xs && ys)) {
                return true;
            }
        }

        return false;
    }

    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }
}
