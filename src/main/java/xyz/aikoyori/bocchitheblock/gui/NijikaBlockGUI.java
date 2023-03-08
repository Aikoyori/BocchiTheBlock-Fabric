package xyz.aikoyori.bocchitheblock.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import xyz.aikoyori.bocchitheblock.blocks.NijikaBlock;
import xyz.aikoyori.bocchitheblock.blocks.NijikaBlockEntity;

public class NijikaBlockGUI extends Screen {
    NijikaBlockEntity neb;
    public NijikaBlockGUI(NijikaBlockEntity neb) {
        super(Text.literal("test"));
        this.neb = neb;
    }

    @Override
    protected void init() {
        super.init();
    }
}
