/**
 * 
 */
package plugins.stef.micromanager.block.lang;

import java.util.List;

import plugins.adufour.vars.gui.model.ValueSelectionModel;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarString;
import plugins.adufour.vars.util.VarListener;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * EzVar representing Micro-Manager config preset.
 * 
 * @author Stephane
 */
public class VarMMPreset extends VarString
{
    public VarMMPreset(String name, VarMMGroup groupVar)
    {
        super(name, "");

        // set values
        refreshPresets(groupVar.getValue());

        // listen group var change
        groupVar.addListener(new VarListener<String>()
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

    public VarMMPreset(VarMMGroup groupVar)
    {
        this("Preset", groupVar);
    }

    public void refreshPresets(String group)
    {
        final List<String> presets = MicroManager.getConfigs(group);

        if (presets.size() > 0)
            setDefaultEditorModel(new ValueSelectionModel<String>(presets.toArray(new String[presets.size()]),
                    presets.get(0), false));
    }
}
