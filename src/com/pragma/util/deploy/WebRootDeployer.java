package com.pragma.util.deploy;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class WebRootDeployer extends Task {

	private String root;

	private String target;

	private String source;

	private String included;

	private FileFilter fileFilter = new OrFilter(new DirectoryFilter(), new RegExpFileFilter("(.*)jsp"));

	// The method executing the task
	public void execute() throws BuildException {
		File source = this.resolvetSource();
		File deploymentTarget = this.resolvetTarget(source);
		File[] sources = source.listFiles(this.fileFilter);
		for (int i = 0; i < sources.length; i++)
			try {
				this.copyDirectory(sources[i], new File(deploymentTarget, sources[i].getName()));
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void copyDirectory(File srcDir, File dstDir) throws IOException {
		if (srcDir.isDirectory()) {
			if (!dstDir.exists()) {
				dstDir.mkdir();
			}

			File[] children = srcDir.listFiles(this.fileFilter);
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(srcDir, children[i].getName()), new File(dstDir, children[i].getName()));
			}
		}
		else {
			if (srcDir.lastModified() > dstDir.lastModified()) {
				copy(srcDir, dstDir);
				this.log(srcDir.getAbsolutePath() + " -> " + dstDir.getAbsolutePath());
			}

		}
	}

	void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	private File resolvetTarget(File source) {
		File file = new File(this.getRoot());
		if (!file.isDirectory())
			throw new IllegalArgumentException("El target debe ser un directorio");
		File[] files = file.listFiles(new DeployDirectoryFilter());
		if (files.length == 1) {
			String targetPath = (this.getTarget() == null) ? source.getName() : this.getTarget();
			return new File(files[0], targetPath);
		}
		throw new RuntimeException("No se ha podido encontrar el directorio de deploy");
	}

	private File resolvetSource() {
		File file = new File(this.getSource());
		if (!file.isDirectory())
			throw new IllegalArgumentException("El target debe ser un directorio");
		return file;
	}

	// The setter for the "message" attribute
	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getIncluded() {
		return included;
	}

	public void setIncluded(String included) {
		this.included = included;
		this.fileFilter = (this.included != null) ? new OrFilter(new DirectoryFilter(), new RegExpFileFilter(this.included))
				: new DummyFilter();
	}

	public static void main(String[] args) {
		WebRootDeployer deployer = new WebRootDeployer();
		deployer.setSource("C:\\fontar_2008\\sistema-fontar\\webroot\\jsp\\");
		deployer.setRoot("C:\\jboss-4.0.3SP1\\server\\fontar\\tmp\\deploy\\");
		deployer.setIncluded("(.*)jsp");
		// deployer.setTarget("jsp");
		deployer.execute();
	}
}
