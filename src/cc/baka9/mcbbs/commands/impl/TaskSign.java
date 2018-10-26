package cc.baka9.mcbbs.commands.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.commands.CommandHandler;
import cc.baka9.mcbbs.model.McbbsUser;
import cc.baka9.mcbbs.utils.TimeUtil;

public class TaskSign implements Command {

	Map<McbbsUser, Long> map = new HashMap<>();

	@Override
	public String execute(String[] args) {
		CommandHandler.execute("Sign");
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

				if (map.size() == 0) {
					for (McbbsUser user : McbbsUser.getUsers()) {
						long time = TimeUtil.randomTime(TimeUtil.getTomorrowBegin(), 60 * 60 * 5, 60 * 60 * 21);
						map.put(user, time);
						Gui.println(user.getName() + " 下次签到在: " + TimeUtil.getFormatTime(time));
					}
				}
				long now = System.currentTimeMillis();
				for (McbbsUser user : map.keySet()) {
					if (now > map.get(user)) {
						if (user.sign()) Gui.println(user.getName() + " 签到成功");
						else {
							Gui.println(user.getName() + " 今日已经签过到");
						}
						map.remove(user);
						break;
					}
				}
			}
		}, 0, 1000 * 60);
		return "已经创建签到循环任务";
	}

	@Override
	public String getName() {

		return "TaskSign";
	}

	@Override
	public String getHelp() {
		return "开启每天循环签到";
	}

}
