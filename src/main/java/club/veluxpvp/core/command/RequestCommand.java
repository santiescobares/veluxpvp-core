package club.veluxpvp.core.command;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RequestCommand {

	private Map<UUID, Long> cooldown;
	private final long COOLDOWN = TimeUnit.MINUTES.toSeconds(3);
	
	public RequestCommand() {
		this.cooldown = Maps.newHashMap();
	}
	
	@Command(name = "request", aliases = {"helpop"}, playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			String reason = "";
			
			if(cooldown.containsKey(player.getUniqueId()) && !player.isOp()) {
				long timeleft = this.cooldown.get(player.getUniqueId());
				
				if(System.currentTimeMillis() < timeleft) {
					player.sendMessage(ChatUtil.TRANSLATE("&cYou can't use this for another &l" + TimeUtil.getFormattedDuration((int) timeleft / 1000) + "&c!"));
					return;
				}
			}
			
			for(int i = 0; i < args.length; i++) {
				reason += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			String request = ChatUtil.TRANSLATE("&9[S] &b" + player.getName() + " &7(" + player.getServer().getServerName() + ") has requested assistance for &f" + reason + "&7.");
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, request);
			
			this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + this.COOLDOWN);
			player.sendMessage(ChatUtil.TRANSLATE("We have successfully &areceived &fyour request!"));
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /request <reason...>"));
		}
	}
}
