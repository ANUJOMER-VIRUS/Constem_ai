package com.omer.constems_ai_assignment;

public class RecordModel {
    private String video,step,startcor,endcor;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStartcor() {
        return startcor;
    }

    public void setStartcor(String startcor) {
        this.startcor = startcor;
    }

    public String getEndcor() {
        return endcor;
    }

    public void setEndcor(String endcor) {
        this.endcor = endcor;
    }

    public RecordModel(String video, String step, String startcor, String endcor) {
        this.video = video;
        this.step = step;
        this.startcor = startcor;
        this.endcor = endcor;
    }
}
