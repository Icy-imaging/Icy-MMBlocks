package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarDouble;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Set the camera exposure value (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetExposure extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarDouble exposure = new VarDouble("Exposure", 10d);
    VarBoolean done = new VarBoolean("Done", false);

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("exposure", exposure);
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
            MicroManager.setExposure(exposure.getValue().doubleValue());
            done.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(exposure, t.getMessage());
        }
    }
}
