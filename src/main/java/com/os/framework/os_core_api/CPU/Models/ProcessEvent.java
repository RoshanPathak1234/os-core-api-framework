package com.os.framework.os_core_api.CPU.Models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ProcessEvent {
    private final Process process;
    private final int startTime;
    private final int endTime;
    private final boolean idle;
    private final boolean contextSwitching;

    public static ProcessEvent idle(int startTime , int endTime) {
        return ProcessEvent.builder()
                .idle(true)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public static ProcessEvent contextSwitching(int startTime , int endTime) {
        return ProcessEvent.builder()
                .contextSwitching(true)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public static ProcessEvent runProcess(Process process , int startTime , int endTime) {
        return ProcessEvent.builder()
                .process(process)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
