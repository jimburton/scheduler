package ci583.scheduling.schedulers;

import ci583.scheduling.Process;
import ci583.scheduling.output.SchedulerStats;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Extends Scheduler as a First Come First Served algorithm
 * Reads a PriorityQueue<Process>, schedules it, and returns a new Queue<Process>
 */
public class FirstComeFirstServed extends Scheduler {

    public FirstComeFirstServed() {
        super("FIRST COME FIRST SERVED");
    }

    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) {
        int startTime = 0; // the current time slice
        int queueSize = q.size();
        int finishTime = 0;
        Process p;
        Process scheduled;
        SchedulerStats schedulerStats = this.getStats();
        Queue<Process> scheduledQueue = new LinkedList<>();

        for (int i = 0; i < queueSize; ++i) {
            p = q.poll(); // MUST BE POLLED SEPARATELY for PriorityQueue to update
            // for (Process p : q) will give the wrong order!

            startTime = Math.max((int) Math.ceil(p.getArrivalTime()), finishTime);
            finishTime = startTime + p.getBurstTime();

            // Don't start any processes after 100 time slices
            if (startTime > 100) break;

            // Record the statistics for this process
            schedulerStats.addWaitTime(startTime - p.getArrivalTime());
            schedulerStats.addTurnaroundTime(finishTime - p.getArrivalTime());
            schedulerStats.addResponseTime(finishTime - startTime);
            schedulerStats.addProcess();

            // Create a new process with the calculated start time and add to a new queue
            scheduled = new Process();
            scheduled.setBurstTime(p.getBurstTime());
            scheduled.setStartTime(startTime);
            scheduled.setName(p.getName());
            scheduledQueue.add(scheduled);
        }
        schedulerStats.addQuanta(finishTime); // Add the total quanta to finish all jobs
        getStats().printTimeChart(scheduledQueue);
        getStats().printRoundAvgStats();
        schedulerStats.nextRound();

        return scheduledQueue;
    }

}
