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
        String previewValue = null;
        for (String arg: args) {
            if (isKey(arg)) {
                usageArgsKeys.add(arg);
            } else if (previewValue != null) {
                IArg iArg = this.hashMap.get(previewValue);
                if (iArg != null) {
                    iArg.setValue(arg);
                }
            }
            previewValue = arg;
        }
    }

    public void setArgument(IArg argument) {
        this.hashMap.put(argument.getKey(), argument);
    }

    public List<String> getUsageArgsKeys() {
        return this.usageArgsKeys;
    }

    public boolean isValid() {
        boolean res = true;
        for (IArg arg: hashMap.values()) {
            if (arg.isRequire() && !usageArgsKeys.contains(arg.getKey())) {
                res = false;
                break;
            }
        }
        if (res) {
            for (String usageArgKey : usageArgsKeys) {
                IArg arg = hashMap.get(usageArgKey);
                if (arg == null) {
                    res = false;
                    break;
                } else if (!arg.isValid()) {
                    res = false;
                    break;
                }
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
