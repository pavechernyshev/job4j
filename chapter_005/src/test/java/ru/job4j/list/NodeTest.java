package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class NodeTest {

    Node<Integer> first = new Node<>(1);
    Node<Integer> second = new Node<>(2);
    Node<Integer> third = new Node<>(3);
    Node<Integer> fourth = new Node<>(4);

    Helper helper = new Helper();


    @Before
    public void fill() {
        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = first;
    }

    @Test
    public void whenHasCycleThenTrue() {
        assertThat(helper.hasCycle(first), is(true));
    }

    @Test
    public void whenNotHasCycleThenFalse() {
        fourth.next = null;
        assertThat(helper.hasCycle(first), is(false));
    }

    @Test
    public void whenHasCycleThreeToTwoThenTrue() {
        fourth.next = null;
        third.next = second;
        assertThat(helper.hasCycle(first), is(true));
    }

    @Test
    public void whenAddSixNodesAndFifthNodeLinkOnTwoThenTrue() {
        Node<Integer> n1 = new Node<>(1);
        Node<Integer> n2 = new Node<>(1);
        Node<Integer> n3 = new Node<>(1);
        Node<Integer> n4 = new Node<>(1);
        Node<Integer> n5 = new Node<>(1);
        Node<Integer> n6 = new Node<>(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n2;
        n6.next = null;
        assertThat(helper.hasCycle(n1), is(true));
    }

    @Test
    public void whenOneItemLinkOnNullThenFalse() {
        Node<Integer> n1 = new Node<>();
        n1.value = 1;
        assertThat(helper.hasCycle(n1), is(false));
    }

    @Test
    public void whenOneItemLinkItSelfThenTrue() {
        Node<Integer> n1 = new Node<>(1);
        n1.next = n1;
        assertThat(helper.hasCycle(n1), is(true));
    }

    @Test
    public void whenAddTwoNodesAndSecondNodeLinkOnFirstThenTrue() {
        Node<Integer> n1 = new Node<>(1);
        Node<Integer> n2 = new Node<>(1);
        n1.next = n2;
        n2.next = n1;
        assertThat(helper.hasCycle(n1), is(true));
    }
}