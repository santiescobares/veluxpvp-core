package club.veluxpvp.core.command;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ReportCommand {

	private Map<UUID, Long> cooldown;
	private final long COOLDOWN = TimeUnit.MINUTES.toSeconds(3);
	
	public ReportCommand() {
		this.cooldown = Maps.newHashMap();
	}
	
	@Command(name = "report", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 2) {
			Player target = Bukkit.getPlayer(args[0]);
			String reason = "";
			
			if(cooldown.containsKey(player.getUniqueId()) && !player.isOp()) {
				long timeleft = this.cooldown.get(player.getUniqueId());
				
				if(System.currentTimeMillis() < timeleft) {
					player.sendMessage(ChatUtil.TRANSLATE("&cYou can't use this for another &l" + TimeUtil.getFormattedDuration((int) timeleft / 1000) + "&c!"));
					return;
				}
			}
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(target.getName().equalsIgnoreCase(player.getName())) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou can't report yourself!"));
				return;
			}
			
			for(int i = 1; i < args.length; i++) {
				reason += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			String report = ChatUtil.TRANSLATE("&9[S] &b" + player.getName() + " &7(" + player.getServer().getServerName() + ") has reported &b" + target.getName() + " &7(" + target.getServer().getServerName() + ") for &f" + reason + "&7.");
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, report);
			
			this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + this.COOLDOWN);
			player.sendMessage(ChatUtil.TRANSLATE("We have successfully &areceived &fyour report!"));
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /report <player> <reason...>"));
		}
	}
}
