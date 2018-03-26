import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Alin on 2018/3/25.
 */
public class Client {
    private static Manager stub = null;
    private Client() {}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Registry reg = LocateRegistry.getRegistry(17524);
            stub = (Manager) reg.lookup("manager");

        } catch (Exception e) {
            System.err.println("Client exception thrown: " + e.toString());
            e.printStackTrace();
        }
        while(true){
            System.out.println("What do you want to do?");
            System.out.println("Enter 1 for Login, 2 for Regiser, only enter for exit:");
            String choice = sc.nextLine();
            if(choice.equals("1")){
                //login
                try {
                    login();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else if(choice.equals("2")){
                //register
                try {
                    register();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else if(choice.isEmpty()){
                System.exit(0);
            }
            else{
                System.out.println("Please enter a valid number:");
            }
        }

    }

    public static void login() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your user name, only enter for exit:");
        String username = sc.nextLine();
        if(username.isEmpty()){
            System.exit(0);
        }
        else{
            System.out.println("Please enter your passward:");
            String passward = sc.nextLine();
            int check = stub.checkInfo(username,passward);
            if(check==1){// login succeed
                System.out.println("Validation Succeed !");
                System.out.printf("Welcome, %s.\n", username);
                System.exit(0);
            }
            else if(check ==2){
                System.out.println("Passward Invalid.");
                System.out.println("Please try again.");
                login();
            }
            else if(check ==3){
                System.out.println("User Name Invalid.");
                System.out.println("Please try again.");
                login();
            }
        }
    }

    public static void register() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a new user name, only enter for exit:");
        String username = sc.nextLine();
        if(username.isEmpty()){
            System.exit(0);
        }
        else{
            System.out.println("Please enter your passward:");
            String passward = sc.nextLine();
            int check = stub.userRegister(username,passward);
            if(check==0){// register failed

                System.out.println("User Name Existed");
                System.out.println("Please try again.");
                register();
            }
            else{// register successed
                // do something or not
                // go on
                System.out.println("Register Successed.");
            }
        }
    }
}

