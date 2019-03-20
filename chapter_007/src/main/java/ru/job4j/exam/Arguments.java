package ru.job4j.exam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Arguments {
    List<String> usageArgsKeys;
    HashMap<String, IArg> hashMap = new HashMap<>();

    Arguments() {
        usageArgsKeys = new LinkedList<>();
    }

    public void execute(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            String curValue = args[i];
            String nextValue = args[i + 1];
            usageArgsKeys.add(curValue);
            IArg arg = hashMap.get(curValue);
            if (arg != null && !isKey(nextValue)) {
                arg.setValue(nextValue);
            }
        }
    }

    public void setArgument(IArg argument) {
        this.hashMap.put(argument.getKey(), argument);
    }

    public List<String> getUsageArgsKeys() {
        return this.usageArgsKeys;
    }

    public boolean isValide() {
        boolean res = true;
        for (String usageArgKey: usageArgsKeys) {
            IArg arg = hashMap.get(usageArgKey);
            if (arg == null) {
                res = false;
                break;
            } else if (!arg.isValid()) {
                res = false;
                break;
            }
        }
        return res;
    }

    public String help() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IArg argument: hashMap.values()) {
            stringBuilder.append(argument.help()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private boolean isKey(String key) {
        return key.substring(0, 1).equals("-");
    }
}
