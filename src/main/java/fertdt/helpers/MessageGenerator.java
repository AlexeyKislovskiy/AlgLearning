package fertdt.helpers;

import fertdt.exceptions.SendEmailException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MessageGenerator {
    public static String[] generateMessage(InputStream is) throws SendEmailException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                int n = (int) (Math.random() * 10);
                code.append(n);
            }
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("insert", code.toString());
                s.append(line);
            }
            String[] ans = new String[2];
            ans[0] = s.toString();
            ans[1] = code.toString();
            return ans;
        } catch (IOException e) {
            throw new SendEmailException("Can't send email", e);
        }
    }
}
