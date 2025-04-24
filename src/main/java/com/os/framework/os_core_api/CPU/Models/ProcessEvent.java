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
}
