package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Set the shutter position: open / close (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetShutter extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarBoolean open = new VarBoolean("Open", true);
    VarBoolean done = new VarBoolean("Done", false);

    @Override
    public void run()
    {
        done.setValue(Boolean.FALSE);

        try
        {
            MicroManager.setShutterOpen(open.getValue().booleanValue());
            done.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(open, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("open", open);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("done", done);
    }
}
