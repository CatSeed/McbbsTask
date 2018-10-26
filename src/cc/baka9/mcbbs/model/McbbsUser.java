package cc.baka9.mcbbs.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.baka9.mcbbs.utils.HttpUtil;

public class McbbsUser {
	private static Set<McbbsUser> users = new HashSet<>();

	private final static String McbbsUrl = "http://www.mcbbs.net/";

	public static void addUser(McbbsUser user) {
		users.add(user);
	}

	public static List<McbbsUser> getUsers() {
		return new ArrayList<>(users);
	}

	private String name;
	private String cookie;
	private String formHash;

	public McbbsUser(String cookie) {
		this.cookie = cookie;
		String homeText = HttpUtil.sendGet(McbbsUrl + "home.php", "", cookie);
		Matcher matcher = Pattern.compile("title=\"访问我的空间\">(.*?)</a>").matcher(homeText);
		if (matcher.find()) this.name = matcher.group(1);
		matcher = Pattern.compile("name=\"formhash\" value=\"(.*?)\"").matcher(homeText);
		if (matcher.find()) this.formHash = matcher.group(1);
	}

	public String getName() {
		return name;
	}

	public String getCookie() {
		return cookie;
	}

	public String getFormHash() {
		return formHash;
	}

	public boolean sign() {
		int emotid = (int) (1 + 10 * Math.random());
		String content;
		switch (emotid) {
		case 1:
			content = "记上一笔，hold住我的快乐！";
			break;
		case 2:
			content = "格式化自己，只为删除那些不愉快！";
			break;
		case 3:
			content = "平平淡淡才是真";
			break;
		case 4:
			content = "我前途无亮啊！！";
			break;
		case 5:
			content = "人生太多无奈，今天的事让我真是傻眼呀！";
			break;
		case 6:
			content = "人生太多事，今天就在这里大哭一次，希望在明天！";
			break;
		case 7:
			content = "还是继续慵懒下去吧~~";
			break;
		case 8:
			content = "每天都要萌萌哒~~";
			break;
		case 9:
			content = "狗年搓狗头，来年不用愁";
			break;
		default:
			content = "今天没啥想说的~";
			break;
		}
		String html = HttpUtil.sendPost(McbbsUrl + "plugin.php?id=dc_signin:sign",
				"formhash=" + getFormHash() + "&signsubmit=yes&handlekey=signin&emotid=" + emotid
						+ "&referer=http://www.mcbbs.net/plugin.php?id=dc_signin&content=" + content,
				cookie);
		if (html.contains("您今日已经签过到")) return false;
		return true;
	}

	@Override
	public String toString() {
		return getName() + " " + getFormHash();
	}

	// 买提升卡
	public boolean buyMagic() {
		String result = HttpUtil.sendGet(McbbsUrl + "home.php", "mod=magic&action=mybox", cookie);
		if (result.contains("提升卡")) return true;
		result = HttpUtil.sendPost(McbbsUrl + "home.php", "mod=magic&action=shop&infloat=yes&formhash=" + getFormHash()
				+ "&operation=buy&mid=bump&magicnum=1&operatesubmit=yes&operatesubmit=true", cookie);
		if (result.contains("没有足够的")) return false;
		if (result.contains("道具包容量不足")) return false;
		if (result.contains("购买了")) return true;
		return false;
	}

	// 顶帖
	public boolean refreshText(String id) {
		String result = HttpUtil.sendPost(McbbsUrl + "home.php",
				"mod=magic&action=mybox&infloat=yes&formhash=" + getFormHash()
						+ "&handlekey=&operation=use&magicid=16&tid=" + id
						+ "&usesubmit=yes&operation=use&magicid=16&idtype=tid&id=335328&usesubmit=true",
				cookie);
		if (result.contains("已提升")) return true;
		if (result.contains("您选择的道具不存在")) return false;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		McbbsUser other = (McbbsUser) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

}
