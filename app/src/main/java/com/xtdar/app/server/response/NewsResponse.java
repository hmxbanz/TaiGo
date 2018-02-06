package com.xtdar.app.server.response;


import java.util.List;

public class NewsResponse {

    /**
     * data : [{"recommend_id":"24","title":"《我的世界》手机版在IOS上率先发布","head_img":"a_re_img/e5d6453703ac05e4d1cb1500b5031f6f.png","game_device":"","post_time":"2017-12-27 14:28:25","source":"网易","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"23","title":"《自由之战2》游戏评测","head_img":"a_re_img/2b389b3a8aca7be87cad3ac0453f3554.jpg","game_device":"","post_time":"2017-12-27 09:27:24","source":"","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"22","title":"《子弹力量》评测：与全球玩家比试枪法！","head_img":"a_re_img/875db89091e677b174167bdb5fe5ab33.jpg","game_device":"","post_time":"2017-12-27 09:25:53","source":"yangjiawen ","writer":"佚名","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"20","title":"《元气骑士》评测：丧心病狂的地牢射击","head_img":"a_re_img/3d7c97274f41193d96721984aa428415.png","game_device":"","post_time":"2017-12-27 09:23:37","source":"隔壁老王","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"19","title":"Gameloft《现代战争：尖峰对决》","head_img":"a_re_img/024242ba364110e94c095c4b15ebaf5b.jpg","game_device":"","post_time":"2017-12-27 09:22:24","source":"搜狐","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"17","title":"《坦克世界闪电战》评测：纯爷们儿的战场","head_img":"a_re_img/828216b7bf678ea66a0940a92571050d.jpg","game_device":"","post_time":"2017-12-27 09:19:30","source":"口袋巴士","writer":"白痴","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"15","title":"《青春篮球》游戏评测","head_img":"a_re_img/de57846a0c0f562074dbae20f851f0cc.jpg","game_device":"","post_time":"2017-12-27 09:17:02","source":"17173","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"14","title":"《迷雾求生》试玩报告：失明丝毫不影响我追捕猎物","head_img":"a_re_img/98334ca309e5fc3b3f40c97b988482e8.jpg","game_device":"","post_time":"2017-12-27 09:16:06","source":"放学不要走","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"13","title":"超豪华赛车经典 《狂野飙车8：极速凌云》评测","head_img":"a_re_img/bd82e117688264b1edbdf6c63c7b94e8.jpg","game_device":"","post_time":"2017-12-27 09:15:29","source":"","writer":"任龙","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"12","title":"《聚爆》评测：这才是真正的游戏大作","head_img":"a_re_img/b64e861cfec2f21b97b0e8afdbbd7170.jpg","game_device":"","post_time":"2017-12-27 09:14:12","source":"口袋巴士","writer":"白痴 ","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"10","title":"《光影对决》游戏评测","head_img":"a_re_img/81443df21b66e48d2237f896822c5b60.jpg","game_device":"","post_time":"2017-12-27 09:11:40","source":"17173","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"},{"recommend_id":"9","title":"《敢达争锋对决》游戏评测","head_img":"a_re_img/3bc103191edd28ca7c0d76210777239d.jpg","game_device":"","post_time":"2017-12-27 09:10:43","source":"17173","writer":"","sort_index":"0","like_count":"0","comment_count":"0","share_count":"0","read_count":"0"}]
     * code : 1
     * msg : 返回数据
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * recommend_id : 24
         * title : 《我的世界》手机版在IOS上率先发布
         * head_img : a_re_img/e5d6453703ac05e4d1cb1500b5031f6f.png
         * game_device :
         * post_time : 2017-12-27 14:28:25
         * source : 网易
         * writer :
         * sort_index : 0
         * like_count : 0
         * comment_count : 0
         * share_count : 0
         * read_count : 0
         */

        private String recommend_id;
        private String title;
        private String head_img;
        private String game_device;
        private String post_time;
        private String source;
        private String writer;
        private String sort_index;
        private String like_count;
        private String comment_count;
        private String share_count;
        private String read_count;

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getGame_device() {
            return game_device;
        }

        public void setGame_device(String game_device) {
            this.game_device = game_device;
        }

        public String getPost_time() {
            return post_time;
        }

        public void setPost_time(String post_time) {
            this.post_time = post_time;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getSort_index() {
            return sort_index;
        }

        public void setSort_index(String sort_index) {
            this.sort_index = sort_index;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getShare_count() {
            return share_count;
        }

        public void setShare_count(String share_count) {
            this.share_count = share_count;
        }

        public String getRead_count() {
            return read_count;
        }

        public void setRead_count(String read_count) {
            this.read_count = read_count;
        }
    }
}
