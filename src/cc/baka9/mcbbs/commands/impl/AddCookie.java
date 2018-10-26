package cc.baka9.mcbbs.commands.impl;

import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.config.ConfigHandler;
import cc.baka9.mcbbs.config.Impl.CookieConfig;

public class AddCookie implements Command {

	@Override
	public String execute(String[] args) {
		if (args.length != 2) return "指令有误," + getHelp();
		String cookie = "ZxYQ_2132_auth=" + args[0] + ";ZxYQ_2132_saltkey=" + args[1];
		CookieConfig config = ConfigHandler.get(CookieConfig.class);
		for (String configCookie : config.getCookies()) {
			if (configCookie.equals(cookie)) return "这条cookie已经存在了";
		}
		config.addCookie(cookie);
		return "已添加一条cookie";
	}

	@Override
	public String getName() {
		return "AddCookie";
	}

	@Override
	public String getHelp() {
		return "指令后面添加参数 [ZxYQ_2132_auth] [ZxYQ_2132_saltkey] 添加一条Cookie(这两条在浏览器中打开mcbbs网站 点f12来找键值)";
	}

}
