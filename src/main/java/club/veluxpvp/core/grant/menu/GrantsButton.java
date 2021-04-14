package club.veluxpvp.core.grant.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.google.common.collect.Lists;

import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import club.veluxpvp.core.menu.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GrantsButton extends Button {

	@Getter private Grant grant;
	
	@Override
	public String getName() {
		return ChatColor.AQUA + this.grant.getMadeOn();
	}
	
	@Override
	public Material getMaterial() {
		return Material.STAINED_GLASS_PANE;
	}
	
	@Override
	public byte getDataValue() {
		return (byte) (this.grant.isActive() ? 5 : 14);
	}
	
	@Override
	public List<String> getLore() {
		List<String> lore = Lists.newArrayList();
		
		lore.add(ChatUtil.MENU_LINE());
		lore.add("&fGranter&7: &b" + this.grant.getGranter());
		lore.add("&fGranted Rank&7: &b" + this.grant.getGrantedRank());
		lore.add("&fReason&7: &b" + this.grant.getReason());
		lore.add("&fMade On&7: &b" + this.grant.getMadeOn());
		
		if(this.grant.isPermanent()) {
			lore.add("&fDuration&7: &bPermanent");
		} else if(this.grant.isActive()) {
			lore.add("&fDuration&7: &b" + this.grant.duration());
			
			long timeleft = 0L;
			try {
				Date expiresOn = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this.grant.getExpiresOn());
				Date now = new Date();
				
				timeleft = (expiresOn.getTime() - now.getTime()) / 1000;
			} catch(ParseException e) {
				e.printStackTrace();
			}
			
			lore.add("&fExpires In&7: &b" + TimeUtil.getFormattedDuration((int) timeleft));
		} else {
			lore.add("&cExpired");
		}
		
		if(this.grant.isRemoved()) {
			lore.add(ChatUtil.MENU_LINE());
			lore.add("&fRemoved By&7: &c" + this.grant.getRemovedBy());
			lore.add("&fRemoved On&7: &c" + this.grant.getRemovedOn());
			lore.add("&fReason&7: &c" + this.grant.getRemoveReason());
		}
		
		lore.add(ChatUtil.MENU_LINE());
		return lore;
	}
}
