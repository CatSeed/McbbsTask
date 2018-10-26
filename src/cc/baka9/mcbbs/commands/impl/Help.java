package cc.baka9.mcbbs.commands.impl;

import java.util.List;

import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.commands.CommandHandler;

public class Help implements Command {

	@Override
	public String execute(String[] args) {
		List<Command> commands = CommandHandler.getCommands();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			sb.append(command.getName());
			sb.append(" ");
			sb.append(command.getHelp());
			if (i + 1 != commands.size()) sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String getName() {
		return "Help";
	}

	@Override
	public String getHelp() {
		return "显示帮助";
	}

}
