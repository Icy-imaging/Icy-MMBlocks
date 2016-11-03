package plugins.stef.micromanager.block.stage;

import java.awt.geom.Point2D;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarDouble;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.tools.StageMover;

/**
 * Set the stage to given XY position (Micro-Manager).
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetStageXY extends AbstractMicroscopeBlock
{
    VarDouble inX;
    VarDouble inY;
    VarObject trigger = new VarObject("Trigger", null);
    VarBoolean wait = new VarBoolean("Wait Stage", true);
    VarBoolean relative = new VarBoolean("Relative", true);
    VarBoolean done = new VarBoolean("Done", false);

    public MicroscopeSetStageXY()
    {
        super();

        try
        {
            Point2D.Double pos = StageMover.getXY();
            inX = new VarDouble("X", pos.x);
            inY = new VarDouble("Y", pos.y);
        }
        catch (Throwable t)
        {
            inX = new VarDouble("X", 0d);
            inY = new VarDouble("Y", 0d);
        }
    }

    @Override
    public void run()
    {
        done.setValue(Boolean.FALSE);

        try
        {
            if (relative.getValue().booleanValue())
                StageMover.moveXYRelative(inX.getValue().doubleValue(), inY.getValue().doubleValue(), wait.getValue()
                        .booleanValue());
            else
                StageMover.moveXYAbsolute(inX.getValue().doubleValue(), inY.getValue().doubleValue(), wait.getValue()
                        .booleanValue());

            done.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(relative, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("x", inX);
        inputMap.add("y", inY);
        inputMap.add("relative", relative);
        inputMap.add("wait", wait);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("done", done);
    }
}
