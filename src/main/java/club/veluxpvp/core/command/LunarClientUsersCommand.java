package club.veluxpvp.core.command;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lunarclient.bukkitapi.LunarClientAPI;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class LunarClientUsersCommand {

	@Command(name = "lunarclientusers", aliases = {"lcusers", "lcu"})
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();
		List<Player> players = LunarClientAPI.getInstance().getPlayersRunningLunarClient().stream().collect(Collectors.toList());
		String playersMsg = " &7* &f";
		
		if(!Core.isLunarAPIEnabled()) {
			sender.sendMessage(ChatUtil.TRANSLATE("&cLunarClientAPI not found!"));
			return;
		}
		
		if(players.size() > 0) {
			for(int i = 0; i < players.size(); i++) {
				playersMsg += players.get(i).getName() + (i != (players.size() - 1) ? "&7, &f" : "");
			}
		} else {
			playersMsg += "&cNobody";
		}
		
		sender.sendMessage(ChatUtil.LINE());
		sender.sendMessage(ChatUtil.TRANSLATE("&b&lPlayers running LunarClient &7(" + players.size() + ")"));
		sender.sendMessage(ChatUtil.LINE());
		sender.sendMessage(ChatUtil.TRANSLATE(playersMsg));
		sender.sendMessage(ChatUtil.LINE());
	}
}
