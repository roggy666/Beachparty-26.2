package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.satisfy.beachparty.client.gui.handler.MiniFridgeGuiHandler;
import net.satisfy.beachparty.client.gui.handler.PalmBarGuiHandler;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

public class ScreenHandlerTypeRegistry {
    // the MenuType constructor is opened by the fabric-menu-api class tweaker
    public static final MenuType<MiniFridgeGuiHandler> MINI_FRIDGE_GUI_HANDLER = register("mini_fridge_gui_handler", new MenuType<>(MiniFridgeGuiHandler::new, FeatureFlags.VANILLA_SET));
    public static final MenuType<PalmBarGuiHandler> PALM_BAR_GUI_HANDLER = register("palm_bar_gui_handler", new MenuType<>(PalmBarGuiHandler::new, FeatureFlags.VANILLA_SET));

    public static void init() {
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType<T> type) {
        return Registry.register(BuiltInRegistries.MENU, BeachpartyIdentifier.identifier(name), type);
    }
}
