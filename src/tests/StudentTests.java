package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
import junit.framework.TestCase;
import tree.PlaceKeysValuesInArrayLists;
import tree.PolymorphicBST;

public class StudentTests extends TestCase {

	private PolymorphicBST<Integer, String> makeTree() {
		PolymorphicBST<Integer, String> ptree = new PolymorphicBST<Integer, String>();

		int[] intArr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String[] stringArr = new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
				"Ten" };
		HashMap<Integer, String> pairs = new HashMap<Integer, String>();
		for (int i = 0; i < intArr.length; i++) {
			pairs.put(intArr[i], stringArr[i]);

		}
		List<Integer> listOfIntegers = new ArrayList<Integer>(pairs.keySet());
		Collections.shuffle(listOfIntegers);
		for (Integer i : listOfIntegers) {
			ptree.put(i, pairs.get(i));
		}
		return ptree;
	}

	@Test
	public void testGet() {
		PolymorphicBST<Integer, String> ptree = makeTree();
		assertEquals("Ten", ptree.get(10));
		assertEquals("Three", ptree.get(3));
		assertEquals("One", ptree.get(1));
		assertEquals(null, ptree.get(11));
	}

	@Test
	public void testMinMax() {
		PolymorphicBST<Integer, String> ptree = new PolymorphicBST<Integer, String>();
		try {
			ptree.getMin();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(0, ptree.size());
			ptree = makeTree();
			assertEquals(10, ptree.size());
			assertTrue(10 == ptree.getMax());
			assertTrue(1 == ptree.getMin());
		}
	}

	@Test
	public void testRemove() {
		PolymorphicBST<Integer, String> ptree = makeTree();
		ptree.remove(1);
		assertEquals(9, ptree.size());
		assertEquals(null, ptree.get(1));
		ptree.remove(10);
		ptree.remove(7);
		ptree.remove(2);
		ptree.remove(6);
		assertEquals(5, ptree.size());
		assertEquals(null, ptree.get(2));
		assertEquals(null, ptree.get(6));
		assertEquals(null, ptree.get(7));
		assertEquals("Three", ptree.get(3));
		ptree.remove(3);
		ptree.remove(4);
		ptree.remove(5);
		ptree.remove(8);
		assertEquals(1, ptree.size());
		ptree.remove(9);
		assertEquals(0, ptree.size());
	}

	@Test
	public void testSubMap() {
		PolymorphicBST<Integer, String> ptree = makeTree();
		PolymorphicBST<Integer, String> subPtree = ptree.subMap(3, 8);
		assertEquals(6, subPtree.size());
		subPtree = subPtree.subMap(4, 6);
		assertEquals(3, subPtree.size());
		subPtree = subPtree.subMap(2, 5);
		assertEquals(2, subPtree.size());
		subPtree = subPtree.subMap(4, 4);
		assertEquals(1, subPtree.size());
		subPtree = subPtree.subMap(3, 1);
		assertEquals(0, subPtree.size());
	}

	@Test
	public void testClear() {
		PolymorphicBST<Integer, String> ptree = makeTree();
		assertEquals(10, ptree.size());
		ptree.clear();
		assertEquals(0, ptree.size());
	}

	@Test
	public void testHeight() {
		PolymorphicBST<Integer, String> ptree = new PolymorphicBST<Integer, String>();
		String s = "Hello";
		ptree.put(3, s);
		ptree.put(7, s);
		ptree.put(2, s);
		ptree.put(8, s);
		ptree.put(1, s);
		ptree.put(11, s);
		assertEquals(4, ptree.height());
		ptree.remove(3);
		assertEquals(5, ptree.size());
		assertEquals(4, ptree.height());
		ptree.remove(11);
		assertEquals(3, ptree.height());
	}

	@Test
	public void testInOrderTraversal() {
		PolymorphicBST<Integer, String> ptree = makeTree();
		PlaceKeysValuesInArrayLists<Integer, String> task = new PlaceKeysValuesInArrayLists<Integer, String>();
		ptree.inorderTraversal(task);
		assertEquals(task.getKeys().toString(), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
		assertEquals(task.getValues().toString(), "[One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten]");
	}

	@Test
	public void testRightRootLeftTraversal() {
		PolymorphicBST<Integer, String> ptree = new PolymorphicBST<Integer, String>();
		ptree.put(2, "Two");
		ptree.put(1, "One");
		ptree.put(3, "Three");
		ptree.put(4, "Four");
		PlaceKeysValuesInArrayLists<Integer, String> task = new PlaceKeysValuesInArrayLists<Integer, String>();
		ptree.rightRootLeftTraversal(task);
		assertEquals(task.getKeys().toString(), "[4, 3, 2, 1]");
		assertEquals(task.getValues().toString(), "[Four, Three, Two, One]");
	}

}