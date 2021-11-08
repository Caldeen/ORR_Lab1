package agent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Agent extends AgentRMIImpl {
    private Agent(){}
    public static void main(String[] args) {
        try {
            String agentName = "1";
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(1345);

            // Looking up the registry for the remote object
            AgentRMIInterface stub = (AgentRMIInterface) registry.lookup("agentRegistry");

            // Calling the remote method using the obtained object

            registry.bind("agent "+agentName,stub);
            System.out.println("agent "+ agentName + "registered");
        } catch (Exception e) {
            System.err.println("agent exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
