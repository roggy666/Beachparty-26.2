package net.satisfy.beachparty.core.util;

import net.minecraft.resources.Identifier;
import net.satisfy.beachparty.Beachparty;

public class BeachpartyIdentifier {

    public static Identifier identifier(String path) {
        return Identifier.fromNamespaceAndPath(Beachparty.MOD_ID, path);
    }
}
