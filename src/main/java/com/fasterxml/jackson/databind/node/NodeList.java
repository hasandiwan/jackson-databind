package com.fasterxml.jackson.databind.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class NodeList implements List<JsonNode> {
	private ArrayNode wrapped = null;

	public NodeList(ArrayNode obj) {
		this.wrapped = obj;
	}

	@Override
	public void add(int index, JsonNode element) {
		this.wrapped.insert(index, element);
	}

	@Override
	public boolean add(JsonNode e) {
		return this.wrapped.add(e) != null;
	}

	@Override
	public boolean addAll(Collection<? extends JsonNode> c) {
		return this.wrapped.addAll(c) != null;
	}

	@Override
	public boolean addAll(int index, Collection<? extends JsonNode> c) {
		return this.subList(index, this.size()).addAll(c);
	}

	@Override
	public void clear() {
		this.wrapped.removeAll();
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof Integer) {
			return this.wrapped.has((Integer) o);
		}
		if (o instanceof String) {
			return this.wrapped.has((String) o);
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean ret = false;
		for (final Object o : c) {
			ret = ret && this.contains(o);
			if (!ret) {
				return ret;
			}
		}
		return ret;
	}

	@Override
	public JsonNode get(int index) {
		return this.wrapped.get(index);
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i != this.wrapped.size(); i++) {
			final JsonNode node = this.wrapped.get(i);
			if (node.equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.wrapped.isEmpty();
	}

	@Override
	public Iterator<JsonNode> iterator() {
		return this.wrapped.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i = this.wrapped.size(); i != 0; i--) {
			final JsonNode node = this.wrapped.get(i);
			if (node.equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<JsonNode> listIterator() {
		return (ListIterator<JsonNode>) this.wrapped.iterator();
	}

	@Override
	public ListIterator<JsonNode> listIterator(int index) {
		return (ListIterator<JsonNode>) this.subList(0, index).iterator();
	}

	@Override
	public JsonNode remove(int index) {
		return this.wrapped.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		for (int i = 0; i != this.wrapped.size(); i++) {
			if (this.wrapped.get(i).equals(o)) {
				this.wrapped.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		final Set<JsonNode> removedSet = new HashSet<>();
		for (final JsonNode json : (Collection<JsonNode>) c) {
			if (this.remove(json) == true) {
				removedSet.add(json);
			}
		}
		return removedSet.isEmpty();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		final Set<JsonNode> container = new HashSet<>((Collection<JsonNode>) this.wrapped);
		return container.retainAll((Collection<JsonNode>) this.wrapped);
	}

	@Override
	public JsonNode set(int index, JsonNode element) {
		return this.wrapped.set(index, element);
	}

	public void setNode(ArrayNode jsonArray) {
		this.wrapped = jsonArray;
	}

	@Override
	public int size() {
		return this.wrapped.size();
	}

	@Override
	public List<JsonNode> subList(int fromIndex, int toIndex) {
		final List<JsonNode> ret = new ArrayList<>();
		final ArrayNode filtering = this.wrapped.deepCopy();
		for (int idx = 0; idx != this.wrapped.size(); idx++) {
			if ((idx < fromIndex) || (idx > toIndex)) {
				ret.add(filtering.get(idx));
			}
		}
		return ret;
	}

	@Override
	public Object[] toArray() {
		return this.subList(0, this.wrapped.size()).toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.subList(0, this.wrapped.size()).toArray(a);
	}

}
