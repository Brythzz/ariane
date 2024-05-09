package dev.bryth.ariane.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;

public class PositionListener {
    private static int timerTick = 1;

    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        LinesRenderer.isTracking = false;
        LinesRenderer.pos = new ArrayList<>();
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (event.entity != player) return;

        LinesRenderer.pos = new ArrayList<>();
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !LinesRenderer.isTracking) return;

        timerTick = (timerTick + 1) % 10;

        if (timerTick % 5 == 0) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player == null) return;

            LinesRenderer.addPosition(player.posX, player.posY, player.posZ);
        }
    }
}