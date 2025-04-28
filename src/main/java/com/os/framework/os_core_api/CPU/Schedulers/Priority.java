package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Priority implements Strategy {
    @Override
    public List<ProcessEvent> execute(List<Process> processes, int contextSwitchingDelay) {
        int numberOfProcessesRemaining = processes.size();

        List<ProcessEvent> events = new ArrayList<>();
        int currentTime = 0;

        while (numberOfProcessesRemaining > 0) {

            final int finalCurrentTime = currentTime;
            List<Process> readyQ = new ArrayList<>(processes.stream()
                    .filter(process -> !process.isCompleted() && process.getArrivalTime() <= finalCurrentTime)
                    .toList());

            if (readyQ.isEmpty()) {
                int idleDuration = processes.stream()
                        .filter(process -> !process.isCompleted())
                        .mapToInt(Process::getArrivalTime)
                        .min()
                        .orElse(currentTime + 1);

                events.add(
                        ProcessEvent.builder()
                                .idle(true)
                                .startTime(currentTime)
                                .endTime(idleDuration)
                                .build()
                );
                currentTime = idleDuration;
                continue;
            }
            events.add(
                    ProcessEvent.builder()
                            .contextSwitching(true)
                            .startTime(currentTime)
                            .endTime(currentTime += contextSwitchingDelay)
                            .build()
            );

            readyQ.sort(Comparator.comparingInt(Process::getPriority));

            for (Process process : readyQ) {
                events.add(
                        ProcessEvent.builder()
                                .process(process)
                                .startTime(currentTime)
                                .endTime(currentTime += process.getBurstTime())
                                .build()
                );

                process.setRemainingBurstTime(0);
                process.setCompleted(true);
                process.setCompletionTime(currentTime);

                int tat = currentTime - process.getArrivalTime();
                int wt = tat - process.getBurstTime();

                process.setTurnAroundTime(tat);
                process.setWaitingTime(wt);

                numberOfProcessesRemaining--;

            }

        }

        return events;
    }
}

