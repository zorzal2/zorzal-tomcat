package com.pragma.util.deploy;

import java.io.File;
import java.io.FileFilter;

public class OrFilter implements FileFilter {

	FileFilter filter1, filter2;

	public OrFilter(FileFilter filter1, FileFilter filter2) {
		super();
		this.filter1 = filter1;
		this.filter2 = filter2;
	}

	public boolean accept(File pathname) {
		return filter1.accept(pathname) || filter2.accept(pathname);
	}

}
