package theDefault.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDefault.cards.ChaserCard;

import static theDefault.TheChaserMod.makeID;

public class MagicNumber2 extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("M2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((ChaserCard) card).isMagicNumber2Modified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((ChaserCard) card).magicNumber2;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((ChaserCard) card).baseMagicNumber2;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((ChaserCard) card).upgradedMagicNumber2;
    }
}