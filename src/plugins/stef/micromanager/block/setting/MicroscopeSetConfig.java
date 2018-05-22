package plugins.stef.micromanager.block.setting;

import java.util.List;

import plugins.adufour.blocks.lang.BlockDescriptor;
import plugins.adufour.blocks.lang.WorkFlow;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.protocols.Protocols;
import plugins.adufour.protocols.gui.MainFrame;
import plugins.adufour.protocols.gui.ProtocolPanel;
import plugins.adufour.protocols.gui.block.BlockPanel;
import plugins.adufour.protocols.gui.block.WorkFlowContainer;
import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.gui.swing.ComboBox;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.adufour.vars.util.VarListener;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.lang.VarMMGroup;
import plugins.stef.micromanager.block.lang.VarMMPreset;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Set a preset for a specified group (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetConfig extends AbstractMicroscopeBlock
{
    VarObject trigger;
    VarMMGroup group;
    VarMMPreset preset;
    VarBoolean wait;
    VarBoolean done;

    public MicroscopeSetConfig()
    {
        super();

        trigger = new VarObject("Trigger", null);
        group = new VarMMGroup();
        preset = new VarMMPreset(group);
        wait = new VarBoolean("Wait", true);
        done = new VarBoolean("Done", false);

        // listen group var to refresh the preset GUI which is disconnected from preset var
        group.addListener(new VarListener<String>()
        {
            @Override
            public void valueChanged(Var<String> source, String oldValue, String newValue)
            {
                refreshPresetsGUI(newValue);
            }

            @Override
            public void referenceChanged(Var<String> source, Var<? extends String> oldReference,
                    Var<? extends String> newReference)
            {
                //
            }
        });
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
            throw new VarException(group, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("group", group);
        inputMap.add("preset", preset);
        inputMap.add("wait", wait);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        if (done != null)
            outputMap.add("done", done);
    }

    @SuppressWarnings("unchecked")
    public void refreshPresetsGUI(String group)
    {
        final MainFrame protocols = Protocols.getInstance();
        if (protocols != null)
        {
            final ProtocolPanel protocolPanel = protocols.getActiveProtocol();
            if (protocolPanel != null)
            {
                final WorkFlowContainer wfc = protocolPanel.getWorkFlowContainer();
                final WorkFlow wf = protocolPanel.getWorkFlow();

                // get our block descriptor
                final BlockDescriptor bd = wf.getInputOwner(preset);
                if (bd != null)
                {
                    // get our block panel
                    final BlockPanel bp = wfc.getBlockPanel(bd);
                    if (bp != null)
                    {
                        // get GUI component for preset var
                        final VarEditor<?> editor = bp.getVarEditor(preset);
                        // should be a combo box
                        if (editor instanceof ComboBox<?>)
                        {
                            final ComboBox<String> combo = (ComboBox<String>) editor;
                            final List<String> presets = MicroManager.getConfigs(group);

                            if (presets.size() > 0)
                                combo.setDefaultValues(presets.toArray(new String[presets.size()]), 0, false);
                        }
                    }
                }
            }
        }
    }
}
