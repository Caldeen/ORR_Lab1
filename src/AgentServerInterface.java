import java.rmi.Remote;

public interface AgentServerInterface extends Remote {
    int multiply(int a, int b);
    int add (int a, int b);
}
