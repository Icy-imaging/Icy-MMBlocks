package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.tools.input.InputBlock;
import plugins.adufour.blocks.util.VarList;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.lang.EzVarMMGroup;
import plugins.stef.micromanager.block.lang.VarChannels;

/**
 * Define a list of channel (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeChannels extends AbstractMicroscopeBlock implements InputBlock
{
    EzVarMMGroup group;
    VarChannels channels;

    public MicroscopeChannels()
    {
        group = new EzVarMMGroup();
        channels = new VarChannels(group);
    }

    @Override
    public void run()
    {
        //
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        if (group != null)
            inputMap.add("groups", group.getVariable());
        if (channels != null)
            inputMap.add("channels", channels);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        //
    }
}
