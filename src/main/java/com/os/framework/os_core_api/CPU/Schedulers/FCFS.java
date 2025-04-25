package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS implements Strategy {

    @Override
    public List<ProcessEvent> execute(List<Process> processes, int contextSwitchingDelay) {
        List<ProcessEvent> timeline = new ArrayList<>();

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;

        for (int i = 0; i < processes.size(); i++) {

            Process process = processes.get(i);

            if (currentTime < process.getArrivalTime()) {
                timeline.add(ProcessEvent.builder()
                        .process(null)
                        .startTime(currentTime)
                        .endTime(process.getArrivalTime())
                        .idle(true)
                        .contextSwitching(false)
                        .build());
                currentTime = process.getArrivalTime();
            }

            if (i > 0 && contextSwitchingDelay > 0) {
                timeline.add(ProcessEvent.builder()
                        .process(null)
                        .startTime(currentTime)
                        .endTime(currentTime + contextSwitchingDelay)
                        .idle(false)
                        .contextSwitching(true)
                        .build());
                currentTime += contextSwitchingDelay;
            }

            int startTime = currentTime;
            int endTime = startTime + process.getBurstTime();

            timeline.add(ProcessEvent.builder()
                    .process(process)
                    .startTime(startTime)
                    .endTime(endTime)
                    .idle(false)
                    .contextSwitching(false)
                    .build());

            currentTime = endTime;

            int tat = endTime - process.getArrivalTime();
            int wt = tat - process.getArrivalTime();

            process.setRemainingBurstTime(0);
            process.setCompleted(true);
            process.setCompletionTime(endTime);
            process.setTurnAroundTime(tat);
            process.setWaitingTime(wt);
        }

        return timeline;
    }
}
