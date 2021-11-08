package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

// Creating Remote interface for our application 
public interface ServerRMIInterface extends Remote {
    void printMsg() throws RemoteException;
    int[][] executeTask(int[][] a, int[][] b) throws RemoteException, ExecutionException, InterruptedException;
}