package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankInfoCommand extends RankCommand {

	@Command(name = "rank.info", aliases = {"rank.information"}, permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			Rank rank = rankManager.getByName(args[0]);
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			sender.sendMessage(ChatUtil.SHORTER_LINE());
			sender.sendMessage(ChatUtil.TRANSLATE("&b&l" + rank.getName() + "'s Information"));
			sender.sendMessage(ChatUtil.SHORTER_LINE());
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fPrefix&7: &b" + (rank.getPrefix().equals("") ? "None" : rank.getPrefix())));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fSuffix&7: &b" + (rank.getSuffix().equals("") ? "None" : rank.getSuffix())));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fPriority&7: &b" + rank.getPriority()));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fColor&7: &b" + rank.getColor().name()));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fDefault&7: " + (rank.isDefault() ? "&aYes" : "&cNo")));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fItalian&7: " + (rank.isItalian() ? "&aYes" : "&cNo")));
			
			if(rank.getInheritances().size() > 0) {
				String inheritances = "";
				
				for(int i = 0; i < rank.getInheritances().size(); i++) {
					inheritances += rank.getInheritances().get(i) + (i != (rank.getInheritances().size() - 1) ? "&7, &b" : "");
				}
				
				sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fInheritances &7(" + rank.getInheritances().size() + "): &b" + inheritances));
			}
			
			if(rank.getPermissions().size() > 0) {
				sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fPermissions &7(" + rank.getPermissions().size() + "):"));
				
				for(String perm : rank.getPermissions()) {
					sender.sendMessage(ChatUtil.TRANSLATE(" &7- &b" + perm));
				}
			}
			
			sender.sendMessage(ChatUtil.SHORTER_LINE());
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank info <rankName>"));
		}
	}
}
