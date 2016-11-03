/**
 * 
 */
package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarInteger;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Set the camera binning value (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetBinning extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarInteger binning = new VarInteger("Binning", 1);
    VarBoolean done = new VarBoolean("Done", false);

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("binning", binning);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("done", done);
    }

    @Override
    public void run()
    {
        done.setValue(Boolean.FALSE);

        try
        {
            MicroManager.setBinning(binning.getValue().intValue());
            done.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(binning, t.getMessage());
        }
    }
}
