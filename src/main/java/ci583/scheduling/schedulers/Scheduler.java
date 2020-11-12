package ci583.scheduling.schedulers;

import ci583.scheduling.Process;
import ci583.scheduling.output.SchedulerStats;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * An object representing a scheduling algorithm which schedules a process queue
 */
public abstract class Scheduler 
{
    protected String name;

    private SchedulerStats schedulerStats;

    public Scheduler (String name) {
        this.name = name;
        schedulerStats = new SchedulerStats(name);
    }

    public String getName() {
        return name;
    }
    
    public SchedulerStats getStats() { return this.schedulerStats; }

    /**
     * Go through the process queue (sorted by arrival time)
     * and create a new process queue using the selected scheduling algorithm
     * @return A scheduled process queue
     */
    public abstract Queue<Process> schedule(PriorityQueue<Process> q);

    public static PriorityQueue<Process> copyQueue(PriorityQueue<Process> q) {
        PriorityQueue<Process> qcopy = new PriorityQueue<>();
        ArrayList<Process> qoriginal = new ArrayList<>();
        while (!q.isEmpty()) {
            Process p = q.poll();
            try {
                qcopy.add((Process) p.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            qoriginal.add(p);
        }
        q.addAll(qoriginal);
        return qcopy;
    }

}
