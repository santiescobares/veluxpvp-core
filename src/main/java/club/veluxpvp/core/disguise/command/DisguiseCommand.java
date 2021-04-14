package club.veluxpvp.core.disguise.command;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.disguise.DisguiseManager;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class DisguiseCommand {

	@Command(name = "disguise", aliases = {"nick", "d"}, permission = "core.command.disguise", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		DisguiseManager disguiseManager = Core.getInstance().getDisguiseManager();
		
		if(args.length >= 2) {
			String newName = args[0];
			Rank rank = Core.getInstance().getRankManager().getByName(args[1]);
			
			if(disguiseManager.isDisguised(player)) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou are already disguised!"));
				return;
			}
			
			if(newName.length() < 3 || newName.length() > 16) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe chosen nick is either too short or too long!"));
				return;
			}
			
			if(!Pattern.matches("[a-zA-Z0-9_]{1,16}", newName)) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe chosen nick contains illegal characters!"));
				return;
			}
			
			Player matchingPlayer = Bukkit.getOnlinePlayers().stream()
					.filter(p -> p.getName().equalsIgnoreCase(newName))
					.findFirst()
					.orElse(null);
			
			if(matchingPlayer != null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThere is already an online player with that nick!"));
				return;
			}
			
			if(rank == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[1] + "\" not found!"));
				return;
			}
			
			disguiseManager.disguise(player, rank, newName);
			player.sendMessage(ChatUtil.TRANSLATE("&aYou are now disguised as &b" + newName + "&a!"));
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /disguise <newName> <rank>"));
		}
	}
}
