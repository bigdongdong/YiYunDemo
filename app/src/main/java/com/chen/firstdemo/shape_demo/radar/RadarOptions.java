package com.chen.firstdemo.shape_demo.radar;

public class RadarOptions {
    public  int count ;
    public  int level ;
    public int maxVal ;
    public int[] vals ;

    private RadarOptions() {
    }

    private RadarOptions(Builder builder) {
        this.count = builder.count;
        this.level = builder.level;
        this.maxVal = builder.maxVal;
        this.vals = builder.vals;
    }

    public static class Builder{
        private int count ;
        private int level ;
        private int maxVal ;
        private int[] vals ;

        public Builder count(int count){
            this.count = count ;
            return this;
        }
        public Builder level(int level){
            this.level = level ;
            return this;
        }
        public Builder maxVal(int maxVal){
            this.maxVal = maxVal ;
            return this;
        }
        public Builder vals(int[] vals){
            this.vals = vals ;
            return this;
        }
        public RadarOptions build(){
            return new RadarOptions(this);
        }
    }
}
