package cc.baka9.mcbbs.commands.impl;

import java.util.List;

import cc.baka9.mcbbs.Gui;
import cc.baka9.mcbbs.commands.Command;
import cc.baka9.mcbbs.model.McbbsText;
import cc.baka9.mcbbs.model.McbbsUser;

public class Text implements Command {

	@Override
	public String execute(String[] args) {
		if (args.length == 0) return getHelp();
		final String id = args[0];
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return "参数错误";
		}
		final McbbsText mcbbsText = new McbbsText(id);
		if (!mcbbsText.existed()) return "帖子不存在";
		new Thread() {
			public void run() {
				List<McbbsUser> users = McbbsUser.getUsers();
				if (users.size() == 0) {
					Gui.println("你至少要登录一个账号才能顶帖");
					return;
				}
				for (McbbsUser user : users) {
					if (user.buyMagic() && user.refreshText(id)) {
						Gui.println(mcbbsText.getTitle() + " 顶帖成功");
						return;
					}

				}
				Gui.println(mcbbsText.getTitle() + " 顶!帖!失!败!");
			};
		}.start();
		return "顶帖请求发送完毕";
	}

	@Override
	public String getName() {

		return "Text";
	}

	@Override
	public String getHelp() {
		return "[帖子id] 顶帖一次";
	}

}
