package br.com.sd.naval.view;

import java.net.InetAddress;
import java.net.NetworkInterface;
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
			/*
			 * if(args.length < 1){ System.err.println(
			 * "Por favor forneça o IP do seu adversário"); throw new
			 * RemoteException(); } String host = args[0]; InetAddress
			 * IP=InetAddress.getLocalHost();
			 * System.setProperty("java.rmi.server.hostname",IP.getHostName());
			 */

			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();

					if (!n.isLoopback()) {
						System.out.println("IP do jogo" + i.getHostAddress());
					}
				}
			}

			// Create and export a remote object
			User user = new User();
			NavalBattle stub = (NavalBattle) UnicastRemoteObject.exportObject(user, 0);

			// Register the remote object with a Java RMI registry
			// Registry registry = LocateRegistry.getRegistry();
			// registry.rebind("Naval", stub);

			// Registry registryRemote = null;
			NavalBattle stubRemote = new User();

			/*
			 * System.out.println("Procurando outro jogador...");
			 * while(registryRemote == null){ Thread.sleep(1000); try{
			 * registryRemote = LocateRegistry.getRegistry(host); stubRemote =
			 * (NavalBattle) registryRemote.lookup("Naval"); } catch (Exception
			 * e){
			 * 
			 * } }
			 */

			initGame(user, stubRemote);

		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}

	}

	private static boolean initGame(User user, NavalBattle enemy) {
		Ship portaAviao = new Ship(5, "Porta Avião"); // 1
		Ship navioDeGuerra = new Ship(4, "Návio de Guerra"); // 1
		Ship cruzador = new Ship(3, "Cruzador");// 1
		Ship submarino1 = new Ship(1, "Submarino");// 3
		Ship submarino2 = new Ship(1, "Submarino");// 3
		Ship submarino3 = new Ship(1, "Submarino");// 3
		Ship destruidor1 = new Ship(2, "Destruidor");// 2
		Ship destruidor2 = new Ship(2, "Destruidor");// 2

		List<Ship> ships = new ArrayList<>();
		ships.add(submarino1);
		ships.add(submarino2);
		ships.add(submarino3);
		ships.add(destruidor2);
		ships.add(destruidor1);
		ships.add(navioDeGuerra);
		ships.add(cruzador);
		ships.add(portaAviao);

		user.setSelfMap(new NavalMap(MAP_SIZE, MAP_SIZE));
		Scanner scanner = new Scanner(System.in);
		for (Ship ship : ships) {
			user.getSelfMap().showMap();

			System.out.println("Em qual posição deseja inserir o " + ship.getName());
			String pos = scanner.next();
			int lin = ((int) (pos.charAt(0)) - 65);
			int col = Integer.parseInt(pos.split("")[1]) - 1;
			System.out.println(col);
			Position position = new Position();
			position.setX(lin);
			position.setY(col);
			position.setHited(false);

			System.out.println("Qual orientação? (V/H)" + ship.getName());
			String ori = scanner.next();
			ori = ori.toLowerCase();
			ship.adjustMap(position, ori.equals("h"), user.getSelfMap());
		}
		user.setShipList(ships);
		return false;
	}

}
