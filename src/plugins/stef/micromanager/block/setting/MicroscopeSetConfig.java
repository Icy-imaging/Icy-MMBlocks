package plugins.stef.micromanager.block.setting;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.lang.EzVarMMGroup;
import plugins.stef.micromanager.block.lang.EzVarMMPreset;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Set a preset for a specified group (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetConfig extends AbstractMicroscopeBlock
{
    VarObject trigger;
    EzVarMMGroup group;
    EzVarMMPreset preset;
    VarBoolean wait;
    VarBoolean done;

    public MicroscopeSetConfig()
    {
        super();

        trigger = new VarObject("Trigger", null);
        group = new EzVarMMGroup();
        preset = new EzVarMMPreset(group);
        wait = new VarBoolean("Wait", true);
        done = new VarBoolean("Done", false);
    }

    @Override
    public void run()
    {
        done.setValue(Boolean.FALSE);

        try
        {
            MicroManager.setConfigForGroup(group.getValue(), preset.getValue(), wait.getValue().booleanValue());
            done.setValue(Boolean.TRUE);
        }
        catch (Throwable t)
        {
            throw new VarException(group.getVariable(), t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("group", group.getVariable());
        inputMap.add("preset", preset.getVariable());
        inputMap.add("wait", wait);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        if (done != null)
            outputMap.add("done", done);
    }
}
