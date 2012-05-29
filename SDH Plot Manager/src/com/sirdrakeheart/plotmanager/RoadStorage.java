package com.sirdrakeheart.plotmanager;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class RoadStorage implements Serializable {

	private static final long serialVersionUID = -4309231803153221488L;

	private static Map<Integer,Road> data = new HashMap<Integer,Road>();
	
	public static Integer getID() {
		return data.size()+1;
	}
	
	public static Road getRoad(Integer id) {
		// Check if plot is new
		if(RoadStorage.check(id)) {
			if(data.containsKey(id)) {
				// Plot is existing, load data
				Road pd = data.get(id);
				return pd;
			}
			else {
				// Load plot into memory
				Road pd = new Road(id);
				pd.load();
				data.put(id, pd);
				return pd;
			}
		}
		else {
			// Plot is new, create data
			Road pd = new Road(id);
			pd.save();
			data.put(id, pd);
			return pd;
		}
	}
	
	public static boolean check(Integer id) {
		File file = new File(PlotManagerPlugin.filepath+"roads" + File.separator + id + ".dat");
		boolean exists = file.exists();
		if (!exists) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
}
