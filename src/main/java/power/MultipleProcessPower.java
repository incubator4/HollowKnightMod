package power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class MultipleProcessPower extends AbstractPower {
    public static final String POWER_ID = "Multiple Process";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static boolean DARK = false;
    private static boolean FROST = false;
    private static boolean LIGHTING = false;
    private static boolean PLASMA = false;

    public MultipleProcessPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion(POWER_ID);
    }

    @Override
    public void atStartOfTurn() {
        DARK = false;
        FROST = false;
        LIGHTING = false;
        PLASMA = false;

    }

    @Override
    public void onChannel(AbstractOrb orb) {
        if (orb.ID == "Dark") {
            DARK = true;
        }
        if (orb.ID == "Forst") {
            FROST = true;
        }
        if (orb.ID == "Lighting") {
            LIGHTING = true;
        }
        if (orb.ID == "Plasma") {
            PLASMA = true;
        }
        if (DARK && FROST && LIGHTING && PLASMA) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(this.owner,this.owner,
                            new FocusPower(this.owner, this.amount),this.amount));
        }
        DARK = false;
        FROST = false;
        LIGHTING = false;
        PLASMA = false;
        super.onChannel(orb);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Juggernaut");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
