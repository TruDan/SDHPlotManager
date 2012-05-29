package com.sirdrakeheart.plotmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Location;

public class Road implements Serializable {

	private static final long serialVersionUID = -3904439228439658268L;
	public HashSet<Road> data = new HashSet<Road>();

	private Integer roadID;
	
	private String roadName;
	
	private Map<Integer,Integer> plots = new HashMap<Integer,Integer>();
	private Map<Integer,Location> signLocations = new HashMap<Integer,Location>();
	
	public Road(Integer roadID) {
		this.roadID = roadID;
	}
	
	public void save() {
		try {
			data.add(this);
			String path = PlotManagerPlugin.filepath + "plots" + File.separator + this.roadID + ".dat";
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(data);
			oos.flush();
			oos.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		try{
			String path = PlotManagerPlugin.filepath + "plots" + File.separator + this.roadID + ".dat";
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			HashSet<Road> inputhashset = null;
			
			if (result instanceof HashSet<?>) {
				inputhashset = (HashSet<Road>) result;
			}
			if (inputhashset != null) {
				this.data = inputhashset;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	
}
