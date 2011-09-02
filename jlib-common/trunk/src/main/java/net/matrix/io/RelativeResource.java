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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		RelativeResource other = (RelativeResource)obj;
		if(path == null){
			if(other.path != null){
				return false;
			}
		}else if(!path.equals(other.path)){
			return false;
		}
		if(root == null){
			if(other.root != null){
				return false;
			}
		}else if(!root.equals(other.root)){
			return false;
		}
		return true;
	}
}
