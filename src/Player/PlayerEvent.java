package Player;

public class PlayerEvent {

    private int eventPoint;
    private int eventPointBoss;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public int getEventPoint() {
        return eventPoint;
    }

    public void setEventPoint(int eventPoint) {
        this.eventPoint = eventPoint;
    }

    public int getEventPointBoss() {
        return eventPointBoss;
    }

    public void setEventPointBoss(int eventPointBHM) {
        this.eventPointBoss = eventPointBHM;
    }

    public void addEventPoint(int num) {
        eventPoint += num;
    }

    public void subEventPoint(int num) {
        eventPoint -= num;
    }

    public void addEventPointBoss(int num) {
        eventPointBoss += num;
    }

    public void subEventPointBoss(int num) {
        eventPointBoss -= num;
    }

    public void update() {

    }

}
