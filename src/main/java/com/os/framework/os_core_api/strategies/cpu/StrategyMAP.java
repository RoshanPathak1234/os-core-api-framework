package com.os.framework.os_core_api.strategies.cpu;

import com.os.framework.os_core_api.CPU.Schedulers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StrategyMAP {
    public static final Map<SchedulingStrategy, Supplier<Strategy>> STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put(SchedulingStrategy.FCFS, FCFS::new);
        STRATEGY_MAP.put(SchedulingStrategy.SJF, SJF::new);
        STRATEGY_MAP.put(SchedulingStrategy.SJF_PREEMPTIVE, SJF_PREEMPTIVE::new);
        STRATEGY_MAP.put(SchedulingStrategy.PRIORITY, Priority::new);
        STRATEGY_MAP.put(SchedulingStrategy.PRIORITY_PREEMPTIVE, PriorityPreemptive::new);
        STRATEGY_MAP.put(SchedulingStrategy.ROUND_ROBIN, RoundRobin::new);
        STRATEGY_MAP.put(SchedulingStrategy.MULTILEVEL_QUEUE, MultilevelQueue::new);
        STRATEGY_MAP.put(SchedulingStrategy.MULTILEVEL_FEEDBACK_QUEUE, MultilevelFeedbackQueue::new);
        STRATEGY_MAP.put(SchedulingStrategy.HRRN, HRRN::new);
        STRATEGY_MAP.put(SchedulingStrategy.LOTTERY, Lottery::new);
        STRATEGY_MAP.put(SchedulingStrategy.FAIR_SHARE, FairShare::new);
        STRATEGY_MAP.put(SchedulingStrategy.SHORTEST_PROCESS_NEXT, ShortestProcessNext::new);
        STRATEGY_MAP.put(SchedulingStrategy.WEIGHTED_ROUND_ROBIN, WeightedRoundRobin::new);
        STRATEGY_MAP.put(SchedulingStrategy.TWO_LEVEL, TwoLevel::new);
        STRATEGY_MAP.put(SchedulingStrategy.EDF, EDF::new);
        STRATEGY_MAP.put(SchedulingStrategy.RMS, RMS::new);
        STRATEGY_MAP.put(SchedulingStrategy.CFS, CFS::new);
    }
}
