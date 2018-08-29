package com.shdd.cfs.dto.log;

import lombok.Data;

@Data
public class JournalInfo {
    String type;
    String time;
    String content;
}
