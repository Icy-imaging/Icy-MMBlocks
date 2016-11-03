package plugins.stef.micromanager.block.stage;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarDouble;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.tools.StageMover;

/**
 * Set the Z stage to given Z position (Micro-Manager).
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetStageZ extends AbstractMicroscopeBlock
{
    VarDouble inZ;
    VarObject trigger = new VarObject("Trigger", null);
    VarBoolean varWait = new VarBoolean("Wait stage", true);
    VarBoolean varRelative = new VarBoolean("Relative", true);
    VarBoolean varDone = new VarBoolean("Done", false);

    public MicroscopeSetStageZ()
    {
        try
        {
            double z = StageMover.getZ();
            inZ = new VarDouble("Z", z);
        }
        catch (Throwable t)
        {
            inZ = new VarDouble("Z", 0d);
        }
    }

    @Override
    public void run()
    {
        varDone.setValue(Boolean.FALSE);
        try
        {
            if (varRelative.getValue().booleanValue())
                StageMover.moveZRelative(inZ.getValue().doubleValue(), varWait.getValue().booleanValue());
            else
                StageMover.moveZAbsolute(inZ.getValue().doubleValue(), varWait.getValue().booleanValue());

            varDone.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(varRelative, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("z", inZ);
        inputMap.add("relative", varRelative);
        inputMap.add("wait", varWait);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("done", varDone);
    }
}
