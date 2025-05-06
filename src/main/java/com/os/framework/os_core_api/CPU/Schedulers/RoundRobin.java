package com.os.framework.os_core_api.CPU.Schedulers;

import com.os.framework.os_core_api.CPU.Models.CpuSchedulerConfig;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;

import java.util.*;

public class RoundRobin implements Strategy {

    @Override
    public List<ProcessEvent> execute(List<Process> processes, CpuSchedulerConfig cpuSchedulerConfig) {
        List<ProcessEvent> events = new ArrayList<>();
        Queue<Process> readyQueue = new LinkedList<>();

        int currentTime = 0;
        int completed = 0;
        int totalProcesses = processes.size();
        int timeQuantum = cpuSchedulerConfig.getTimeQuantum();
        int contextSwitchingDelay = cpuSchedulerConfig.getContextSwitchingDelay();

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int index = 0; // Index for arriving processes
        Process previous = null;

        while (completed < totalProcesses) {

            // Add newly arrived processes to the ready queue
            while (index < totalProcesses && processes.get(index).getArrivalTime() <= currentTime) {
                readyQueue.offer(processes.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                if (index < totalProcesses) {
                    int nextArrival = processes.get(index).getArrivalTime();
                    events.add(ProcessEvent.idle(currentTime, nextArrival));
                    currentTime = nextArrival;
                    continue;
                } else {
                    break; // All processes are done
                }
            }

            Process current = readyQueue.poll();

            // Context switch delay if switching processes
            if (previous != current && contextSwitchingDelay > 0) {
                events.add(ProcessEvent.contextSwitching(currentTime, currentTime + contextSwitchingDelay));
                currentTime += contextSwitchingDelay;
            }

            int executionTime = Math.min(timeQuantum, current.getRemainingBurstTime());

            events.add(ProcessEvent.runProcess(current, currentTime, currentTime + executionTime));

            current.setRemainingBurstTime(current.getRemainingBurstTime() - executionTime);
            currentTime += executionTime;

            if (current.getRemainingBurstTime() == 0) {
                current.setCompleted(true);
                completed++;
                current.setCompletionTime(currentTime);
                int tat = current.getCompletionTime() - current.getArrivalTime();
                int wt = tat - current.getBurstTime();
                current.setTurnAroundTime(tat);
                current.setWaitingTime(wt);
            } else {
                // Still has burst left, add back to queue
                while (index < totalProcesses && processes.get(index).getArrivalTime() <= currentTime) {
                    readyQueue.offer(processes.get(index));
                    index++;
                }
                readyQueue.offer(current);
            }

            previous = current;
        }

        return events;
    }
}
