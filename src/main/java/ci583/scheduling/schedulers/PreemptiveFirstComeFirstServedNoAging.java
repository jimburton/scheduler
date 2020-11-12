package ci583.scheduling.schedulers;

import ci583.scheduling.Process;
import ci583.scheduling.output.SchedulerStats;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************************************************
 * Extends Scheduler with a FCFS algorithm w/o aging which uses preemptive scheduling 
 * with priority first then arrival time as a tiebreaker
 * Reads a PriorityQueue<Process>, schedules it, and returns a new Queue<Process>
 * *****************************************************************/

public class PreemptiveFirstComeFirstServedNoAging extends Scheduler {

    public PreemptiveFirstComeFirstServedNoAging() {
        super("PREEMPTIVE FIRST COME FIRST SERVED (NO AGING)");
    }

    @Override
    public Queue<Process> schedule(PriorityQueue<Process> q) {
        int finishTime = 0;
        int startTime;
        Process p;
        Process scheduled;
        Process remaining;
        SchedulerStats schedulerStats = this.getStats();
        Queue<Process> scheduledQueue = new LinkedList<>();

        // Need to keep track of these to calculate turnaround and wait times
        Map<Character, Integer> startTimes = new HashMap<>();
        Map<Character, Integer> finishTimes = new HashMap<>();

        // Queue processes that are ready to run, and order by shortest run time
        // break ties with arrival time so they are readied in the correct order
        @SuppressWarnings({"unchecked", "rawtypes"})
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

        // Queue processes that are waiting to run by priority and arrival time
        PriorityQueue<Process> waitingQueue = new PriorityQueue<>(10,
                new Comparator<Object>() {
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

        while (!q.isEmpty() || !readyQueue.isEmpty() || !waitingQueue.isEmpty()) {

            // add processes that have arrived by now to the ready queue
            while (!q.isEmpty() && q.peek().getArrivalTime() <= finishTime) readyQueue.add(q.poll());

            // Get the process with the shortest remaining time that can start now
            // Break ties Waiting > Ready > Q to prioritize already running process
            if (readyQueue.isEmpty())
                p = (waitingQueue.isEmpty()) ? q.poll() : waitingQueue.poll();
            else if (waitingQueue.isEmpty())
                p = readyQueue.poll();
            else                         // needs to be < not <= to prioritize waitingQueue
                p = (readyQueue.peek().getPriority() < waitingQueue.peek().getPriority()) ? readyQueue.poll() : waitingQueue.poll();

            // Assign p one time slice for now
            startTime = Math.max((int) Math.ceil(p.getArrivalTime()), finishTime);
            finishTime = startTime + 1;

            // Record some stats if we haven't seen this process before
            if (!startTimes.containsKey(p.getName())) {
                if (startTime > 100) break;
                startTimes.put(p.getName(), startTime);
                schedulerStats.addWaitTime(startTime - p.getArrivalTime());
                schedulerStats.addResponseTime(startTime - p.getArrivalTime() + 1);
            } else // add the wait time this process was in waitingQueue
                schedulerStats.addWaitTime(startTime - finishTimes.get(p.getName()));

            // split p into a second process with n-1 time slices and add to waiting queue
            if (p.getBurstTime() > 1) {
                try {
                    remaining = (Process) p.clone();
                    remaining.setBurstTime(remaining.getBurstTime() - 1);
                    waitingQueue.add(remaining);
                    finishTimes.put(remaining.getName(), finishTime);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(NonpreemptiveHighestPriorityFirstNoAging.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else { // this process finished so record turnaround time
                schedulerStats.addTurnaroundTime(finishTime - startTimes.get(p.getName()));
                schedulerStats.addProcess();
            }

            // Create a new process with the calculated start time and add to a new queue
            scheduled = new Process();
            scheduled.setBurstTime(1);
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
