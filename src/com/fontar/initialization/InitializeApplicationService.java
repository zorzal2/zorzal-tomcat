package com.fontar.initialization;

import java.util.List;

public class InitializeApplicationService  {

	private List<InitializationCommand> commands;
	
	
	public void initializeApplication(){
		for (InitializationCommand command: this.commands) {
			command.initialize();
			command.execute();
		}
	}


	public List<InitializationCommand> getCommands() {
		return commands;
	}


	public void setCommands(List<InitializationCommand> commands) {
		this.commands = commands;
	}


	public void afterPropertiesSet() throws Exception {
		this.initializeApplication();
	}
	
	
}
