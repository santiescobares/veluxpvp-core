package club.veluxpvp.core.menu;

import org.bukkit.Material;

public class FillButton extends Button {

	@Override
	public Material getMaterial() {
		return Material.STAINED_GLASS_PANE;
	}
	
	@Override
	public byte getDataValue() {
		return (byte) 15;
	}
	
	@Override
	public String getName() {
		return " "; 
	}
}
