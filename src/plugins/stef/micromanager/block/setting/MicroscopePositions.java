package plugins.stef.micromanager.block.setting;

import org.micromanager.MMStudio;
import org.micromanager.api.MultiStagePosition;
import org.micromanager.api.PositionList;

import plugins.adufour.blocks.tools.input.InputBlock;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.lang.VarMultiStagePosition;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Define a list of stage positions (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopePositions extends AbstractMicroscopeBlock implements InputBlock
{
    VarArray<MultiStagePosition> varPositions;

    public MicroscopePositions()
    {
        super();

        varPositions = new VarMultiStagePosition("Positions", null);
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("positions", varPositions);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        //
    }

    @Override
    public void run()
    {
        final MMStudio mstudio = MicroManager.getMMStudio();
        if (mstudio == null)
            throw new VarException(null,
                    "Cannot retrieve Micro-Manager core...\nBe sure you started Micro-Manager plugin before using Micro-Manager blocks !");

        try
        {
            final PositionList list = mstudio.getPositionList();

            if (list.getPositions() == null)
                throw new VarException(varPositions, "PositionList is empty. There should be at least one value.");

            varPositions.setValue(list.getPositions());
        }
        catch (Exception e)
        {
            throw new VarException(varPositions, e.getMessage());
        }
    }
}
