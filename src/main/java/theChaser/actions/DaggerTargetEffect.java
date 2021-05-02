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
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
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
        float x;
        if(this.amount % 2 != 0) {
            float start = 5.5F - ( 0.5F * (float) this.amount);
            for(int j = 0; j < this.amount; j++) {
                float f = start + (float) j;
                x = AbstractDungeon.player.hb.cX - MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new TargetDaggerEffect(x, AbstractDungeon.player.hb.cY + (this.flipX ? 120.0F : -100.0F) * Settings.scale + f * (this.flipX ? -18.0F : 18.0F) * Settings.scale, (f * 4) - (this.flipX ? 30.0F : 20.0F), this.flipX));
            }
        } else {
            float start = 5.5F - ((float) this.amount / 2.0F);
            for(int j = 0; j < this.amount; j++) {
                float f = start + (float) j;
                x = AbstractDungeon.player.hb.cX - MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new TargetDaggerEffect(x, AbstractDungeon.player.hb.cY + (this.flipX ? 120.0F : -100.0F) * Settings.scale + f * (this.flipX ? -18.0F : 18.0F) * Settings.scale, (f * 4) - (this.flipX ? 30.0F : 20.0F), this.flipX));
            }
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
