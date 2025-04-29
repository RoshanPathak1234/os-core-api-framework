package com.os.framework.os_core_api.CPU.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CpuSchedulerConfig {

    @Schema(description = "Time taken for context switching between processes (in milliseconds). Default is 0 unit.", defaultValue = "0")
    private int contextSwitchingDelay = 0;

    @Schema(description = "Time quantum of CPU (maximum time a process can use CPU in a round-robin scheduler, in milliseconds). Default is Integer.MAX_VALUE.", defaultValue = "2147483647")
    private int timeQuantum = Integer.MAX_VALUE;

}
