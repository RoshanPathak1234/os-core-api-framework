package com.os.framework.os_core_api.strategies.cpu;

import com.os.framework.os_core_api.CPU.Models.CpuSchedulerConfig;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;

import java.util.List;

/**
 * Interface for CPU scheduling strategies.
 * Implementing classes define different scheduling algorithms for process execution.
 *
 * @author Roshan Pathak
 * @version 1.0
 */
public interface Strategy {

    /**
     * Executes the scheduling algorithm on the provided processes.
     *
     * @param processes The list of {@link Process} to be scheduled.
     * @param cpuSchedulerConfig Configuration containing parameters like context switching delay and time quantum.
     *
     * @return A list of {@link ProcessEvent} reflecting the scheduled execution order and events.
     */
    List<ProcessEvent> execute(List<Process> processes, CpuSchedulerConfig cpuSchedulerConfig);
}
