package club.veluxpvp.core.command;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class HealCommand {

	private Map<UUID, Long> cooldown;
	private final long COOLDOWN_MILLIS = TimeUnit.MINUTES.toMillis(8);
	
	public HealCommand() {
		this.cooldown = Maps.newConcurrentMap();
	}
	
	@Command(name = "heal", permission = "core.command.heal")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			if(!sender.hasPermission("core.command.heal.others")) {
				sender.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			if(sender instanceof Player) {
				if(System.currentTimeMillis() <= this.cooldown.getOrDefault(((Player) sender).getUniqueId(), 0L) && !sender.isOp()) {
					long timeleft = (this.cooldown.get(((Player) sender).getUniqueId()) - System.currentTimeMillis()) / 1000;
					sender.sendMessage(ChatUtil.TRANSLATE("&cYou can't use this for another &l" + TimeUtil.getFormattedDuration((int) timeleft) + "&c!"));
					return;
				}
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			target.setHealth(target.getMaxHealth());
			target.sendMessage(ChatUtil.TRANSLATE("You have been &ahealed&f!"));
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + " &fhas been &ahealed&f!"));
			
			if(sender instanceof Player && !sender.isOp()) this.cooldown.put(((Player) sender).getUniqueId(), System.currentTimeMillis() + this.COOLDOWN_MILLIS);
		} else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /heal <player>"));
				return;
			}
			
			Player player = (Player) sender;
			
			if(System.currentTimeMillis() <= this.cooldown.getOrDefault(player.getUniqueId(), 0L) && !player.isOp()) {
				long timeleft = (this.cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
				sender.sendMessage(ChatUtil.TRANSLATE("&cYou can't use this for another &l" + TimeUtil.getFormattedDuration((int) timeleft) + "&c!"));
				return;
			}
			
			player.setHealth(player.getMaxHealth());
			player.sendMessage(ChatUtil.TRANSLATE("You have been &ahealed&f!"));
			
			if(!player.isOp()) this.cooldown.put(player.getUniqueId(), System.currentTimeMillis() + this.COOLDOWN_MILLIS);
		}
	}
}
