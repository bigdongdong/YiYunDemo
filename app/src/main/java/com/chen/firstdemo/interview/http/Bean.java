package com.chen.firstdemo.interview.http;

import android.support.annotation.NonNull;

import java.util.List;

class Bean {


    /**
     * code : 200
     * message : 成功!
     * result : [{"sid":"31577089","text":"孩子厉害👍","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2020/1026/5f967bc4e7de2_wpd.jpg","video":"http://uvideo.spriteapp.cn/video/2020/1026/5f967bc4e7de2_wpd.mp4","images":null,"up":"114","down":"3","forward":"0","comment":"6","uid":"23005857","name":"无情无义","header":"http://wimg.spriteapp.cn/profile/large/2020/02/09/5e3fc8f551f9a_mini.jpg","top_comments_content":"厉害不厉害不知道。反正比我强","top_comments_voiceuri":"","top_comments_uid":"11981984","top_comments_name":"不得姐用户","top_comments_header":"http://qzapp.qlogo.cn/qzapp/100336987/D2C67A061C37841FD39E2D6232DE9833/100","passtime":"2020-12-24 18:30:05"},{"sid":"31551566","text":"给我一个你不想买的理由\u200b\u200b\u200b\u200b","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2020/0730/5f22acff7206e_wpd.jpg","video":"http://uvideo.spriteapp.cn/video/2020/0730/5f22acff7206e_wpd.mp4","images":null,"up":"68","down":"0","forward":"0","comment":"13","uid":"23131725","name":"天才少女爱我","header":"http://wimg.spriteapp.cn/profile/large/2019/07/04/5d1d909ccbf44_mini.jpg","top_comments_content":null,"top_comments_voiceuri":null,"top_comments_uid":null,"top_comments_name":null,"top_comments_header":null,"passtime":"2020-11-26 10:53:03"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sid : 31577089
         * text : 孩子厉害👍
         * type : video
         * thumbnail : http://wimg.spriteapp.cn/picture/2020/1026/5f967bc4e7de2_wpd.jpg
         * video : http://uvideo.spriteapp.cn/video/2020/1026/5f967bc4e7de2_wpd.mp4
         * images : null
         * up : 114
         * down : 3
         * forward : 0
         * comment : 6
         * uid : 23005857
         * name : 无情无义
         * header : http://wimg.spriteapp.cn/profile/large/2020/02/09/5e3fc8f551f9a_mini.jpg
         * top_comments_content : 厉害不厉害不知道。反正比我强
         * top_comments_voiceuri :
         * top_comments_uid : 11981984
         * top_comments_name : 不得姐用户
         * top_comments_header : http://qzapp.qlogo.cn/qzapp/100336987/D2C67A061C37841FD39E2D6232DE9833/100
         * passtime : 2020-12-24 18:30:05
         */

        private String sid;
        private String text;
        private String type;
        private String thumbnail;
        private String video;
        private Object images;
        private String up;
        private String down;
        private String forward;
        private String comment;
        private String uid;
        private String name;
        private String header;
        private String top_comments_content;
        private String top_comments_voiceuri;
        private String top_comments_uid;
        private String top_comments_name;
        private String top_comments_header;
        private String passtime;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getDown() {
            return down;
        }

        public void setDown(String down) {
            this.down = down;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getTop_comments_content() {
            return top_comments_content;
        }

        public void setTop_comments_content(String top_comments_content) {
            this.top_comments_content = top_comments_content;
        }

        public String getTop_comments_voiceuri() {
            return top_comments_voiceuri;
        }

        public void setTop_comments_voiceuri(String top_comments_voiceuri) {
            this.top_comments_voiceuri = top_comments_voiceuri;
        }

        public String getTop_comments_uid() {
            return top_comments_uid;
        }

        public void setTop_comments_uid(String top_comments_uid) {
            this.top_comments_uid = top_comments_uid;
        }

        public String getTop_comments_name() {
            return top_comments_name;
        }

        public void setTop_comments_name(String top_comments_name) {
            this.top_comments_name = top_comments_name;
        }

        public String getTop_comments_header() {
            return top_comments_header;
        }

        public void setTop_comments_header(String top_comments_header) {
            this.top_comments_header = top_comments_header;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "sid='" + sid + '\'' +
                    ", text='" + text + '\'' +
                    ", type='" + type + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", video='" + video + '\'' +
                    ", images=" + images +
                    ", up='" + up + '\'' +
                    ", down='" + down + '\'' +
                    ", forward='" + forward + '\'' +
                    ", comment='" + comment + '\'' +
                    ", uid='" + uid + '\'' +
                    ", name='" + name + '\'' +
                    ", header='" + header + '\'' +
                    ", top_comments_content='" + top_comments_content + '\'' +
                    ", top_comments_voiceuri='" + top_comments_voiceuri + '\'' +
                    ", top_comments_uid='" + top_comments_uid + '\'' +
                    ", top_comments_name='" + top_comments_name + '\'' +
                    ", top_comments_header='" + top_comments_header + '\'' +
                    ", passtime='" + passtime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Bean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result.toString() +
                '}';
    }
}
