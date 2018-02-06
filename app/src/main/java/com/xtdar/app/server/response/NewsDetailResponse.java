package com.xtdar.app.server.response;


public class NewsDetailResponse {

    /**
     * data : {"recommend_id":"2","title":"《QQ飞车手游》评测：最适合手游的\u201c端游\u201d","content":"","head_img":"a_re_img/fa0dc694d673b10b3e1537c2432165f4.jpg","game_device":"手柄","post_time":"2017-12-26 13:54:13","source":"游民星空","writer":"超级不笑猫","sort_index":"0","like_count":"1","comment_count":"0","share_count":"1","read_count":13,"is_like":null}
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
         * recommend_id : 2
         * title : 《QQ飞车手游》评测：最适合手游的“端游”
         * content :
         * head_img : a_re_img/fa0dc694d673b10b3e1537c2432165f4.jpg
         * game_device : 手柄
         * post_time : 2017-12-26 13:54:13
         * source : 游民星空
         * writer : 超级不笑猫
         * sort_index : 0
         * like_count : 1
         * comment_count : 0
         * share_count : 1
         * read_count : 13
         * is_like : null
         */

        private String recommend_id;
        private String title;
        private String content;
        private String head_img;
        private String game_device;
        private String post_time;
        private String source;
        private String writer;
        private String sort_index;
        private String like_count;
        private String comment_count;
        private String share_count;
        private int read_count;
        private Object is_like;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getRead_count() {
            return read_count;
        }

        public void setRead_count(int read_count) {
            this.read_count = read_count;
        }

        public Object getIs_like() {
            return is_like;
        }

        public void setIs_like(Object is_like) {
            this.is_like = is_like;
        }
    }
}
