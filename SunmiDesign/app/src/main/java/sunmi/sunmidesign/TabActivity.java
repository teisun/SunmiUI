package sunmi.sunmidesign;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sunmi.sunmiui.viewgraoup.TabViewPager;

/**
 * 项目名称：SunmiDesign
 * 类描述：
 * 创建人：Abtswiath丶lxy
 * 创建时间：2016/10/27 12:58
 * 修改人：longx
 * 修改时间：2016/10/27 12:58
 * 修改备注：
 */
public class TabActivity extends Activity {

    private TabViewPager tabViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        tabViewPager = (TabViewPager) this.findViewById(R.id.tab_view);
        List<String> s = new ArrayList<>();
        s.add("推荐");
        s.add("反馈");
        TextView t = new TextView(this);
        t.setText(ss);
        t.setTextColor(Color.parseColor("#2868C7"));
        TextView t1 = new TextView(this);
        t1.setText(ss);
        t1.setTextColor(Color.parseColor("#C73428"));
        List<View> v = new ArrayList<>();
        v.add(t);
        v.add(t1);
        try {
            tabViewPager.setData(s,v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabViewPager.setBackgroundColor(Color.parseColor("#ff6900"));
    }



    private String ss = "　1、手心捧着的文字，清清浅浅，刻着你的眉，你的眼，素笔深深，想你的日子，光阴开成了一朵思念的花，那妖，那艳。\n" +
            "\n" +
            "　　2、终难忘，年少时光，偶尔脸红心跳的告白，没有离别，没有伤痛，温暖如花，开满整个夏天。\n" +
            "\n" +
            "　　3、多年以后，终于明白，世界上总有两个人是天生一对，有情的会相爱，久别的会重逢，这是你告诉我的答案。\n" +
            "\n" +
            "　　4、月色满空，微凉如斯。你说我不来，你不走，可花期已过，情心已散，我找遍了城市的每个角落，依然不见你的身影，哭泣的蹲在路边，像个孩子。\n" +
            "\n" +
            "　　5、说好的幸福，还未到的明天，就挥手再见，是不是每个人的青春，都有无法掩饰的伤，与爱有关，与你有关。\n" +
            "\n" +
            "　　6、学会珍惜和善待对你好的人，因为，不是所有的人，都会对你掏心掏肺。\n" +
            "\n" +
            "　　7、盛夏，月光倾城。半朵烟花下，我们背靠背沉默着，一直到天明，没有说话，怕一开口就要转身天涯。\n" +
            "\n" +
            "　　8、你走之后，我变得爱发呆，望着雨天，一边傻笑，一边流泪。\n" +
            "\n" +
            "　　9、心如琉璃，水月菩提，想把光阴过的安稳一些，素净一些。却总是忍不住牵动内心深处，那似苦似疼的伤口，把它藏得再好又怎样呢，遇上你，一切伪装都苍白无力。\n" +
            "\n" +
            "　　10、也曾期待过，与你安安静静，谈一场不分手的恋爱，就好。\n" +
            "\n" +
            "　　11、看到一些很美的故事，却总在不经意间想起你，而你，又在想谁？\n" +
            "\n" +
            "　　12、吃着一碗热腾腾的面，留着开心的泪，忽而想起曼曼说的一句话：你是我的瘾，且无法可戒。\n" +
            "\n" +
            "　　13、花开依颜，情心画骨。你信么，每个人，都会遇见生命中的那个人，不管是缘，还是劫。\n" +
            "\n" +
            "　　14、喜欢他的时候，没有车没有房。只是那天，阳光很好，他穿了一件我喜爱的白衬衫，脚踏车塔塔的响着，路过那颗会开花的树，一切都刚刚好。\n" +
            "\n" +
            "　　15、至今都不喜欢去热闹的地方，是那人潮的拥挤，拉开了我们的距离。\n" +
            "\n" +
            "　　16、流星划过，许愿池上的少女，她说世界之大，岁月之长，能与街角那个他相遇，便是上帝最好的安排。\n" +
            "\n" +
            "　　17、迷迷糊糊，又走了一个夏天，你说放手的时候，我们谁都不要哭。我微笑着说好，看着你远去的影子，刹那间，这个秋天来的那么凉，那么凉。\n" +
            "\n" +
            "　　18、路过熟悉的街头，想念若隐若现，从“我”变成“我们”，最后又是一个人的海角天涯，原来，所有的昨天都会变成回忆。\n" +
            "\n" +
            "　　19、有时候心会莫名的累，宅在家里，关上手机，那么一刻，什么都不重要了，只做自己就好。\n" +
            "\n" +
            "　　20、不要害怕告别，要相信，四季更替，花开花落，都是命中注定。\n" +
            "\n" +
            "　　21、怀旧，是为了想念那个最初的自己；执着，是为了等待宿命的爱情。\n" +
            "\n" +
            "　　22、喜欢文字，喜欢浅浅遇、深深藏的感觉，就如喜欢你，哪需要什么理由。\n" +
            "\n" +
            "　　23、童话里灰姑娘变成了幸福的公主，不是因为她从今以后有用不完的金钱，而是她找到了一个不让她颠沛流离的王子，免了惊，免了苦。\n" +
            "\n" +
            "　　24、你总叫我傻丫头，我知道的，其实一点不傻。\n" +
            "\n" +
            "　　25、所有的事情到最后都会变成好事，就像你和我，争吵过，疼痛过，却还是手牵手走在了一起。\n" +
            "\n" +
            "　　26、悲伤逆流成河，说的不过是幸福之前，这世间总有人会温暖你，不管是友情还是爱情。\n" +
            "\n" +
            "　　27、爱做梦的女子，听说都没舍安全感，要等的那个人还没来，要走的那个人，早已潇洒转身。\n" +
            "\n" +
            "　　28、不要放弃愿意陪你吃苦的那个人，这个物欲横流的社会，花言巧语不要去当真，往往爱你的人，总在你需要的时候，出现在你身边。\n" +
            "\n" +
            "　　29、不要自卑，每一份感情，只要是真心，都值得尊重。\n" +
            "\n" +
            "　　30、忍不住会去想，写下这些句子，是为了什么。抬头一看，你的故乡，下雨了……";
}
