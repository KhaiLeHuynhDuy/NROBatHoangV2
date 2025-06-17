package Data;

import Player.Player;
import com.girlkun.database.GirlkunDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GetData {
    
    // Hàm để lấy username từ id của bảng player
    public static String getUsernameFromPlayerId(int playerId) {
        String username = null;
        try (Connection con = GirlkunDB.getConnection()) {
            String sql = "SELECT account_id FROM player WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, playerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int accountId = rs.getInt("account_id");
                        String accountSql = "SELECT username FROM account WHERE id = ?";
                        try (PreparedStatement accountPstmt = con.prepareStatement(accountSql)) {
                            accountPstmt.setInt(1, accountId);
                            try (ResultSet accountRs = accountPstmt.executeQuery()) {
                                if (accountRs.next()) {
                                    username = accountRs.getString("username");
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
    
    // Hàm Kiểm tra xem người chơi đã Xác Nhận tài khoản chưa
    public static boolean checkPlayerXacNhan(int playerId) {
        boolean isXacNhan = false;
        try (Connection con = GirlkunDB.getConnection()) {
            String sql = "SELECT account_id FROM player WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, playerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int accountId = rs.getInt("account_id");
                        String accountSql = "SELECT xac_nhan FROM account WHERE id = ?";
                        try (PreparedStatement accountPstmt = con.prepareStatement(accountSql)) {
                            accountPstmt.setInt(1, accountId);
                            try (ResultSet accountRs = accountPstmt.executeQuery()) {
                                if (accountRs.next()) {
                                    int xacNhan = accountRs.getInt("xac_nhan");
                                    isXacNhan = xacNhan == 1;
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isXacNhan;
    }
    
    // Hàm để lấy accountId từ ID_Player
    public static int getAccountIDFromPlayerId(int playerId) {
        int accountId = -1;
        try (Connection con = GirlkunDB.getConnection()) {
            String sql = "SELECT account_id FROM player WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, playerId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Kiểm tra xem có bản ghi nào trả về không
                    if (rs.next()) {
                        accountId = rs.getInt("account_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountId;
    }
    
    // Hàm để kiểm tra xem Người chơi đã tạo Acount được 30 ngày chưa
    public static boolean isAccountCreatedWithinLast30Days(int accountId) {
        LocalDate today = LocalDate.now();
        String query = "SELECT date_created FROM account WHERE id = ?";
        try (Connection connection = GirlkunDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LocalDate dateCreated = resultSet.getDate("date_created").toLocalDate();
                return ChronoUnit.DAYS.between(dateCreated, today) <= 30;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật cơ sở dữ liệu cho CountLeft trong bảng GiftCode
    public static void SaveCountLeftGiftCode(int countLeft, String code){
        try(Connection con = GirlkunDB.getConnection()){
            String sql = "UPDATE giftcode SET count_left = ? WHERE code = ?"; 
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,  countLeft);
            stmt.setString(2, code);
            stmt.executeUpdate();
        } catch (Exception erorlog) {}
    }
    
    // Hàm để lấy ID_Giftcode từ Code Giftcode của bảng giftcode
    public static int getIDGiftCodeFromCodeGiftCode(String Code) {
        int idCode = -1;
        try (Connection con = GirlkunDB.getConnection()) {
            String accountSql = "SELECT id FROM giftcode WHERE code = ?";
            try (PreparedStatement accountPstmt = con.prepareStatement(accountSql)) {
                accountPstmt.setString(1, Code);
                try (ResultSet accountRs = accountPstmt.executeQuery()) {
                    if (accountRs.next()) {
                        idCode = accountRs.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCode;
    }
    
    // Cập nhật cơ sở dữ liệu cho CountLeft trong bảng GiftCode
    public static void AddPlayerUseGiftCode(Player player, String Code){
        int STT = -1;
        try(Connection con = GirlkunDB.getConnection()){
            String sql = "SELECT MAX(stt) FROM history_giftcode";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                STT = rs.getInt(1) + 1;
            }
        } catch (Exception erorlog) {}
        int IDGiftUse = getIDGiftCodeFromCodeGiftCode(Code);
        try(Connection con = GirlkunDB.getConnection()){
            String sql = "INSERT INTO history_giftcode (stt, id_player, name, id_giftcode) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, STT);
            statement.setInt(2,(int) player.id);
            statement.setString(3, player.name);
            statement.setInt(4, IDGiftUse);
            statement.executeUpdate();
        } catch (Exception erorlog) {}
    }
    
    // Check xem người chơi đã nhập GiftCode này chưa
    public static boolean hasPlayerUsedGiftcode(Player player, String Code) {
        boolean hasUsed = false;
        int IDGiftUse = getIDGiftCodeFromCodeGiftCode(Code);
        try (Connection con = GirlkunDB.getConnection()) {
            String sql = "SELECT COUNT(*) FROM history_giftcode WHERE id_giftcode = ? AND id_player = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, IDGiftUse);
                stmt.setInt(2,(int) player.id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        hasUsed = count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasUsed;
    }
    
    // Hàm để lấy gold_vo_dai của bảng player
    public static long getGoldChallenge(int playerId, int temp) {
        long gold = 0;
        try (Connection con = GirlkunDB.getConnection()) {
            String sql = "", columLabel = "";
            if(temp == 1){
                sql = "SELECT gold_vo_dai FROM player WHERE id = ?";
                columLabel = "gold_vo_dai";
            } else if(temp == 2) {
                sql = "SELECT gold_dai_hoi FROM player WHERE id = ?";
                columLabel = "gold_dai_hoi";
            }
            if(!sql.isEmpty() && !columLabel.isEmpty()){
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setInt(1, playerId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            gold = rs.getInt(columLabel);
                        }
                    }
                }
            }
        } catch (SQLException e) {}
        return gold;
    }
    
    // Cập nhật database cột gold_vo_dai trong bảng player
    public static void SaveGoldChallenge(long goldChallenge, int playerId, int temp){
        try(Connection con = GirlkunDB.getConnection()){
            String sql = "";
            if(temp == 1){
                sql = "UPDATE player SET gold_vo_dai = ? WHERE id = ?"; 
            } else if(temp == 2) {
                sql = "UPDATE player SET gold_dai_hoi = ? WHERE id = ?"; 
            }
            if(!sql.isEmpty()){
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setLong(1,  goldChallenge);
                stmt.setInt(2,  playerId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {}
    }
    
}

