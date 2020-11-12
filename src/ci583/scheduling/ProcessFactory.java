package ci583.scheduling;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * ProcessFactory generates processes and puts them in a queue
 */

public class ProcessFactory {
    /*
     * Create processCount random processes and add to a priority queue
     * @return q A PriorityQueue ordered with lowest arrival time first
     **/
    public static PriorityQueue<Process> generateProcesses(int processCount)
    {
        String names ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Use a priority queue to order processes by arrival time (IMPORTANT!!)
        PriorityQueue<Process> q = new PriorityQueue<>();

        // get random arrival, expected time, and priority
        Random randomArrival = new Random();
        Random randomPriority = new Random();        
        Random randomExpectedTime = new Random();
        
        // Generate new processes and add to the process queue 
        for(int i = 0; i < 10; ++i)
        {		
            Process p = new Process();
            p.setArrivalTime(randomArrival.nextInt(50)+1); 
            p.setBurstTime(randomExpectedTime.nextInt(10) + 1);
            p.setPriority(randomPriority.nextInt(4) + 1);
            p.setName(names.charAt(i));
            q.add(p);
            
            //nextArrival += randomArrival.next;
        }
        return q;
    }
}
