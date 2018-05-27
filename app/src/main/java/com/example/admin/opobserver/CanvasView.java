package com.example.admin.opobserver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class CanvasView extends View implements SurfaceHolder.Callback {
    public int width;
    public int height;
    private Bitmap mBitmap;
    public Bitmap sBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private float downX,upX;
    private static final float TOLERANCE = 5;
    Context context;
    CountDownTimer timer;
    private String childPos;
    public int ta=0, sa=0;
    public int undoflag=0;
    public interface OnTextUpdateListener {
        void onTextUpdate(String text);
        String getTextValue();
        String getChildValue();
        int getSeconds();
    }
    private OnTextUpdateListener mOnTextUpdateListener;

    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;
    public String tt = "done!";
    public ArrayList<CirclePath> paths = new ArrayList<>();
    public ArrayList<DataBitmaps> bitmapArray = new ArrayList<DataBitmaps>();
    public Bitmap bitmapchair = BitmapFactory.decodeResource(getResources(), R.drawable.chair);
    public Bitmap bitmapdoor = BitmapFactory.decodeResource(getResources(), R.drawable.door);
    public Bitmap bitmapblackboard = BitmapFactory.decodeResource(getResources(), R.drawable.blackboard);
    public Bitmap bitmaptable = BitmapFactory.decodeResource(getResources(), R.drawable.table);
    public ArrayList<String> finalResult = new ArrayList<String>();


    public class DataBitmaps{
        private Bitmap  b1;
        private Float  fx;
        private Float  fy;

        public DataBitmaps(Bitmap b,Float x,Float y){

            b1 = b;
            fx = x;
            fy = y;
        }

    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        mOnTextUpdateListener = (OnTextUpdateListener)context;
        sBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chair);


    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    public void surfaceCreated(SurfaceHolder holder) {
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void startTouch(float x, float y) {
        mX = x;
        mY = y;
    }

    public void clearCanvas() {
        mPath.reset();
        tt="done!";
        if(timer!=null)
        {timer.cancel();}
        mBitmap.eraseColor(Color.TRANSPARENT);
        bitmapArray.clear();
        mCanvas = new Canvas(mBitmap);
        paths.clear();
        sa=0; ta=0;
        finalResult= new ArrayList<String>();
        invalidate();

    }

    public void undo() {
        if (paths.size()>0 && finalResult.size()>0 && undoflag==0) {
            paths.remove(paths.size() - 1);
            finalResult.remove(finalResult.size()-1);
            tt = "done!";
            if (timer != null) {timer.cancel();}
            if (childPos!=null && (childPos.equals("1") || childPos.equals("CD") ||
                    childPos.equals("GI") || childPos.equals("Wh") || childPos.equals("TQ") || childPos.equals("BM") || childPos.equals("L") || childPos.equals("ILE")))
            {ta=ta-1;}
            if (childPos!=null && (childPos.equals("Wv") || childPos.equals("PEL") || childPos.equals("REF") ||
                    childPos.equals("GF") || childPos.equals("M") || childPos.equals("SQ") || childPos.equals("LSP")))
            {sa=sa-1;}
            invalidate();
            undoflag=1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        canvas.drawPaint(p);


        if(paths != null)
        {
        for (CirclePath cp : paths)
            {
            mPaint.setColor(cp.color);
            canvas.drawPath(cp.path, mPaint);
            }
        }
        if(bitmapArray != null)
        {
            for (int i = 0; i < bitmapArray.size(); i++) {
                canvas.drawBitmap(bitmapArray.get(i).b1, bitmapArray.get(i).fx, bitmapArray.get(i).fy, mPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //upTouch();
                upX = (int)event.getX();
                if(Math.abs(upX - downX) < 100)
                {
                    startTouch(x, y);

                    childPos = mOnTextUpdateListener.getChildValue();
                    if(childPos!=null && childPos.equals("Chair"))
                    {
                        bitmapArray.add(new DataBitmaps(bitmapchair,mX,mY)); // Add a bitmap chair
                    }
                    if(childPos!=null && childPos.equals("Blackboard"))
                    {
                        bitmapArray.add(new DataBitmaps(bitmapblackboard,mX,mY)); // Add a bitmap bb
                    }
                    if(childPos!=null && childPos.equals("Door"))
                    {
                        bitmapArray.add(new DataBitmaps(bitmapdoor,mX,mY)); // Add a bitmap door
                    }
                    if(childPos!=null && childPos.equals("Table"))
                    {
                        bitmapArray.add(new DataBitmaps(bitmaptable,mX,mY)); // Add a bitmap table
                    }

                    tt = mOnTextUpdateListener.getTextValue();
                    seconds = mOnTextUpdateListener.getSeconds();
                    seconds=seconds*1000;
                    Log.i("Meaning",tt);


                    if(tt.equals("done!") && childPos!=null && (childPos.equals("1") || childPos.equals("CD") ||
                            childPos.equals("GI") || childPos.equals("Wh") || childPos.equals("TQ") || childPos.equals("BM") || childPos.equals("L") || childPos.equals("ILE") ||
                            childPos.equals("Wv") || childPos.equals("PEL") || childPos.equals("REF") ||
                            childPos.equals("GF") || childPos.equals("M") || childPos.equals("SQ") || childPos.equals("LSP")
                    ) ) {

                        /////////////Teacher's Activities/////////////
                        if(childPos!=null && childPos.equals("1")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#00738c"), 4f, mPath,0);
                            finalResult.add("One on one teacher individual student");
                            ta=ta+1;
                            paths.add(cp);


                        }
                        if(childPos!=null && childPos.equals("CD")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#81b0b2"), 4f, mPath,0);
                            finalResult.add("Class teaching demonstration");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("GI")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#d6ead4"), 4f, mPath,0);
                            finalResult.add("Giving instructions");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("Wh")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#eff2d8"), 4f, mPath,0);
                            finalResult.add("Whiteboard writing");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("TQ")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#97a675"), 4f, mPath,0);
                            finalResult.add("Teacher asking questions");
                            ta=ta+1;
                            paths.add(cp);

                        }

                        if(childPos!=null && childPos.equals("BM")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#4f745e"), 4f, mPath,0);
                            finalResult.add("Behaviour Management");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("L")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#818656"), 4f, mPath,0);
                            finalResult.add("Tutor lecturing/PPT presentation");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("ILE")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#b7a56c"), 4f, mPath,0);
                            finalResult.add("Creating an inclusive learning environment");
                            ta=ta+1;
                            paths.add(cp);

                        }
                        /////////////Students' Activities/////////////
                        if(childPos!=null && childPos.equals("Wv")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#ffd901"), 4f, mPath,1);
                            finalResult.add("Watching a video or watching student activity");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("PEL")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#ffbeae"),4f, mPath,1);
                            finalResult.add("Engaged in practical experiential learning activity");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("REF")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#ffade2"), 4f, mPath,1);
                            finalResult.add("Reflective practice - Learners making meaning");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("GF")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#ff5682"), 4f, mPath,1);
                            finalResult.add("Giving feedback on learners ideas");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("M")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#f42439"), 4f, mPath,1);
                            finalResult.add("Moving around the learning setting");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("SQ")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#c35c2f"), 4f, mPath,1);
                            finalResult.add("Teacher responding to student questions");
                            sa=sa+1;
                            paths.add(cp);

                        }
                        if(childPos!=null && childPos.equals("LSP")) {
                            mPath = new Path();
                            mPath.addCircle(mX, mY, 50, Path.Direction.CW);
                            CirclePath cp = new CirclePath(Color.parseColor("#ea9b67"), 4f, mPath,1);
                            finalResult.add("Listening to student presentation/ideas");
                            sa=sa+1;
                            paths.add(cp);

                        }

                        timer = new CountDownTimer(seconds, 1000) { // adjust the milli seconds here

                            public void onTick(long millisUntilFinished) {

                                mOnTextUpdateListener.onTextUpdate(("" + String.format(FORMAT,
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))));
                            }

                            public void onFinish() {
                                mOnTextUpdateListener.onTextUpdate("done!");
                            }
                        }.start();
                        undoflag=0;
                    }
                    invalidate();
                }
                break;

        }

        return true;
    }
}
