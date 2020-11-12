# OS Scheduling Simulation

This repository contains two simulators of OS scheduling algorithms both written in Java.

Get a copy of the repository and open it in your IDE. The first simulator is in the package 
`ci583.scheduling.os`. This simulator implements several scheduling algorithms: 

* *first-come-first-served*,
* *preemptive-first-come-first-served (no aging)*, and
* *non-preemptive-highest-priority-first (no aging)*. 

## *first-come-first-served*

Run the `main` method in the `FirstComeFirstServed` class and inspect the output. The first
block of text in the output is the initial queue of processes to be scheduled. You will see
that each process is due to start at a different time (`arrivalTime`) and is expected to run for
a different number of "time slices" (`expectedRunTime`). A time slice, also called a quantum, is the
 unit of time the scheduler will allow a process to run before pausing that process and switching 
 context to the next in the queue. Each process also has a priority -- this is ignored by the 
 *first-come-first-served* scheduler. 
 
 The next block of text represents the activity the scheduler on the queue of processes, allowing each to
 run in turn. Process B is the first to "arrive" and the string of eith `B`s represents the process
 doing its work to completion. Scroll to the bottom of the output to see the overall results for this
 algorithm. 

and PreemptiveFirstComeFirstServed schedulers have a
main method, so you can execute the simulations. You can compile and run the code
from the command line as well. The main methods contain the simulation parameters,
which can be changed. Run the simulations and try to understand the output that the
simulators produced.
You can also add a main method to the Non-preemptive Highest Priority First No
Aging s code, so that it can also be executed.
MOS-Schedular
This another implementation of the First Come First Served scheduling algorithm. As
above, unzip the file and create a project to look at the code, but you will have to run
the simulation from the command line. The main method is in the Scheduling.java file
and the simulation parameters are specified in the scheduling.conf file. To run the
simulation, invoke the code supplying the configuration file as a parameter: java
Scheduling scheduling.conf.
The results of the simulation are saved in the two files:
Summary-Results and Summary-Processes. The program will display
"Working..." while the simulation is working, and "Completed." when the simulation is
complete.
Working...
Completed.
The simulator reads parameters from the configuration file ("scheduling.conf"). It
creates a specified number of processes, each of which blocks for input or output aftera number of milliseconds that can be specified for each process. Each process is
allowed to run for a randomly generated amount of time, with the amount of time
constrained by a specified average (mean) in milliseconds, and standard deviations
from that average.
After reading the configuration file, the scheduling algorithm then "runs" the
processes, causing each to block for input or output after the specified interval until
all processes have completed their randomly generated amount of runtime, or until
the maximum amount of runtime for the simulation is exceeded.
As the simulation proceeds, a log file ("Summary-Processes") is generated which
shows the activity of the scheduling algorithm as it considers each process in the
process queue.
After the simulation halts, a summary report ("Summary-Results") is generated which
shows statistics for each process and for the simulation as a whole.
The fields and columns in the report are described in the following table.
Field
Description
Scheduling
Type: The type of the scheduling algorithm used. The value displayed is "hard
coded" in the SchedulingAlgorithm.java file.
Scheduling
Name: The name of the scheduling algorithm used. The value displayed is "hard
coded" in the SchedulingAlgorithm.java file.
Simulation
Run Time: The number of milliseconds that the simulation ran. This may be less than or
equal to the total amount of time specified by the "runtime" configuration
parameter.
Mean: The average amount of runtime for the processes as specified by the
"meandev" configuration parameter.
Standard
Deviation: The standard deviation from the average amount of runtime for the processes
as specified by the "standdev" configuration parameter.
Process # The process number assigned to the process by the simulator. The process
number is between 0 and n-1, where n is the number specified by the
"numprocess" configuration parameter.
CPU Time The randomly generated total runtime for the process in milliseconds. This is
determined by the "meandev" and "standdev" parameters in the
configuration file.
IO Blocking The amount of time the process runs before it blocks for input or output.
This is specified for each process by a "process" directive in the
configuration file.
CPU
Completed The amount of runtime in milliseconds completed for the process. Note that
this may be less than the CPU Time for the process if the simulator runs out
of time as specified by the "runtime" configuration parameter.
CPU Blocked The number of times the process blocked for input or output during the
simulation.1. Create a configuration file in which all processes run an average of 2000
milliseconds with a standard deviation of zero, and which are blocked for input
or output every 500 milliseconds. Run the simulation for 10000 milliseconds
with 2 processes. Examine the two output files. Try again for 5 processes. Try
again for 10 processes. Explain what's happening.

