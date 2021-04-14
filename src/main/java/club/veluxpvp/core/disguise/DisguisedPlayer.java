package club.veluxpvp.core.disguise;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.rank.Rank;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;

@Getter
public class DisguisedPlayer {

	private UUID realUUID;
	private String realName, fakeName;
	private Rank fakeRank;
	
	public DisguisedPlayer(UUID realUUID, String realName, String fakeName, Rank fakeRank) {
		this.realUUID = realUUID;
		this.realName = realName;
		this.fakeName = fakeName;
		this.fakeRank = fakeRank;
	}
	
	public void disguise() {
		Player player = Bukkit.getPlayer(this.realUUID);
		if(player == null) return;
		
		CraftPlayer craftPlayer = (CraftPlayer) player;
		GameProfile playerProfile = craftPlayer.getHandle().getProfile();
		
		craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle()));
		
		try {
			Field f = playerProfile.getClass().getDeclaredField("name");
			
			f.setAccessible(true);
			f.set(playerProfile, this.fakeName);
			f.setAccessible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			CraftPlayer cp = (CraftPlayer) p;
			
			cp.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
			Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
				cp.getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle()));
				cp.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()));
			
				p.hidePlayer(player);
				p.showPlayer(player);
			}, 2L);
		}
	}
	
	public void undisguise() {
		Player player = Bukkit.getPlayer(this.realUUID);
		CraftPlayer craftPlayer = (CraftPlayer) player;
		GameProfile playerProfile = craftPlayer.getHandle().getProfile();
		
		craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle()));
		
		try {
			Field f = playerProfile.getClass().getDeclaredField("name");
			
			f.setAccessible(true);
			f.set(playerProfile, this.realName);
			f.setAccessible(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			CraftPlayer cp = (CraftPlayer) p;
			
			cp.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
			Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
				cp.getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle()));
				cp.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()));
			
				p.hidePlayer(player);
				p.showPlayer(player);
			}, 2L);
		}
	}
}
