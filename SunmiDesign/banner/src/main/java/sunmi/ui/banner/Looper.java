package sunmi.ui.banner;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tomcat on 2016/10/26.
 */
class Looper {


    enum Vane{
        LEFT,
        RIGHT
    }

    private static final String TAG = "Looper";
    private static final String TAG1 = "Looper1";
    private static final String TAG2 = "Looper2";
    private Context context;

//    State state = new State();
    BannerLayout bannerLayout;
    Banner origin;
    Banner exitHead;
    Banner enterHead;
    Banner runningMan;

    Object animCancelLock = new Object();
    boolean animCancel = false;

    Object vaneLock = new Object();
    Vane vane;

//    Handler handler = new Handler();

    public Looper(BannerLayout layout){
        this.bannerLayout = layout;
        this.context = layout.getContext();
    }

    private Looper(){}

    List<Banner> bannerList = new ArrayList<>();

    public void clear(){
        enterHead = null;
        exitHead = null;
        bannerList.clear();
        runningMan = null;
        setVane(Vane.LEFT);
    }

    public void add(Banner banner){
        bannerList.add(banner);
        int indexOf = bannerList.indexOf(banner);
        int size = bannerLayout.getSize();
        banner.looper = this;
        if(indexOf == 1){
            enterHead = banner;
        }
        if(indexOf == (size -1) ){
            exitHead = banner;
        }
        if(indexOf == 0){
            origin = banner;
            return;
        }

        if(indexOf-1>=0){
            banner.setState(State.Entered);
            if(indexOf-1==0)return;
            Banner preBanner = bannerList.get(indexOf - 1);//1
            preBanner.nextEnter = banner;//banner 2
            banner.nextExit = preBanner;
        }
    }

    public void start(){
            setAnimCancel(false);
            if(runningMan == null){
                exitHead.exit(Banner.DELAY, true);
                setVane(Vane.LEFT);
                return;
            }
            Banner banner = runningMan;
            switch (getVane()){
                case LEFT:
                    if(banner.isEnterHead() && banner.isExited()){
                        setVane(Vane.RIGHT);
                        enterHead.enter( 0, true);
                    }else {
                        banner.exit(0, true);
                    }
                    break;
                case RIGHT:
                    if(banner.isExitHead() && banner.isEntered()){
                        setVane(Vane.LEFT);
                        exitHead.exit( 0, true);
                    }else {
                        banner.enter( 0, true);
                    }
                    break;
                default:
                    break;
            }
    }

    public void stop(){
        setAnimCancel(true);
        if(runningMan!=null){
            runningMan.cancelAnim();
            LogUtil.d(TAG, "stop() position:"+runningMan.position+" state:"+runningMan.getState());
        }
    }

    public synchronized void changeRunningManAuto(Banner banner, State state){
        runningMan = banner;
        LogUtil.d(TAG, "changeRunningManAuto: runningMan position"+ runningMan.position+" state:"+state);
        switch (state){
            case Exited:
                bannerLayout.notifyOnPageShow();
                if(banner.isEnterHead()){
                    setVane(Vane.RIGHT);
                    if(enterHead != null)
                    enterHead.enter( Banner.DELAY, true);
                }else{
                    banner.nextExit.exit(Banner.DELAY, true);
                }
                break;
            case Entered:
                bannerLayout.notifyOnPageShow();
                if(banner.isExitHead()){
                    setVane(Vane.LEFT);
                    exitHead.exit(Banner.DELAY, true);
                }else{
                    banner.nextEnter.enter(Banner.DELAY, true);
                }
                break;
            default:
                break;
        }
    }

    public Banner get(int position){
        return bannerList.get(position);
    }

    public void setVane(Vane vane){
        synchronized (vaneLock){
            this.vane = vane;
        }
    }

    public Vane getVane(){
        synchronized (vaneLock){
            return vane;
        }
    }

    public void setAnimCancel(boolean cancel){
        synchronized (animCancelLock){
            this.animCancel = cancel;
        }
    }

    public boolean getAnimCancel(){
        synchronized (animCancelLock){
            return animCancel;
        }
    }

    public boolean bannerClick(final Banner banner){
        bannerLayout.removeCallbacks(reStart);
        stop();
        boolean flag1 = banner.isEntered();
        Banner nextEnter = banner.nextEnter;
        boolean flag2 = (nextEnter == null && banner.isExitHead()) ||
                (nextEnter != null && nextEnter.isExited()) ||
                (banner.isOrigin()&&enterHead.isExited());

        boolean canShow = flag1&&flag2;

        LogUtil.d(TAG, "bannerClick flag1:"+flag1+" flag2:"+flag2);
        LogUtil.d(TAG, "bannerClick canShow:"+canShow);
        if(!canShow){
            displayClickBanner(banner);
            bannerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bannerLayout.notifyOnPageShow();
                }
            }, Banner.DURATION);

            bannerLayout.postDelayed(reStart, Banner.DELAY);
        }
        return canShow;
    }

    Runnable reStart = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };

    private void displayClickBanner(Banner banner) {
        LogUtil.d(TAG, "displayClickBanner");
        Banner temp = banner;
        if(banner.isOrigin()){//第一个banner
            LogUtil.d(TAG, "displayClickBanner banner.position==0");
            temp = exitHead;
            temp.exit(0, false);
            while (temp.nextExit != null){
                temp = temp.nextExit;
                temp.exit(0, false);
            }
            setVane(Vane.RIGHT);
            runningMan = enterHead;
            return;
        }

        switch (banner.getState()){
            case PreEnter:
            case Entering:
            case Entered:
                LogUtil.d(TAG, "displayClickBanner PreEnter or Entered bammer pos:"+banner.position+" enter getVane():"+getVane());
                while (temp.nextEnter != null){
                    temp = temp.nextEnter;
                    temp.exit(0, false);
                }
                break;
            case PreExit:
            case Exiting:
            case Exited:
                LogUtil.d(TAG, "displayClickBanner Exited banner pos:"+banner.position+" enter getVane():"+getVane());
                while (temp.nextExit != null){
                    temp = temp.nextExit;
                    temp.enter(0, false);
                }
                break;
            default:
                break;
        }
        if(!banner.isEntered()){
            banner.enter( 0, false);
        }

        switch (getVane()){
            case LEFT:
                runningMan = banner;
                break;
            case RIGHT:
                if(banner.nextEnter != null){
                    runningMan = banner.nextEnter;
                }else {
                    setVane(Vane.LEFT);
                    runningMan = banner;
                }
                break;
            default:
                break;
        }
    }

    public Banner changeRunningManByScrollDirection(int scrolledX) {
        float runningManViewX = runningMan.view.getX();
        if(scrolledX>0){
            //左滑
            if(runningMan.isEnterHead()){
                return runningMan;
            }
            LogUtil.d(TAG2, "左滑 pre runningMan runningManViewX:"+runningManViewX+" runningMan.exitEndX:"+runningMan.exitEndX);
            if(runningManViewX == runningMan.exitEndX){
                LogUtil.d(TAG2, " 左滑 runningMan pos:"+runningMan.position+" change to pos:"+runningMan.nextExit.position);
                runningMan = runningMan.nextExit;
            }

        }else {
            //右滑
            if(runningMan.isExitHead()){
                return runningMan;
            }
            LogUtil.d(TAG2, "右滑 pre runningMan runningManViewX:"+runningManViewX+" runningMan.exitEndX:"+runningMan.enterEndX);
            if(runningManViewX == runningMan.enterEndX){
                LogUtil.d(TAG2, " 右滑 runningMan pos:"+runningMan.position+" change to pos:"+runningMan.nextEnter.position);
                runningMan = runningMan.nextEnter;
            }
        }
        return  runningMan;
    }

    public void changeRunningManOnScrollFinished() {
        View view = runningMan.view;
        LogUtil.d(TAG1, "changeRunningManOnScrollFinished: getX"+ view.getX()+" runningMan.enterEndX:"+runningMan.enterEndX
        +" runningMan.exitEndX:"+runningMan.exitEndX+" pos:"+runningMan.position);
        switch (getVane()){
            case LEFT:
                if(!runningMan.isEnterHead() && runningMan.isExited()){
                    LogUtil.d(TAG2, " 滑动结束 runningMan pos:"+runningMan.position+" change to pos:"+runningMan.nextExit.position);
                    runningMan = runningMan.nextExit;
                }else {
                    setVane(Vane.RIGHT);
                }
                break;
            case RIGHT:
                if(!runningMan.isExitHead() && runningMan.isEntered()){
                    runningMan = runningMan.nextEnter;
                }else {
                    setVane(Vane.LEFT);
                }
                break;
            default:

                break;
        }

    }

    public Banner getShowingBanner(){
        Banner result = null;
        int size = bannerList.size();
        for(int i=size-1;i>=0;i-- ){
            Banner banner = bannerList.get(i);
            if(!banner.isExited()){
                result = banner;
                break;
            }
        }
        return result;
    }

}
