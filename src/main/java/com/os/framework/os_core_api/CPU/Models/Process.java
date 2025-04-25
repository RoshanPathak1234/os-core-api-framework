package com.os.framework.os_core_api.CPU.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a process in the operating system with various attributes
 * required for scheduling and performance metrics.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Represents a process in the operating system",
        requiredProperties = {"arrivalTime", "burstTime"}
)
public class Process {

    private static final AtomicInteger pidCounter = new AtomicInteger(1); // Atomic counter for generating unique PID

    /**
     * Process ID - a unique identifier for the process
     */
    @Schema(description = "Process ID", example = "1")
    private int pid;

    /**
     * Name of the process
     */
    @Schema(description = "Name of the process", example = "P1", defaultValue = "P1")
    private String name;

    /**
     * Arrival time of the process in the system
     */
    @Schema(description = "Arrival time of the process", example = "0", defaultValue = "0")
    private int arrivalTime;

    /**
     * Burst time required by the process for execution
     */
    @Schema(description = "Burst time required for execution", example = "10")
    private int burstTime;

    /**
     * Remaining burst time for the process (updated during execution)
     */
    @Schema(description = "Remaining burst time for the process", example = "8")
    private int remainingBurstTime;

    /**
     * Priority of the process, used for scheduling purposes
     * Default value is -1 indicating that the priority is not set
     */
    @Schema(description = "Priority of the process", example = "3", defaultValue = "-1")
    private int priority = -1;

    /**
     * Time when the process finishes execution
     */
    @Schema(description = "Completion time of the process", example = "15")
    private int completionTime;

    /**
     * Turnaround time of the process
     */
    @Schema(description = "Turnaround time of the process", example = "10")
    private int turnAroundTime;

    /**
     * Waiting time of the process before execution starts
     */
    @Schema(description = "Waiting time of the process", example = "5")
    private int waitingTime;

    /**
     * Flag indicating whether the process has completed execution
     */
    @Schema(description = "Completion status of the process", example = "true")
    private boolean completed;

    /**
     * Factory method to create a process using the builder pattern
     * and automatically initializes the PID and name.
     *
     * @param arrivalTime The arrival time of the process
     * @param burstTime The burst time of the process
     * @param priority The priority of the process
     * @return The created process instance
     */
    public static Process create(int arrivalTime, int burstTime, int priority) {
        Process process = Process.builder()
                .arrivalTime(arrivalTime)
                .burstTime(burstTime)
                .priority(priority)
                .pid(pidCounter.getAndIncrement())
                .remainingBurstTime(burstTime)
                .build();

        process.setName("P" + process.pid);
        return process;
    }

    public static void reset() {
        pidCounter.set(1);
    }


}
