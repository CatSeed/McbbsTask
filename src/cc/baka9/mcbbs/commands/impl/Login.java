package cc.baka9.mcbbs.commands.impl;

import java.util.List;

import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.config.ConfigHandler;
import cc.baka9.mcbbs.config.Impl.CookieConfig;
import cc.baka9.mcbbs.model.McbbsUser;

public class Login implements Command {

	@Override
	public String execute(String[] args) {
		final List<String> cookies = ConfigHandler.get(CookieConfig.class).getCookies();
		new Thread() {
			public void run() {
				for (String cookie : cookies) {
					McbbsUser user = new McbbsUser(cookie);
					if (user.getName() == null) {
						Gui.println(cookie);
						Gui.println("登录失败");
						continue;
					}
					Gui.println(user.getName() + "已登录");
					McbbsUser.addUser(user);
				}
				;
			}
		}.start();
		return "全部cookie已经提交登录,请等待结果";
	}

	@Override
	public String getName() {
		return "login";
	}

	@Override
	public String getHelp() {
		return "全部登录";
	}

}
