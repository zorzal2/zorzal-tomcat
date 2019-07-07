package com.pragma.util.deploy;

public class DeployDirectoryFilter extends RegExpFileFilter {

	public DeployDirectoryFilter(String pattern) {
		super(pattern);
	}

	public DeployDirectoryFilter() {
		super("tmp(.*)fontar-exp.war");
	}

}
