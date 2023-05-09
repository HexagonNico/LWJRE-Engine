package gamma.engine.tree;

import gamma.engine.resources.Resources;
import gamma.engine.utils.Reflection;
import gamma.engine.utils.YamlSerializer;

import java.util.HashMap;

public final class NodeResource {

	static {
		YamlSerializer.mappingRepresent(NodeResource.class, nodeResource -> {
			HashMap<String, Object> values = new HashMap<>();
			if(nodeResource.override != null && !nodeResource.override.isEmpty()) {
				values.put("override", nodeResource.override);
			} else if(nodeResource.type != null && !nodeResource.type.isEmpty()) {
				values.put("type", nodeResource.type);
			}
			if(!nodeResource.properties.isEmpty()) {
				values.put("properties", nodeResource.properties);
			}
			if(!nodeResource.children.isEmpty()) {
				values.put("children", nodeResource.children);
			}
			return values;
		});
	}

	public static NodeResource getOrLoad(String path) {
		if(path != null && !(path.isEmpty() || path.isBlank())) {
			Object resource = Resources.getOrLoad(path);
			if(resource instanceof NodeResource nodeResource) {
				return nodeResource;
			}
		}
		return new NodeResource();
	}

	public String type;
	public String override = "";
	public final HashMap<String, Object> properties = new HashMap<>();
	public final HashMap<String, NodeResource> children = new HashMap<>();

	public NodeResource(String type) {
		this.type = type;
	}

	public NodeResource() {
		this("gamma.engine.tree.Node");
	}

	public Node instantiate() {
		Node node;
		if(this.type == null || this.type.isEmpty()) {
			this.type = "gamma.engine.tree.Node";
		}
		if(this.override != null && !this.override.isEmpty()) {
			node = getOrLoad(this.override).instantiate();
		} else {
			node = (Node) Reflection.instantiate(this.type);
		}
		this.properties.forEach((name, value) -> Reflection.setField(node, name, value));
		this.children.forEach((key, resource) -> node.addChild(key, resource.instantiate()));
		return node;
	}
}
