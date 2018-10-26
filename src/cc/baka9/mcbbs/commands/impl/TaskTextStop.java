package cc.baka9.mcbbs.commands.impl;

import cc.baka9.mcbbs.TaskManage;
import cc.baka9.mcbbs.commands.Command;

public class TaskTextStop implements Command {

	@Override
	public String execute(String[] args) {
		if (args.length == 0) return getHelp();
		if (TaskManage.cancelTask(args[0])) {
			return "正在关闭线程 " + args[0];
		}

		return "没有找到此线程 活跃的线程有" + TaskManage.getTaskNames().toString();
	}

	@Override
	public String getName() {
		return "TaskTextStop";
	}

	@Override
	public String getHelp() {
		return "[线程名] 中止一个线程任务";
	}

}
