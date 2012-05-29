package com.sirdrakeheart.plotmanager;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PlotStorage implements Serializable {

	private static final long serialVersionUID = -4309231803153221488L;

	private static Map<Integer,Plot> data = new HashMap<Integer,Plot>();
	
	public static Integer getID() {
		return data.size()+1;
	}
	
	public static Plot getPlot(Integer id) {
		// Check if plot is new
		if(PlotStorage.check(id)) {
			if(data.containsKey(id)) {
				// Plot is existing, load data
				Plot pd = data.get(id);
				return pd;
			}
			else {
				// Load plot into memory
				Plot pd = new Plot(id);
				pd.load();
				data.put(id, pd);
				return pd;
			}
		}
		else {
			// Plot is new, create data
			Plot pd = new Plot(id);
			pd.save();
			data.put(id, pd);
			return pd;
		}
	}
	
	public static boolean check(Integer id) {
		File file = new File(PlotManagerPlugin.filepath+"plots" + File.separator + id + ".dat");
		boolean exists = file.exists();
		if (!exists) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
}
