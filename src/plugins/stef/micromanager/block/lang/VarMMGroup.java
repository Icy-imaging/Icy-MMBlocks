/**
 * 
 */
package plugins.stef.micromanager.block.lang;

import java.util.List;

import plugins.adufour.vars.gui.model.ValueSelectionModel;
import plugins.adufour.vars.lang.VarString;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * EzVar representing Micro-Manager config group.
 * 
 * @author Stephane
 */
public class VarMMGroup extends VarString
{
    public VarMMGroup(String name)
    {
        super(name, "");
       
        final List<String> groups = MicroManager.getConfigGroups();
        
        if (groups.size() > 0)
            setDefaultEditorModel(new ValueSelectionModel<String>(groups.toArray(new String[groups.size()]), groups.get(0), false));
    }

    public VarMMGroup()
    {
        this("Group");
    }
}
