package dev.bryth.ariane.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KeybindListener {
    private final KeyBinding toggleKeybind = new KeyBinding("Toggle tracer", Keyboard.KEY_K, "Tracer Mod");
    private final KeyBinding renderKeybind = new KeyBinding("Toggle lines rendering", Keyboard.KEY_L, "Tracer Mod");
    private final KeyBinding clearKeybind = new KeyBinding("Clear all tracer lines", Keyboard.KEY_M, "Tracer Mod");

    public KeybindListener() {
        ClientRegistry.registerKeyBinding(toggleKeybind);
        ClientRegistry.registerKeyBinding(renderKeybind);
        ClientRegistry.registerKeyBinding(clearKeybind);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (toggleKeybind.isPressed()) {
            String action = LinesRenderer.isTracking ? "disabled" : "enabled";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.format("§dTracer mod %s", action)));
            LinesRenderer.isTracking = !LinesRenderer.isTracking;
            LinesRenderer.pos.add(null);
        }

        if(renderKeybind.isPressed()) {
            String action = LinesRenderer.isRendering ? "disabled" : "enabled";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.format("§dLines rendering %s", action)));
            LinesRenderer.isRendering = !LinesRenderer.isRendering;
        }

        if(clearKeybind.isPressed()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§cCleared all lines"));
            LinesRenderer.pos = new ArrayList<>();
        }
    }
}