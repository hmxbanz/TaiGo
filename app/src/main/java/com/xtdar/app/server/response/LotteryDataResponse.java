package com.xtdar.app.server.response;


import java.util.List;

public class LotteryDataResponse {

    /**
     * data : {"score":"8000","left_over":50,"luck_user_list":[{"use_name":"17188800***","draw_rolls_tips":"iphoneX"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"Danp***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"iphoneX"}],"draw_rolls_item_list":[{"rolls_item_tips":"iphoneX","degree":"0","show_img":"a_img/fe69028c52532ed0fee45939ad9c6bb2.png"},{"rolls_item_tips":"谢谢参与","degree":"36","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"精装AR枪","degree":"72","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"100积分","degree":"108","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"高级AR枪","degree":"144","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"50元话费","degree":"180","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"OPPO R11s","degree":"216","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"U盘","degree":"252","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"300积分","degree":"288","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"10元话费","degree":"324","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"}],"draw_rolls_img":"a_img/0bee16f0d5df20ccf71073e9b08aae4f.png","ios_min_ver":"20171208","android_min_ver":"20171208"}
     * code : 1
     * msg : 返回数据
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * score : 8000
         * left_over : 50
         * luck_user_list : [{"use_name":"17188800***","draw_rolls_tips":"iphoneX"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"小***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"500积分"},{"use_name":"Danp***","draw_rolls_tips":"100积分"},{"use_name":"小***","draw_rolls_tips":"iphoneX"}]
         * draw_rolls_item_list : [{"rolls_item_tips":"iphoneX","degree":"0","show_img":"a_img/fe69028c52532ed0fee45939ad9c6bb2.png"},{"rolls_item_tips":"谢谢参与","degree":"36","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"精装AR枪","degree":"72","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"100积分","degree":"108","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"高级AR枪","degree":"144","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"50元话费","degree":"180","show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"},{"rolls_item_tips":"OPPO R11s","degree":"216","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"U盘","degree":"252","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"300积分","degree":"288","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"},{"rolls_item_tips":"10元话费","degree":"324","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"}]
         * draw_rolls_img : a_img/0bee16f0d5df20ccf71073e9b08aae4f.png
         * ios_min_ver : 20171208
         * android_min_ver : 20171208
         */

        private String score;
        private int left_over;
        private String draw_rolls_img;
        private String ios_min_ver;
        private String android_min_ver;
        private List<LuckUserListBean> luck_user_list;
        private List<DrawRollsItemListBean> draw_rolls_item_list;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getLeft_over() {
            return left_over;
        }

        public void setLeft_over(int left_over) {
            this.left_over = left_over;
        }

        public String getDraw_rolls_img() {
            return draw_rolls_img;
        }

        public void setDraw_rolls_img(String draw_rolls_img) {
            this.draw_rolls_img = draw_rolls_img;
        }

        public String getIos_min_ver() {
            return ios_min_ver;
        }

        public void setIos_min_ver(String ios_min_ver) {
            this.ios_min_ver = ios_min_ver;
        }

        public String getAndroid_min_ver() {
            return android_min_ver;
        }

        public void setAndroid_min_ver(String android_min_ver) {
            this.android_min_ver = android_min_ver;
        }

        public List<LuckUserListBean> getLuck_user_list() {
            return luck_user_list;
        }

        public void setLuck_user_list(List<LuckUserListBean> luck_user_list) {
            this.luck_user_list = luck_user_list;
        }

        public List<DrawRollsItemListBean> getDraw_rolls_item_list() {
            return draw_rolls_item_list;
        }

        public void setDraw_rolls_item_list(List<DrawRollsItemListBean> draw_rolls_item_list) {
            this.draw_rolls_item_list = draw_rolls_item_list;
        }

        public static class LuckUserListBean {
            /**
             * use_name : 17188800***
             * draw_rolls_tips : iphoneX
             */
            private String use_name;
            private String draw_rolls_tips;

            public String getUse_name() {
                return use_name;
            }

            public void setUse_name(String use_name) {
                this.use_name = use_name;
            }

            public String getDraw_rolls_tips() {
                return draw_rolls_tips;
            }

            public void setDraw_rolls_tips(String draw_rolls_tips) {
                this.draw_rolls_tips = draw_rolls_tips;
            }
        }

        public static class DrawRollsItemListBean {
            /**
             * rolls_item_tips : iphoneX
             * degree : 0
             * show_img : a_img/fe69028c52532ed0fee45939ad9c6bb2.png
             */

            private String rolls_item_tips;
            private String degree;
            private String show_img;

            public String getRolls_item_tips() {
                return rolls_item_tips;
            }

            public void setRolls_item_tips(String rolls_item_tips) {
                this.rolls_item_tips = rolls_item_tips;
            }

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getShow_img() {
                return show_img;
            }

            public void setShow_img(String show_img) {
                this.show_img = show_img;
            }
        }
    }
}
