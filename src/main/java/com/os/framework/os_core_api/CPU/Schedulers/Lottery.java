package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.List;

public class Lottery implements Strategy {
    @Override
    public List<ProcessEvent> execute(List<Process> processes, int contextSwitchingDelay) {
        return List.of();
    }
}
