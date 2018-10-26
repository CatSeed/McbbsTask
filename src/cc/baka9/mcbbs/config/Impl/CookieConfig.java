package cc.baka9.mcbbs.config.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.baka9.mcbbs.config.Config;

public class CookieConfig extends Config {

	private Set<String> cookies = new HashSet<String>();

	@Override
	public void onLoad() {
		cookies = new HashSet<>(read());

	}

	public List<String> getCookies() {
		return new ArrayList<>(cookies);
	}

	public void addCookie(String cookie) {
		write(cookie, true);
		cookies.add(cookie);
	}

	@Override
	public String getName() {
		return "Cookie";
	}

}
