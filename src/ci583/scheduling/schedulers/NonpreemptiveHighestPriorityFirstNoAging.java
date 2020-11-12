package ci583.scheduling.schedulers;

import ci583.scheduling.Process;
import ci583.scheduling.output.SchedulerStats;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*******************************************************************
 * Extends Scheduler as a Non-preemptive highest priority first algorithm w/o aging
 * which schedules based on priority and allows process to finish even if a higher
 * priority process has become ready to run
 * Reads a PriorityQueue<Process>, schedules it, and returns a new Queue<Process>
 * *****************************************************************/

public class NonpreemptiveHighestPriorityFirstNoAging extends Scheduler {

    public NonpreemptiveHighestPriorityFirstNoAging() {
        super("NON-PREEMPTIVE HIGHEST PRORIOTY FIRST (NO AGING)");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) {
        int finishTime = 0;
        int startTime;
        Process p;
        Process scheduled;
        SchedulerStats schedulerStats = this.getStats();
        Queue<Process> scheduledQueue = new LinkedList<>();

        // Queue processes that are ready to run, and order by shortest run time
        // break ties with arrival time so they are readied in the correct order
        @SuppressWarnings("unchecked")
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(10,
                new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Process p1 = (Process) o1;
                        Process p2 = (Process) o2;
                        if (p1.getPriority() == p2.getPriority())
                            return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
                        else
                            return p1.getPriority() < p2.getPriority() ? -1 : 1;
                    }
                });

        while (!q.isEmpty() || !readyQueue.isEmpty()) {

            // add processes that have arrived by now to the ready queue
            while (!q.isEmpty() && q.peek().getArrivalTime() <= finishTime)
                readyQueue.add(q.poll());

            p = readyQueue.isEmpty() ? q.poll() : readyQueue.poll();

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
