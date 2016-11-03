package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarInteger;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Return camera binning value (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeGetBinning extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarInteger binning = new VarInteger("Binning", 1);

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("binning", binning);
    }

    @Override
    public void run()
    {
        try
        {
            binning.setValue(Integer.valueOf(MicroManager.getBinning()));
        }
        catch (Throwable t)
        {
            throw new VarException(binning, t.getMessage());
        }
    }
}
