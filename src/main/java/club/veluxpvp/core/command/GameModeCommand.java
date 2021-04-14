package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class GameModeCommand {

	@Command(name = "gamemode", aliases = {"gm"}, permission = "core.command.gamemode")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			GameMode gamemode = this.getGameMode(args[0]);
			Player target = Bukkit.getPlayer(args[1]);
			
			if(gamemode == null || gamemode == GameMode.SPECTATOR) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cGame mode \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[1] + "\" not found!"));
				return;
			}
			
			target.setGameMode(gamemode);
			target.sendMessage(ChatUtil.TRANSLATE("Game mode &aupdated &fto &b" + (gamemode == GameMode.SURVIVAL ? "Survival" : gamemode == GameMode.CREATIVE ? "Creative" : "Adventure") + "&f!"));
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's game mode &aupdated &fto &b" + (gamemode == GameMode.SURVIVAL ? "Survival" : gamemode == GameMode.CREATIVE ? "Creative" : "Adventure") + "&f!"));
		} else if(args.length == 1) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /gamemode <mode> <player>"));
				return;
			}
			
			Player player = (Player) sender;
			GameMode gamemode = this.getGameMode(args[0]);
			
			if(gamemode == null || gamemode == GameMode.SPECTATOR) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cGame mode \"" + args[0] + "\" not found!"));
				return;
			}
			
			player.setGameMode(gamemode);
			player.sendMessage(ChatUtil.TRANSLATE("Game mode &aupdated &fto &b" + (gamemode == GameMode.SURVIVAL ? "Survival" : gamemode == GameMode.CREATIVE ? "Creative" : "Adventure") + "&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /gamemode <mode> " + ((sender instanceof Player) ? "[player]" : "<player>")));
		}
	}
	
	@SuppressWarnings("deprecation")
	private GameMode getGameMode(String gamemode) {
		GameMode mode = null;
		GameMode[] modes = GameMode.values();
		
		for(int i = 0; i < modes.length; i++) {
			if(modes[i].name().equalsIgnoreCase(gamemode)) {
				mode = modes[i];
				break;
			}
		}
		
		if(mode == null) {
			try {
				mode = GameMode.getByValue(Integer.valueOf(gamemode));
			} catch(NumberFormatException ignored) {}
		}
		
		if(mode == null) {
			switch(gamemode.toLowerCase()) {
			case "s":
				mode = GameMode.SURVIVAL;
				break;
			case "c":
				mode = GameMode.CREATIVE;
				break;
			case "a":
				mode = GameMode.ADVENTURE;
				break;
			default:
				mode = null;
			}
		}
		
		return mode;
	}
}
