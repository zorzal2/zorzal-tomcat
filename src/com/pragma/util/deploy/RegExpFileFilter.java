package com.pragma.util.deploy;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpFileFilter implements FileFilter {

	private Pattern pattern;

	public RegExpFileFilter(String pattern) {
		super();
		this.pattern = Pattern.compile(pattern);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public boolean accept(File pathname) {
		Matcher matcher = pattern.matcher(pathname.getName());
		return matcher.matches();
	}

}