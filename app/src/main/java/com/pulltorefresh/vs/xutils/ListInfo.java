package com.pulltorefresh.vs.xutils;

import java.util.List;

/**
 * Created by think on 2017/11/17.
 *
 */

public class ListInfo {

    /**
     * data : [{"collect_num":"1646","food_str":"大虾 葱 生姜 植物油 料酒","id":"8289","num":1646,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/9/8289.jpg","title":"油焖大虾"},{"collect_num":"1580","food_str":"猪肉 青蒜 青椒 红椒 姜片","id":"2127","num":1580,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/3/2127.jpg","title":"四川回锅肉"},{"collect_num":"1513","food_str":"QQ糖 牛奶 芒果","id":"30630","num":1513,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/31/30630.jpg","title":"超简单芒果布丁"},{"collect_num":"1416","food_str":"鲜鱼 姜 葱 蒜 花椒","id":"9073","num":1416,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/10/9073.jpg","title":"家常红烧鱼"},{"collect_num":"1399","food_str":"豆腐 新鲜红椒 青椒 葱花 油","id":"10097","num":1399,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/11/10097.jpg","title":"家常煎豆腐"},{"collect_num":"1334","food_str":"瘦猪肉 生菜 豆瓣酱 干辣椒 花椒","id":"10509","num":1334,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/11/10509.jpg","title":"水煮肉片"},{"collect_num":"1240","food_str":"银耳 苹果 红糖","id":"46968","num":1240,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/47/46968.jpg","title":"红糖苹果银耳汤"},{"collect_num":"1208","food_str":"豆腐 肉末 生抽 白糖 芝麻油","id":"10191","num":1208,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/11/10191.jpg","title":"麻婆豆腐"},{"collect_num":"1138","food_str":"大米 皮蛋 猪肉 油条 香葱","id":"2372","num":1138,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/3/2372.jpg","title":"皮蛋瘦肉粥"},{"collect_num":"1134","food_str":"红薯粉 肉 姜 蒜 花椒","id":"2166","num":1134,"pic":"http://www.qubaobei.com/ios/cf/uploadfile/132/3/2166.jpg","title":"蚂蚁上树"}]
     * ret : 1
     */

    private int ret;
    private List<DataBean> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * collect_num : 1646
         * food_str : 大虾 葱 生姜 植物油 料酒
         * id : 8289
         * num : 1646
         * pic : http://www.qubaobei.com/ios/cf/uploadfile/132/9/8289.jpg
         * title : 油焖大虾
         */

        private String collect_num;
        private String food_str;
        private String id;
        private int num;
        private String pic;
        private String title;

        public String getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(String collect_num) {
            this.collect_num = collect_num;
        }

        public String getFood_str() {
            return food_str;
        }

        public void setFood_str(String food_str) {
            this.food_str = food_str;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
