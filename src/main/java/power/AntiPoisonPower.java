package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;



public class AntiPoisonPower extends AbstractPower {
    public static final String Power_ID = "AntiPoisonPower";
    public static final String IMG = "images/ui/power/AntiPoisonPower.png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(Power_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AntiPoisonPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = Power_ID;
        this.owner = owner;
        this.img = ImageMaster.loadImage(IMG);
        this.type = PowerType.BUFF;
        this.updateDescription();
        this.loadRegion(Power_ID);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
    public void atEndOfTurn(boolean isPlayer) {
        if (owner.getPower("Poison") != null) {
            owner.getPower("Poison").amount /= 2;
        }
    }
}
