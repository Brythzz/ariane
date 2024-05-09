package dev.bryth.ariane;

import dev.bryth.ariane.events.KeybindListener;
import dev.bryth.ariane.events.LinesRenderer;
import dev.bryth.ariane.events.PositionListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid="ariane",  name="Ariane",  version="1.0")
public class Ariane {
    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new LinesRenderer());
        MinecraftForge.EVENT_BUS.register(new KeybindListener());
        MinecraftForge.EVENT_BUS.register(new PositionListener());
    }
}
