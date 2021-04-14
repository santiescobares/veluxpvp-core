package club.veluxpvp.core.grant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.PlayerUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Grant {

	private String id;
	private UUID grantedUUID;
	private String granter, grantedRank, reason, madeOn, expiresOn, duration;
	private String removedBy, removedOn, removeReason;
	
	public Grant(UUID grantedUUID, String granter) {
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.grantedUUID = grantedUUID;
		this.granter = granter;
		this.grantedRank = "";
		this.reason = "";
		this.madeOn = "";
		this.expiresOn = "";
		this.duration = "";
		this.removedBy = "";
		this.removedOn = "";
		this.removeReason = "";
	}
	
	public boolean isPermanent() {
		return this.duration.equalsIgnoreCase("Permanent");
	}
	
	public boolean isRemoved() {
		return !this.removedBy.equals("") && !this.removedOn.equals("") && !this.removeReason.equals("");
	}
	
	public String duration() {
		if(this.isPermanent()) return "Permanent";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			Date madeOn = sdf.parse(this.madeOn);
			Date expiresOn = sdf.parse(this.expiresOn);
			
			long diff = (expiresOn.getTime() - madeOn.getTime()) / 1000;
			return TimeUtil.getFormattedDuration((int) diff);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean isActive() {
		if(this.isRemoved()) return false;
		if(this.isPermanent()) return true;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			Date expireOnDate = sdf.parse(this.expiresOn);
			Date now = new Date();
			
			return now.before(expireOnDate) || sdf.format(expireOnDate).equals(sdf.format(now));
		} catch(ParseException e) {
			e.printStackTrace();	
		}
		
		return false;
	}
	
	public void grant() {
		Profile target = Core.getInstance().getProfileManager().getProfile(this.grantedUUID);
		
		if(target == null) {
			target = new Profile(this.grantedUUID);
		}
		
		target.setRank(this.grantedRank);
		this.madeOn = TimeUtil.formatDate(new Date());
		if(!this.isPermanent()) {
			this.expiresOn = TimeUtil.formatDate(TimeUtil.addTimeToDate(this.duration));
		}
		
		Player p = Bukkit.getPlayer(target.getUuid());
		if(p != null) {
			p.sendMessage(ChatUtil.TRANSLATE("&b" + this.granter + " &fhas &agranted &fyou &b" + this.grantedRank + " &ffor &b" + this.duration() + "&f!"));
			Core.getInstance().getRankManager().updatePermissions(p);
		}
		
		Core.getInstance().getProfileManager().saveAsync(target);
		Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&9[Log] &b" + this.granter + " &fhas &agranted &b" + this.grantedRank + " &frank to &b" + PlayerUtil.name(this.grantedUUID) + " &ffor &b" + this.duration + " &ffor &b" + this.reason + "&f."));
	}
	
	public void remove() {
		Profile target = Core.getInstance().getProfileManager().getProfile(this.grantedUUID);
		Rank rank = Core.getInstance().getRankManager().getByName(target.getRank());
		
		if(rank == null || !rank.getName().equals(this.grantedRank)) return;
		
		Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
			Grant activeGrant = Core.getInstance().getGrantManager().getHighestActiveGrant(this.grantedUUID);
			
			if(activeGrant != null) {
				Rank activeRank = Core.getInstance().getRankManager().getByName(activeGrant.getGrantedRank());
				
				if(activeRank != null) target.setRank(activeRank.getName());
			} else {
				Rank defaultRank = Core.getInstance().getRankManager().getDefaultRank();
				
				if(defaultRank != null) {
					target.setRank(defaultRank.getName());
				} else {
					target.setRank("");
				}
			}
			
			Core.getInstance().getProfileManager().saveAsync(target);
			Player p = Bukkit.getPlayer(this.grantedUUID);
			
			if(p != null) {
				Core.getInstance().getRankManager().updatePermissions(p);
			}
		}, 20L);
	}
	
	public String serialize() {
		return "GrantedUUID=" + this.grantedUUID.toString() + " Granter=" + this.granter + " GrantedRank=" + this.grantedRank + 
				" Duration=" + this.duration;
	}
}
