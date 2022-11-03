package me.aroze.vanillacheats.cheats

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerAnimationEvent
import org.bukkit.event.player.PlayerAnimationType
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector
import java.util.function.Predicate

object Reach : Listener {

    @EventHandler
    private fun fancyOnClick(event : PlayerAnimationEvent) {
        if (!event.animationType.equals(PlayerAnimationType.ARM_SWING)) return

        val maxDistance = 7

        val player = event.player

        val trace: LivingEntity = rayTrace(player.eyeLocation, player.eyeLocation.direction, maxDistance) {
            it != player
        } ?: return

        trace.damage(0.1)
    }

    private fun rayTrace(start: Location, direction: Vector, distance: Int, filter: Predicate<LivingEntity>? = null): LivingEntity? {
        val vector = start.toVector().clone()
        var dir = direction.clone().normalize().multiply(0.25)

        for (i in 1..(4 * distance)) {
            vector.add(dir)

            //start.world!!.spawnParticle(Particle.CRIT, vector.toLocation(start.world!!), 1, 0.0, 0.0, 0.0, 0.0)

            return start.world!!.getNearbyEntities(
                vector.toLocation(start.world!!),
                2.0, 4.0, 2.0
            ) {
                it is LivingEntity && filter?.test(it) ?: true
            }
                .map { it as LivingEntity }
                .firstOrNull {
                    it.boundingBox.contains(vector)
                } ?: continue
        }

        return null
    }

}