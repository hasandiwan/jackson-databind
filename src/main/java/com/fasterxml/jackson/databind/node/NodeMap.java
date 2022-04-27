package com.fasterxml.jackson.databind.node;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NodeMap extends AbstractMap<String, JsonNode> implements Map<String, JsonNode> {
	ObjectNode wrapped;

	@Override
	public void clear() {
		this.wrapped.removeAll();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.wrapped.has((String) key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.values().contains(value);
	}

	@Override
	public Set<Entry<String, JsonNode>> entrySet() {
		final Set<Entry<String, JsonNode>> ret = new HashSet<>();
		final Iterator<Entry<String, JsonNode>> it = this.wrapped.fields();
		while (it.hasNext()) {
			final Entry<String, JsonNode> nextIt = it.next();
			ret.add(nextIt);
		}
		return ret;
	}

	@Override
	public JsonNode get(Object key) {
		if (key instanceof Integer) {
			final int realKey = (Integer) key;
			return this.wrapped.get(realKey);
		}
		if (key instanceof String) {
			final String realKey = (String) key;
			return this.wrapped.get(realKey);
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return this.wrapped.isNull();
	}

	@Override
	public Set<String> keySet() {
		final Set<String> ret = new HashSet<>();
		final Iterator<String> iter = this.wrapped.fieldNames();
		while (iter.hasNext()) {
			final String thisEntry = iter.next();
			ret.add(thisEntry);
		}
		return ret;
	}

	@Override
	public JsonNode put(String key, JsonNode value) {
		return this.wrapped.set(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends JsonNode> m) {
		for (final String k : m.keySet()) {
			this.wrapped.set(k, m.get(k));
		}
	}

	@Override
	public JsonNode remove(Object key) {
		return this.wrapped.remove((String) key);
	}

	@Override
	public int size() {
		return this.wrapped.size();
	}

	@Override
	public Collection<JsonNode> values() {
		final List<JsonNode> resultList = new ArrayList<>();
		for (final String name : this.keySet()) {
			final JsonNode valuesNode = this.wrapped.get(name);
			if (valuesNode != null) {
				for (final JsonNode valueNode : valuesNode) {
					if ((valueNode.get("value") != null) && !valueNode.get("value").isNull()) {
						resultList.add(valueNode.get("value"));
					}
				}
			}
		}
		return resultList;
	}
}
