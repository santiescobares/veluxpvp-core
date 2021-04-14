package club.veluxpvp.core.profile;

import java.net.InetAddress;
import java.util.UUID;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.PlayerUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Profile {

	private UUID uuid;
	private InetAddress firstAddress, lastAddress;
	private String firstJoin, lastSeen, lastServer;
	private boolean privateMessages, privateMessagesSounds, verified;
	private String rank, tag;
	
	public Profile(UUID uuid) {
		this.uuid = uuid;
		this.firstAddress = null;
		this.lastAddress = null;
		this.firstJoin = "";
		this.lastSeen = "";
		this.lastServer = "";
		this.privateMessages = true;
		this.privateMessagesSounds = true;
		this.verified = false;
		this.rank = "";
		this.tag = "";
	}
	
	public String colorName() {
		Rank r = Core.getInstance().getRankManager().getByName(this.rank);
		return r == null ? PlayerUtil.name(this.uuid) : r.color() + PlayerUtil.name(this.uuid);
	}
	
	public String color() {
		Rank r = Core.getInstance().getRankManager().getByName(this.rank);
		return r == null ? "" : r.color();
	}
}
