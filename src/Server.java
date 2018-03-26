import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alin on 2018/3/25.
 */
public class Server {
    public Server() {}

    public static void main(String args[]) {

        int port = 17524;
        try {
            ManagerImpl robj = new ManagerImpl();
            Manager stub = (Manager) UnicastRemoteObject.exportObject(robj, port);

            LocateRegistry.createRegistry(port);
            Registry registry = LocateRegistry.getRegistry(port);
            registry.rebind("manager", stub);
            System.out.println("Manager Server is ready to listen... ");

        } catch (Exception e) {
            System.err.println("Server exception thrown: " + e.toString());
            e.printStackTrace();
        }
    }
}
