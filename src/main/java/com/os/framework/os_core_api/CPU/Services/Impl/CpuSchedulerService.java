package com.os.framework.os_core_api.CPU.Services.Impl;

import com.os.framework.os_core_api.CPU.Models.SchedulerResponse;
import com.os.framework.os_core_api.CPU.Models.SchedulingRequest;
import com.os.framework.os_core_api.CPU.Services.CpuScheduler;
import com.os.framework.os_core_api.strategies.cpu.SchedulingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CpuSchedulerService {


    public ResponseEntity<?> getAllSchedulingStrategy() {
        return new ResponseEntity<>(SchedulingStrategy.getAllStrategies(), HttpStatus.OK);
    }

    public ResponseEntity<?> executeScheduler(SchedulingRequest request) {

        CpuScheduler scheduler;
        try{
            scheduler =  initializeScheduler(request);
        } catch (Exception e) {
            return new ResponseEntity<>(e , HttpStatus.BAD_REQUEST);
        }

        scheduler.setStrategy(request.getStrategy());
        scheduler.execute();
        
        SchedulerResponse response =  buildResponse(scheduler);

        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    private SchedulerResponse buildResponse(CpuScheduler scheduler) {
        return  SchedulerResponse.builder()
                .averageTurnaroundTime(scheduler.averageTurnaroundTime())
                .cpuUtilization(scheduler.cpuUtilization())
                .averageWaitingTime(scheduler.averageWaitingTime())
                .efficiency(scheduler.efficiency())
                .throughput(scheduler.throughput())
                .processes(scheduler.getProcesses())
                .ganttChart(scheduler.displayGanttChart())
                .build();
    }

    private CpuScheduler initializeScheduler(SchedulingRequest request) {

        List<Integer> arrivalTime = new ArrayList<>(request.getArrivalTime());
        List<Integer> burstTime = new ArrayList<>(request.getBurstTime());
        int contextSwitchingDelay = 0;
        long numberOfProcesses = 0;

        if (arrivalTime.size() != burstTime.size()) {
            log.error("Mismatch in size of arrival and burst times!");
            throw new IllegalArgumentException("Mismatch in size of arrival and burst times!");
        }

        numberOfProcesses = arrivalTime.size();

        List<Integer> priority = new ArrayList<>();
        if (request.getPriority() != null) {
            if (request.getPriority().size() != arrivalTime.size()) {
                log.error("Priority list size does not match arrival/burst list size.");
                throw new IllegalArgumentException("Priority list size does not match arrival/burst list size!");
            } else {
                priority.addAll(request.getPriority());
            }
        } else {
            for (int i = 0; i < numberOfProcesses; i++) {
                priority.add(1);
            }
        }

        if(request.getContextSwitchingDelay() != null) {
            contextSwitchingDelay = request.getContextSwitchingDelay();
        }

        return new CpuScheduler(arrivalTime , burstTime , priority , contextSwitchingDelay);

    }
}
