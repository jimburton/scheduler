# OS Scheduling Simulation

This repository contains two simulators of OS scheduling algorithms both written in Java. The first simulator 
implements several scheduling algorithms: 
                                                                                          
* *first-come-first-served*,
* *preemptive-first-come-first-served (no aging)*, and
* *non-preemptive-highest-priority-first (no aging)*. 

The second simulator implements *first-come-first-served* only and is controlled by a configuration file.

Get a copy of the repository and open it in your IDE. This project uses *maven*, which is a tool for
 building Java projects, and the code is organised in the standard maven way: the application code is
 the folder `src/main/java` while the tests are in the folder `src/test/java`.   

## *First-come-first-served*

Run the test `testFCFS`, which runs the first simulator with the *first-come-first-served* algorithm.
 Inspect the output in the console. The first
block of text is the queue of processes to be scheduled. You will see
that each process is due to start at a different time (`arrivalTime`) and is expected to run for
a different number of "time slices" (`expectedRunTime`). A time slice, also called a "quantum", is the
 unit of time the scheduler will allow a process to run before pausing that process and switching 
 context to the next in the queue. Each process also has a *priority* -- this is ignored by the 
 *first-come-first-served* scheduler. 
 
 The next block of text represents the sequence in which the processes are executed by the scheduler, 
 which allows each to run in turn. Note that the "CPU" is doing nothing (represented by underscores)
 until the first process arrives. Check that the first process that did "arrive" is the one with the 
 smallest arrival time. Next, check that each process ran for the expected number of time slices.
 
 Now look at the `schedule` method in the class `ci583.scheduling.schedulers.FirstComeFirstServed`.
 This is the method that chooses the next process to run. Try to find the line of code that selects
 the next process, and note the way in which it happens.
 
## *Non-preemptive highest-priority*

Run the test `testNPHP` to see the next scheduling algorithm in action. This scheduler will select the 
next process based on it having the highest priority of those available to run. Because it is not
pre-emptive, the scheduler will not interrupt a process once it is running, even if a higher priority
process is available.

Now read the code for this scheduler, which is in the `schedulers` package. Not the difference in the way
the next process is selected.

## *Preemptive first-come-first-served*

Run the test `testPFCFS` to see the third scheduling algorithm. This scheduler will select the 
next process based on it having the shortest time remaining to run. It is pre-emptive, so as new 
processes become runnable they may be given priority over the currently running process. Again, 
read the code and identify the section where these decisions are made.

## MOS-Scheduler

There is a second implementation of the *first-come-first-served* scheduling algorithm in
the package `ci583.scheduling.mos`. This simulator was developed as part of the [Modern 
Operating Systems](http://www.ontko.com/moss) project. The main method is in the `Scheduling` class
and the simulation parameters are read from the config file in `etc/scheduling.conf`. The results 
of the simulation are saved in the two files: `etc/summary-results.log` and `etc/summary-processes.log`. 

The simulator reads parameters from the config file then
creates the specified number of processes, each of which blocks (simulating what happens when a process
needs to pause to wait for input or output) after a number of milliseconds that can be specified for each process. 
Each process is allowed to run for a randomly generated amount of time, with the amount of time
constrained by a specified average (mean) in milliseconds, and standard deviations
from that average.

After reading the configuration file, the scheduling algorithm then "runs" the
processes, causing each to block for input or output after the specified interval until
all processes have completed their randomly generated amount of runtime, or until
the maximum amount of runtime for the simulation is exceeded.

As the simulation proceeds, a log file (`etc/summary-processes.log`) is generated which
shows the activity of the scheduling algorithm as it considers each process in the
process queue. After the simulation halts, a summary report (`etc/summary-results.log`) is generated which
shows statistics for each process and for the simulation as a whole. The fields and columns in the report 
are described in the following table.

| Field                   | Description                                | 
| ----------------------- | ------------------------------------------ |
| Scheduling Type         | The type of scheduling algorithm used. The value displayed is hard coded in the `SchedulingAlgorithm` class.|
| Scheduling Name         | The name of the scheduling algorithm used. The value displayed is hard coded in the `SchedulingAlgorithm` class.               |
| Simulation Run Time     | The number of milliseconds that the simulation ran. This may be less than or equal to the total amount of time specified by the `runtime` configuration parameter. |
| Mean                   | The average amount of runtime for the processes as specified by the `meandev` configuration parameter. |
| Standard Deviation      | The standard deviation from the average amount of runtime for the processes as specified by the `standdev` configuration parameter.|
| Process #               | The process number assigned to the process by the simulator. The process number is between 0 and `n-1`, where `n` is the number specified by the `numprocess` configuration parameter. |
| CPU Time                | The randomly generated total runtime for the process in milliseconds. This is determined by the `meandev` and `standdev` parameters in the config file. |
| IO Blocking             | The amount of time the process runs before it blocks for input or output. This is specified for each process by a `process` directive in the config file. |
| CPU Completed           | The amount of runtime in milliseconds completed for the process. Note that this may be less than the CPU Time for the process if the simulator runs out of time as specified by the `runtime` configuration parameter. |
| CPU Blocked             | The number of times the process blocked for input or output during the simulation |

 Create a configuration file in which all processes run an average of 2000
milliseconds with a standard deviation of zero, and which are blocked for input
or output every 500 milliseconds. Run the simulation for 10000 milliseconds
with 2 processes. Examine the two output files. Try again for 5 processes. Try
again for 10 processes. Explain what's happening.

