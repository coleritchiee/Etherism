package net.iicosahedra.etherism.util;

import net.iicosahedra.etherism.Etherism;
import net.minecraft.resources.ResourceLocation;

public class ResourceLoc {
    public static ResourceLocation create(String path){
        return ResourceLocation.fromNamespaceAndPath(Etherism.MODID, path);
    }
}
