package fertdt.helpers;

import java.util.ArrayList;
import java.util.List;

public class SessionIDGenerator implements ISessionIDGenerator {
    private List<Character> list;

    public SessionIDGenerator() {
        list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
            if (i < 10) list.add((char) ('0' + i));
        }
    }

    @Override
    public String generate() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            ans.append(list.get((int) (Math.random() * 62)));
        }
        return ans.toString();
    }
}
