package agent;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Agent extends AgentRMIImpl {
    private Agent() {
    }

    public static void main(String[] args) {
        try {
            AgentRMIImpl agentRMI = new AgentRMIImpl();
            String agentName = args[0];
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(1346);

            //Registry registry = LocateRegistry.createRegistry(1346);
            AgentRMIInterface stub = (AgentRMIInterface) UnicastRemoteObject.exportObject(agentRMI, 0);
            // Looking up the registry for the remote object

            // Calling the remote method using the obtained object

            registry.bind("agent " + agentName, stub);
            System.out.println("agent " + agentName + " registered");
        } catch (Exception e) {
            System.err.println("agent exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
