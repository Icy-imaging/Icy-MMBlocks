package plugins.stef.micromanager.block.stage;

import icy.type.point.Point3D;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarDouble;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.tools.StageMover;

/**
 * Return the stage XYZ position (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeGetStageXYZ extends AbstractMicroscopeBlock
{
    VarObject trigger = new VarObject("Trigger", null);
    VarBoolean wait = new VarBoolean("Wait stage", true);
    VarDouble outX = new VarDouble("X", 0d);
    VarDouble outY = new VarDouble("Y", 0d);
    VarDouble outZ = new VarDouble("Z", 0d);

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("wait", wait);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("x", outX);
        outputMap.add("y", outY);
        outputMap.add("z", outZ);
    }

    @Override
    public void run()
    {
        try
        {
            Point3D.Double pos = StageMover.getXYZ();
            outX.setValue(Double.valueOf(pos.x));
            outY.setValue(Double.valueOf(pos.y));
            outZ.setValue(Double.valueOf(pos.z));
        }
        catch (Throwable t)
        {
            throw new VarException(outX, t.getMessage());
        }
    }
}
