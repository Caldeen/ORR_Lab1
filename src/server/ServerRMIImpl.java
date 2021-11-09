package server;

import agent.AgentRMIInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Implementing the remote interface
public class ServerRMIImpl implements ServerRMIInterface {
    private int threadNumber=Server.threadNumber;

    private class OperationCallable implements Callable<Integer[]> {
        private int x;
        private int y;
        private int op;
        private int dest;

        public OperationCallable(int x, int y1, int op, int dest) {
            this.x = x;
            this.y = y1;
            this.op = op;
            this.dest = dest;
        }
        @Override
        public Integer[] call() throws Exception {
            int result;
            Registry registry = LocateRegistry.getRegistry(1346);
            int assignedAgent = dest%threadNumber;
            // Looking up the registry for the remote object
            AgentRMIInterface stub = (AgentRMIInterface) registry.lookup("agent "+assignedAgent);
            result=stub.operation(x,y,op);
            return new Integer[]{result, dest};
        }
    }

    // Implementing the interface method
    public void printMsg() {
        System.out.println("This is an example RMI program");
    }

    @Override
    public int[][] executeTask(int[][] a, int[][] b) throws RemoteException, ExecutionException, InterruptedException {
        System.out.println("liczba: "+threadNumber);
        ExecutorService executorService = Executors.newFixedThreadPool(Server.threadNumber);
        List<Callable<Integer[]>> tasks = new ArrayList<>();
        tasks.add(new OperationCallable(a[0][0], b[0][0], 1, 0));
        tasks.add(new OperationCallable(a[1][0], b[0][1], 1, 1));
        tasks.add(new OperationCallable(a[0][0], b[1][0], 1, 2));
        tasks.add(new OperationCallable(a[1][0], b[1][1], 1, 3));
        tasks.add(new OperationCallable(a[0][1], b[0][0], 1, 4));
        tasks.add(new OperationCallable(a[1][1], b[0][1], 1, 5));
        tasks.add(new OperationCallable(a[0][1], b[1][0], 1, 6));
        tasks.add(new OperationCallable(a[1][1], b[1][1], 1, 7));
        List<Future<Integer[]>> futures = new ArrayList<>();
        try {
            futures = executorService.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tasks.clear();
        futures.sort((o1, o2) -> {
            try {
                return o1.get()[1].compareTo(o2.get()[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        });
        for (int i = 0; i < 4; i++) {
            tasks.add(new OperationCallable(futures.get(2 * i).get()[0], futures.get(2 * i + 1).get()[0], 0, i));
        }
        try {
            futures = executorService.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[][] result = new int[2][2];

        result[0][0] = futures.get(0).get()[0];
        result[1][0] = futures.get(1).get()[0];
        result[0][1] = futures.get(2).get()[0];
        result[1][1] = futures.get(3).get()[0];
        executorService.shutdown();
        return result;
    }
}