package cc.baka9.mcbbs.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.baka9.mcbbs.utils.HttpUtil;

public class McbbsText {
	public static Pattern pattern = Pattern.compile("id=\"thread_subject\">(.*?)</span>");
	private String id;

	public McbbsText(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean existed() {
		return pattern.matcher(HttpUtil.sendGet("http://www.mcbbs.net/thread-" + id + "-1-1.html", "", "")).find();

	}

	public String getTitle() {
		String title = "";
		Matcher matcher = pattern.matcher(HttpUtil.sendGet("http://www.mcbbs.net/thread-" + id + "-1-1.html", "", ""));
		if (matcher.find()) title = matcher.group(1);

		return title;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		McbbsText other = (McbbsText) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}
