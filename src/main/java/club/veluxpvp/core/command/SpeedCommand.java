package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class SpeedCommand {

	@Command(name = "speed", permission = "core.command.speed", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();

		if(args.length >= 3) {
			if(!player.hasPermission("core.command.speed.others")) {
				player.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			int speed = 0;
			String type = args[2];
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[2] + "\" not found!"));
				return;
			}
			
			try {
				speed = Integer.valueOf(args[1]);
			} catch(NumberFormatException e) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
				return;
			}
			
			if(speed < 0) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be lower than 0!"));
				return;
			}
			
			if(speed > 10) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be higher than 10!"));
				return;
			}
			
			if(type.equalsIgnoreCase("walk")) {
				float amount = this.convertToFloat(speed);
				
				target.setWalkSpeed(amount);
				target.sendMessage(ChatUtil.TRANSLATE("Your walk speed has been &aset &fto &b" + speed + "&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's walk speed has been &aset &fto &b" + speed + "&f!"));
			} else if(type.equalsIgnoreCase("fly")) {
				float amount = this.convertToFloat(speed);
				
				target.setFlySpeed(amount);
				target.sendMessage(ChatUtil.TRANSLATE("Your fly speed has been &aset &fto &b" + speed + "&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's fly speed has been &aset &fto &b" + speed + "&f!"));
			} else {
				player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /speed [player] <amount> [walk|fly]"));
				return;
			}
		} else if(args.length == 2) {
			Player target = Bukkit.getPlayer(args[0]);
			int speed = 0;
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[2] + "\" not found!"));
				return;
			}
			
			try {
				speed = Integer.valueOf(args[1]);
			} catch(NumberFormatException e) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
				return;
			}
			
			if(speed < 0) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be lower than 0!"));
				return;
			}
			
			if(speed > 10) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be higher than 10!"));
				return;
			}
			
			if(!target.isFlying()) {
				float amount = this.convertToFloat(speed);
				
				target.setWalkSpeed(amount);
				target.sendMessage(ChatUtil.TRANSLATE("Your walk speed has been &aset &fto &b" + speed + "&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's walk speed has been &aset &fto &b" + speed + "&f!"));
			} else {
				float amount = this.convertToFloat(speed);
				
				target.setFlySpeed(amount);
				target.sendMessage(ChatUtil.TRANSLATE("Your fly speed has been &aset &fto &b" + speed + "&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's fly speed has been &aset &fto &b" + speed + "&f!"));
			}
		} else if(args.length == 1) {
			int speed = 0;
			
			try {
				speed = Integer.valueOf(args[0]);
			} catch(NumberFormatException e) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
				return;
			}
			
			if(speed < 0) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be lower than 0!"));
				return;
			}
			
			if(speed > 10) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe amount can't be higher than 10!"));
				return;
			}
			
			if(!player.isFlying()) {
				float amount = this.convertToFloat(speed);
				
				player.setWalkSpeed(amount);
				player.sendMessage(ChatUtil.TRANSLATE("Your walk speed has been &aset &fto &b" + speed + "&f!"));
			} else {
				float amount = this.convertToFloat(speed);
				
				player.setFlySpeed(amount);
				player.sendMessage(ChatUtil.TRANSLATE("Your fly speed has been &aset &fto &b" + speed + "&f!"));
			}
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /speed [player] <amount> [walk|fly]"));
		}
	}
	
	private float convertToFloat(int amount) {
		String result = "0.";
		
		switch(amount) {
		case 0:
			result += "0";
			break;
		case 1:
			result += "2";
			break;
		case 2:
			result += "3";
			break;
		case 3:
			result += "4";
			break;
		case 4:
			result += "5";
			break;
		case 5:
			result += "6";
			break;
		case 6:
			result += "7";
			break;
		case 7:
			result += "8";
			break;
		case 8:
			result += "9";
			break;
		case 9:
			result += "10";
			break;
		case 10:
			result += "10";
			break;
		}
		
		try {
			return Float.parseFloat(result);
		} catch(NumberFormatException e) { return 0.2F; }
	}
}
