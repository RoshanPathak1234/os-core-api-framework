package com.os.framework.os_core_api.CPU.Services;

import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;
import com.os.framework.os_core_api.CPU.Services.Interfaces.SchedulerPerformanceMatrics;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import com.os.framework.os_core_api.strategies.cpu.StrategyMAP;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class CpuScheduler implements SchedulerPerformanceMatrics {

    @Getter
    private List<Process> processes;

    private List<ProcessEvent> events;

    private int contextSwitchingDelay = 0;

    @Getter
    private Strategy strategy;


    public CpuScheduler(Object arrivalTimes, Object burstTimes, Object priorities) {
        this(arrivalTimes, burstTimes, priorities, 0);
    }

    public CpuScheduler(Object arrivalTimes, Object burstTimes, Object priorities, int contextSwitchingDelay) {
        try {
            if (contextSwitchingDelay < 0) {
                throw new Exception("Context switching delay must be greater or equal to 0!");
            }
        } catch (Exception e) {
            log.error("Invalid context switching delay!");
        }

        this.contextSwitchingDelay = contextSwitchingDelay;
        this.processes = initializeProcesses(arrivalTimes, burstTimes, priorities);
    }

    private List<Process> initializeProcesses(Object arrivalTimes, Object burstTimes, Object priorities) {
        int[] arrival = convertToIntArray(arrivalTimes);
        int[] burst = convertToIntArray(burstTimes);
        int[] priority = priorities != null ? convertToIntArray(priorities) : new int[arrival.length];

        if (arrival.length != burst.length || (priorities != null && burst.length != priority.length)) {
            throw new IllegalArgumentException("Input arrays must have the same size.");
        }

        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < arrival.length; i++) {
            processes.add(Process.create(arrival[i] , burst[i] , priority[i]));
        }
        return processes;
    }

    private int[] convertToIntArray(Object input) {
        if (input instanceof int[]) {
            return (int[]) input;
        } else if (input instanceof List<?>) {
            List<?> list = (List<?>) input;
            int[] array = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                if (!(list.get(i) instanceof Integer)) {
                    throw new IllegalArgumentException("List elements must be integers.");
                }
                array[i] = (Integer) list.get(i);
            }
            return array;
        } else {
            throw new IllegalArgumentException("Invalid input type. Expected int[] or List<Integer>.");
        }
    }

    private void clearResults() {
        this.events.clear();
    }

    public CpuScheduler buildScheduler(Object arrivalTimes, Object burstTimes, Object priorities, int contextSwitchingDelay) {
        return new CpuScheduler(arrivalTimes , burstTimes , priorities , contextSwitchingDelay);
    }

    public void reset() {
        processes.clear();
        Process.reset();
    }

    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("No strategy set. Use setStrategy() before executing.");
        }
        events = strategy.execute(processes, contextSwitchingDelay);
    }

    public void setStrategy(SchedulingStrategy strategyName) {
        Supplier<Strategy> StrategyMethod = StrategyMAP.STRATEGY_MAP.get(strategyName);
        this.strategy = StrategyMethod.get();
        if (events != null)
            clearResults();
    }


    @Override
    public double averageTurnaroundTime() {
        return processes.stream().mapToDouble(Process::getTurnAroundTime).average().orElse(0);
    }

    @Override
    public double averageWaitingTime() {
        return processes.stream().mapToDouble(Process::getWaitingTime).average().orElse(0);
    }

    @Override
    public double cpuUtilization() {
        int totalBusyTime = events.stream().filter(e -> !e.isIdle() && !e.isContextSwitching())
                .mapToInt(e -> e.getEndTime() - e.getStartTime()).sum();
        int lastCompletionTime = events.stream().mapToInt(ProcessEvent::getEndTime).max().orElse(1);

        return Double.parseDouble(String.format("%.3f", (100.0 * totalBusyTime / lastCompletionTime)));
    }

    @Override
    public void displayGanttChart() {
        if (events == null) {
            throw new IllegalStateException("Execute the scheduler before displaying the Gantt chart.");
        }

        System.out.println("\nGantt Chart:");
        StringBuilder chart = new StringBuilder();
        for (ProcessEvent event : events) {
            chart.append(event.isIdle() ? String.format("[Idle %d-%d] ", event.getStartTime(), event.getEndTime()) :
                    event.isContextSwitching() ? String.format("[CS %d-%d] ", event.getStartTime(), event.getEndTime()) :
                            String.format("[P%d %d-%d] ", event.getProcess().getPid(), event.getStartTime(), event.getEndTime()));
        }
        System.out.println(chart);

    }

    @Override
    public double throughput() {
        int lastCompletionTime = processes.stream().mapToInt(Process::getCompletionTime).max().orElse(1);
        return Double.parseDouble(String.format("%.3f", (100.0 * processes.size() / lastCompletionTime)));
    }

    @Override
    public double efficiency() {
        int totalBurstTime = processes.stream().mapToInt(Process::getBurstTime).sum();
        int lastCompletionTime = processes.stream().mapToInt(Process::getCompletionTime).max().orElse(1);
        return Double.parseDouble(String.format("%.3f", (100.0 * totalBurstTime / lastCompletionTime)));

    }
}
