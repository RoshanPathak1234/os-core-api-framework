package com.os.framework.os_core_api.strategies.cpu;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing all supported CPU scheduling algorithms.
 * These strategies determine how processes are scheduled onto the CPU.
 */
@Schema(description = "Enumeration of all supported CPU scheduling strategies")
public enum SchedulingStrategy {

    /**
     * First-Come, First-Served Scheduling (Non-preemptive).
     * Processes are executed in the order they arrive.
     */
    @Schema(description = "First Come First Serve")
    FCFS,

    /**
     * Shortest Job First (Non-preemptive).
     * Executes the process with the shortest burst time.
     */
    @Schema(description = "Shortest Job First (Non-preemptive)")
    SJF,

    /**
     * Shortest Remaining Time First (Preemptive SJF).
     * Preempts the current process if a new process has a shorter burst time.
     */
    @Schema(description = "Shortest Job First (Preemptive) / Shortest Remaining Time First")
    SJF_PREEMPTIVE,

    /**
     * Priority Scheduling (Non-preemptive).
     * Higher-priority processes are scheduled before lower-priority ones.
     */
    @Schema(description = "Priority Scheduling (Non-preemptive)")
    PRIORITY,

    /**
     * Preemptive Priority Scheduling.
     * Preempts current process if a higher-priority process arrives.
     */
    @Schema(description = "Preemptive Priority Scheduling")
    PRIORITY_PREEMPTIVE,

    /**
     * Round Robin Scheduling.
     * Each process gets an equal time quantum in a cyclic order.
     */
    @Schema(description = "Round Robin Scheduling")
    ROUND_ROBIN,

    /**
     * Multilevel Queue Scheduling.
     * Processes are assigned to different queues with varying priorities and strategies.
     */
    @Schema(description = "Multilevel Queue Scheduling")
    MULTILEVEL_QUEUE,

    /**
     * Multilevel Feedback Queue Scheduling.
     * Allows processes to move between queues based on behavior and execution history.
     */
    @Schema(description = "Multilevel Feedback Queue Scheduling")
    MULTILEVEL_FEEDBACK_QUEUE,

    /**
     * Highest Response Ratio Next (HRRN) Scheduling.
     * Non-preemptive algorithm that chooses process with highest response ratio.
     */
    @Schema(description = "Highest Response Ratio Next Scheduling")
    HRRN,

    /**
     * Lottery Scheduling.
     * Randomized scheduling based on ticket allocation.
     */
    @Schema(description = "Lottery Scheduling")
    LOTTERY,

    /**
     * Fair Share Scheduling.
     * Allocates CPU time fairly among users or groups.
     */
    @Schema(description = "Fair Share Scheduling")
    FAIR_SHARE,

    /**
     * Shortest Process Next (SPN).
     * Prediction-based SJF variant using historical burst times.
     */
    @Schema(description = "Shortest Process Next")
    SHORTEST_PROCESS_NEXT,

    /**
     * Weighted Round Robin Scheduling.
     * Round Robin where each process has a weight determining its share of CPU time.
     */
    @Schema(description = "Weighted Round Robin")
    WEIGHTED_ROUND_ROBIN,

    /**
     * Two-Level Scheduling.
     * Combines long-term and short-term scheduling strategies.
     */
    @Schema(description = "Two-Level Scheduling")
    TWO_LEVEL,

    /**
     * Earliest Deadline First (EDF).
     * Real-time scheduling strategy that prioritizes processes with earlier deadlines.
     */
    @Schema(description = "Earliest Deadline First (Real-Time)")
    EDF,

    /**
     * Rate Monotonic Scheduling (RMS).
     * Real-time static priority scheduling based on periodicity.
     */
    @Schema(description = "Rate Monotonic Scheduling (Real-Time)")
    RMS,

    /**
     * Completely Fair Scheduler (CFS).
     * Linux kernel’s default scheduling algorithm using red-black trees for fairness.
     */
    @Schema(description = "Completely Fair Scheduler (used in Linux)")
    CFS;

    /**
     * Returns a list of all available CPU scheduling strategies.
     *
     * @return List of SchedulingStrategy
     */
    public static List<SchedulingStrategy> getAllStrategies() {
        return Arrays.asList(SchedulingStrategy.values());
    }

    @Override
    public String toString() {
        return name();
    }
}
