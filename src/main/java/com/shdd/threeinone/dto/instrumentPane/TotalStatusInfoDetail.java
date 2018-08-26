package com.shdd.threeinone.dto.instrumentPane;

import lombok.Data;

@Data
public class TotalStatusInfoDetail {
    private  Integer task;
    private  Integer running;
    private  Integer completed;
    private  Integer added;
    private  Double distributedUsed;
    private  Double tapeUsed;
    private  Double diskUsed;
    private  Integer warning;

}
