package theChaser.cards.uncommon;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theChaser.TheChaserMod;
import theChaser.cards.ChaserCard;
import theChaser.cards.temp.Camouflage;
import theChaser.cards.temp.KnifeThrow;
import theChaser.cards.temp.BloodyWind;
import theChaser.characters.TheChaser;

import java.util.ArrayList;
import java.util.Iterator;

import static theChaser.TheChaserMod.makeCardPath;

public class Flexibility extends ChaserCard {

    public static final String ID = TheChaserMod.makeID("Flexibility");
    public static final String IMG = makeCardPath("Flexibility.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheChaser.Enums.COLOR_CHASER;

    private static final int COST = 2;
    private static final int UNT = 3;
    private static final int UP_UNT = 1;

    public Flexibility() {
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
        stanceChoices.add(new KnifeThrow());
        stanceChoices.add(new Camouflage());
        stanceChoices.add(new BloodyWind());
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
