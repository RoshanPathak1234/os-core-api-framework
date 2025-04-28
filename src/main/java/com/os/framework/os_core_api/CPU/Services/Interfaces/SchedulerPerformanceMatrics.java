package com.os.framework.os_core_api.CPU.Services.Interfaces;

/**
 * Interface representing performance metrics of a CPU scheduling algorithm.
 * Implementations of this interface are expected to compute and provide
 * detailed performance analysis based on scheduling results.
 */
public interface SchedulerPerformanceMatrics {

    /**
     * Calculates the average turnaround time for all scheduled processes.
     *
     * @return the average turnaround time in milliseconds.
     */
    double averageTurnaroundTime();

    /**
     * Calculates the average waiting time for all scheduled processes.
     *
     * @return the average waiting time in milliseconds.
     */
    double averageWaitingTime();

    /**
     * Calculates the CPU utilization percentage.
     * It reflects how efficiently the CPU was used during scheduling.
     *
     * @return the CPU utilization as a percentage (0 to 100).
     */
    double cpuUtilization();

    /**
     * Displays the Gantt chart representation of the process execution order.
     * Useful for visualizing the timeline of process scheduling.
     */
    String displayGanttChart();

    /**
     * Calculates the throughput of the scheduling strategy.
     * Throughput is defined as the number of processes completed per unit time.
     *
     * @return the throughput value (processes per millisecond).
     */
    double throughput();

    /**
     * Calculates the efficiency of the scheduling strategy.
     * Efficiency is a derived metric based on waiting time, turnaround time,
     * and resource usage.
     *
     * @return the efficiency score (unitless or as a percentage).
     */
    double efficiency();
}
