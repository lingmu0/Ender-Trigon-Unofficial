package nonamecrackers2.endertrigon.common.init;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Instrument;
import nonamecrackers2.endertrigon.EnderTrigonMod;
public class EnderTrigonInstruments {
    public static void register() {
        Registry.register(BuiltInRegistries.INSTRUMENT, EnderTrigonMod.id("dragon_horn"), new Instrument(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(EnderTrigonSoundEvents.DRAGON_HORN.get()), 60, 32.0F));
    }
}
