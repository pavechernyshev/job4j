package shildt.fullmanual.multithread;

public class CurrentThread {

    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();
        System.out.println("Teкyщий поток исполнения: " + currentThread);
        //изменить имя потока исполнения
        currentThread.setName("My Thread");
        System.out.println("Пocлe изменения имени потока: " + currentThread);
        try {
            for (int n = 5; n > 0; n--) {
                System.out.println(n);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ie) {
            System.out.println("Глaвный поток исполнения прерван");
        }
    }
}