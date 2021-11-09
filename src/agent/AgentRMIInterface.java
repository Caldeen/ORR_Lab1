package agent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AgentRMIInterface extends Remote {
    int operation(int x,int y, int op) throws RemoteException;
}
