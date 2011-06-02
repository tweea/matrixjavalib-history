package net.matrix.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;

/**
 * 相对定位资源的根注册
 */
public class RelativeResourceRootRegister
{
	private Map<String, Resource> roots;

	public RelativeResourceRootRegister()
	{
		roots = new HashMap<String, Resource>();
	}

	public void registerRoot(String name, Resource root)
	{
		roots.put(name, root);
	}

	public Resource getRoot(String name)
	{
		return roots.get(name);
	}

	public Resource getResource(RelativeResource relativeResource)
		throws IOException
	{
		Resource root = getRoot(relativeResource.getRoot());
		if(root == null){
			throw new IllegalStateException("根 " + relativeResource.getRoot() + " 未注册");
		}
		return root.createRelative(relativeResource.getPath());
	}
}
