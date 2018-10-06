package ru.job4j.list;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Helper {
    public boolean hasCycle(Node node) {
        Node startNode = node;
        boolean notChecked = true;
        boolean res = false;
        int counter = 0;
        while (notChecked) {
            node = node.next;
            if (node == null) {
                notChecked = false;
            } else if (hasNode(startNode, node, ++counter)) {
                notChecked = false;
                res = true;
            }
        }
        return res;
    }

    /***
     * Check Node in diapason first to count
     * @param first Node start
     * @param check Node whose check
     * @param count count of use method next
     * @return boolean has check Node in diapason
     */
    private boolean hasNode(Node first, Node check, int count) {
        boolean res = false;
        for (int i = 0; i < count; i++) {
            if (first == check) {
                res = true;
                break;
            }
            first = first.next;
        }
        return res;
    }
}
