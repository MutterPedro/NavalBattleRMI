package br.com.sd.naval.models;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.sd.naval.interfaces.NavalBattle;

public class User implements NavalBattle {

	private String name;
	private String password;
	private long points;
	private NavalMap selfMap;
	private NavalMap enemyMap;
	private List<Ship> shipList;
	private boolean turn;
	private boolean winner;

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public NavalMap getSelfMap() {
		return selfMap;
	}

	public void setSelfMap(NavalMap selfMap) {
		this.selfMap = selfMap;
	}

	public NavalMap getEnemyMap() {
		return enemyMap;
	}

	public void setEnemyMap(NavalMap enemyMap) {
		this.enemyMap = enemyMap;
	}

	public List<Ship> getShipList() {
		return shipList;
	}

	public void setShipList(List<Ship> shipList) {
		this.shipList = shipList;
	}

	@Override
	public boolean attack(int row, int col, NavalBattle stub) throws RemoteException {
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println("Atacou " + row + "/" + col);
		Map<Integer, String> attack = stub.handleAttack(row, col);

		Set<Integer> setKey = attack.keySet();

		int hit = setKey.iterator().next();

		this.getEnemyMap().setMapPoint(row, col, hit);
		System.out.println(attack.get(hit));

		if (hit == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<Integer, String> handleAttack(int row, int col) throws RemoteException {
		System.out.println("Foi atacado " + row + "/" + col);
		String msg;
		int hit = this.getSelfMap().getMap()[row][col];
		switch (hit) {
		case 0:
			msg = "Água";
			hit = 3;
			break;
		case 1:
			msg = "Acertou o návio " + this.shipInPosition(row, col).getName();
			this.getSelfMap().setMapPoint(row, col, 2);
			this.setPoints(points - 1);
			hit = 2;
			break;
		default:
			msg = "Alvo já acertado";
			break;
		}
		System.out.println(msg);
		Map<Integer, String> result = new HashMap<>();
		result.put(hit, msg);
		turn = true;
		return result;
	}

	@Override
	public boolean isTurn() {
		return turn;
	}

	@Override
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public Ship shipInPosition(int row, int col) {
		for (Ship ship : shipList) {
			for (Position pos : ship.getPositions()) {
				if (pos.getX() == row && pos.getY() == col) {
					return ship;
				}
			}
		}

		return null;
	}

	@Override
	public boolean win() throws RemoteException {
		System.out.println("Parábens, você destruio todos os barcos do adversário!");
		this.winner = true;
		return true;
	}

}
