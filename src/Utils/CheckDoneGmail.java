package Utils;

import Player.Player;
import Services.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.girlkun.database.GirlkunDB;

public class CheckDoneGmail {
    
    private static final Map<Integer, Long> lastSentTime = new HashMap<>(); // Lưu thời gian gửi mã lần cuối của từng userId
    private static final long MIN_TIME_BETWEEN_SENDS = 2 * 60 * 1000; // Thời gian tối thiểu giữa 2 lần gửi (1 phút)

    private static String verificationCode;
    
    public static void Done(int userId, Player pl) {
        long currentTime = System.currentTimeMillis();
        Long lastSend = lastSentTime.get(userId);
        if (lastSend == null || currentTime - lastSend >= MIN_TIME_BETWEEN_SENDS) {
            try (Connection con = GirlkunDB.getConnection()) {
                String sql = "SELECT gmail FROM account WHERE id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setInt(1, userId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) { // Kiểm tra xem có email được tìm thấy hay không
                            Service.gI().sendThongBao(pl, "Đã gửi mã!");
                            VerificationCodeGenerator generator = new VerificationCodeGenerator();
                            verificationCode = generator.generateCode();
                            String email = rs.getString("gmail");
                            EmailSender emailSender = new EmailSender();
                            emailSender.sendEmail(email, "Your Verification Code", "Your verification code is: " + verificationCode);
                            lastSentTime.put(userId, currentTime); // Cập nhật thời gian gửi mã
                        } else {
                            // Xử lý trường hợp không tìm thấy email
                            System.out.println("Email không tìm thấy.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Gửi thông báo cho người chơi rằng họ cần đợi thêm 1 phút
            long remainingTime = MIN_TIME_BETWEEN_SENDS - (currentTime - lastSend);
            Service.gI().sendThongBao(pl, "Vui lòng đợi " + (remainingTime / 1000) + " giây để gửi mã tiếp theo!");
        }
    }
    
    public static boolean verifyCode(int userId, String enteredCode) {
        if (enteredCode.equals(verificationCode)) {
            try (Connection con = GirlkunDB.getConnection()) {
                String sql = "UPDATE account SET xac_nhan = 1 WHERE id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}