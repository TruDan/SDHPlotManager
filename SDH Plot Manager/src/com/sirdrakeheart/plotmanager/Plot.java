package com.sirdrakeheart.plotmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class Plot implements Serializable {

	private static final long serialVersionUID = -8720060726811870727L;

	public HashSet<Plot> data = new HashSet<Plot>();

	private Integer roadID;
	private Integer plotID;
	private Integer plotNumber; // Per-road
	private Integer price = 500;
	private String world;
	
	private Boolean isForSale;
	
	private String plotOwner;
	private String worldGuardRegionID;
	
	private Location signLocation;
	
	public Plot(Integer roadID) {
		this.roadID = roadID;
		this.plotID = PlotStorage.getID();
	}
	
	public Integer getRoadID() {
		return roadID;
	}
	public Integer getPlotID() {
		return plotID;
	}
	public Boolean getIsForSale() {
		return isForSale;
	}
	public String getPlotOwner() {
		return plotOwner;
	}
	public String getWorldGuardRegionID() {
		return worldGuardRegionID;
	}
	public void setRoadID(Integer roadID) {
		this.roadID = roadID;
	}
	public void setPlotID(Integer plotID) {
		this.plotID = plotID;
	}
	public void setIsForSale(Boolean isForSale) {
		this.isForSale = isForSale;
	}
	public void setPlotOwner(String plotOwner) {
		this.plotOwner = plotOwner;
	}
	public void setWorldGuardRegionID(String worldGuardRegionID) {
		this.worldGuardRegionID = worldGuardRegionID;
	}

	public Location getSignLocation() {
		return signLocation;
	}

	public void setSignLocation(Location signLocation) {
		this.signLocation = signLocation;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public void updateSign() {
		
		String line1 = PlotManagerPlugin.main.getConfig().getString("sign.line1");
		String line2 = PlotManagerPlugin.main.getConfig().getString("sign.line2");
		String line3 = PlotManagerPlugin.main.getConfig().getString("sign.line3");
		String line4 = PlotManagerPlugin.main.getConfig().getString("sign.line4");
		
		if(isForSale) {
			line1 = PlotManagerPlugin.main.getConfig().getString("sign.sold.line1");
			line2 = PlotManagerPlugin.main.getConfig().getString("sign.sold.line2");
			line3 = PlotManagerPlugin.main.getConfig().getString("sign.sold.line3");
			line4 = PlotManagerPlugin.main.getConfig().getString("sign.sold.line4");
		}
		String roadName = RoadStorage.getRoad(roadID).getRoadName();
		
		line1 = line1.replace("%player%", plotOwner).replace("%road%", roadName).replace("%price%",""+price).replace("%plot%",""+plotNumber);
		line2 = line2.replace("%player%", plotOwner).replace("%road%", roadName).replace("%price%",""+price).replace("%plot%",""+plotNumber);
		line3 = line3.replace("%player%", plotOwner).replace("%road%", roadName).replace("%price%",""+price).replace("%plot%",""+plotNumber);
		line4 = line4.replace("%player%", plotOwner).replace("%road%", roadName).replace("%price%",""+price).replace("%plot%",""+plotNumber);
		
		String world = this.world;
		Block signBlock = PlotManagerPlugin.main.getServer().getWorld(world).getBlockAt(signLocation);
		if (signBlock.getType().equals(Material.SIGN_POST) || signBlock.getType().equals(Material.WALL_SIGN) || signBlock.getType().equals(Material.SIGN)) {
            Sign sign = (Sign) signBlock.getState();
            sign.setLine(0, line1);
            sign.setLine(1, line2);
            sign.setLine(2, line3);
            sign.setLine(3, line4);
            sign.update(true);
        }
	}
	
	
	public void save() {
		try {
			data.add(this);
			String path = PlotManagerPlugin.filepath + "plots" + File.separator + this.plotID + ".dat";
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
			String path = PlotManagerPlugin.filepath + "plots" + File.separator + this.plotID + ".dat";
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			HashSet<Plot> inputhashset = null;
			
			if (result instanceof HashSet<?>) {
				inputhashset = (HashSet<Plot>) result;
			}
			if (inputhashset != null) {
				this.data = inputhashset;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
