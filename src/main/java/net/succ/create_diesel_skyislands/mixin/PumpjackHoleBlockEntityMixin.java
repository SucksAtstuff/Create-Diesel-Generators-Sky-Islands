package net.succ.create_diesel_skyislands.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.jesz.createdieselgenerators.content.pumpjack.PumpjackHoleBlockEntity", remap = false)
public abstract class PumpjackHoleBlockEntityMixin extends BlockEntity {

    @Shadow public int pipeLength;

    // Stores the actual scan count captured just before CDG resets pipeLength to 0.
    // Written by captureScanLength; read by setValidIfFloor in the same tick.
    @Unique private int skyIslands$scannedLength;

    protected PumpjackHoleBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // CDG's tick loop increments the local pipeLength for every block it checks,
    // then at the end stores it to this.pipeLength only when a deposit was found
    // (ordinal 0), or resets this.pipeLength to 0 when no deposit was found
    // (ordinal 1). In Case B (pipes to the build floor), ordinal 1 fires.
    // We intercept it BEFORE the reset so the local count is still available via @Local.
    @Inject(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lcom/jesz/createdieselgenerators/content/pumpjack/PumpjackHoleBlockEntity;pipeLength:I",
            opcode = Opcodes.PUTFIELD,
            ordinal = 1,
            remap = false
        ),
        remap = false
    )
    private void captureScanLength(CallbackInfo ci, @Local(ordinal = 0) int localPipeLength) {
        skyIslands$scannedLength = localPipeLength;
    }

    // Fires just before this.valid = valid. If valid is already true (oil deposit found
    // normally), pass it through. Otherwise check whether the scan reached the build
    // floor and flip valid to true if so.
    @ModifyVariable(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lcom/jesz/createdieselgenerators/content/pumpjack/PumpjackHoleBlockEntity;valid:Z",
            opcode = Opcodes.PUTFIELD,
            remap = false
        ),
        index = 2,
        remap = false
    )
    private boolean setValidIfFloor(boolean valid) {
        if (valid) return true;
        if (getLevel() == null) return false;
        int expected = getBlockPos().getY() - getLevel().getMinBuildHeight();
        if (skyIslands$scannedLength >= expected - 1) {
            this.pipeLength = skyIslands$scannedLength;
            return true;
        }
        return false;
    }
}
