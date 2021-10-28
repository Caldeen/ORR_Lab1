import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Agent extends AgentServerImpl{
    private Agent(){}
    public static void main(String[] args) {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object
            AgentServerInterface skeleton = (AgentServerInterface) registry.lookup("agentRegistry");

            // Calling the remote method using the obtained object
            stub.printMsg();

            int clientReturnVal = stub.testReturn(5,22);
            System.out.println("return = "+clientReturnVal);

            // System.out.println("Remote method invoked");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
