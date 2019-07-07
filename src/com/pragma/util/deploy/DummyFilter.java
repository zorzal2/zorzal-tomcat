package com.pragma.util.deploy;

import java.io.File;
import java.io.FileFilter;

public class DummyFilter implements FileFilter {

	public boolean accept(File pathname) {
		return true;
	}

}
