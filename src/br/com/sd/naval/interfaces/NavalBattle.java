package br.com.sd.naval.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface NavalBattle extends Remote{

	boolean attack(int row, int col, NavalBattle stub) throws RemoteException;
	Map<Integer, String> handleAttack(int row, int col) throws RemoteException;
	void setTurn(boolean play) throws RemoteException;
	boolean isTurn() throws RemoteException;
	boolean win() throws RemoteException;
}
