package Action

import Card.Blue.DeepFreeze
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot

class DeepFreezeAction(private val ac: AbstractCreature, info: DamageInfo) : AbstractGameAction() {
    private var info: DamageInfo? = null

    init {
        this.info = info
    }

    override fun update() {
        AbstractDungeon.player.orbs.indices.forEach {
            if (AbstractDungeon.player.orbs[it].ID === "Forst"){
                this.addToTop(DamageAction(this.ac, this.info, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true))
            }
        }
        this.isDone = true
    }
}
