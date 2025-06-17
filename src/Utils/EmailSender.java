package Utils;

import Data.GetData;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import com.girlkun.database.GirlkunDB;

import java.util.Properties;

public class EmailSender {
    private final String username = "anhjkr@gmail.com"; // Tài khoản Gmail Gửi
    private final String password = "pnjq lntg fehx euis"; // Mật khẩu ứng dụng của Gmail
    
    private final Map<String, Long> sentCodes = new HashMap<>();

    public void sendEmail(String to, String subject, String text) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            
            // Ghi lại thời gian mà mã đã được gửi đi
            sentCodes.put(to, System.currentTimeMillis());
            
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean isCodeValid(String recipientEmail) {
        Long sentTime = sentCodes.get(recipientEmail);
        if (sentTime == null) {
            // Không tìm thấy mã xác thực cho email này
            return false;
        }

        // Kiểm tra thời gian hết hạn (2 phút)
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - sentTime;
        return elapsedTime <= 2 * 60 * 1000; // 2 phút tính bằng millisecond
    }
    
    public static boolean isEmailEntered(int playerId) {
        try {
            // Lấy username từ playerId
            String username = GetData.getUsernameFromPlayerId(playerId);
            
            // Nếu không tìm thấy username, trả về false
            if (username == null || username.isEmpty()) {
                return false;
            }

            // Kết nối đến cơ sở dữ liệu
            try (Connection con = GirlkunDB.getConnection()) {
                // Chuẩn bị truy vấn SQL
                String sql = "SELECT gmail FROM account WHERE username = ?";

                // Tạo một câu lệnh
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, username);

                    // Thực hiện truy vấn và nhận kết quả
                    try (ResultSet rs = pstmt.executeQuery()) {
                        // Kiểm tra xem cột gmail có dữ liệu không
                        if (rs.next()) {
                            String email = rs.getString("gmail");
                            return email != null && !email.isEmpty();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
