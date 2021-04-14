package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.rank.RankManager;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankCommand {

	protected RankManager rankManager;
	
	public RankCommand() {
		this.rankManager = Core.getInstance().getRankManager();
		Core.getInstance().getCommandFramework().registerCommands(this);
	}
	
	@Command(name = "rank", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		sendHelpMessage(cmd.getSender());
	}
	
	private void sendHelpMessage(CommandSender sender) {
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		sender.sendMessage(ChatUtil.TRANSLATE("&b&lRank Commands"));
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank create <rankName>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank delete <rankName>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setprefix <rankName> <prefix>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setsuffix <rankName> <suffix>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setpiority <rankName> <priority>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setcolor <rankName> <color>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setdefault <rankName> <true|false>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank setitalian <rankName> <true|false>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank addperm <rankName> <permission>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank removeperm <rankName> <permission>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank inherit <rankName> <inheritRankName>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank uninherit <rankName> <uninheritRankName>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank info <rankName>"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank list"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/rank saveall"));
		sender.sendMessage(ChatUtil.SHORTER_LINE());
	}
}
