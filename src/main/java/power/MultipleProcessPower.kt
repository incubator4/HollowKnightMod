package power

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.orbs.AbstractOrb
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.FocusPower

class MultipleProcessPower(owner: AbstractCreature, newAmount: Int) : AbstractPower() {

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = owner
        this.amount = newAmount
        this.updateDescription()
        this.loadRegion(POWER_ID)
    }

    @Override
    override fun atStartOfTurn() {
        DARK = false
        FROST = false
        LIGHTING = false
        PLASMA = false

    }

    @Override
    override fun onChannel(orb: AbstractOrb) {
        if (orb.ID === "Dark") {
            DARK = true
        }
        if (orb.ID === "Forst") {
            FROST = true
        }
        if (orb.ID === "Lighting") {
            LIGHTING = true
        }
        if (orb.ID === "Plasma") {
            PLASMA = true
        }
        if (DARK && FROST && LIGHTING && PLASMA) {
            this.flash()
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(this.owner, this.owner,
                            FocusPower(this.owner, this.amount), this.amount))
        }
        DARK = false
        FROST = false
        LIGHTING = false
        PLASMA = false
        super.onChannel(orb)
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]
    }

    companion object {
        val POWER_ID = "Multiple Process"
        private val powerStrings: PowerStrings
        val NAME: String
        val DESCRIPTIONS: Array<String>
        private var DARK = false
        private var FROST = false
        private var LIGHTING = false
        private var PLASMA = false

        init {
            powerStrings = CardCrawlGame.languagePack.getPowerStrings("Juggernaut")
            NAME = powerStrings.NAME
            DESCRIPTIONS = powerStrings.DESCRIPTIONS
        }
    }
}
