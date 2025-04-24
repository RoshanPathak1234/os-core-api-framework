package com.os.framework.os_core_api.CPU.Services.Impl;

import com.os.framework.os_core_api.CPU.Models.Process;
import com.os.framework.os_core_api.CPU.Models.SchedulingRequest;
import com.os.framework.os_core_api.CPU.Services.CpuScheduler;
import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CpuShedulerService {


    public ResponseEntity<?> getAllSchedlingStrategy() {
        return new ResponseEntity<>(SchedulingStrategy.getAllStrategies(), HttpStatus.OK);
    }

    public ResponseEntity<?> executeSheduler(SchedulingRequest request) {
        List<Integer> arrivalTime = new ArrayList<>(request.getArrivalTime());
        List<Integer> burstTime = new ArrayList<>(request.getBurstTime());
        Integer numberOfProcesses = 0;

        if (arrivalTime.size() != burstTime.size()) {
            log.error("Mismatch in number of arrival and burst times");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Arrival time and Burst time lists must be of the same length.");
        }

        numberOfProcesses = arrivalTime.size();

        List<Integer> priority = new ArrayList<>();
        if (request.getPriority() != null) {
            if (request.getPriority().size() != arrivalTime.size()) {
                log.warn("Priority list size does not match arrival/burst list size. Defaulting to empty priority.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Priority list size does not match arrival/burst list size. Defaulting to priority 1.");
            } else {
                priority.addAll(request.getPriority());
            }
        } else {
            for (int i = 0; i < numberOfProcesses; i++) {
                priority.add(1);
            }
        }



        CpuScheduler scheduler = new CpuScheduler();
        List<Process> processes = new ArrayList<>();
        return null;
    }
}
