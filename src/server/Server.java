package server;

import agent.Agent;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends ServerRMIImpl {
    private List<Thread> agentThreads = new ArrayList<>();
    public static int threadNumber;
    public static void main(String args[]) {
        threadNumber=Integer.valueOf(args[0]);
        try {
            // Instantiating the implementation class
            ServerRMIImpl serverRMI = new ServerRMIImpl();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            ServerRMIInterface stub = (ServerRMIInterface) UnicastRemoteObject.exportObject(serverRMI, 0);
            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(1345);
            LocateRegistry.createRegistry(1346);
            for (int i = 0; i < threadNumber; i++) {
                Agent.main(new String[]{String.valueOf(i)});
            }
            //agent.AgentRMIInterface agentStub = (agent.AgentRMIInterface) registry.lookup("agentRegistry");
            registry.bind("server.ServerRMIInterface", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}