package br.com.sd.naval.models;

import java.rmi.RemoteException;
import java.util.List;

import br.com.sd.naval.interfaces.NavalBattle;

public class User implements NavalBattle{
	
	private String name;
	private String password;
	private long points;
	private NavalMap selfMap;
	private NavalMap enemyMap;
	private List<Ship> shipList;
	
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
		System.out.println("Atacou "+row+"/"+col);
		stub.handleAttack(row, col);
		return false;
	}

	@Override
	public boolean handleAttack(int row, int col) throws RemoteException {
		System.out.println("Foi atacado "+row+"/"+col);
		return false;
	}
	
}
