package cc.baka9.mcbbs.commands.impl;

import java.util.List;

import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.model.McbbsUser;

public class Sign implements Command {

	@Override
	public String execute(String[] args) {
		final List<McbbsUser> users = McbbsUser.getUsers();
		if (users.size() == 0) {
			return "提交签到请求失败,没有登陆的用户";
		}
		new Thread() {
			@Override
			public void run() {
				for (McbbsUser user : users) {
					if (user.sign()) Gui.println(user.getName() + " 签到成功");
					else {
						Gui.println(user.getName() + " 今日已经签过到");
					}
				}
			}
		}.start();
		return "所有已登录的用户已经提交签到请求";
	}

	@Override
	public String getName() {
		return "sign";
	}

	@Override
	public String getHelp() {
		return "全部签到";
	}

}
