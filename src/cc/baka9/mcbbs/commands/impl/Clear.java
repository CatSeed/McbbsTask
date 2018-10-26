package cc.baka9.mcbbs.commands.impl;

import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.commands.Command;

public class Clear implements Command {

	@Override
	public String execute(String[] args) {
		Gui.text.setText(null);
		return "清理完毕";
	}

	@Override
	public String getName() {
		return "Clear";
	}

	@Override
	public String getHelp() {
		return "清理控制台的全部文字";
	}

}
