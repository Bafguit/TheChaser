//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theChaser.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingDaggerEffect;

import java.util.ArrayList;

public class DaggerTargetEffect extends AbstractGameEffect {
    private boolean flipX;
    private int amount;

    public DaggerTargetEffect(boolean shouldFlip, int amount) {
        this.flipX = shouldFlip;
        this.amount = amount;
    }

    public void update() {
        this.isDone = true;
        ArrayList<Float> am = new ArrayList<>();
        int i;
        float x;
        if(this.amount % 2 != 0) {
            float start = 5.5F - ( 0.5F * (float) this.amount);
            for(int j = 0; j < this.amount; j++) {
                am.add(start + (float) j);
            }
        } else {
            float start = 5.5F - ((float) this.amount / 2.0F);
            for(int j = 0; j < this.amount; j++) {
                am.add(start + (float) j);
            }
        }
        if (this.flipX) {
            for(i = am.size(); i > 0; --i) {
                float f = am.get(i);
                x = AbstractDungeon.player.hb.cX - MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, AbstractDungeon.player.hb.cY + 120.0F * Settings.scale + f * -18.0F * Settings.scale, (f * 4) - 30.0F, true));
            }
        } else {
            for(i = 0; i < am.size(); ++i) {
                float f = am.get(i);
                x = AbstractDungeon.player.hb.cX + MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, AbstractDungeon.player.hb.cY - 100.0F * Settings.scale + f * 18.0F * Settings.scale, (f * 4) - 20.0F, false));
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
