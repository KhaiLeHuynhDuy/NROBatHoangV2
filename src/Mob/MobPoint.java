package Mob;

import Utils.Util;

public class MobPoint {

    public final Mob mob;
    public long hp;
    public long maxHp;
    public long dame;
    
    public MobPoint(Mob mob){
        this.mob = mob;
    }

    public long getHpFull() {
        return maxHp;
    }
    public long get15HpFull() {
        return (long) (maxHp*0.15f);
    }
    public void setHpFull(long hp) {
        maxHp = hp;
    }

    public long gethp() {
        return hp;
    }

    public void sethp(long hp) {
        if (this.hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    public long getDameAttack() {
        return this.dame != 0 ? this.dame + Util.nextInt(-(Util.NQHxMNint(this.dame / 100)), Util.NQHxMNint(this.dame / 100))
                : this.get15HpFull() * Util.nextInt(mob.pDame - 1, mob.pDame + 1) / 100
                + Util.nextInt(-(mob.level * 10), mob.level * 10);
    }
}
