package com.os.framework.os_core_api.CPU.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents the response model containing performance metrics
 * after executing a CPU scheduling strategy.
 */
@Data
@Builder
@Schema(description = "Response model for CPU scheduling results, including performance metrics.")
public class SchedulerResponse {

    @Schema(description = "List of all the processes.")
    List<Process> processes;

    @Schema(description = "CPU utilization as a percentage", example = "85.5")
    private double cpuUtilization;

    @Schema(description = "Number of processes completed per unit time", example = "2.5")
    private double throughput;

    @Schema(description = "Average turnaround time of all processes", example = "12.3")
    private double averageTurnaroundTime;

    @Schema(description = "Average waiting time of all processes", example = "5.4")
    private double averageWaitingTime;

    @Schema(description = "Overall efficiency of the scheduling algorithm", example = "90.2")
    private double efficiency;
}
