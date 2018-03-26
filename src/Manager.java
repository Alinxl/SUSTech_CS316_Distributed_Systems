import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Alin on 2018/3/21.
 */

public interface Manager extends Remote{

    public int userRegister(String userName, String passward)throws RemoteException;;
    public int checkInfo(String userName, String passward)throws RemoteException;;

}
