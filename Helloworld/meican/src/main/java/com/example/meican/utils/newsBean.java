package com.example.meican.utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jorry on 2016/7/21.
 */
public class newsBean {

    /**
     * errNum : 0
     * errMsg : success
     * retData : [{"title":"052D型导弹驱逐舰银川舰今日入列 部署南海地区","url":"http://mil.gmw.cn/2016-07/12/content_20933316.htm","abstract":"资料图：新型银川舰海试资料图：通过上一代银川舰与现役的银川舰的对比，可以看出中国国防实力的飞速猛进央视网7月12日消息，今天上午，我国自行设计制造的导弹驱逐舰银川舰入列命名授旗仪式在海南三亚某军港码头举行，这也标志着该舰正式加入人民海军战斗序列。","image_url":"http://p3.pstatp.com/list/a62000345ffd1eac96c"},{"title":"只不过让狗狗去捡球，结果回来时把我笑趴了！","url":"http://toutiao.com/group/6286961239911629058/","abstract":"只不过让狗狗去捡球\u2026结果回来时把我笑趴了在家准备练习舞蹈，结果傻喵居然在这儿抢镜头，一边儿去真正的吃货敢于直视任何眼神，坚定而勇敢的\u2026\u2026继续吃~~听说新的一年流行\u2026\u2026钓猫捕猫篮\u2026\u2026喵星人真是自己作死呀乖乖，你以为你是在吃披萨吗？那是我的毛发！","image_url":"http://p3.pstatp.com/list/6ce00083a2a02b26fb4"},{"title":"2016年北京养老金平均3355元稳居全国榜首","url":"http://yanglao.chinaso.com/ylzx/detail/20160602/1000200032905821464855052983902189_1.html","abstract":"这次调整的具体内容是怎么的，跟着小编一起去看看吧这次养老金的调整是采取挂钩调整、定额调整和适当倾斜相结合的办法。挂钩调整体现多缴多得原则，增加的额度与缴费年限挂钩。根据规定，缴费年限满 10年及以上的退休人员，缴费年限每满1年，每月增加3.","image_url":"http://p3.pstatp.com/list/6f20003d652052a57bc"},{"title":"26岁周恩来在黄埔军校如何打造有信仰有主义的军队？","url":"http://toutiao.com/group/6303695702586360066/","abstract":"蒋介石（右）与周恩来（左），现藏于广州黄埔军校纪念馆。油画所表现的是1925年3月25日，正在东征途中的黄埔校军惊闻孙中山病逝北京的消息后，由东征军总指挥蒋介石主持，军校政治部主任周恩来宣读祭文，召开东征军追悼孙中山及阵亡将士大会的场景。","image_url":"http://p3.pstatp.com/list/78f00097cecc60d795a"},{"title":"我分娩一天一夜，孩子出生婆婆破门而入做了个动作医生把她骂哭了","url":"http://toutiao.com/group/6295330473041395969/","abstract":"文/稻花香香【口述：刘艳||申明：图文无关，原创作品，转载请注明出处，授权请联系作者，抄袭必究】我是湖南农村里出来的，大学毕业后在广东一家工厂打工，老公是这家工厂的老板，然后他看上我了，就跟我结婚了，而且已经结婚一年了。","image_url":"http://p1.pstatp.com/list/7da0001bc822942df47"}]
     */

    private int errNum;
    private String errMsg;
    /**
     * title : 052D型导弹驱逐舰银川舰今日入列 部署南海地区
     * url : http://mil.gmw.cn/2016-07/12/content_20933316.htm
     * abstract : 资料图：新型银川舰海试资料图：通过上一代银川舰与现役的银川舰的对比，可以看出中国国防实力的飞速猛进央视网7月12日消息，今天上午，我国自行设计制造的导弹驱逐舰银川舰入列命名授旗仪式在海南三亚某军港码头举行，这也标志着该舰正式加入人民海军战斗序列。
     * image_url : http://p3.pstatp.com/list/a62000345ffd1eac96c
     */

    private List<RetDataBean> retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<RetDataBean> getRetData() {
        return retData;
    }

    public void setRetData(List<RetDataBean> retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String title;
        private String url;
        @SerializedName("abstract")
        private String abstractX;
        private String image_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        @Override
        public String toString() {
            return "RetDataBean{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", abstractX='" + abstractX + '\'' +
                    ", image_url='" + image_url + '\'' +
                    '}';
        }
    }




}
