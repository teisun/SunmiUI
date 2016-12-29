
package sunmi.ui.wheelview;


/**
 * 滚轮抽象接口
 *
 * @author longtao.li
 */
public interface IWheelView {

    /**
     * 滚轮数据是否循环，默认不循环
     */
    boolean LOOP = true;

    /**
     * 设置滚轮是否循环滚动
     *
     * @param loop
     */
    void setLoop(boolean loop);


    /**
     * 设置滚轮数据源适配器
     *
     * @param adapter
     */
    void setWheelAdapter(BaseWheelAdapter adapter);


    /**
     * 设置滚轮个数
     *
     * @param wheelSize
     */
    void setWheelSize(int wheelSize);

}
