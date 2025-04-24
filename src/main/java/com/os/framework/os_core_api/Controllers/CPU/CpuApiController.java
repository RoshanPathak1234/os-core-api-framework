package com.os.framework.os_core_api.Controllers.CPU;

import com.os.framework.os_core_api.CPU.Models.SchedulerResponse;
import com.os.framework.os_core_api.CPU.Models.SchedulingRequest;
import com.os.framework.os_core_api.CPU.Services.Impl.CpuShedulerService;
import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("os-core-api/cpu")
public class CpuApiController {

    @Autowired
    private CpuShedulerService cpuShedulerService;

    @Operation(summary = "Get all available CPU scheduling strategies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved strategies",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SchedulingStrategy.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("getAllSchedulingStrategy")
    public ResponseEntity<?> getAllSchedlingStrategy() {
        return cpuShedulerService.getAllSchedlingStrategy();
    }


    @Operation(summary = "Execute selected CPU scheduling algorithm based on input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed scheduler",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SchedulerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("compute")
    public ResponseEntity<?> executeSheduler(@RequestBody SchedulingRequest request) {
        return cpuShedulerService.executeSheduler(request);
    }
}
