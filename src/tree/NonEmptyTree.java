package tree;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */

	private K key;
	private V value;
	private Tree<K, V> left, right;

	/**
	 * Only constructor we need.
	 * 
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public V search(K key) {
		try {
			if (this.key.compareTo(key) == 0) {
				return this.value;
			} else if (key.compareTo(this.key) < 0) {
				return this.left.search(key);
			} else {
				return this.right.search(key);
			}
		} catch (NullPointerException e) {
			return null;
		}
	}

	public NonEmptyTree<K, V> insert(K key, V value) {
		try {
			if (this.key.compareTo(key) == 0) {
				this.value = value;
				return this;
			} else if (key.compareTo(this.key) < 0) {
				left = left.insert(key, value);
			} else {
				right = right.insert(key, value);
			}
			return this;
		} catch (NullPointerException e) {
			return new NonEmptyTree(key, value, EmptyTree.getInstance(), EmptyTree.getInstance());
		}
	}

	// Use the algorithm introduced in class
	public Tree<K, V> delete(K key) {
		try {
			if (this.key.compareTo(key) == 0) {

				// If the key is found, then replace the info of current node with max of left
				// child. If left is empty, then go to right.
				try {
					K leftMaxK = left.max();
					this.key = leftMaxK;
					this.value = left.search(leftMaxK);
					left = left.delete(leftMaxK);
				} catch (TreeIsEmptyException e1) {

					// It is still possible that right is also empty. In that case, the current node
					// is a leaf node, so we simply return an empty tree.
					try {
						K rightMinK = right.min();
						this.key = rightMinK;
						this.value = right.search(rightMinK);
						right = right.delete(rightMinK);
					} catch (TreeIsEmptyException e2) {
						return EmptyTree.getInstance();
					}
				}
			} else if (key.compareTo(this.key) < 0) {
				left = left.delete(key);
			} else {
				right = right.delete(key);
			}
			return this;
		} catch (NullPointerException e) {
			return this;
		}
	}

	public K max() {
		try {
			K rightMaxK = right.max();
			int result = key.compareTo(rightMaxK);
			return result > 0 ? key : rightMaxK;
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	public K min() {
		try {
			K leftMinK = left.min();
			int result = key.compareTo(leftMinK);
			return result < 0 ? key : leftMinK;
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	public int size() {
		return 1 + left.size() + right.size();
	}

	public void addKeysToCollection(Collection<K> c) {
		c.add(key);
		left.addKeysToCollection(c);
		right.addKeysToCollection(c);
	}

	public Tree<K, V> subTree(K fromKey, K toKey) {
		if (max().compareTo(fromKey) < 0 || min().compareTo(toKey) > 0) {
			return EmptyTree.getInstance();
		}
		if (this.key.compareTo(toKey) <= 0 && this.key.compareTo(fromKey) >= 0) {
			NonEmptyTree<K, V> newTree = new NonEmptyTree(key, value, EmptyTree.getInstance(), EmptyTree.getInstance());
			newTree.left = this.left.subTree(fromKey, toKey);
			newTree.right = this.right.subTree(fromKey, toKey);
			return newTree;
		} else {

			// Obviously, if the root node is not within the interval, then only one of the
			// two children can fall into the interval.
			if (this.key.compareTo(toKey) > 0) {
				return left.subTree(fromKey, toKey);
			} else {
				return right.subTree(fromKey, toKey);
			}
		}
	}

	public int height() {
		return 1 + (left.height() > right.height() ? left.height() : right.height());
	}

	public void inorderTraversal(TraversalTask<K, V> p) {
		left.inorderTraversal(p);
		p.performTask(key, value);
		right.inorderTraversal(p);
	}

	public void rightRootLeftTraversal(TraversalTask<K, V> p) {
		right.rightRootLeftTraversal(p);
		p.performTask(key, value);
		left.rightRootLeftTraversal(p);
	}
}