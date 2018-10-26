package cc.baka9.mcbbs;

import java.util.ArrayList;
import java.util.List;

import cc.baka9.mcbbs.utils.ThreadUtil;

public class TaskManage {
	private static List<TaskManage> list = new ArrayList<>();
	private String name;
	private Runnable runnable;
	private boolean cancel;

	public static List<String> getTaskNames() {
		List<String> names = new ArrayList<>();
		for (TaskManage tm : list)
			names.add(tm.name);

		return names;
	}

	private TaskManage(Runnable runnable) {
		this.runnable = runnable;
	}

	public static void startTask(Runnable runnable, final long delay, final long period) {
		final TaskManage tm = new TaskManage(runnable);
		Thread thread = new Thread() {
			@Override
			public void run() {
				ThreadUtil.sleep(delay);
				while (!tm.cancel) {
					tm.runnable.run();
					ThreadUtil.sleep(period);
				}
				Gui.syncPrintln(Thread.currentThread().getName(), "结束!");
			}
		};
		tm.name = thread.getName();
		list.add(tm);
		thread.start();
	}

	public static boolean cancelTask(String taskName) {
		for (TaskManage tm : list) {
			if (tm.name.equals(taskName)) {
				tm.cancel = true;
				list.remove(tm);
				return true;
			}
		}
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
		TaskManage other = (TaskManage) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

}
