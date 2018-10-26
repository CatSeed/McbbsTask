package cc.baka9.mcbbs.commands;

import java.util.ArrayList;
import java.util.List;

import cc.baka9.mcbbs.commands.impl.AddCookie;
import cc.baka9.mcbbs.commands.impl.Clear;
import cc.baka9.mcbbs.commands.impl.Help;
import cc.baka9.mcbbs.commands.impl.Login;
import cc.baka9.mcbbs.commands.impl.Sign;
import cc.baka9.mcbbs.commands.impl.TaskSign;
import cc.baka9.mcbbs.commands.impl.TaskText;
import cc.baka9.mcbbs.commands.impl.TaskTextStop;
import cc.baka9.mcbbs.commands.impl.Text;

public class CommandHandler {
	private static List<Command> commands = new ArrayList<>();
	static {
		commands.add(new Help());
		commands.add(new Clear());
		commands.add(new AddCookie());
		commands.add(new Login());
		commands.add(new Sign());
		commands.add(new TaskSign());
		commands.add(new Text());
		commands.add(new TaskText());
		commands.add(new TaskTextStop());
	}

	public static void register(Command command) {
		commands.add(command);
	}

	public static List<Command> getCommands() {
		return new ArrayList<>(commands);

	}

	public static String execute(String str) {
		String[] strs = str.split(" ");
		if (strs.length != 0) {
			String name = strs[0];
			String[] args = new String[strs.length - 1];
			for (int i = 1; i < strs.length; i++) {
				args[i - 1] = strs[i];
			}
			for (Command command : commands) {
				if (command.getName().equalsIgnoreCase(name)) return command.execute(args);
			}
		}
		return "不存在这个指令,请输入 Help 获取帮助";

	}
}
