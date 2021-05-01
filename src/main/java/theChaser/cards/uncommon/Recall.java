package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.AcrobaticsAlt;
import theChaser.cards.temp.BladeDanceAlt;
import theChaser.cards.temp.OutmaneuverAlt;
import theChaser.characters.TheChaser;

import java.util.ArrayList;
import java.util.Iterator;

import static theChaser.TheChaserMod.makeCardPath;

public class Recall extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Recall");
    public static final String IMG = makeCardPath("Recall.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int UNT = 3;
    private static final int UP_UNT = 1;

    public Recall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, 0, 0, UNT);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChooseOneAction(this.getCardGroup()));
        this.addToBot(new ChooseOneAction(this.getCardGroup()));
    }

    private ArrayList<AbstractCard> getCardGroup() {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new BladeDanceAlt());
        stanceChoices.add(new AcrobaticsAlt());
        stanceChoices.add(new OutmaneuverAlt());
        if (this.upgraded) {
            Iterator var4 = stanceChoices.iterator();

            while(var4.hasNext()) {
                ChaserCard c = (ChaserCard) var4.next();
                c.upgrade();
            }
        }

        return stanceChoices;
    }

    @Override
    public void upgradeCard() {
        upgradeMagicNumber(UP_UNT);
    }

}
