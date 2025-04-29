package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.CpuSchedulerConfig;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS implements Strategy {

    @Override
    public List<ProcessEvent> execute(List<Process> processes, CpuSchedulerConfig cpuSchedulerConfig) {
        List<ProcessEvent> events = new ArrayList<>();

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;

        for (Process process : processes) {

            if (currentTime < process.getArrivalTime()) {
                events.add(ProcessEvent.builder()
                        .process(null)
                        .startTime(currentTime)
                        .endTime(process.getArrivalTime())
                        .idle(true)
                        .contextSwitching(false)
                        .build());
                currentTime = process.getArrivalTime();
            }

            if (cpuSchedulerConfig.getContextSwitchingDelay() > 0) {
                events.add(ProcessEvent.builder()
                        .process(null)
                        .startTime(currentTime)
                        .endTime(currentTime += cpuSchedulerConfig.getContextSwitchingDelay())
                        .idle(false)
                        .contextSwitching(true)
                        .build());
            }

            int startTime = currentTime;
            int endTime = startTime + process.getBurstTime();

            events.add(ProcessEvent.builder()
                    .process(process)
                    .startTime(startTime)
                    .endTime(endTime)
                    .idle(false)
                    .contextSwitching(false)
                    .build());

            currentTime = endTime;

            int tat = endTime - process.getArrivalTime();
            int wt = tat - process.getBurstTime();

            process.setRemainingBurstTime(0);
            process.setCompleted(true);
            process.setCompletionTime(endTime);
            process.setTurnAroundTime(tat);
            process.setWaitingTime(wt);
        }

        return events;
    }
}
