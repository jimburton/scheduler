package ci583.scheduling.output;

import ci583.scheduling.Process;

import java.util.Queue;

/* Keep track of various time and throughput statistics */
public class SchedulerStats
{
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;
    private int processCount;
    private int quanta;

    private int roundTurnaroundTime;
    private int roundWaitingTime;
    private int roundResponseTime;
    private int roundProcessCount;
    private int roundQuanta;
    private String name;

    public SchedulerStats(String name) {
        this.name = name;
    }

    public double getAvgTurnaroundTime()
    {
        return turnaroundTime / (double) processCount;
    }

    public double getRoundAvgTurnaroundTime()
    {
        return roundTurnaroundTime / (double) roundProcessCount;
    }

    public double getAvgWaitingTime()
    {
        return waitingTime / (double) processCount;
    }

    public double getRoundAvgWaitingTime()
    {
        return roundWaitingTime / (double) roundProcessCount;
    }

    public double getAvgResponseTime()
    {
        return responseTime / (double) processCount;
    }

    public double getRoundAvgResponseTime()
    {
        return roundResponseTime / (double) roundProcessCount;
    }

    public double getAvgThroughput()
    {
        return 100 * processCount / (double) quanta;
    }

    public double getRoundAvgThroughput()
    {
        return 100 * roundProcessCount / (double) roundQuanta;
    }

    public void addWaitTime(double time)
    {
        waitingTime += time;
        roundWaitingTime += time;
    }

    public void addResponseTime(double time)
    {
        responseTime += time;
        roundResponseTime += time;
    }

    public void addTurnaroundTime(double time)
    {
        turnaroundTime += time;
        roundTurnaroundTime += time;
    }

    public void addProcess()
    {
        ++processCount;
        ++roundProcessCount;
    }

    public void addQuanta(int quantaCount)
    {
        quanta += quantaCount;
        roundQuanta += quantaCount;
    }

    public void nextRound()
    {
        roundTurnaroundTime = 0;
        roundWaitingTime = 0;
        roundResponseTime = 0;
        roundProcessCount = 0;
        roundQuanta = 0;
    }
    ///////////////////////////////
    /**
     * Print out the average statistics for the given scheduling algorithm
     */
    public String formatAvgStats()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(name+"\n");
        sb.append("    Turnaround time: " + getAvgTurnaroundTime() + "\n");
        sb.append("    Waiting time: " + getAvgWaitingTime()+ "\n");
        sb.append("    Response time: " + getAvgResponseTime()+ "\n");
        sb.append("    Throughput per 100 quanta: "+  getAvgThroughput()+ "\n");
        return sb.toString();
    }

    /**
     * Print out the average statistics for the current round  only
     */
    public void printRoundAvgStats()
    {
        System.out.format("    Turnaround: %-2.3f Waiting: %-2.3f Response: %-2.3f Throughput: %-2.3f\n",
                getRoundAvgTurnaroundTime(), getRoundAvgWaitingTime(),
                getRoundAvgResponseTime(),  getRoundAvgThroughput());
    }

    /**
     * Print a "time chart" of the results, e.g. ABCDABCD...
     * @param q A list of Processes that have been scheduled
     */
    public static void printTimeChart(Queue<Process> q)
    {
        int quanta = 0;
        System.out.print("    ");
        for (Process p : q)
        {
            while (quanta++ < p.getStartTime()) // show idle time
                System.out.print("_");
            quanta = p.getStartTime() + p.getBurstTime();

            for (int i = 0; i < p.getBurstTime(); ++i)
                System.out.print(p.getName());
        }
        System.out.println();
    }
}
