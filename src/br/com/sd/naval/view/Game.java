package br.com.sd.naval.view;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.NetworkInterface;
import java.util.Enumeration

import br.com.sd.naval.interfaces.NavalBattle;
import br.com.sd.naval.models.User;

public class Game{
	private static final int MAP_SIZE = 20;
	
	public static void main(String[] args) {
		
		try {
			if(args.length < 1){
				System.err.println("Por favor forneça o IP do seu adversário");
				throw new RemoteException();
			}
			String host = args[0];
			InetAddress IP=InetAddress.getLocalHost();
            System.setProperty("java.rmi.server.hostname",IP.getHostAddress());            
			
            Enumeration e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements())
			{
    			NetworkInterface n = (NetworkInterface) e.nextElement();
    			Enumeration ee = n.getInetAddresses();
   				 while (ee.hasMoreElements()){
        			InetAddress i = (InetAddress) ee.nextElement();

        			if(!n.isLoopback()){
        				System.out.println("IP do jogo" + i.getHostAddress());
        			}
    			}
			}

			//Create and export a remote object
			User user = new User();			
			NavalBattle stub = (NavalBattle) UnicastRemoteObject.exportObject(user,0);
						
			//Register the remote object with a Java RMI registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Naval", stub);
			
			Registry registryRemote = null;
			NavalBattle stubRemote = new User();
			
			System.out.println("Procurando outro jogador...");
			while(registryRemote == null){
				Thread.sleep(1000);
				try{
					registryRemote = LocateRegistry.getRegistry(host);
					stubRemote = (NavalBattle) registryRemote.lookup("Naval");
				} catch (Exception e){
					
				}
			}
			
			user.attack(1,10,stubRemote);
			 
						
		}
			catch (Exception e) {
				System.err.println("Exception: " + e.toString());
				e.printStackTrace();			
		}

	}

}
