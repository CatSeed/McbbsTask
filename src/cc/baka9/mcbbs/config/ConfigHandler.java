package cc.baka9.mcbbs.config;

import java.util.ArrayList;
import java.util.List;

import cc.baka9.mcbbs.config.Impl.CookieConfig;

public class ConfigHandler {
	private static List<Config> configs = new ArrayList<>();
	static {
		register(new CookieConfig());
	}

	public static Config register(Config config) {
		configs.add(config);
		config.load();
		return config;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Config> T get(Class<T> clazz) {
		for (Config config : configs) {
			if (config.getClass() == clazz) return (T) config;

		}
		return null;

	}
}
