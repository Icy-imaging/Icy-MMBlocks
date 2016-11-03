package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarDouble;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Return camera exposure value in ms (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeGetExposure extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarDouble exposure = new VarDouble("Exposure (ms)", 50d);

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("exposure", exposure);
    }

    @Override
    public void run()
    {
        try
        {
            exposure.setValue(Double.valueOf(MicroManager.getExposure()));
        }
        catch (Throwable t)
        {
            throw new VarException(exposure, t.getMessage());
        }
    }
}
