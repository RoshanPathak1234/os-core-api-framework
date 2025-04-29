package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.CpuSchedulerConfig;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRTF implements Strategy {
    int currentTime = 0;

    @Override
    public List<ProcessEvent> execute(List<Process> processes, CpuSchedulerConfig cpuSchedulerConfig) {
        List<ProcessEvent> events = new ArrayList<>();
        int numberOfProcessesRemaining = processes.size();

        while (numberOfProcessesRemaining > 0) {

            Process currentProcess = getCurrentProcess(processes);

            if (currentProcess == null) {
                int idleUntil = processes.stream()
                        .filter(p -> !p.isCompleted())
                        .mapToInt(Process::getArrivalTime)
                        .min()
                        .orElse(currentTime + 1);

                events.add(ProcessEvent.builder()
                        .idle(true)
                        .startTime(currentTime)
                        .endTime(idleUntil)
                        .build());

                currentTime = idleUntil;
                continue;
            }

            if (cpuSchedulerConfig.getContextSwitchingDelay() > 0) {
                events.add(ProcessEvent.builder()
                        .contextSwitching(true)
                        .startTime(currentTime)
                        .endTime(currentTime += cpuSchedulerConfig.getContextSwitchingDelay())
                        .build());
            }

            Process nextProcess = getNextArrivingLowestBurstTimeProcess(processes, currentProcess);

            int executeUntil;

            executeUntil = (nextProcess == null ?
                    currentTime + currentProcess.getRemainingBurstTime() :
                    Math.min(currentTime + currentProcess.getRemainingBurstTime(), nextProcess.getArrivalTime()));

            int executionDuration = executeUntil - currentTime;

            events.add(ProcessEvent.builder()
                    .process(currentProcess)
                    .startTime(currentTime)
                    .endTime(executeUntil)
                    .build());

            currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - executionDuration);
            currentTime = executeUntil;

            if (currentProcess.getRemainingBurstTime() == 0) {
                currentProcess.setCompleted(true);
                currentProcess.setCompletionTime(currentTime);

                int tat = currentProcess.getCompletionTime() - currentProcess.getArrivalTime();
                int wt = tat - currentProcess.getBurstTime();

                currentProcess.setTurnAroundTime(tat);
                currentProcess.setWaitingTime(wt);

                numberOfProcessesRemaining--;
            }
        }

        return events;
    }

    private Process getCurrentProcess(List<Process> processes) {
        return processes.stream()
                .filter(p -> !p.isCompleted() && p.getArrivalTime() <= currentTime)
                .min(Comparator.comparingInt(Process::getBurstTime))
                .orElse(null);
    }

    private Process getNextArrivingLowestBurstTimeProcess(List<Process> processes, Process currentProcess) {
        return processes.stream()
                .filter(p -> !p.isCompleted() && p.getArrivalTime() >= currentTime
                        && p.getBurstTime() < currentProcess.getBurstTime())
                .min(Comparator.comparingInt(Process::getArrivalTime))
                .orElse(null);
    }
}

