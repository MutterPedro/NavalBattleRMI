package br.com.sd.naval.view;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import br.com.sd.naval.interfaces.NavalBattle;
import br.com.sd.naval.models.NavalMap;
import br.com.sd.naval.models.Position;
import br.com.sd.naval.models.Ship;
import br.com.sd.naval.models.User;

public class Game {
	private static final int MAP_SIZE = 20;

	public static void main(String[] args) {

		try {
			if (args.length < 1) {
				System.err.println("Por favor forneça o IP do seu adversário");
				throw new RemoteException();
			}
			String host = args[0];
			InetAddress IP = InetAddress.getLocalHost();
			System.setProperty("java.rmi.server.hostname", IP.getHostName());

			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					
					 /* if (!n.isLoopback()) { System.out.println("IP do jogo" +
					  i.getHostAddress()); }*/
					 
				}
			}

			// Create and export a remote object
			User user = new User();
			NavalBattle stub = (NavalBattle) UnicastRemoteObject.exportObject(user, 0);

			//Register the remote object with a Java RMI registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Naval", stub);

			 Registry registryRemote = null;
			NavalBattle stubRemote = new User();

			System.out.println("Procurando outro jogador...");
			Object registryRemote1 = null;
			while (registryRemote1 == null) {
				Thread.sleep(1000);
				try {
					registryRemote1 = LocateRegistry.getRegistry(host);
					stubRemote = (NavalBattle) ((Registry) registryRemote1).lookup("Naval");
				} catch (Exception e1) {

				}
			}

			initGame(user, stubRemote);

		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
		
	}
	

	private static boolean initGame(User user, NavalBattle enemy) {
		
				  Ship portaAviao = new Ship(5, "Porta Avião"); // 1
		  Ship navioDeGuerra = new Ship(4, "Návio de Guerra"); // 1 
		  Ship cruzador = new Ship(3,"Cruzador"); // 1 
		  Ship submarino1 = new Ship(1, "Submarino");// 3 
		  Ship submarino2 = new Ship(1, "Submarino");// 3 
		  Ship submarino3 = new Ship(1, "Submarino");// 3 
		  Ship destruidor1 = new Ship(2, "Destruidor");// 2 
		  Ship destruidor2 = new Ship(2, "Destruidor");// 2
		  
		  List<Ship> ships = new ArrayList<>(); 
		  ships.add(submarino1);
		  //ships.add(submarino2); 
		  //ships.add(submarino3); 
		  //ships.add(destruidor2);
		  //ships.add(destruidor1); 
		  //ships.add(navioDeGuerra);
		  //ships.add(cruzador); 
		  //ships.add(portaAviao);
		  
		  user.setSelfMap(new NavalMap(MAP_SIZE, MAP_SIZE));
		  
		  Scanner scanner = new Scanner(System.in);
		  
		  for (Ship ship : ships) { 
			  user.getSelfMap().showMap();
		  
		  	System.out.println("Em qual posi��o deseja inserir o " +
		  	ship.getName()); String pos = scanner.next();
		  	while(!pos.matches("[A-Z][0-9]{1,2}")){ 
			  System.out.println("Utilize o padr�o correto, ex.: A10"); 
			  pos = scanner.next();		  
		  	}
		  
		  String linStr = pos.replaceAll("\\D*", ""); String colStr =
		  pos.replaceAll("\\d*", "" );
		  
		  int lin = ((int) (colStr.charAt(0)) - 65); int col =
		  Integer.parseInt(linStr) - 1;
		  
		  Position position = new Position(); position.setX(lin);
		  position.setY(col); position.setHited(false);
		  
		  System.out.println("Qual orienta��o? (V/H)" + ship.getName()); String
		  ori = scanner.next(); while(!ori.matches("[V]|[H]")){
		  System.out.println("Digite apenas V ou H"); ori = scanner.next(); }
		  
		  ori = ori.toLowerCase();
		  
		  while(!ship.adjustMap(position, ori.equals("h"), user.getSelfMap())){
		  
		  System.out.println("[Posi��o Inv�lida]"); System.out.println(
		  "Informe a nova posi��o " + ship.getName()); pos = scanner.next();
		  while(!pos.matches("[A-Z][0-9]{1,2}")){ System.out.println(
		  "Utilize o padr�o correto, ex.: A10"); pos = scanner.next();
		  
		  }
		  
		  linStr = pos.replaceAll("\\D*", ""); 
		  colStr = pos.replaceAll("\\d*", "" );
		  
		  lin = ((int) (colStr.charAt(0)) - 65); 
		  col = Integer.parseInt(linStr) - 1;
		  
		  position.setX(lin); 
		  position.setY(col); 
		  position.setHited(false);
		  
		  
		  System.out.println("Qual a nova orienta��o? (V/H)" + ship.getName());
		  ori = scanner.next(); 
		  while(!ori.matches("[V]|[H]")){
			  System.out.println("Digite apenas V ou H"); ori = scanner.next();
		  
		  } 
		  user.setShipList(ships);


		  }

		  }

		 // Scanner scanner = new Scanner(System.in);

		  user.setPoints(23);

		  String pos, linStr, colStr;

		  int row, col;
		  long points;

		  while (user.getPoints() != 0) {

				if (user.isTurn()) {

					try {
						user.getEnemyMap().showMap();
						System.out.println("[Sua Vez] Escolha a posição de ataque!");
						pos = scanner.next();
						while (!pos.matches("[A-Z][0-9]{1,2}")) {
							System.out.println("Utilize o padrão correto, ex.: A10");
							pos = scanner.next();

						}

						linStr = pos.replaceAll("\\D*", "");
						colStr = pos.replaceAll("\\d*", "");

						row = ((int) (colStr.charAt(0)) - 65);
						col = Integer.parseInt(linStr) - 1;

						if(!user.attack(row, col, enemy)){
							points = user.getPoints() - 1;
							user.setPoints(points);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		  scanner.close();
		return false;
	}
}
