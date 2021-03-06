package ru.job4j.tracker;

public class ValidateInput implements Input {

    private final Input input; //композиция

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
          try {
              value = this.input.ask(question, range);
              invalid = false;
          } catch (MenuOutException moe) {
              System.out.println("Выберите ключ из меню");
          } catch (NumberFormatException nfe) {
              System.out.println("Введите число");
          }
        } while (invalid);
        return value;
    }
}
