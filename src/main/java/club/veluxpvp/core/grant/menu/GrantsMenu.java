package club.veluxpvp.core.grant.menu;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.menu.Button;
import club.veluxpvp.core.menu.Menu;
import club.veluxpvp.core.utilities.PlayerUtil;

public class GrantsMenu extends Menu {

	private UUID targetUUID;
	
	public GrantsMenu(Player viewer, UUID targetUUID) {
		super(viewer);
		this.targetUUID = targetUUID;
	}
	
	@Override
	public String getTitle() {
		return PlayerUtil.name(this.targetUUID) + "'s Grants";
	}

	@Override
	public Map<Integer, Button> getButtons() {
		Map<Integer, Button> buttons = Maps.newConcurrentMap();
		List<Grant> grants = Core.getInstance().getGrantManager().getSortedPlayerGrants(this.targetUUID);
		
		int slot = 0;
		for(Grant g : grants) {
			buttons.put(slot++, new GrantsButton(g));
		}
		
		return buttons;
	}
}
