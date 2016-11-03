/**
 * 
 */
package plugins.stef.micromanager.block.lang;

import plugins.adufour.ezplug.EzVarText;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * EzVar representing Micro-Manager config group.
 * 
 * @author Stephane
 */
public class EzVarMMGroup extends EzVarText
{
    public EzVarMMGroup(String name)
    {
        super(name, MicroManager.getConfigGroups().toArray(new String[0]), 0, Boolean.FALSE);
    }

    public EzVarMMGroup()
    {
        this("Group");
    }
}
