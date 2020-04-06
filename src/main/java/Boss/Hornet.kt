package Boss

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction
import com.megacrit.cardcrawl.actions.common.*
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.MonsterStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.beyond.Spiker
import com.megacrit.cardcrawl.powers.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import power.AntiPoisonPower

import java.util.HashMap


class Hornet : AbstractMonster(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, null, -50.0f, 30.0f) {
    private var thresholdReached = false
    private var firstMove = true
    private val spawnX = -100.0f
    private val enemySlots = HashMap<Int, AbstractMonster>()

    init {
        this.type = EnemyType.BOSS
        this.dialogX = 0.0f
        this.dialogY = 0.0f
        this.img = Texture(Gdx.files.internal("images/monster/Hornet.png"))
        this.setHp(HP_MIN, HP_MAX_A)
        this.damage.add(DamageInfo(this, stingerDamage))
        this.damage.add(DamageInfo(this, dancingsewingDamage))
        this.damage.add(DamageInfo(this, needleDamage))
        this.damage.add(DamageInfo(this, SprintDamage))

    }


    override fun usePreBattleAction() {
        logger.info("Before usePreBattleAction()")
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(this, this,
                        AntiPoisonPower(this)))
        logger.info("After usePreBattleAction()")
    }

    override fun takeTurn() {
        when (this.nextMove) {
            STINGER -> stringer()
            DANCINGSEWING -> dancingsewing()
            NEEDLE -> needle()
            SPRINT -> sprint()
            STRENGTHUP -> {
                strengthup()
                summonSpiker()
            }
            SUMMOMSPIKER -> summonSpiker()
        }//logger.info("ERROR: Default Take Turn was called on " + this.name);
    }


    private fun stringer() {
        AbstractDungeon.actionManager.addToBottom(AnimateFastAttackAction(this))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        AbstractDungeon.player, this, VulnerablePower(AbstractDungeon.player, 2, true)))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        AbstractDungeon.player, this, WeakPower(AbstractDungeon.player, 2, true)))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        AbstractDungeon.player, this, ConstrictedPower(AbstractDungeon.player, this, 2)))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        AbstractDungeon.player, this, FrailPower(AbstractDungeon.player, 2, true)))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        AbstractDungeon.player, this, DrawReductionPower(AbstractDungeon.player, 2)))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(AbstractDungeon.player, this,
                        StrengthPower(this, -1), -1))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(AbstractDungeon.player, this,
                        DexterityPower(this, -1), -1))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(AbstractDungeon.player, this,
                        FocusPower(this, -1), -1))



        AbstractDungeon.actionManager.addToBottom(
                DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT))
    }

    private fun dancingsewing() {
        for (i in 0 until dancingsewingCount) {
            AbstractDungeon.actionManager.addToBottom(AnimateHopAction(this))
            AbstractDungeon.actionManager.addToBottom(WaitAction(0.2f))
            AbstractDungeon.actionManager.addToBottom(
                    DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_LIGHT))
        }

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(this, this, PlatedArmorPower(this, 7)))
    }

    private fun needle() {
        AbstractDungeon.actionManager.addToBottom(AnimateHopAction(this))
        AbstractDungeon.actionManager.addToBottom(WaitAction(0.2f))
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.BLUNT_HEAVY))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(this, this, BlurPower(this, 3)))
    }

    private fun sprint() {
        AbstractDungeon.actionManager.addToBottom(AnimateHopAction(this))
        AbstractDungeon.actionManager.addToBottom(WaitAction(0.2f))
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(AbstractDungeon.player, this.damage.get(3), AttackEffect.BLUNT_LIGHT))
        AbstractDungeon.actionManager.addToBottom(GainBlockAction(this, this, 20))
    }

    private fun strengthup() {
        AbstractDungeon.actionManager.addToBottom(AnimateHopAction(this))
        AbstractDungeon.actionManager.addToBottom(WaitAction(0.2f))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(this, this,
                        StrengthPower(this, 3), 3))
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(this, this, PlatedArmorPower(this, 5)))
    }

    private fun summonSpiker() {
        for (i in 0..1) {
            val spiker = Spiker(this.spawnX + -185.0f * (i + 1).toFloat(), MathUtils.random(-5.0f, 25.0f))
            AbstractDungeon.actionManager.addToBottom(SFXAction("MONSTER_COLLECTOR_SUMMON"))
            AbstractDungeon.actionManager.addToBottom(SpawnMonsterAction(spiker, true))
            this.enemySlots.put(i, spiker)
        }
    }


    override fun getMove(num: Int) {
        if (this.firstMove) {
            this.firstMove = false
            this.setMove(STINGER, Intent.ATTACK_DEBUFF)
        } else if (this.currentHealth < this.maxHealth / 2 && !this.thresholdReached) {
            this.thresholdReached = true
            this.setMove(SUMMOMSPIKER, Intent.UNKNOWN)
        } else {
            val tmp = MathUtils.random(2, 5)
            when (tmp.toByte()) {
                DANCINGSEWING -> this.setMove(DANCINGSEWING, Intent.ATTACK)
                NEEDLE -> this.setMove(NEEDLE, Intent.ATTACK_BUFF)
                SPRINT -> this.setMove(DANCINGSEWING, Intent.ATTACK_DEFEND)
                STRENGTHUP -> strengthup()
            }
        }

    }


    override fun die() {
        super.die()
        for (monster in AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDying) {
                AbstractDungeon.actionManager.addToBottom(EscapeAction(monster))
            }
        }

        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.onBossVictoryLogic()
            this.onFinalBossVictoryLogic()
        }
    }

    companion object {
        val ID = "HollowKnightMod:Hornet"
        private val ENCOUNTER_NAME = ID
        private val monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID)
        private val NAME = monsterStrings.NAME
        private val MOVES = monsterStrings.MOVES
        private val DIALOG = monsterStrings.DIALOG
        private val logger = LogManager.getLogger(Hornet::class.java.name)
        //location
        private val HB_X = 0.0f
        private val HB_Y = 0.0f
        private val HB_W = 0.0f
        private val HB_H = 0.0f

        //stats
        private val HP_MIN = 370
        private val HP_MAX = 380
        private val HP_MIN_A = 400
        private val HP_MAX_A = 420


        //Attack para
        private val STINGER: Byte = 1
        private val stingerDamage = 1

        private val dancingsewingDamage = 10
        private val DANCINGSEWING: Byte = 2
        private val dancingsewingCount = 2

        private val needleDamage = 20
        private val NEEDLE: Byte = 3

        private val SprintDamage = 4
        private val SPRINT: Byte = 4

        private val STRENGTHUP: Byte = 5
        private val SUMMOMSPIKER: Byte = 6
    }


}
