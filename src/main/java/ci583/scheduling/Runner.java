package ci583.scheduling;

import ci583.scheduling.schedulers.Scheduler;

import java.util.PriorityQueue;

public class Runner {
    private static final int SIMULATION_RUNS = 3;
    private static final int MAX_PROCESSES_PER_RUN = 10;
    private static final int ALGORITHM_COUNT = 1;

    public static void run(Scheduler scheduler) {
        run(scheduler, SIMULATION_RUNS);
    }

    public static void run(Scheduler scheduler, int runs) {
        // Hold duplicated process queues for each algorithm to use
        @SuppressWarnings("unchecked")
        PriorityQueue<Process>[] q = new PriorityQueue[ALGORITHM_COUNT + 1];
        q = (PriorityQueue<Process>[]) q;

        System.out.println("---------------------------");
        System.out.format(scheduler.getName()+":\n");
        // Test the scheduling algorithm SIMULATION_RUNS times
        for (int i = 0; i < runs; ++i) {
            System.out.println("---------------------------");
            System.out.format("Scheduling Process Queue %d:\n", i + 1);
            System.out.println("---------------------------");

            //generate a new process queue for this testing round then duplicate it
            q[0] = ProcessFactory.generateProcesses(MAX_PROCESSES_PER_RUN);
            for (int j = 1; j < ALGORITHM_COUNT + 1; ++j)
                q[j] = Scheduler.copyQueue(q[0]);

            // Print the process list by ascending arrival time
            while (!q[ALGORITHM_COUNT].isEmpty())
                System.out.println(q[ALGORITHM_COUNT].poll());

            // Run each scheduling algorithm and show the results

            System.out.println("\n---------------------------");
            System.out.println("\n"+scheduler.getName());
            scheduler.schedule(q[0]);
        }
    }
}
