package cc.baka9.mcbbs.commands;

public interface Command {

	String execute(String args[]);

	String getName();

	String getHelp();

}
