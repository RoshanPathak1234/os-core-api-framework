package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.CpuSchedulerConfig;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.*;

public class HRRN implements Strategy {

    @Override
    public List<ProcessEvent> execute(List<Process> processes, CpuSchedulerConfig cpuSchedulerConfig) {
        List<ProcessEvent> events = new ArrayList<>();

        int currentTime = 0;
        int numberOfProcessesRemaining = processes.size();
        int contextSwitchingDelay = cpuSchedulerConfig.getContextSwitchingDelay();

        while (numberOfProcessesRemaining > 0) {

            final int finalCurrentTime = currentTime;

            Process highestRRProcess = processes.stream()
                    .filter(p -> !p.isCompleted() && p.getArrivalTime() <= finalCurrentTime)
                    .max(Comparator.comparingDouble(
                            p -> (double)(finalCurrentTime - p.getArrivalTime()) / p.getBurstTime() + 1.0))
                    .orElse(null);

            // no process in readyQ , updating cpu....
            if (highestRRProcess == null) {
                int nextArrival = processes.stream()
                        .filter(p -> !p.isCompleted())
                        .mapToInt(Process::getArrivalTime)
                        .min()
                        .orElse(currentTime + 1);

                events.add(ProcessEvent.idle(currentTime, nextArrival));
                currentTime = nextArrival;
                continue;
            }

            // Context switching ongoing.......
            if (contextSwitchingDelay > 0) {
                events.add(ProcessEvent.contextSwitching(currentTime, currentTime + contextSwitchingDelay));
                currentTime += contextSwitchingDelay;
            }

            // process is running.....
            int startTime = currentTime;
            int endTime = startTime + highestRRProcess.getBurstTime();

            events.add(ProcessEvent.runProcess(highestRRProcess, startTime, endTime));
            currentTime = endTime;

            // Update process metrics
            highestRRProcess.setCompleted(true);
            highestRRProcess.setCompletionTime(currentTime);

            int tat = currentTime - highestRRProcess.getArrivalTime();
            int wt = tat - highestRRProcess.getBurstTime();
            highestRRProcess.setTurnAroundTime(tat);
            highestRRProcess.setWaitingTime(wt);

            numberOfProcessesRemaining--;
        }

        return events;
    }
}
