package Utils;

import java.util.Random;

public class VerificationCodeGenerator {
    public String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Tạo mã 6 chữ số
        return String.valueOf(code);
    }
}
