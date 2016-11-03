/**
 * 
 */
package plugins.stef.micromanager.block.lang;

import java.util.List;

import plugins.adufour.ezplug.EzVar;
import plugins.adufour.ezplug.EzVarText;
import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.util.VarListener;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * EzVar representing Micro-Manager config preset.
 * 
 * @author Stephane
 */
public class EzVarMMPreset extends EzVarText
{
    public EzVarMMPreset(String name, EzVarMMGroup groupVar)
    {
        super(name, new String[] {""}, 0, Boolean.FALSE);

        // set values
        refreshPresets(groupVar);

        // listen group var change
        groupVar.getVariable().addListener(new VarListener<String>()
        {
            @Override
            public void valueChanged(Var<String> source, String oldValue, String newValue)
            {
                refreshPresets(newValue);
            }

            @Override
            public void referenceChanged(Var<String> source, Var<? extends String> oldReference,
                    Var<? extends String> newReference)
            {
                //
            }
        });
    }

    public EzVarMMPreset(EzVarMMGroup groupVar)
    {
        this("Preset", groupVar);
    }

    public void refreshPresets(String group)
    {
        final List<String> presets = MicroManager.getConfigs(group);

        setDefaultValues(presets.toArray(new String[0]), 0, false);
        VarEditor<String> comp = getVarEditor();
    }

    public void refreshPresets(EzVar<String> groupVar)
    {
        refreshPresets(groupVar.getValue());
    }
}
