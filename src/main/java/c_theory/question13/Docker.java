package c_theory.question13;

public class Docker {

    //todo A
    // What is a server?
    // Answer:
    // A Machine (physical or virtual) that is used to relay or provide some sort of information/service to it's users (clients)

    //todo B
    // What is the difference between build server and production server?
    // Answer: Build servers are used to compile the source code and test it.
    // Production server runs the tested and compiled app for end users. It is important to separate the tasks to
    // different servers to mitigate the risk and consequences of unauthorised tampering.
    //
    //

    //todo C
    // What is docker?
    // Answer: It's a virtualization technology that allows to run and develop apps in virtual containers which are
    //         system agnostic (those containers don't care what system you run them on).

    //todo D
    // Name and explain docker container benefits over virtual machine setup (java jar as system process and installed nginx)
    // 1 In case of the the docker, it's container's environment for the app/service does is not depend on the host system,
    //   while as in a virtual machine, we have to make sure that all of the dependencies for the app/service are also compatible with the host machine.
    // 2 Docker communicates with host machine natively while as virtual machine has to use an translation layer (hypervisor) which affects performance.


    //todo E
    // Name and explain docker container drawback over virtual machine setup (java jar as system process and installed nginx)
    // 1 Virtual machines are more secure because they don't share the host's kernel, while docker containers do.
    //   So if host system is compromised, it does not mean that the virtual machine's system is.

    //todo F
    // Name and describe tools for docker architecture
    // 1 Prometheus - A tool that provides monitoring and analytics for docker containers.
    // 2 Kubernetes - Container cluster management system, allows you to run application containers on different clusters.

    //todo G
    // Name and explain why are companies slow in moving existing systems to docker architecture (do not explain why docker is bad)
    // 1 Companies already have established systems that do not use dockers, and most of the time for the companies it's
    //   more cost effective to fix problems that come from not using dockers, rather than reconstructing their entire infrastructures.
    // 2 It takes additional resources and effort to train (or hire) employees that know how to work with dockers.
}
