/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import java.io.File;

public class UserDataHome
{
	private File homeDir;

	public UserDataHome(File homeDir)
	{
		this.homeDir = homeDir;
		if(!homeDir.exists()){
			homeDir.mkdirs();
		}
	}

	public File getHomeDir()
	{
		return homeDir;
	}

	public File getDir(String subDir)
	{
		File dir = new File(homeDir, subDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}

	public File getFile(String subDir)
	{
		return new File(homeDir, subDir);
	}
}
