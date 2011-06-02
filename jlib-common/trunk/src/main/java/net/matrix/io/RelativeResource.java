package net.matrix.io;

/**
 * 相对定位的资源
 */
public class RelativeResource
{
	private String root;

	private String path;

	public RelativeResource(String root, String path)
	{
		this.root = root;
		this.path = path;
	}

	public RelativeResource(RelativeResource parent, String path)
	{
		this.root = parent.root;
		this.path = parent.path + '/' + path;
	}

	/**
	 * @return 根路径代码
	 */
	public String getRoot()
	{
		return root;
	}

	/**
	 * @return 相对路径
	 */
	public String getPath()
	{
		return path;
	}

	@Override
	public String toString()
	{
		return "RelativeResource[" + root + "/" + path + "]";
	}
}
