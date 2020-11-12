package ci583.scheduling;

import ci583.scheduling.schedulers.FirstComeFirstServed;
import ci583.scheduling.schedulers.NonpreemptiveHighestPriorityFirstNoAging;
import ci583.scheduling.schedulers.PreemptiveFirstComeFirstServedNoAging;
import ci583.scheduling.schedulers.Scheduler;
import org.junit.Test;

public class TestSchedulers {

    @Test
    public void testFCFS() {
        Scheduler fcfs = new FirstComeFirstServed();
        Runner.run(fcfs, 1);
    }

    @Test
    public void testNPHP() {
        Scheduler nphp = new NonpreemptiveHighestPriorityFirstNoAging();
        Runner.run(nphp, 1);
    }

    @Test
    public void runPFCFS() {
        Scheduler pfcfs = new PreemptiveFirstComeFirstServedNoAging();
        Runner.run(pfcfs, 1);
    }

    @Test
    public void testAll() {
        Scheduler fcfs = new FirstComeFirstServed();
        Runner.run(fcfs);
        Scheduler nphp = new NonpreemptiveHighestPriorityFirstNoAging();
        Runner.run(nphp);
        Scheduler pfcfs = new PreemptiveFirstComeFirstServedNoAging();
        Runner.run(pfcfs);

        System.out.println("\n-------------------------------------------");
        System.out.println("Average Statistics by Scheduling Algorithm");

        System.out.println("\n---------------------------");
        System.out.println(fcfs.getStats().formatAvgStats());
        System.out.println("\n---------------------------");
        System.out.println(nphp.getStats().formatAvgStats());
        System.out.println("\n---------------------------");
        System.out.println(pfcfs.getStats().formatAvgStats());
    }
}
