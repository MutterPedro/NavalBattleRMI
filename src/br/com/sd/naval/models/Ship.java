package br.com.sd.naval.models;

import java.util.ArrayList;
import java.util.List;

public class Ship {
	private int size;
	private String name;
	private List<Position> positions;

	public Ship(int size, String name) {
		this.size = size;
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public boolean adjustMap(Position position, boolean orientation, NavalMap map) {
		// horizontal
		
		if (!orientation) {
			for (int i = 0; i < this.size; i++) {
				if (position.getX() + i < 0 || position.getX() + i >= map.getNumCol()
						|| map.getMap()[position.getX() + i][position.getY()] == 1) {
				 return false;
				}
			}
			List<Position> list = new ArrayList<>();
			for (int i = 0; i < this.size; i++) {
				Position pos = new Position();
				pos.setX(position.getX()+i);
				pos.setY(position.getY());
				pos.setHited(false);
				list.add(pos);
				map.setMapPoint(position.getX()+i, position.getY(), 1);
			}
			this.setPositions(list);
		} else {
			for (int i = 0; i < this.size; i++) {
				if (position.getY() + i < 0 || position.getY() + i >= map.getNumCol()
						|| map.getMap()[position.getX()][position.getY()+i] == 1) {
					return false;
				}
			}
			List<Position> list = new ArrayList<>();
			for (int i = 0; i < this.size; i++) {
				Position pos = new Position();
				pos.setX(position.getX());
				pos.setY(position.getY()+i);
				pos.setHited(false);
				list.add(pos);
				map.setMapPoint(position.getX(), position.getY()+i, 1);
			}
			this.setPositions(list);
		}

		return true;
	}

}
