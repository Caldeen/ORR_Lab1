package client;

import server.ServerRMIInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        try {
            int[][] matrix1 ;
            int[][] matrix2 ;
            // Getting the registry

            Registry registry = LocateRegistry.getRegistry(1345);

            // Looking up the registry for the remote object
            ServerRMIInterface stub = (ServerRMIInterface) registry.lookup("server.ServerRMIInterface");

            // Calling the remote method using the obtained object
            stub.printMsg();
            Scanner scanner = new Scanner(System.in);
            matrix1 = fillMatrix("pierwsza macierz");
            matrix2 = fillMatrix("druga macierz");
            //matrix1= new int[][]{{1, 2}, {2, 3}};
            //matrix2= new int[][]{{4, 5}, {6, 7}};


            for (int k = 0; k < 2; k++) {
                System.out.print(matrix1[0][k]+" ");
                System.out.print(matrix1[1][k]);
                System.out.println();
            }
            for (int k = 0; k < 2; k++) {
                System.out.print(matrix2[0][k]+" ");
                System.out.print(matrix2[1][k]);
                System.out.println();
            }
            long start = System.currentTimeMillis();
            int[][] clientReturnVal = stub.executeTask(matrix1,matrix2);
            System.out.println("execution time:"+ (System.currentTimeMillis()-start) +"ms");
            System.out.println("outcome: ");
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    System.out.print(" "+clientReturnVal[j][i]);
                }
                System.out.println();
            }

            // System.out.println("Remote method invoked");
        } catch (Exception e) {
            System.err.println("client.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    private static int [][] fillMatrix(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        int [][] matrix = new int[2][2];
        for (int i = 0; i < 2; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                list.add(scanner.nextInt());
            }

            for (int j = 0; j < 2; j++) {
                matrix[j][i] = list.remove(0);
            }
        }
        return matrix;
    }
}