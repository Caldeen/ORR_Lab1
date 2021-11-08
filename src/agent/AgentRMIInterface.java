package agent;

import java.rmi.Remote;

public interface AgentRMIInterface extends Remote {
    int multiply(int a, int b);
    int add (int a, int b);
}
