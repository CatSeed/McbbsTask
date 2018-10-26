package cc.baka9.mcbbs.commands.impl;

import java.util.HashMap;
import java.util.Map;
import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.TaskManage;
import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.commands.CommandHandler;
import cc.baka9.mcbbs.model.McbbsText;
import cc.baka9.mcbbs.utils.TimeUtil;

public class TaskText implements Command {
	String regex = "\\d+\\-\\d+";

	@Override
	public String execute(String[] args) {
		if (args.length != 2) return getHelp();
		final String id = args[0];

		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return "帖子id参数错误";
		}
		if (!args[1].matches(regex)) return "时间格式错误";
		McbbsText mcbbsText = new McbbsText(id);
		if (!mcbbsText.existed()) return "帖子不存在";
		final long time = TimeUtil.formatHHMM(args[1]);
		long toDayRefresh = TimeUtil.getToDayBegin() + time;
		final Map<String, Long> task = new HashMap<>(1);
		if (System.currentTimeMillis() < toDayRefresh) {
			task.put(id, toDayRefresh);
			Gui.println("下一次顶帖在 " + TimeUtil.getFormatTime(toDayRefresh));
		}
		TaskManage.startTask(new Runnable() {

			@Override
			public void run() {
				if (task.size() == 0) {
					long ref = TimeUtil.getTomorrowBegin() + time;
					Gui.syncPrintln(Thread.currentThread().getName(), "下一次顶帖在 " + TimeUtil.getFormatTime(ref));
					task.put(id, ref);
				}
				if (System.currentTimeMillis() > task.get(id)) {
					Gui.syncPrintln(Thread.currentThread().getName(), CommandHandler.execute("Text " + id));
					long ref = TimeUtil.getTomorrowBegin() + time;
					Gui.syncPrintln(Thread.currentThread().getName(), "下一次顶帖在 " + TimeUtil.getFormatTime(ref));
					task.put(id, ref);
				}

			}
		}, 0, 1000 * 60);
		return "已经创建每天循环定点顶帖任务";
	}

	@Override
	public String getName() {

		return "TaskText";
	}

	@Override
	public String getHelp() {
		return "[帖子id] [时间] 开启每天循环定点顶帖,时间格式为 HH-MM 例: 15-20";
	}

}
