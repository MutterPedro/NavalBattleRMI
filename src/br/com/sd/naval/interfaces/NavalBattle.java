package br.com.sd.naval.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NavalBattle extends Remote{

	boolean attack(int row, int col, NavalBattle stub) throws RemoteException;
	boolean handleAttack(int row, int col) throws RemoteException;
}
