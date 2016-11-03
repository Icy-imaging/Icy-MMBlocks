package plugins.stef.micromanager.block.stage;

import icy.system.thread.ThreadUtil;
import mmcorej.CMMCore;

import org.micromanager.api.MultiStagePosition;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.setting.MicroscopePositions;

/**
 * Set the stage to given position (Micro-Manager).<br>
 * This block should be used in conjunction with {@link MicroscopePositions} block and an indexer
 * so we can move to each position.
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetStagePosition extends AbstractMicroscopeBlock
{
    Var<MultiStagePosition> input;
    VarBoolean waitForStage;
    VarBoolean done;

    public MicroscopeSetStagePosition()
    {
        super();

        input = new Var<MultiStagePosition>("Position", MultiStagePosition.class);
        waitForStage = new VarBoolean("Wait stage", true);
        done = new VarBoolean("Done", false);
    }

    @Override
    public void run()
    {
        done.setValue(Boolean.FALSE);

        final CMMCore core = getMMCore(true);

        if (waitForStage.getValue().booleanValue())
        {
            try
            {
                MultiStagePosition.goToPosition(input.getValue(), core);
                done.setValue(Boolean.TRUE);
            }
            catch (Throwable t)
            {
                throw new VarException(input, t.getMessage());
            }
        }
        else
        {
            // do it in background
            ThreadUtil.bgRun(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        MultiStagePosition.goToPosition(input.getValue(), core);
                    }
                    catch (Throwable t)
                    {
                        throw new VarException(input, t.getMessage());
                    }
                }
            });

            done.setValue(Boolean.TRUE);
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        if (input != null)
            inputMap.add("position", input);
        if (waitForStage != null)
            inputMap.add("waitStage", waitForStage);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        if (done != null)
            outputMap.add("done", done);
    }
}
