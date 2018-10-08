package ru.job4j.list;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Helper {
    public boolean hasCycle(Node node) {
        Node turtle = node;
        Node hare = node.next;
        boolean hasCycle = false;
        while (turtle != null && hare != null) {
            if (turtle == hare) {
                hasCycle = true;
                break;
            }
            turtle = turtle.next;
            hare = hare.next;
            if (hare != null) {
                hare = hare.next;
            }
        }
        return hasCycle;
    }
}
