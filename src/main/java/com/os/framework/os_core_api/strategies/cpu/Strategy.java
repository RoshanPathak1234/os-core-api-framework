package com.os.framework.os_core_api.strategies.cpu;

import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;

import java.util.List;

/**
 * The Strategy interface defines the contract for scheduling algorithms used in CPU scheduling.
 * It provides a method for executing a scheduling strategy on an array of processes.
 *
 * The scheduling algorithm should be implemented by classes that implement this interface, and
 * each algorithm can have its own approach to ordering the processes.
 *
 * @author Roshan Pathak
 * @version 1.0
 */
public interface Strategy {

    /**
     * Executes the scheduling strategy on the provided processes.
     *
     * This method will execute the scheduling algorithm, considering the provided processes
     * and the context switching delay. The implementation should return a list of process events
     * reflecting the scheduled execution order and any associated events.
     *
     * @param processes The array of processes to be scheduled.
     * @param contextSwitchingDelay The time delay for context switching between processes.
     *
     * @return A list of {@link ProcessEvent} representing the events that occurred during the
     *         execution of the scheduling algorithm.
     */
    List<ProcessEvent> execute(List<Process> processes, int contextSwitchingDelay);
}
