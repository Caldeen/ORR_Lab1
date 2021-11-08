package server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server extends ServerRMIImpl {
    private List<Thread> agentThreads = new ArrayList<>();
    public static void main(String args[]) {

        try {
            // Instantiating the implementation class
            ServerRMIImpl serverRMI = new ServerRMIImpl();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            ServerRMIInterface stub = (ServerRMIInterface) UnicastRemoteObject.exportObject(serverRMI, 0);
            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(1345);

            //agent.AgentRMIInterface agentStub = (agent.AgentRMIInterface) registry.lookup("agentRegistry");
            registry.bind("server.ServerRMIInterface", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
} 