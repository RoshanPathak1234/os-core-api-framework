package com.os.framework.os_core_api.CPU.Services;

import com.os.framework.os_core_api.CPU.Models.ProcessEvent;
import com.os.framework.os_core_api.strategies.cpu.Strategy;
import com.os.framework.os_core_api.CPU.Services.Interfaces.SchedulerPerformanceMatrics;
import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import com.os.framework.os_core_api.strategies.cpu.StrategyMAP;

import java.util.List;
import java.util.function.Supplier;

public class CpuScheduler implements SchedulerPerformanceMatrics {

    private List<Process> processes;
    private List<ProcessEvent> events;
    private int contextSwitchingDelay = 0;
    private Strategy strategy;



    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("No strategy set. Use setStrategy() before executing.");
        }
        events = strategy.execute(processes , contextSwitchingDelay);
    }

    public void setStrategy(SchedulingStrategy strategyName) {
        Supplier<Strategy> StrategyMethod =  StrategyMAP.STRATEGY_MAP.get(strategyName);
        this.strategy = StrategyMethod.get();
        clearResults();
    }

    private void clearResults() {
        this.events.clear();
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
        return (double) totalBusyTime / lastCompletionTime;
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
        return (double) processes.size() / lastCompletionTime;
    }

    @Override
    public double efficiency() {
        int totalBurstTime = processes.stream().mapToInt(Process::getBurstTime).sum();
        int lastCompletionTime = processes.stream().mapToInt(Process::getCompletionTime).max().orElse(1);
        return (double) totalBurstTime / lastCompletionTime;
    }
}
