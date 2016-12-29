
package sunmi.ui.wheelview;

import java.io.Serializable;


/**
 * 滚轮数据
 *
 * @author longtao.li
 */
public class WheelData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private int id;

    /**
     * 选中时的资源id
     */
    private int id_s;

    /**
     * 数据名称
     */
    private String name;

    /**
     * 颜色
     */
    private String color;



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public WheelData() {
    }

    public int getId_s() {
        return id_s;
    }

    public void setId_s(int id_s) {
        this.id_s = id_s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
