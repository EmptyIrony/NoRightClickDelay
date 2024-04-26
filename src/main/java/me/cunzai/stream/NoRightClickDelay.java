package me.cunzai.stream;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mod(
	modid = NoRightClickDelay.MODID,
	name = NoRightClickDelay.NAME,
	version = NoRightClickDelay.VERSION
)
public class NoRightClickDelay {
	public static final String MODID = "no_right_click_delay";
	public static final String NAME = "No Right Click Delay";
	public static final String VERSION = "1.0";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	private Field field;

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent preinit) {
		MinecraftForge.EVENT_BUS.register(this);
        try {
            field = Minecraft.class.getDeclaredField("field_71467_ac");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
	}

	@SubscribeEvent
	public void tick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.side == Side.CLIENT) {
            try {
                field.set(Minecraft.getMinecraft(), 0);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
	}
}
