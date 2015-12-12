package cn.gzw.sliddingdelete;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by gzw on 2015/12/12.
 */
public class MyListView extends ListView {
    private int mDownPosition;//点击的view索引
    private View view;//点击的item
    private int oldX = 0;//点击时的X轴坐标
    private int oldY = 0;//点击时的Y轴坐标
    private int screenWidth;//屏幕宽度
    private int diffX = 0;//移动X距离
    private int diffY = 0;//移动Y距离
    private ListViewItemDeleteListener listener = null;
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setListViewItemDeleteListener( ListViewItemDeleteListener listener){
        this.listener = listener;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                down(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                move(ev);
                break;
            case MotionEvent.ACTION_UP:
                up(ev);
                break;
        }
        return true;
    }

    private void down(MotionEvent ev) {
        oldX = (int) ev.getX();
        oldY = (int) ev.getY();
        mDownPosition = pointToPosition(oldX,oldY);
        view = getChildAt(mDownPosition);
        Log.d("hehe",mDownPosition+"");
    }

    private void move(MotionEvent ev) {
        diffX = (int) (oldX- ev.getX());
        diffY = (int) (oldY - ev.getY());
        Log.d("hehe",diffX+"");
        if(diffX <= screenWidth / 2 && diffY <10 && ev.getX() < oldX){
            ViewHelper.setTranslationX(view, -diffX);
            Log.d("float->",(diffX*1.0f/(screenWidth*1.0f/2))+"");
            ViewHelper.setAlpha(view,1-(diffX*1.0f/(screenWidth*1.0f/2)));
        }
    }
    private void up(MotionEvent ev) {
        if(diffX < 5){
            super.onTouchEvent(ev);
        }else  if(diffX <= screenWidth/2){
            ViewHelper.setTranslationX(view,0);
            ViewHelper.setAlpha(view, 1);
        }else{
            ViewHelper.setAlpha(view,0);
            final ViewGroup.LayoutParams lp  = view.getLayoutParams();
            final int height = lp.height;
            ValueAnimator animator = ValueAnimator.ofInt(height,0).setDuration(300);
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (listener != null) {
                        listener.onDelete(view,mDownPosition);
                    }
                    ViewHelper.setAlpha(view, 1f);
                    ViewHelper.setTranslationX(view, 0);
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    lp.height = height;
                    view.setLayoutParams(lp);
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lp.height = (int) animation.getAnimatedValue();
                    view.setLayoutParams(lp);
                }
            });
        }
    }
    public interface ListViewItemDeleteListener{
        public void onDelete(View view,int position);
    }
}
