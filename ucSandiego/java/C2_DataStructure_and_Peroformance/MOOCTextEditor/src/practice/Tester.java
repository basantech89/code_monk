package practice;

public class Tester {

    public static void main (String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        System.out.println(list.size());
        list.add(2);
        list.add(5);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(3);
        System.out.println(list.size());
        System.out.println(list);
        System.out.println(list.isEmpty());
        System.out.println(list.get(5));
        System.out.println(list.get(0));
        System.out.println(list.get(3));
        System.out.println(list.remove(5));
        System.out.println(list.size());
        System.out.println(list.set(4, 15));
        System.out.println(list);
        list.add(2, 3242);
        System.out.println(list);
    }
}
