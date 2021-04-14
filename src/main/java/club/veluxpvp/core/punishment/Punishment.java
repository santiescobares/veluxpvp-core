package club.veluxpvp.core.punishment;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Punishment {

	private String id;
	private UUID punishedUUID;
	private String punisher;
	private PunishmentType type;
	private String reason, madeOn, expiresOn, duration, punishedIP;
	private boolean ip, silent, active;
	
	// Non IP punishment
	public Punishment(UUID punishedUUID, String punisher, PunishmentType type, String reason, String duration, boolean silent) {
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.punishedUUID = punishedUUID;
		this.punisher = punisher;
		this.type = type;
		this.reason = reason;
		this.duration = duration;
		this.silent = silent;
		this.madeOn = TimeUtil.formatDate(new Date());
		this.expiresOn = duration.equalsIgnoreCase("Permanent") ? "Never" : TimeUtil.formatDate(TimeUtil.addTimeToDate(duration));
		this.punishedIP = "";
		this.ip = false;
		this.active = type == PunishmentType.KICK;
	}
	
	// IP punishment
	public Punishment(UUID punishedUUID, String punisher, PunishmentType type, String reason, String duration, String ip, boolean silent) {
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.punishedUUID = punishedUUID;
		this.punisher = punisher;
		this.type = type;
		this.reason = reason;
		this.duration = duration;
		this.silent = silent;
		this.madeOn = TimeUtil.formatDate(new Date());
		this.expiresOn = duration.equalsIgnoreCase("Permanent") ? "Never" : TimeUtil.formatDate(TimeUtil.addTimeToDate(duration));
		this.punishedIP = ip;
		this.ip = true;
		this.active = type == PunishmentType.KICK;
	}
	
	// For punishment manager
	public Punishment(UUID punishedUUID) {
		this.punishedUUID = punishedUUID;
		this.id = "";
		this.punisher = "";
		this.type = null;
		this.reason = "";
		this.duration = "";
		this.silent = false;
		this.madeOn = "";
		this.expiresOn = "";
		this.punishedIP = "";
		this.ip = false;
		this.active = false;
	}
	
	public boolean isPermanent() {
		return this.duration.equalsIgnoreCase("Permanent") || this.expiresOn.equalsIgnoreCase("Never");
	}
	
	public void punish() {
		Player player = Bukkit.getPlayer(this.punishedUUID);
		if(player == null) return;
		
		switch(this.type) {
		case KICK:
			player.kickPlayer(ChatUtil.TRANSLATE("&cYou have been kicked from VeluxPvP!\n&7" + this.madeOn + "\n\n&fReason&7: &b" + this.reason));
			break;
		case WARN:
			player.sendMessage(ChatUtil.TRANSLATE("&cYou have been warned by a staff member for " + this.reason + "."));
			break;
		case BAN:
			player.kickPlayer(ChatUtil.TRANSLATE("&cYour account has been suspended from VeluxPvP!\n&7" + this.madeOn + "\n\n&fReason&7: &b" + this.reason + "\n&fExpires In&7: &b" + this.expiresOn));
			break;
		}
	}
}
