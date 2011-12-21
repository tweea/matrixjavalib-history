package net.matrix.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 相对定位资源的根注册
 */
public class RelativeResourceRootRegister
{
	private static final Logger LOG = LoggerFactory.getLogger(RelativeResourceRootRegister.class);

	private Map<String, Resource> roots;

	/**
	 * 空的根注册
	 */
	public RelativeResourceRootRegister()
	{
		roots = new HashMap<String, Resource>();
	}

	/**
	 * 注册资源根路径
	 * @param name 根路径名
	 * @param root 路径
	 */
	public void registerRoot(String name, Resource root)
	{
		roots.put(name, root);
	}

	/**
	 * 获取根路径
	 * @param name 根路径名
	 * @return 路径
	 */
	public Resource getRoot(String name)
	{
		return roots.get(name);
	}

	/**
	 * @param relativeResource 需要定位的资源
	 * @return 已定位的绝对路径资源
	 * @throws IOException 定位失败
	 * @throws IllegalStateException 根路径没有注册
	 */
	public Resource getResource(RelativeResource relativeResource)
		throws IOException
	{
		Resource root = getRoot(relativeResource.getRoot());
		if(root == null){
			throw new IllegalStateException("根 " + relativeResource.getRoot() + " 未注册");
		}
		return root.createRelative(relativeResource.getPath());
	}

	/**
	 * 根据文件路径名称和文件名称得到文件实际对应的 <code>File</code> 对象。如果文件存在先删除文件。
	 * @param relativeFile 抽象定位文件
	 * @return <code>File</code> 对象
	 */
	public File getNewFile(RelativeResource relativeFile)
		throws IOException
	{
		File file = getResource(relativeFile).getFile();
		if(file.exists()){
			LOG.debug("删除旧文件：" + file);
			file.delete();
		}
		return file;
	}

	/**
	 * 移动文件
	 * @param src 源抽象定位文件
	 * @param dest 目标抽象定位文件
	 * @throws IOException 文件操作异常
	 */
	public void moveFile(RelativeResource src, RelativeResource dest)
		throws IOException
	{
		if(src.equals(dest)){
			return;
		}
		LOG.debug("搬移文件从  " + src + " 到 " + dest);
		File srcFile = getResource(src).getFile();
		File destFile = getResource(dest).getFile();
		if(!srcFile.exists()){
			throw new FileNotFoundException(srcFile.getAbsolutePath());
		}
		if(destFile.exists()){
			destFile.delete();
		}
		FileUtils.moveFile(srcFile, destFile);
	}

	/**
	 * 复制文件
	 * @param src 源抽象定位文件
	 * @param dest 目标抽象定位文件
	 * @throws IOException 文件操作异常
	 */
	public void copyFile(RelativeResource src, RelativeResource dest)
		throws IOException
	{
		if(src.equals(dest)){
			return;
		}
		LOG.debug("复制文件从  " + src + " 到 " + dest);
		File srcFile = getResource(src).getFile();
		File destFile = getResource(dest).getFile();
		if(!srcFile.exists()){
			throw new FileNotFoundException(srcFile.getAbsolutePath());
		}
		if(destFile.exists()){
			destFile.delete();
		}
		FileUtils.copyFile(srcFile, destFile);
	}
}
