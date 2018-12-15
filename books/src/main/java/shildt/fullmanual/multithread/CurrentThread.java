package shildt.fullmanual.multithread;

public class CurrentThread {

    public static void main(String args[]) {
        Thread t =Thread.currentThread();
        System.out.println("Teкyщий поток исполнения: " + t);
        //изменить имя потока исполнения
        t.setName("My Thread");
        System.out.println("Пocлe изменения имени потока: " + t);
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println(n);
                Thread.sleep(1000);
            }
        } catch (InterruptedException е) {
            System.out.println("Глaвный поток исполнения прерван");
        }
    }
}