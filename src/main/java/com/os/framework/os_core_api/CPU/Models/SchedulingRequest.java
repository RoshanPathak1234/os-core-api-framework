package com.os.framework.os_core_api.CPU.Models;

import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents the input model for a CPU scheduling request.
 * Includes arrival times, burst times, optional priority values,
 * and the selected scheduling strategy.
 */
@Data
@Builder
@Schema(description = "Input model for CPU scheduling simulation.")
public class SchedulingRequest {

    @NotEmpty(message = "Arrival time list cannot be empty")
    @Schema(description = "List of arrival times for the processes", example = "[0, 2, 4, 6]")
    private List<@NotNull(message = "Arrival time values must not be null") Integer> arrivalTime;

    @NotEmpty(message = "Burst time list cannot be empty")
    @Schema(description = "List of burst times for the processes. Values must be greater than 0.", example = "[5, 3, 1, 2]")
    private List<@NotNull(message = "Burst time values must not be null") @Min(value = 1, message = "Burst time must be greater than 0") Integer> burstTime;

    @Schema(description = "Optional list of priorities. Required only for priority-based scheduling. Each value must be greater than 0.\n Note that : Lower the number higher the priority.", example = "[1, 3, 2, 4]")
    private List<@NotNull(message = "Priority values must not be null") @Min(value = 1, message = "Priority must be greater than 0") Integer> priority;

    @Min(value = 0 , message = "Context switching delay must be greater or equal to 0")
    private Integer contextSwitchingDelay;
    @NotNull(message = "Scheduling strategy must be specified")
    @Schema(description = "The scheduling strategy to be used", implementation = SchedulingStrategy.class)
    private SchedulingStrategy strategy;
}
